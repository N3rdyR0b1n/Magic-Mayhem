package com.example.main.Spells.custom.actions;

import com.example.main.Entity.ModEntities;
import com.example.main.Entity.custom.ChromaticOrbEntity;
import com.example.main.Item.ModItems;
import com.example.main.Spells.ModSpells;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;

public class ChromaticOrbAcid extends ActionPerformable {
    @Override
    public void Perform(ItemStack stack, PlayerEntity player, World world) {
        if (!world.isClient) {
            ChromaticOrbEntity orb = new ChromaticOrbEntity(ModEntities.CHROMATIC_ORB, player, world, stack) {
                @Override
                protected void onEntityHit(EntityHitResult entityHitResult) {
                    if (entityHitResult.getEntity() instanceof LivingEntity target && this.getOwner() instanceof PlayerEntity owner) {
                        ModSpells.CHROMATIC_ORB.onHit(owner, this.getWorld(), target, 1f);
                        target.damage(target.getDamageSources().playerAttack(owner), 0.01f);
                        target.damage(target.getDamageSources().cramming(), 7.5f);
                        if (!target.isAlive()) {
                            ModSpells.CHROMATIC_ORB.onKill(owner, this.getWorld(), target);
                        }
                        if (target instanceof ServerPlayerEntity playertarget) {
                            for (ItemStack armor : playertarget.getArmorItems()) {
                                armor.damage(55, this.getWorld().getRandom(), playertarget);
                            }
                        }

                    }
                    this.HasHit();
                }
            };
            orb.setItem(new ItemStack(ModItems.CHROMATIC_ORB_ACID));
            orb.setVelocity(player, player.getPitch(), player.getYaw(), 0.0f, 2.5f, 0.0f);
            world.spawnEntity(orb);
        }
        player.incrementStat(Stats.USED.getOrCreateStat(stack.getItem()));
    }
}
