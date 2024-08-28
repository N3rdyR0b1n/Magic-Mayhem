package com.example.main.Spells.custom.actions;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;

public class TickHeal extends ActionPerformable {
    private final float health;
    public TickHeal(float health) {
        this.health=health;
    }

    @Override
    public void Perform(ItemStack stack, PlayerEntity player, World world) {
        if (!world.isClient) {
            player.heal(health);
        }
        world.addParticle(ParticleTypes.HEART, player.getX()+0.1, player.getY()+0.5, player.getZ()-0.1, 0, 0.1, 0);
        world.addParticle(ParticleTypes.HEART, player.getX()+0.2, player.getY(), player.getZ()+0.1, 0, 0.1, 0);
        world.addParticle(ParticleTypes.HEART, player.getX()+0.1, player.getY()+1.3, player.getZ()+0.2, 0, 0.1, 0);
        world.addParticle(ParticleTypes.HEART, player.getX()-0.2f, player.getY() +1.5, player.getZ(), 0, 0.1, 0);
        world.addParticle(ParticleTypes.HEART, player.getX()-0.2f, player.getY()+1.2f, player.getZ() - 0.1f, 0, 0.1, 0);
        world.addParticle(ParticleTypes.HEART, player.getX(), player.getY()+ 0.4f, player.getZ() + 0.1f, 0, 0.1, 0);
        world.playSound(null, player.getBlockPos(), SoundEvents.BLOCK_BEACON_POWER_SELECT, SoundCategory.PLAYERS, 0.5f,2);
    }
}
