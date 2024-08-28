package com.example.main.Spells.custom;

import com.example.main.SpellUtil.SpellSchool;
import com.example.main.SpellUtil.Spells.Spell;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class CleanseSpell extends Spell {

    public CleanseSpell(int manaCost, SpellSchool school, int chargeTime, String name, int cooldown, Identifier texture, int min, int max, int upcast) {
        super(0, school, chargeTime, name, cooldown, texture, min, max, upcast);
        super.finishManaCost = manaCost;
        super.text = GetText();
    }

    @Override
    public void castReleaseSpell(PlayerEntity player, World world, ItemStack stack, int tick) {
        player.getStatusEffects().clear();
        world.addParticle(ParticleTypes.WAX_OFF, player.getX()+0.1, player.getY()+0.5, player.getZ()-0.1, 0, 0.1, 0);
        world.addParticle(ParticleTypes.WAX_OFF, player.getX()+0.2, player.getY(), player.getZ()+0.1, 0, 0.1, 0);
        world.addParticle(ParticleTypes.WAX_OFF, player.getX()+0.1, player.getY()+1.3, player.getZ()+0.2, 0, 0.1, 0);
        world.addParticle(ParticleTypes.WAX_OFF, player.getX()-0.2f, player.getY() +1.5, player.getZ(), 0, 0.1, 0);
        world.addParticle(ParticleTypes.WAX_OFF, player.getX()-0.2f, player.getY()+1.2f, player.getZ() - 0.1f, 0, 0.1, 0);
        world.addParticle(ParticleTypes.WAX_OFF, player.getX(), player.getY()+ 0.4f, player.getZ() + 0.1f, 0, 0.1, 0);
        world.playSound(null, player.getBlockPos(), SoundEvents.BLOCK_BEACON_POWER_SELECT, SoundCategory.PLAYERS, 1,0.5f);
        world.playSound(null, player.getBlockPos(), SoundEvents.BLOCK_BREWING_STAND_BREW, SoundCategory.PLAYERS, 1,2f);
    }

}
