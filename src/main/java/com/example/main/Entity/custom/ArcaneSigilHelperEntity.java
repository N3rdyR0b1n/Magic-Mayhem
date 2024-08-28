package com.example.main.Entity.custom;

import com.example.main.Entity.ModEntities;
import com.example.main.Spells.ModSpells;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

import static net.minecraft.world.explosion.Explosion.getExposure;

public class ArcaneSigilHelperEntity extends ThrownItemEntity {

    private int explosionticks;
    private boolean covered;
    private boolean exploded = false;

    private ParticleEffect effect;


    public ParticleEffect GetEffect() {
        return this.effect;
    }

    public ArcaneSigilHelperEntity(EntityType<? extends ArcaneSigilHelperEntity> chromaticOrbEntityEntityType,LivingEntity livingEntity, World world, ParticleEffect effect) {
        super(chromaticOrbEntityEntityType, livingEntity, world);
        this.setNoGravity(true);
        this.effect = effect;
    }

    public ArcaneSigilHelperEntity(EntityType<? extends ArcaneSigilHelperEntity> chromaticOrbEntityEntityType, World world, ParticleEffect effect) {
        super(chromaticOrbEntityEntityType, world);
        this.effect = effect;
    }

    public void tick() {
        if (this.getOwner() == null) {
            this.kill();
        }

        if (GetEffect() == null) {
            this.getWorld().addImportantParticle(ParticleTypes.FLAME, true, this.getX(), this.getY(), this.getZ(), 0, 0.5, 0);
            return;
        }
        if (this.age % 3 == 0) {
            float silly = this.age;
            silly /= 6.283f;
            this.getWorld().addImportantParticle(GetEffect(), true, (MathHelper.sin(silly) * getExplosionRange()) + this.getX(), this.getY() + 0.6f, (MathHelper.cos(silly) * getExplosionRange()) + this.getZ(), 0, 0.05, 0);

        }
        if (!exploded && (shouldExplode())) {
            exploded = true;
            explode();
            this.age = -26;
        }
        super.tick();
        if (this.age == -2 && !this.getWorld().isClient) {
            this.kill();
        }
    }


    @Override
    protected Item getDefaultItem() {
        return Items.AIR;
    }


    protected void onEntityHit(EntityHitResult entityHitResult) {
    }

    protected void onBlockHit(BlockHitResult result) {
    }

    public List<LivingEntity> GetNearbyEntities (float range, World world, LivingEntity source) {
        List<LivingEntity> Entities = world.getNonSpectatingEntities(LivingEntity.class, this.getBoundingBox().expand(range));
        List<LivingEntity> ReturnList = new ArrayList<>();
        for (LivingEntity entity : Entities) {
            if (entity == source || ((this.distanceTo(entity) / range)*(this.distanceTo(entity) / range)>5.0f)) {
                continue;
            }
            boolean bl = false;
            for (int i = 0; i < 2; ++i) {
                Vec3d vec3d2 = new Vec3d(this.getX(), this.getBodyY(0.5 * (double) i), this.getZ());
                BlockHitResult hitResult = world.raycast(new RaycastContext(this.getPos(), vec3d2, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, source));
                if (((HitResult) hitResult).getType() != HitResult.Type.MISS) continue;
                bl = true;
                break;
            }
            if (!bl) continue;
            ReturnList.add(entity);
        }
        return ReturnList;
    }
    public float getExplosionRange() {
        return 1;
    }
    private boolean shouldExplode(){
        return shouldExplode(getExplosionRange());
    }
    private boolean shouldExplode(float range) {

        List<LivingEntity> Entities = this.getWorld().getNonSpectatingEntities(LivingEntity.class, this.getBoundingBox().expand(range));
        for (LivingEntity entity : Entities) {
            if (this.distanceTo(entity) <= range) {
                return true;
            }
        }
        return false;
    }
    public void weakhit(LivingEntity target) {

    }
    public void stronghit(LivingEntity target) {

    }
    public void explode(){

    }
    public void explode(float areamultiplier, PlayerEntity player, float knockbackmultiplier, float weak, float strong) {
        areamultiplier /=5;
        Entity shooter = this;
        Vec3d vec3d = shooter.getPos();
        List<LivingEntity> list = this.getWorld().getNonSpectatingEntities(LivingEntity.class, shooter.getBoundingBox().expand(5.0 * areamultiplier));
        for (LivingEntity livingEntity : list) {
            if (((shooter.distanceTo(livingEntity) / areamultiplier)*(shooter.distanceTo(livingEntity) / areamultiplier)) > 25.0)
                continue;
            boolean bl = false;
            for (int i = 0; i < 2; ++i) {
                Vec3d vec3d2 = new Vec3d(livingEntity.getX(), livingEntity.getBodyY(0.5 * (double) i), livingEntity.getZ());
                BlockHitResult hitResult = this.getWorld().raycast(new RaycastContext(vec3d, vec3d2, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, shooter));
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
                if (bl) {
                    ModSpells.ARCANE_SIGIL.onHit(player, this.getWorld(),livingEntity, weak);
                    weakhit(livingEntity);
                    if (livingEntity.isDead()) {
                        ModSpells.ARCANE_SIGIL.onKill(player, this.getWorld(), livingEntity);
                    }
                }
                else {
                    ModSpells.ARCANE_SIGIL.onHit(player, this.getWorld(),livingEntity, strong);
                    stronghit(livingEntity);
                    if (livingEntity.isDead()) {
                        ModSpells.ARCANE_SIGIL.onKill(player, this.getWorld(), livingEntity);
                    }
                }
                livingEntity.addVelocity(
                        x * ac * knockbackmultiplier,
                        y * ac * knockbackmultiplier,
                        z * ac * knockbackmultiplier
                );
                livingEntity.velocityModified = true;
            }
        }
    }
}
