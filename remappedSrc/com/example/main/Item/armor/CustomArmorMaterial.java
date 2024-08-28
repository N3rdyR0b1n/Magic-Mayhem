package com.example.main.Item.armor;

import com.example.main.Item.customarmor.MagicArmorItem;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;

public interface CustomArmorMaterial {
    public int getDurability(MagicArmorItem.Type var1);

    public int getProtection(MagicArmorItem.Type var1);

    public int getEnchantability();

    public SoundEvent getEquipSound();

    public Ingredient getRepairIngredient();

    public String getName();

    public float getToughness();

    public float getKnockbackResistance();
}
