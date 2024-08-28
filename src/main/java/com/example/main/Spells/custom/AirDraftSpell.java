package com.example.main.Spells.custom;

import com.example.main.SpellUtil.SpellSchool;
import com.example.main.SpellUtil.Spells.Spell;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class AirDraftSpell extends Spell {


    public AirDraftSpell(int manaCost, SpellSchool school, int chargeTime, String name, int cooldown, Identifier texture, int min, int max, int upcastcost) {
        super( manaCost, school, chargeTime, name, cooldown, texture, min, max, upcastcost);

    }

    @Override
    public void castSpell(PlayerEntity player, World world, ItemStack stack) {
        if (world.isClient && !player.isFallFlying()) {
            Vec3d start = player.getEyePos();
            Vec3d end = player.getEyePos().add(player.getRotationVec(0.5f).multiply(1));
            Vec3d direction = end.subtract(start).normalize().multiply(0.75D + (double) Level() /3);
            player.addVelocity(direction);
        }
        world.playSound(null, player.getBlockPos(), SoundEvents.ITEM_TRIDENT_RIPTIDE_3, SoundCategory.PLAYERS, 2, 0.75f);
        super.castSpell(player,world,stack);
    }

}
