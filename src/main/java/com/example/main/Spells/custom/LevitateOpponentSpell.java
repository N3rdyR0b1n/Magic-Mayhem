package com.example.main.Spells.custom;

import com.example.main.SpellUtil.GenericSpellAbilities;
import com.example.main.SpellUtil.HitValues;
import com.example.main.SpellUtil.SpellSchool;
import com.example.main.Spells.extra.ContinousUsageSpell;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class LevitateOpponentSpell extends ContinousUsageSpell {


    public LevitateOpponentSpell(int manaCost, SpellSchool school, int chargeTime, String name, int cooldown, Identifier texture, int useTime, boolean needsHeld, int overtimecost, int min, int max, int up) {
        super(manaCost, school, chargeTime, name, cooldown, texture, useTime, needsHeld, overtimecost, min, max, 0, up);
    }


    @Override
    public void tickAction(PlayerEntity player, World world, ItemStack stack, int index, int tick) {
        HitValues target = GenericSpellAbilities.HitscanSelect(world, player, 256, false);
        GenericSpellAbilities.addParticles(world, player.getEyePos().subtract(0,0.25f,0), target.endPos(), ParticleTypes.END_ROD, 6);
        if (target == null) {
            if (tick % 4 == 0) {
                world.playSound(null, player.getBlockPos(), SoundEvents.ENTITY_SHULKER_SHOOT, SoundCategory.PLAYERS, 0.75f, 2f);
            }
            return;
        }
        if (target.getTarget() instanceof LivingEntity entity) {
            entity.addStatusEffect(new StatusEffectInstance(StatusEffects.LEVITATION, 2, 2 + Level()));
        }
        if (tick % 4 == 0) {
            world.playSound(null, player.getBlockPos(), SoundEvents.ENTITY_SHULKER_SHOOT, SoundCategory.PLAYERS, 0.75f, 2f);
        }
    }

}
