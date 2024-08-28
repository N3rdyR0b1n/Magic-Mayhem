package com.example.main.Item.armor;


import com.example.main.Attributes.ModAttributes;
import com.example.main.Item.customarmor.MagicArmorItem;
import com.google.common.collect.ImmutableMultimap;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.ArmorItem;

public class ArmorAttributeHolders {

    public static ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> ClassicBoots() {
        ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> map = new ImmutableMultimap.Builder<>();
        map.put(ModAttributes.MAXIMUM_MANA, new EntityAttributeModifier("Helmet Mana", 275, EntityAttributeModifier.Operation.ADDITION));
        map.put(ModAttributes.MAXIMUM_MANA, new EntityAttributeModifier("Helmet Mana", 0.25f, EntityAttributeModifier.Operation.MULTIPLY_BASE));
        map.put(EntityAttributes.GENERIC_MAX_HEALTH, new EntityAttributeModifier("Helmet Hp", 6, EntityAttributeModifier.Operation.ADDITION));
        map.put(ModAttributes.MANA_REGENERATION, new EntityAttributeModifier("Helmet Mana Reg", 1, EntityAttributeModifier.Operation.ADDITION));
        return map;
    }
    public static  ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> ClassicLegs() {
        ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> map = new ImmutableMultimap.Builder<>();
        map.put(ModAttributes.MAXIMUM_MANA, new EntityAttributeModifier("Helmet Mana", 520, EntityAttributeModifier.Operation.ADDITION));
        map.put(ModAttributes.MAXIMUM_MANA, new EntityAttributeModifier("Helmet Mana", 0.25f, EntityAttributeModifier.Operation.MULTIPLY_BASE));
        map.put(EntityAttributes.GENERIC_MAX_HEALTH, new EntityAttributeModifier("Helmet Hp", 12, EntityAttributeModifier.Operation.ADDITION));
        map.put(ModAttributes.MANA_ON_HIT, new EntityAttributeModifier("Helmet Mana Reg", 1, EntityAttributeModifier.Operation.ADDITION));
        return  map;
    }
    public static  ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> ClassicChest() {
        ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> map = new ImmutableMultimap.Builder<>();
        map.put(ModAttributes.MAXIMUM_MANA, new EntityAttributeModifier("Helmet Mana", 790, EntityAttributeModifier.Operation.ADDITION));
        map.put(ModAttributes.MAXIMUM_MANA, new EntityAttributeModifier("Helmet Mana", 0.25f, EntityAttributeModifier.Operation.MULTIPLY_BASE));
        map.put(EntityAttributes.GENERIC_MAX_HEALTH, new EntityAttributeModifier("Helmet Hp", 14, EntityAttributeModifier.Operation.ADDITION));
        map.put(ModAttributes.MANA_ON_KILL, new EntityAttributeModifier("Helmet Mana Reg", 1, EntityAttributeModifier.Operation.ADDITION));
        return map;
    }
    public static  ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> ClassicHelmet() {
        ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> map = new ImmutableMultimap.Builder<>();
        map.put(ModAttributes.MAXIMUM_MANA, new EntityAttributeModifier("Helmet Mana", 350, EntityAttributeModifier.Operation.ADDITION));
        map.put(ModAttributes.MAXIMUM_MANA, new EntityAttributeModifier("Helmet Mana", 0.25f, EntityAttributeModifier.Operation.MULTIPLY_BASE));
        map.put(EntityAttributes.GENERIC_MAX_HEALTH, new EntityAttributeModifier("Helmet Hp", 8, EntityAttributeModifier.Operation.ADDITION));
        map.put(ModAttributes.PASSIVE_MANA_REGENERATION, new EntityAttributeModifier("Helmet Mana Reg", 1, EntityAttributeModifier.Operation.ADDITION));
        return map;
    }
    public static ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> WardenBoots() {
        ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> map = new ImmutableMultimap.Builder<>();
        map.put(ModAttributes.MAXIMUM_MANA, new EntityAttributeModifier("Helmet Mana", 40, EntityAttributeModifier.Operation.ADDITION));
        map.put(EntityAttributes.GENERIC_MAX_HEALTH, new EntityAttributeModifier("Helmet Hp", 16, EntityAttributeModifier.Operation.ADDITION));
        map.put(ModAttributes.MANA_ON_KILL, new EntityAttributeModifier("Helmet Mana Reg", 15, EntityAttributeModifier.Operation.ADDITION));
        map.put(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, new EntityAttributeModifier("Helmet Mana Reg", 1, EntityAttributeModifier.Operation.ADDITION));
        map.put(ModAttributes.MANA_ON_HIT, new EntityAttributeModifier("Helmet Mana Reg", 3, EntityAttributeModifier.Operation.ADDITION));
        map.put(ModAttributes.MANA_REGENERATION, new EntityAttributeModifier("Helmet Mana Reg", 1, EntityAttributeModifier.Operation.ADDITION));
        return map;

    }
    public static  ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> WardenLegs() {
        ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> map = new ImmutableMultimap.Builder<>();
        map.put(ModAttributes.MAXIMUM_MANA, new EntityAttributeModifier("Helmet Mana", 100, EntityAttributeModifier.Operation.ADDITION));
        map.put(EntityAttributes.GENERIC_MAX_HEALTH, new EntityAttributeModifier("Helmet Hp", 24, EntityAttributeModifier.Operation.ADDITION));
        map.put(ModAttributes.MANA_ON_KILL, new EntityAttributeModifier("Helmet Mana Reg", 25, EntityAttributeModifier.Operation.ADDITION));
        map.put(ModAttributes.MANA_ON_HIT, new EntityAttributeModifier("Helmet Mana Reg", 3, EntityAttributeModifier.Operation.ADDITION));
        map.put(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, new EntityAttributeModifier("Helmet Mana Reg", 1, EntityAttributeModifier.Operation.ADDITION));
        map.put(ModAttributes.MANA_REGENERATION, new EntityAttributeModifier("Helmet Mana Reg", 1, EntityAttributeModifier.Operation.ADDITION));
        return  map;
    }
    public static  ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> WardenChest() {
        ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> map = new ImmutableMultimap.Builder<>();
        map.put(ModAttributes.MAXIMUM_MANA, new EntityAttributeModifier("Helmet Mana", 150, EntityAttributeModifier.Operation.ADDITION));
        map.put(EntityAttributes.GENERIC_MAX_HEALTH, new EntityAttributeModifier("Helmet Hp", 36, EntityAttributeModifier.Operation.ADDITION));
        map.put(ModAttributes.MANA_ON_KILL, new EntityAttributeModifier("Helmet Mana Reg", 45, EntityAttributeModifier.Operation.ADDITION));
        map.put(ModAttributes.MANA_ON_HIT, new EntityAttributeModifier("Helmet Mana Reg", 3, EntityAttributeModifier.Operation.ADDITION));
        map.put(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, new EntityAttributeModifier("Helmet Mana Reg", 1, EntityAttributeModifier.Operation.ADDITION));
        map.put(ModAttributes.MANA_REGENERATION, new EntityAttributeModifier("Helmet Mana Reg", 1, EntityAttributeModifier.Operation.ADDITION));
        return map;
    }
    public static  ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> WardenHelmet() {
        ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> map = new ImmutableMultimap.Builder<>();
        map.put(ModAttributes.MAXIMUM_MANA, new EntityAttributeModifier("Helmet Mana", 50, EntityAttributeModifier.Operation.ADDITION));
        map.put(EntityAttributes.GENERIC_MAX_HEALTH, new EntityAttributeModifier("Helmet Hp", 12, EntityAttributeModifier.Operation.ADDITION));
        map.put(ModAttributes.MANA_ON_KILL, new EntityAttributeModifier("Helmet Mana Reg", 15, EntityAttributeModifier.Operation.ADDITION));
        map.put(ModAttributes.MANA_ON_HIT, new EntityAttributeModifier("Helmet Mana Reg", 3, EntityAttributeModifier.Operation.ADDITION));
        map.put(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, new EntityAttributeModifier("Helmet Mana Reg", 1, EntityAttributeModifier.Operation.ADDITION));
        map.put(ModAttributes.MANA_REGENERATION, new EntityAttributeModifier("Helmet Mana Reg", 1, EntityAttributeModifier.Operation.ADDITION));
        return map;
    }

    public static ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> EnderBoots() {
        ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> map = new ImmutableMultimap.Builder<>();
        map.put(ModAttributes.MAXIMUM_MANA, new EntityAttributeModifier("Helmet Mana", 5, EntityAttributeModifier.Operation.ADDITION));
        map.put(EntityAttributes.GENERIC_MAX_HEALTH, new EntityAttributeModifier("Helmet Hp", 8, EntityAttributeModifier.Operation.ADDITION));
        map.put(ModAttributes.MANA_REGENERATION, new EntityAttributeModifier("Helmet Mana Reg", 2, EntityAttributeModifier.Operation.ADDITION));
        map.put(ModAttributes.CAST_SPEED, new EntityAttributeModifier("Helmet Mana Reg", 0.05f, EntityAttributeModifier.Operation.ADDITION));
        map.put(ModAttributes.PASSIVE_MANA_REGENERATION, new EntityAttributeModifier("Helmet Mana Reg", 1f, EntityAttributeModifier.Operation.ADDITION));
        return map;
    }
    public static  ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> EnderLegs() {
        ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> map = new ImmutableMultimap.Builder<>();
        map.put(ModAttributes.MAXIMUM_MANA, new EntityAttributeModifier("Helmet Mana", 20, EntityAttributeModifier.Operation.ADDITION));
        map.put(EntityAttributes.GENERIC_MAX_HEALTH, new EntityAttributeModifier("Helmet Hp", 15, EntityAttributeModifier.Operation.ADDITION));
        map.put(ModAttributes.MANA_REGENERATION, new EntityAttributeModifier("Helmet Mana Reg", 3, EntityAttributeModifier.Operation.ADDITION));
        map.put(ModAttributes.CAST_SPEED, new EntityAttributeModifier("Helmet Mana Reg", 0.15f, EntityAttributeModifier.Operation.ADDITION));
        map.put(ModAttributes.PASSIVE_MANA_REGENERATION, new EntityAttributeModifier("Helmet Mana Reg", 2f, EntityAttributeModifier.Operation.ADDITION));
        return  map;
    }
    public static  ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> EnderChest() {
        ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> map = new ImmutableMultimap.Builder<>();
        map.put(ModAttributes.MAXIMUM_MANA, new EntityAttributeModifier("Helmet Mana", 35, EntityAttributeModifier.Operation.ADDITION));
        map.put(EntityAttributes.GENERIC_MAX_HEALTH, new EntityAttributeModifier("Helmet Hp", 20, EntityAttributeModifier.Operation.ADDITION));
        map.put(ModAttributes.MANA_REGENERATION, new EntityAttributeModifier("Helmet Mana Reg", 4.5f, EntityAttributeModifier.Operation.ADDITION));
        map.put(ModAttributes.CAST_SPEED, new EntityAttributeModifier("Helmet Mana Reg", 0.2f, EntityAttributeModifier.Operation.ADDITION));
        map.put(ModAttributes.PASSIVE_MANA_REGENERATION, new EntityAttributeModifier("Helmet Mana Reg", 3f, EntityAttributeModifier.Operation.ADDITION));

        return map;
    }
    public static  ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> EnderHelmet() {
        ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> map = new ImmutableMultimap.Builder<>();
        map.put(ModAttributes.MAXIMUM_MANA, new EntityAttributeModifier("Helmet Mana", 10, EntityAttributeModifier.Operation.ADDITION));
        map.put(EntityAttributes.GENERIC_MAX_HEALTH, new EntityAttributeModifier("Helmet Hp", 12, EntityAttributeModifier.Operation.ADDITION));
        map.put(ModAttributes.MANA_REGENERATION, new EntityAttributeModifier("Helmet Mana Reg", 2.5f, EntityAttributeModifier.Operation.ADDITION));
        map.put(ModAttributes.CAST_SPEED, new EntityAttributeModifier("Helmet Mana Reg", 0.1f, EntityAttributeModifier.Operation.ADDITION));
        map.put(ModAttributes.PASSIVE_MANA_REGENERATION, new EntityAttributeModifier("Helmet Mana Reg", 1f, EntityAttributeModifier.Operation.ADDITION));
        return map;
    }


}
