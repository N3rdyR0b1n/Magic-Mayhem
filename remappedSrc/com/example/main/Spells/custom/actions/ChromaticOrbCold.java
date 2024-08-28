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

public class ChromaticOrbCold extends ActionPerformable {
    @Override
    public void Perform(ItemStack stack, PlayerEntity player, World world) {
        if (!world.isClient) {
            ChromaticOrbEntity orb = new ChromaticOrbEntity(ModEntities.CHROMATIC_ORB, player, world, stack) {
                @Override
                protected void onEntityHit(EntityHitResult entityHitResult) {
                    if (this.getOwner() instanceof PlayerEntity owner) {
                        if (entityHitResult.getEntity() instanceof LivingEntity target) {
                            ModSpells.CHROMATIC_ORB.onHit(owner, this.method_48926(), target, 1f);
                            target.damage(target.getDamageSources().playerAttack(owner), 0.01f);
                            target.damage(target.getDamageSources().cramming(), 7.5f);
                            if (!target.isAlive()) {
                                ModSpells.CHROMATIC_ORB.onKill(owner, this.method_48926(), target);
                            }
                        }

                        List<LivingEntity> entityList = this.GetNearbyEntities(
                                3f, this.method_48926(), owner);
                        for (LivingEntity target : entityList) {
                            target.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 200, 0));
                        }
                    }
                    this.HasHit();
                }
                @Override
                protected void onBlockHit(BlockHitResult result) {
                    if (this.getOwner() instanceof LivingEntity owner) {
                        List<LivingEntity> entityList = this.GetNearbyEntities(
                                3f, this.method_48926(), owner);
                        for (LivingEntity target : entityList) {
                            target.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 200, 0));
                        }
                    }
                    if (!this.method_48926().isClient) {
                        this.kill();
                    }
                }

            };
            orb.setItem(new ItemStack(ModItems.CHROMATIC_ORB_COLD));
            orb.setVelocity(player, player.getPitch(), player.getYaw(), 0.0f, 2.5f, 0.0f);
            world.spawnEntity(orb);
        }
        player.incrementStat(Stats.USED.getOrCreateStat(stack.getItem()));
    }
}
