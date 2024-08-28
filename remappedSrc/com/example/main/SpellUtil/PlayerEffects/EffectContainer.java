package com.example.main.SpellUtil.PlayerEffects;


import com.example.main.SpellUtil.Spells.Spell;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public interface EffectContainer {

    public void OnTargetHit(Entity target, PlayerEntity user, World world, ItemStack stack, Spell spell);
    public void OnTargetKilled(Entity target, PlayerEntity user, World world, ItemStack stack, Spell spell);
}
