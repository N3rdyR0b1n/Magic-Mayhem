package com.example.main.Spells.custom.actions;

import com.example.main.Entity.ModEntities;
import com.example.main.Entity.custom.ChromaticOrbEntity;
import com.example.main.Item.ModItems;
import com.example.main.Spells.ModSpells;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.stat.Stats;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;

import java.util.List;

public class ChromaticOrbFire extends ActionPerformable {
    @Override
    public void Perform(ItemStack stack, PlayerEntity player, World world) {
        if (!world.isClient) {
            ChromaticOrbEntity orb = new ChromaticOrbEntity(ModEntities.CHROMATIC_ORB, player, world, stack) {
                @Override
                protected void onEntityHit(EntityHitResult entityHitResult) {
                    if (this.getOwner() instanceof PlayerEntity owner) {
                        if (entityHitResult.getEntity() instanceof LivingEntity target) {
                            ModSpells.CHROMATIC_ORB.onHit(owner, this.getWorld(), target, 1);

                            target.damage(target.getDamageSources().playerAttack(owner), 0.01f);
                            target.damage(target.getDamageSources().onFire(), 10f);
                            if (!target.isWet()) {
                                target.setOnFireFor(25 + target.getFireTicks());
                            }
                            if (target.isDead()) {
                                ModSpells.CHROMATIC_ORB.onKill(owner, this.getWorld(), target);
                            }
                        }
                        List<LivingEntity> entityList = this.GetNearbyEntities(
                                2.5f, this.getWorld(), owner);
                        for (LivingEntity target : entityList) {
                            ModSpells.CHROMATIC_ORB.onHit(owner, this.getWorld(), target, 0.5f);
                            target.damage(target.getDamageSources().playerAttack(owner), 0.01f);
                            if (!target.isWet()) {
                                target.setOnFireFor(20 + target.getFireTicks());
                            }
                            if (target.isDead()) {
                                ModSpells.CHROMATIC_ORB.onKill(owner, this.getWorld(), target);
                            }
                        }
                    }
                    this.HasHit();
                }
                @Override
                protected void onBlockHit(BlockHitResult result) {
                    if (this.getOwner() instanceof  PlayerEntity owner) {
                        List<LivingEntity> entityList = this.GetNearbyEntities(
                                1.5f, this.getWorld(), owner);
                        for (LivingEntity target : entityList) {
                            ModSpells.CHROMATIC_ORB.onHit(owner, this.getWorld(), target, 0.5f);
                            target.damage(target.getDamageSources().playerAttack(owner), 0.01f);
                            if (!target.isWet()) {
                                target.setOnFireFor(20 + target.getFireTicks());
                            }
                            if (target.isDead()) {
                                ModSpells.CHROMATIC_ORB.onKill(owner, this.getWorld(), target);
                            }
                        }
                    }
                    this.HasHit();
                }
            };
            orb.setItem(new ItemStack(ModItems.CHROMATIC_ORB_FIRE));
            orb.setVelocity(player, player.getPitch(), player.getYaw(), 0.0f, 2.5f, 0.0f);
            world.spawnEntity(orb);
        }
        player.incrementStat(Stats.USED.getOrCreateStat(stack.getItem()));
    }
}
