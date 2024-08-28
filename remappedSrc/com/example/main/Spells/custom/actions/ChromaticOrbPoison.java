package com.example.main.Spells.custom.actions;

import com.example.main.Entity.ModEntities;
import com.example.main.Entity.custom.ChromaticOrbEntity;
import com.example.main.Item.ModItems;
import com.example.main.Spells.ModSpells;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.stat.Stats;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;

import java.util.List;

public class ChromaticOrbPoison extends ActionPerformable {
    @Override
    public void Perform(ItemStack stack, PlayerEntity player, World world) {
        if (!world.isClient) {
            ChromaticOrbEntity orb = new ChromaticOrbEntity(ModEntities.CHROMATIC_ORB, player, world, stack) {
                @Override
                protected void onEntityHit(EntityHitResult entityHitResult) {
                    if (this.getOwner() instanceof  PlayerEntity owner) {
                        if (entityHitResult.getEntity() instanceof LivingEntity target) {
                            ModSpells.CHROMATIC_ORB.onHit(owner, this.method_48926(), target, 1);
                            target.damage(target.getDamageSources().playerAttack(owner), 0.01f);
                            target.damage(target.getDamageSources().cramming(), 7.5f);
                            target.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 300, 10));
                            if (target.isDead()) {
                                ModSpells.CHROMATIC_ORB.onKill(owner, this.method_48926(), target);
                            }
                        }

                        List<LivingEntity> entityList = this.GetNearbyEntities(
                                2.5f, this.method_48926(), owner);
                        for (LivingEntity target : entityList) {
                            ModSpells.CHROMATIC_ORB.onHit(owner, this.method_48926(), target, 0.5f);
                            target.damage(target.getDamageSources().playerAttack(owner), 0.01f);
                            target.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 150, 10));
                            if (target.isDead()) {
                                ModSpells.CHROMATIC_ORB.onKill(owner, this.method_48926(), target);
                            }
                        }
                    }
                    this.HasHit();
                }

                @Override
                protected void onBlockHit(BlockHitResult result) {
                    if (this.getOwner() instanceof  PlayerEntity owner) {
                        List<LivingEntity> entityList = this.GetNearbyEntities(
                                2.5f, this.method_48926(), owner);
                        for (LivingEntity target : entityList) {
                            ModSpells.CHROMATIC_ORB.onHit(owner, this.method_48926(), target, 0.5f);
                            target.damage(target.getDamageSources().playerAttack(owner), 0.01f);
                            target.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 150, 10));ModSpells.CHROMATIC_ORB.onHit(owner, world, target, 1);
                            if (target.isDead()) {
                                ModSpells.CHROMATIC_ORB.onKill(owner, this.method_48926(), target);
                            }
                        }
                    }
                    this.HasHit();
                }
            };
            orb.setItem(new ItemStack(ModItems.CHROMATIC_ORB_POISON));
            orb.setVelocity(player, player.getPitch(), player.getYaw(), 0.0f, 2.5f, 0.0f);
            world.spawnEntity(orb);
        }
        player.incrementStat(Stats.USED.getOrCreateStat(stack.getItem()));
    }


}
