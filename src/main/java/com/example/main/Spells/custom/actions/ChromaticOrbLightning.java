package com.example.main.Spells.custom.actions;

import com.example.main.Entity.ModEntities;
import com.example.main.Entity.custom.ChromaticOrbEntity;
import com.example.main.Item.ModItems;
import com.example.main.Spells.ModSpells;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.stat.Stats;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class ChromaticOrbLightning extends ActionPerformable {
    @Override
    public void Perform(ItemStack stack, PlayerEntity player, World world) {
        if (!world.isClient) {
            ChromaticOrbEntity orb = new ChromaticOrbEntity(ModEntities.CHROMATIC_ORB, player, world, stack) {
                @Override
                protected void onEntityHit(EntityHitResult entityHitResult) {
                    if (this.getOwner() instanceof  PlayerEntity owner) {
                        Entity entity = entityHitResult.getEntity();
                        if (entity instanceof LivingEntity target) {
                            ModSpells.CHROMATIC_ORB.onHit(owner, this.getWorld(), target, 1f);
                            target.damage(target.getDamageSources().playerAttack(owner), 0.01f);
                            if (target.isWet()) {
                                target.damage(target.getDamageSources().lightningBolt(), 15f);
                            }
                            else {
                                target.damage(target.getDamageSources().lightningBolt(), 7.5f);
                            }
                            if (target.isDead()) {
                                ModSpells.CHROMATIC_ORB.onKill(owner, this.getWorld(), target);
                            }
                        }
                        List<LivingEntity> entities = this.GetNearbyEntities(5f, this.getWorld(), owner);
                        Random random = new Random();
                        for (LivingEntity target : entities) {
                            Vec3d direction = target.getPos().subtract(this.getPos());
                            direction.normalize();
                            this.getWorld().addImportantParticle(
                                    ParticleTypes.ELECTRIC_SPARK, target.getX(), target.getY(), target.getZ(),
                                    direction.x, direction.y, direction.z
                            );
                            for (int i = 0; i < 10; i++) {
                                this.getWorld().addImportantParticle(
                                        ParticleTypes.ELECTRIC_SPARK, target.getX(), target.getY(), target.getZ(),
                                        random.nextFloat(-0.25f, 0.25f),
                                        random.nextFloat(-0.25f, 0.25f),
                                        random.nextFloat(-0.25f, 0.25f)
                                );
                            }
                            ModSpells.CHROMATIC_ORB.onHit(owner, this.getWorld(), target, 0.5f);
                            target.damage(target.getDamageSources().playerAttack(owner), 0.01f);
                            if (target.isWet()) {
                                target.damage(target.getDamageSources().lightningBolt(), 15f);
                            }
                            else {
                                target.damage(target.getDamageSources().lightningBolt(), 7.5f);
                            }
                            if (target.isDead()) {
                                ModSpells.CHROMATIC_ORB.onKill(owner, this.getWorld(), target);
                            }
                        }
                        LightningEntity lightning = new LightningEntity(EntityType.LIGHTNING_BOLT, this.getWorld());
                        lightning.setPosition(this.getPos());
                        this.getWorld().spawnEntity(lightning);
                    }
                    this.HasHit();
                }

                @Override
                protected void onBlockHit(BlockHitResult hitResult) {

                    if (this.getOwner() instanceof  PlayerEntity owner) {
                        List<LivingEntity> entities = this.GetNearbyEntities(5f, this.getWorld(), owner);
                        Random random = new Random();
                        for (LivingEntity target : entities) {
                            Vec3d direction = target.getPos().subtract(this.getPos());
                            direction.normalize();
                            this.getWorld().addImportantParticle(
                                    ParticleTypes.ELECTRIC_SPARK, target.getX(), target.getY(), target.getZ(),
                                    direction.x, direction.y, direction.z
                            );
                            for (int i = 0; i < 10; i++) {
                                this.getWorld().addImportantParticle(
                                        ParticleTypes.ELECTRIC_SPARK, target.getX(), target.getY(), target.getZ(),
                                        random.nextFloat(-0.25f, 0.25f),
                                        random.nextFloat(-0.25f, 0.25f),
                                        random.nextFloat(-0.25f, 0.25f)
                                );
                            }

                            ModSpells.CHROMATIC_ORB.onHit(owner, this.getWorld(), target, 0.5f);
                            target.damage(target.getDamageSources().playerAttack(owner), 0.01f);
                            if (target.isWet()) {
                                target.damage(target.getDamageSources().lightningBolt(), 15f);
                            }
                            else {
                                target.damage(target.getDamageSources().lightningBolt(), 7.5f);
                            }
                            if (target.isDead()) {
                                ModSpells.CHROMATIC_ORB.onKill(owner, this.getWorld(), target);
                            }
                            target.setVelocity(0, 0, 0);
                        }
                        LightningEntity lightning = new LightningEntity(EntityType.LIGHTNING_BOLT, this.getWorld());
                        lightning.setPosition(this.getPos());
                        this.getWorld().spawnEntity(lightning);
                    }
                    this.HasHit();
                }
            };
            orb.setItem(new ItemStack(ModItems.CHROMATIC_ORB_LIGHTNING));
            orb.setVelocity(player, player.getPitch(), player.getYaw(), 0.0f, 2.5f, 0.0f);
            world.spawnEntity(orb);
        }
        player.incrementStat(Stats.USED.getOrCreateStat(stack.getItem()));
    }
}
