package com.example.main.Item.custom;

import com.example.main.SpellUtil.Spells.Spell;
import net.minecraft.item.ItemStack;

public interface SpellContainerItem {

    Spell getSelectedSpell(ItemStack stack);
}
