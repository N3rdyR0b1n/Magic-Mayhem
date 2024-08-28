package com.example.main.Spells.custom;

import com.example.main.SpellUtil.DamageTypes.ModDamageTypes;
import com.example.main.SpellUtil.GenericSpellAbilities;
import com.example.main.SpellUtil.SpellSchool;
import com.example.main.SpellUtil.Spells.Spell;
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
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
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
        Vec3d direction = player.getRotationVec(0.5F).multiply(128);
        WitherSkullEntity skull = new WitherSkullEntity(world, player, direction.x, direction.y, direction.z) {
            @Override
            protected void onEntityHit(EntityHitResult entityHitResult) {
               explode();
            }

            @Override
            protected void onCollision(HitResult hitResult) {
                explode();
            }
            private void explode() {

                List<LivingEntity> entityList = GenericSpellAbilities.GetNearbyEntities(1.5f, getWorld(), this, true, true, LivingEntity.class);
                Vec3d vec3d = this.getPos();
                World world = this.getWorld();
                if (world instanceof ServerWorld serverWorld) {
                        serverWorld.spawnParticles(ParticleTypes.EXPLOSION_EMITTER, this.getX(), this.getY(), this.getZ(), 5, 1, 1, 1, 0);

                }
                DamageSource source = ModDamageTypes.of(getWorld(), ModDamageTypes.MAGIC_TICK);
                DamageSource playersource = this.getOwner().getDamageSources().playerAttack((PlayerEntity) this.getOwner());
                for (LivingEntity entity : entityList) {
                    boolean bl = false;
                    for (int i = 0; i < 2; ++i) {
                        Vec3d vec3d2 = new Vec3d(entity.getX(), entity.getBodyY(0.5 * (double) i), entity.getZ());
                        BlockHitResult hitResult = world.raycast(new RaycastContext(vec3d, vec3d2, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, this));
                        if (((HitResult) hitResult).getType() != HitResult.Type.MISS) continue;
                        bl = true;
                        break;
                    }
                    entity.damage(playersource, 0.000001f);
                    if (bl) {
                        entity.damage(source, 15);
                        entity.addStatusEffect(new StatusEffectInstance(StatusEffects.WITHER, 100, 1));
                    }
                    else {
                        entity.damage(source, 10);
                        entity.addStatusEffect(new StatusEffectInstance(StatusEffects.WITHER, 50, 1));
                    }
                }
                this.discard();
            }
        };
        skull.setPosition(player.getEyePos());
        world.spawnEntity(skull);
    }
}
