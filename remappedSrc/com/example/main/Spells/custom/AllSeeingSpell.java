package com.example.main.Spells.custom;

import com.example.main.SpellUtil.GenericSpellAbilities;
import com.example.main.SpellUtil.SpellSchool;
import com.example.main.Spells.ModSpells;
import com.example.main.Spells.extra.ContinousUsageSpell;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import java.util.List;

public class AllSeeingSpell extends ContinousUsageSpell {


    public AllSeeingSpell(int manaCost, SpellSchool school, int chargeTime, String name, int cooldown, Identifier texture, int useTime, boolean needsHeld, int overtimecost, int min, int max, int up, int uptime) {
        super(manaCost, school, chargeTime, name, cooldown, texture, useTime, needsHeld, overtimecost, min, max, up, uptime);
    }

    @Override
    public void castSpell(PlayerEntity player, World world, ItemStack stack) {
        super.castSpell(player,world,stack);
        tickAction(player, world, stack, 40, 0);
    }

    @Override
    public void tickAction(PlayerEntity player, World world, ItemStack stack, int index, int tick) {
        if (tick % 40 == 0) {
            world.playSound(null, player.getBlockPos(), SoundEvents.BLOCK_SCULK_SENSOR_CLICKING, SoundCategory.PLAYERS, 1, 1);
                List<Entity> targets = GenericSpellAbilities.GetNearbyEntities(32 + 16 * Level(), world, player, true, true, Entity.class);
                for (Entity entity : targets) {
                    if (entity instanceof LivingEntity living) {
                        living.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 10), player);
                    }
            }
        }
        super.tickAction(player,world,stack,index,tick);
    }
    @Override
    public String GetText() {
        return ModSpells.FormatSpell(name, Manacost(), level);
    }
}
