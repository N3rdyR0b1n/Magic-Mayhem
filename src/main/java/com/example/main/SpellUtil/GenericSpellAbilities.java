package com.example.main.SpellUtil;

import com.example.main.SpellUtil.DamageTypes.ModDamageTypes;
import com.example.main.SpellUtil.Spells.Spell;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.network.packet.s2c.play.ParticleS2CPacket;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.LightType;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static net.minecraft.world.explosion.Explosion.getExposure;

public class GenericSpellAbilities {


    public static ItemStack GetOppositeHandStack(PlayerEntity player, ItemStack stack) {
        if (player.getMainHandStack() == stack) {
            return player.getOffHandStack();
        }
        return player.getMainHandStack();
    }

    public static void removeAttributeModifier(ItemStack stack, String name) {
        if (stack.hasNbt() && stack.getNbt().contains("AttributeModifiers",  NbtElement.LIST_TYPE)) {
            NbtList nbtList = stack.getNbt().getList("AttributeModifiers", NbtElement.COMPOUND_TYPE);
            for (int i = 0; i < nbtList.size(); i++) {
                NbtCompound compound = (NbtCompound) nbtList.get(i);
                String comparison = compound.getString("Name");
                if (Objects.equals(comparison, name)) {
                    nbtList.remove(i);
                    return;
                }
            }
            stack.addEnchantment(Enchantments.EFFICIENCY, 1);
        }
    }

    public static HitValues HitscanSelect(World world, PlayerEntity player , float range, boolean ignoreblocks) {
        Vec3d start = player.getEyePos();
        Vec3d end = player.getEyePos().add(player.getRotationVec(0.5F).multiply(range));
        Vec3d direction = end.subtract(start).normalize();
        Box box = new Box(start.subtract(-1f, -1f, -1f), start.add(1f, 1f, 1f)).stretch(direction.multiply(range)).expand(1.0, 1.0, 1.0);
        BlockHitResult blockhit = world.raycast(new RaycastContext(start, end, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.ANY, player));
        if (blockhit.getType() != HitResult.Type.MISS && !ignoreblocks) {
            end = blockhit.getPos();
        }
        EntityHitResult hitResult = ProjectileUtil.raycast(player, start, end, box, (entity) -> (entity.isAttackable()), range * range);
        Entity entity = null;
        boolean bool = false;
        if (hitResult != null) {
            end = hitResult.getPos();
            entity = hitResult.getEntity();
            bool = true;
        }
        return new HitValues(end, bool, entity);

    }

    public static HitValues HitscanAttack(PlayerEntity player, World world, int piercinglevel, float range, float damage, boolean ignoreInvincibility, Spell spell, ItemStack stack) {
        Vec3d start = player.getEyePos();
        Vec3d end = player.getEyePos().add(player.getRotationVec(0.5F).multiply(range));
        HitResult hitResult = player.getWorld().raycast(new RaycastContext(start, end, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, player));
        if (hitResult.getType() != HitResult.Type.MISS) {
            end = hitResult.getPos();
        }
        List<Entity> targets = new ArrayList<>();
        Vec3d direction = end.subtract(start).normalize();
        BlockHitResult blockhit = world.raycast(new RaycastContext(start, end, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.ANY, player));
        Box box = new Box(start.subtract(-1f, -1f, -1f), start.add(1f, 1f, 1f)).stretch(direction.multiply(range)).expand(1.0, 1.0, 1.0);
        EntityHitResult lastEntityhit = null;
        boolean piercingqualifier = true;
        while (piercingqualifier) {
            EntityHitResult entityhit = ProjectileUtil.raycast(player, start, end, box, (entity) -> (!targets.contains(entity)) && (entity.isAttackable()), range * range);
            if (entityhit == null)
                break;
            piercingqualifier = entityhit.getType() != HitResult.Type.MISS && piercinglevel > 0;
            if (entityhit.getType() != HitResult.Type.MISS)
                lastEntityhit = entityhit;
            if (piercingqualifier) {
                piercinglevel--;
                start = entityhit.getPos();
                if (piercinglevel == 0)
                    end = entityhit.getPos();
                targets.add(entityhit.getEntity());
            }
        }
        for (int i = 0; i < targets.size(); i++) {
            Entity entity = targets.get(i);
            if (entity instanceof LivingEntity) {
                entity.damage(entity.getDamageSources().playerAttack(player), 0.01f);
                entity.damage(ModDamageTypes.of(world, ModDamageTypes.MAGIC_TICK), damage);
                spell.onHit(player, world, entity, 1f);
                if (((LivingEntity) entity).isDead()) {
                    spell.onKill(player, world, entity);
                }
            }
        }
        return new HitValues(end, !targets.isEmpty(), targets);
    }

    public static void addParticles(World world, Vec3d start, Vec3d end, ParticleEffect type, float spead) {
        if (world instanceof ServerWorld serverWorld) {
            List<ServerPlayerEntity> players = serverWorld.getPlayers();
            ExecutorService ex = Executors.newFixedThreadPool(3);
            Future[] tasks = new Future[players.size()];
            for (int i = 0; i < players.size(); i++) {
                ServerPlayerEntity player = players.get(i);
                Vec3d newstart = new Vec3d(start.x, start.y, start.z);
                Vec3d newend = new Vec3d(end.x, end.y, end.z);
                float spread = spead;
                tasks[i] = ex.submit(() -> SendPacket(player, newstart, newend, type, spread));
            }
            boolean works = false;
            while (!works) {
                works = true;
                for (int i = 0; i < tasks.length; i++) {
                    if (!tasks[i].isDone()) {
                        works = false;
                        break;
                    }
                }
            }
        }
    }
    public static void SendPacket(ServerPlayerEntity player, Vec3d start, Vec3d end, ParticleEffect effect, float spread) {
        Vec3d delta = (end.subtract(start));
        Vec3d direction = delta.normalize();
        double vectorsize = MathHelper.floor(delta.length()) / spread;
        for (int i = 1; i < vectorsize; i++) {
            Vec3d particlelocation = start.add(direction.multiply(i * spread));
            ParticleS2CPacket particleS2CPacket = new ParticleS2CPacket(effect, true, particlelocation.x, particlelocation.y, particlelocation.z, 0,0,0,0,0);
            player.networkHandler.sendPacket(particleS2CPacket);
        }
    }
    public static void ParticleNoS2C(World world, Vec3d start, Vec3d end, ParticleEffect effect, float spread) {
        Vec3d delta = (end.subtract(start));
        Vec3d direction = delta.normalize();
        double vectorsize = MathHelper.floor(delta.length()) / spread;
        for (int i = 1; i < vectorsize; i++) {
            Vec3d particlelocation = start.add(direction.multiply(i * spread));
            world.addImportantParticle(effect, true, particlelocation.x, particlelocation.y, particlelocation.z, 0, 0,0);
        }
    }
    public static <T extends Entity> List<T> GetNearbyEntities (float range, World world, Entity source, boolean round, boolean ignoreblocks, Class<T> type) {
        List<T> Entities = world.getNonSpectatingEntities(type, source.getBoundingBox().expand(range));
        List<T> ReturnList = new ArrayList<>();
        Vec3d pos = source.getPos();
        for (T entity : Entities) {
            if (entity == source || (round && ((source.distanceTo(entity) / range)>1.000f))) {
                continue;
            }
            if (!ignoreblocks) {
                boolean bl = false;
                for (int i = 0; i < 2; ++i) {
                    Vec3d vec3d2 = new Vec3d(source.getX(), source.getBodyY(0.5 * (double) i), source.getZ());
                    BlockHitResult hitResult = world.raycast(new RaycastContext(pos, vec3d2, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, source));
                    if (((HitResult) hitResult).getType() != HitResult.Type.MISS) continue;
                    bl = true;
                    break;
                }
                if (!bl) continue;
            }
            ReturnList.add(entity);
        }

        return ReturnList;
    }

    public static void explode(float damagemultiplier, float knockbackmultiplier, float areamultiplier, World world, Entity shooter) {
        Vec3d vec3d = shooter.getPos();
        List<LivingEntity> list = world.getNonSpectatingEntities(LivingEntity.class, shooter.getBoundingBox().expand(5.0 * areamultiplier));
        for (LivingEntity livingEntity : list) {
            if (livingEntity == shooter || ((shooter.distanceTo(livingEntity) / areamultiplier)*(shooter.distanceTo(livingEntity) / areamultiplier)) > 25.0)
                continue;
            boolean bl = false;
            for (int i = 0; i < 2; ++i) {
                Vec3d vec3d2 = new Vec3d(livingEntity.getX(), livingEntity.getBodyY(0.5 * (double) i), livingEntity.getZ());
                BlockHitResult hitResult = world.raycast(new RaycastContext(vec3d, vec3d2, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, shooter));
                if (((HitResult) hitResult).getType() != HitResult.Type.MISS) continue;
                bl = true;
                break;
            }
            if (!bl) continue;
            double x = livingEntity.getX() - shooter.getX();
            double y = (livingEntity.getEyeY() - shooter.getY());
            double z = livingEntity.getZ() - shooter.getZ();
            double aa = Math.sqrt(x * x + y * y + z * z);
            if (aa != 0.0) {
                x /= aa;
                y /= aa;
                z /= aa;
                double area = (5 * areamultiplier);
                double w = Math.sqrt(livingEntity.squaredDistanceTo(vec3d)) / (area * area);
                double ab = getExposure(vec3d, livingEntity);
                double ac = (1.0 - w) * ab;
                float g = damagemultiplier * ((float) Math.sqrt((25.0 - (shooter.distanceTo(livingEntity) / areamultiplier) * (shooter.distanceTo(livingEntity) / areamultiplier))));
                livingEntity.damage(livingEntity.getDamageSources().playerAttack((PlayerEntity) shooter), g);
                livingEntity.addVelocity(
                        x * ac * knockbackmultiplier,
                        y * ac * knockbackmultiplier,
                        z * ac * knockbackmultiplier
                );
            }
        }
    }

    public static List<LivingEntity> explodeFlatAoe (float damage, float knockbackmultiplier, float areamultiplier, World world, Entity shooter, PlayerEntity player, Spell spell, float hitscaling, float weakerhitscaling, DamageSource source) {
        Vec3d vec3d = shooter.getPos();
        List<LivingEntity> list = world.getNonSpectatingEntities(LivingEntity.class, shooter.getBoundingBox().expand(5.0 * areamultiplier));
        List<LivingEntity> returnlist = new ArrayList<>();
        for (LivingEntity livingEntity : list) {
            if (livingEntity.isDead() || livingEntity == player || shooter.distanceTo(livingEntity) > 5.0 * areamultiplier) {
                continue;
            }
            returnlist.add(livingEntity);
            boolean bl = false;
            for (int i = 0; i < 2; ++i) {
                Vec3d vec3d2 = new Vec3d(livingEntity.getX(), livingEntity.getBodyY(0.5 * (double) i), livingEntity.getZ());
                BlockHitResult hitResult = world.raycast(new RaycastContext(vec3d, vec3d2, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, shooter));
                if (((HitResult) hitResult).getType() != HitResult.Type.MISS) continue;
                bl = true;
                break;
            }
            double x = livingEntity.getX() - shooter.getX();
            double y = (livingEntity.getEyeY() - shooter.getY());
            double z = livingEntity.getZ() - shooter.getZ();
            double aa = Math.sqrt(x * x + y * y + z * z);
            if (aa != 0.0) {
                x /= aa;
                y /= aa;
                z /= aa;
                double area = (5 * areamultiplier);
                double w = Math.sqrt(livingEntity.squaredDistanceTo(vec3d)) / (area * area);
                double ab = getExposure(vec3d, livingEntity);
                double ac = (1.0 - w) * ab;
                livingEntity.damage(livingEntity.getDamageSources().playerAttack(player), 0.0001f);
                if (bl) {
                    spell.onHit(player, world,livingEntity, hitscaling);
                    livingEntity.damage(source, damage);
                    if (livingEntity.isDead()) {
                        spell.onKill(player, world, livingEntity);
                    }
                }
                else {
                    spell.onHit(player, world,livingEntity, weakerhitscaling);
                    livingEntity.damage(source, damage/2);
                    if (livingEntity.isDead()) {
                        spell.onKill(player, world, livingEntity);
                    }
                }
                livingEntity.addVelocity(
                        x * ac * knockbackmultiplier,
                        y * ac * knockbackmultiplier,
                        z * ac * knockbackmultiplier
                );
            }
        }
        return returnlist;
    }
    public static List<LivingEntity> explodeFlatAoe (float damage, float knockbackmultiplier, float areamultiplier, World world, Entity shooter, PlayerEntity player, Spell spell, float hitscaling, float weakerhitscaling) {
        return explodeFlatAoe(damage, knockbackmultiplier, areamultiplier, world, shooter, player, spell, hitscaling, weakerhitscaling, ModDamageTypes.of(world, ModDamageTypes.MAGIC_TICK));
    }
    public static boolean InvisibleArmor(Entity entity, int invis) {
        if (entity instanceof PlayerEntity player) {
            if (invis > 0) {
                World world = player.getWorld();
                if (world.getLightLevel(LightType.SKY, player.getBlockPos()) < invis || ((!world.isDay() || world.getTimeOfDay() % 24000 >= 12500 )&& world.getLightLevel(LightType.BLOCK, player.getBlockPos()) < invis)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static float getMaxAbsorptionLevel(PlayerEntity player) {
        if (player.hasStatusEffect(StatusEffects.HEALTH_BOOST)) {
            return player.getMaxHealth() - player.getStatusEffect(StatusEffects.HEALTH_BOOST).getAmplifier() * 4 + 4;
        }
        return player.getMaxHealth();
    }
    public static void ApplyAbsorption(PlayerEntity player, ItemStack stack, float hp) {
        float value = player.getAbsorptionAmount() + hp;
        float maxabsorption = getMaxAbsorptionLevel(player);
        if (value > maxabsorption) {
            value = maxabsorption;
        }
        if (player.getAbsorptionAmount() < maxabsorption) {
            player.setAbsorptionAmount(value);
        }
    }

}
