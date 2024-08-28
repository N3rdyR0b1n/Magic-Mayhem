package com.example.main.SpellUtil.PlayerEffects;


import com.example.main.SpellUtil.Spells.Spell;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public interface SelfEffectContainer {

    public void OnHit(Entity attacker, PlayerEntity user, World world, ItemStack stack, Spell spell, float damage, DamageSource source);
    public void OnKilled(Entity attacker, PlayerEntity user, World world, ItemStack stack, Spell spell, float damage, DamageSource source);
}
