package com.example.main.Spells.custom;

import com.example.main.SpellUtil.DamageTypes.ModDamageTypes;
import com.example.main.SpellUtil.GenericSpellAbilities;
import com.example.main.SpellUtil.SpellSchool;
import com.example.main.SpellUtil.Spells.Spell;
import com.example.main.Spells.ModSpells;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.WitherSkullEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

import java.util.List;

public class WitherSkullSpell extends Spell {
    public WitherSkullSpell(int manaCost, SpellSchool school, int chargeTime, String name, int cooldown, Identifier texture, int minlevel, int maxlevel, int upcastCost) {
        super(manaCost, school, chargeTime, name, cooldown, texture, minlevel, maxlevel, upcastCost);
    }


    @Override
    public void castSpell(PlayerEntity player, World world, ItemStack stack) {
        super.castSpell(player,world,stack);
        if (world.isClient()) {
            return;
        }

            Random random = world.getRandom();
            world.playSound(null, player.getBlockPos(), SoundEvents.ENTITY_WITHER_SHOOT, SoundCategory.PLAYERS, 0.75f, (random.nextFloat() - random.nextFloat()) * 0.2f + 1.0f);

        Vec3d direction = player.getRotationVec(0.5F).multiply(128);
        WitherSkullEntity skull = new WitherSkullEntity(world, player, direction.x, direction.y, direction.z) {
            private int level = Level();
            @Override
            protected void onEntityHit(EntityHitResult entityHitResult) {
               this.setPosition(entityHitResult.getPos());
               explode();
            }

            @Override
            protected void onCollision(HitResult hitResult) {
                this.setPosition(hitResult.getPos());
                explode();
            }
            private void explode() {
                List<LivingEntity> entityList = GenericSpellAbilities.GetNearbyEntities(2f, getWorld(), this, true, true, LivingEntity.class);
                Vec3d vec3d = this.getPos();
                World world = this.getWorld();
                world.playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.PLAYERS, 1f, (1.0f + (world.random.nextFloat() - world.random.nextFloat()) * 0.2f) * 0.7f);
                if (world instanceof ServerWorld serverWorld) {
                        serverWorld.spawnParticles(ParticleTypes.EXPLOSION, this.getX(), this.getY(), this.getZ(), 1, 1, 1, 1, 0);
                }
                PlayerEntity owner = (PlayerEntity) this.getOwner();
                DamageSource source = ModDamageTypes.of(getWorld(), ModDamageTypes.MAGIC_TICK, owner);
                int damage = 14;
                int hdamage = damage/2;
                int duration = 100 + 40 * level;
                int hduration = duration/2;
                Spell spell = ModSpells.WITHER_SKULL;
                for (LivingEntity entity : entityList) {
                    if (entity.isDead()) {
                        continue;
                    }
                    boolean bl = false;
                    for (int i = 0; i < 2; ++i) {
                        Vec3d vec3d2 = new Vec3d(entity.getX(), entity.getBodyY(0.5 * (double) i), entity.getZ());
                        BlockHitResult hitResult = world.raycast(new RaycastContext(vec3d, vec3d2, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, this));
                        if (((HitResult) hitResult).getType() != HitResult.Type.MISS) continue;
                        bl = true;
                        break;
                    }
                    if (bl) {
                        entity.damage(source, damage);
                        entity.addStatusEffect(new StatusEffectInstance(StatusEffects.WITHER, duration, level));
                        spell.onHit(owner, this.getWorld(), entity, 1);
                        if (entity.isDead()) {
                            spell.onKill(owner, this.getWorld(), entity);
                        }
                    }
                    else {
                        entity.damage(source, hdamage);
                        entity.addStatusEffect(new StatusEffectInstance(StatusEffects.WITHER, hduration, level));
                        spell.onHit(owner, this.getWorld(), entity, 0.5f);
                        if (entity.isDead()) {
                            spell.onKill(owner, this.getWorld(), entity);
                        }
                    }
                }
                this.discard();
            }
        };
        skull.setPosition(player.getEyePos());
        world.spawnEntity(skull);
    }
}
