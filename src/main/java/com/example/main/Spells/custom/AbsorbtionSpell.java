package com.example.main.Spells.custom;

import com.example.main.SpellUtil.GenericSpellAbilities;
import com.example.main.SpellUtil.SpellSchool;
import com.example.main.SpellUtil.Spells.Spell;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class AbsorbtionSpell extends Spell {
    private float shieldamount;
    public AbsorbtionSpell(int manaCost, SpellSchool school, int chargeTime, String name, Identifier texture, int cooldown, float shieldamount, int min, int max, int cost) {
        super(manaCost, school, chargeTime, name, cooldown, texture, min, max, cost);
        this.shieldamount = shieldamount;
    }

    @Override
    public void castSpell(PlayerEntity player, World world, ItemStack stack) {
        GenericSpellAbilities.ApplyAbsorption(player,stack, shieldamount * (Level() + 1));
        super.castSpell(player,world,stack);
    }




}
