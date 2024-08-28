package com.example.main.Item.armor;

import com.example.main.Item.customarmor.MagicArmorItem;
import net.minecraft.item.*;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Lazy;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.Util;

import java.util.EnumMap;
import java.util.function.Supplier;

public enum MagicArmorMaterials implements CustomArmorMaterial, StringIdentifiable {

    MAX_MANA_TIER_6("tank_mana_t6", 56, (EnumMap)Util.make(new EnumMap(ArmorItem.Type.class), map -> {
        map.put(ArmorItem.Type.BOOTS, 1);
        map.put(ArmorItem.Type.LEGGINGS, 1);
        map.put(ArmorItem.Type.CHESTPLATE, 1);
        map.put(ArmorItem.Type.HELMET, 1);
    }), 15, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0f, () -> Ingredient.ofItems(Items.WHITE_WOOL, Items.STRING), ArmorTierValues.TIER_6)
    ;


    private static final EnumMap<MagicArmorItem.Type, Integer> BASE_DURABILITY;
    private final String name;
    private final int durabilityMultiplier;
    private final EnumMap<ArmorItem.Type, Integer> protectionAmounts;
    private final int enchantability;
    private final SoundEvent equipSound;
    private final float knockbackResistance;

    private final Lazy<Ingredient> repairIngredientSupplier;
    private final ArmorTierValues armorTier;

     MagicArmorMaterials(String name, int durabilityMultiplier, EnumMap<ArmorItem.Type, Integer> protectionAmounts, int enchantability, SoundEvent equipSound, float knockbackResistance, Supplier<Ingredient> repairIngredientSupplier, ArmorTierValues armorTier) {
        this.name = name;
        this.durabilityMultiplier = durabilityMultiplier;
        this.protectionAmounts = protectionAmounts;
        this.enchantability = enchantability;
        this.equipSound = equipSound;
        this.knockbackResistance = knockbackResistance;
        this.repairIngredientSupplier = new Lazy<Ingredient>(repairIngredientSupplier);
         this.armorTier = armorTier;
     }
     @Override
    public int getDurability(MagicArmorItem.Type type) {
        return BASE_DURABILITY.get((Object)type) * this.durabilityMultiplier;
    }

    @Override
    public int getProtection(MagicArmorItem.Type type) {
        return this.protectionAmounts.get((Object)type);
    }

    @Override
    public int getEnchantability() {
        return this.enchantability;
    }

    @Override
    public SoundEvent getEquipSound() {
        return this.equipSound;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return this.repairIngredientSupplier.get();
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public float getToughness() {
        return 0;
    }

    public ArmorTierValues getArmorTier() {
         return this.armorTier;
    }

    @Override
    public float getKnockbackResistance() {
        return this.knockbackResistance;
    }

    public static final Codec<ArmorMaterials> CODEC;

    static {
        CODEC = StringIdentifiable.createCodec(ArmorMaterials::values);
        BASE_DURABILITY = Util.make(new EnumMap(MagicArmorItem.Type.class), map -> {
            map.put(MagicArmorItem.Type.BOOTS, 13);
            map.put(MagicArmorItem.Type.LEGGINGS, 15);
            map.put(MagicArmorItem.Type.CHESTPLATE, 16);
            map.put(MagicArmorItem.Type.HELMET, 11);
        });
    }

    @Override
    public String asString() {
        return this.name;
    }
}

