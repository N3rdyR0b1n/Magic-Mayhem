package com.example.main.Spells.custom;

import com.example.main.SpellUtil.DodgeContainer;
import com.example.main.SpellUtil.SpellSchool;
import com.example.main.SpellUtil.Spells.Spell;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class NuhUhSpell extends Spell {
    public NuhUhSpell(int manaCost, SpellSchool school, int chargeTime, String name, int cooldown, Identifier texture, int min, int max, int up) {
        super(manaCost, school, chargeTime, name, cooldown, texture, min, max, up);
    }

    public void castSpell(PlayerEntity player, World world, ItemStack stack) {
        ((DodgeContainer) player).setDodges(Level() + 1);
    }


}
