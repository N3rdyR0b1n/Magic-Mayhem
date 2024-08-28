package com.example.main.SpellUtil.Spells;

import com.example.main.Spells.custom.actions.ActionPerformable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;

public abstract class Action extends ActionPerformable  {

    public abstract void castSpell(PlayerEntity player, World world, ItemStack stack);
    public abstract void castReleaseSpell(PlayerEntity player, World world, ItemStack stack, int tick);

    public String getExtraInfo(ItemStack stack) {
        return "";
    }
    public int getExtraInfoColor(ItemStack stack) {
        return 0;
    }
    public int getInfoColor(ItemStack stack) {
        return -1;
    }


    public abstract String getInfo(PlayerEntity player, ItemStack stack) throws CloneNotSupportedException;

    public abstract int GetChargeTime(PlayerEntity player, ItemStack stack);

    public void onHit(PlayerEntity player, World world, @Nullable Entity target, float hitlvl ) {

    }

    public void onKill(PlayerEntity player, World world, @Nullable Entity target) {

    }
}
