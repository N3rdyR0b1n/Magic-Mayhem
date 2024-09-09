package com.example.main.Attributes;

import net.minecraft.entity.attribute.ClampedEntityAttribute;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModAttributes {

    public static final EntityAttribute MAXIMUM_MANA = register("magic_mayhem.maximum_mana", (new ClampedEntityAttribute("attribute.name.magic_mayhem.maximum_mana", 0.0, 0.0, 16777216.0)).setTracked(true));
    public static final EntityAttribute MANA_REGENERATION = register("magic_mayhem.mana_regeneration", (new ClampedEntityAttribute("attribute.name.magic_mayhem.mana_regeneration", 0.0, -16777216.0, 16777216.0)).setTracked(true));
    public static final EntityAttribute PASSIVE_MANA_REGENERATION = register("magic_mayhem.passive_mana_regeneration", (new ClampedEntityAttribute("attribute.name.magic_mayhem.passive_mana_regeneration", 0.0, -16777216.0, 16777216.0)).setTracked(true));
    public static final EntityAttribute MANA_LOSS_MULTIPLIER = register("magic_mayhem.mana_loss", (new ClampedEntityAttribute("attribute.name.magic_mayhem.mana_loss", 0.0, -16777216.0, 16777216.0)).setTracked(true));
    public static final EntityAttribute MANA_ON_KILL = register("magic_mayhem.mana_kill", (new ClampedEntityAttribute("attribute.name.magic_mayhem.mana_kill", 0.0, -16777216.0, 16777216.0)).setTracked(true));
    public static final EntityAttribute MANA_ON_HIT = register("magic_mayhem.mana_hit", (new ClampedEntityAttribute("attribute.name.magic_mayhem.mana_hit", 0.0, -16777216.0, 16777216.0)).setTracked(true));

    public static final EntityAttribute HP_ON_KILL =  register("magic_mayhem.health_kill", (new ClampedEntityAttribute("attribute.name.magic_mayhem.health_kill", 0.0, -16777216.0, 16777216.0)).setTracked(true));
    public static final EntityAttribute HP_ON_HIT =register("magic_mayhem.health_hit", (new ClampedEntityAttribute("attribute.name.magic_mayhem.health_hit", 0.0, -16777216.0, 16777216.0)).setTracked(true));
    public static final EntityAttribute LIFESTEAL = register("magic_mayhem.lifesteal", (new ClampedEntityAttribute("attribute.name.magic_mayhem.lifesteal", 0.0, 0.0, 16777216.0)).setTracked(true));
    public static final EntityAttribute CAST_SPEED = register("magic_mayhem.casting_speed", (new ClampedEntityAttribute("attribute.name.magic_mayhem.casting_speed", 1.0, 0.00001, 16777216.0)).setTracked(true));
    public static final EntityAttribute TRUE_INVISIBILITY = register("magic_mayhem.invisibility", (new ClampedEntityAttribute("attribute.name.magic_mayhem.invisibility", -1, -1, 11)).setTracked(true));
    public static final EntityAttribute CASTING_LEVEL = register("magic_mayhem.casting_level", (new ClampedEntityAttribute("attribute.name.magic_mayhem.casting_level", 0, -1, 256)).setTracked(true));
    public static final EntityAttribute DAMAGE_REDUCTION = register("magic_mayhem.damage_reduction", new ClampedEntityAttribute("attribute.name.magic_mayhem.damage_reduction", 0, -100000, 100000)).setTracked(true);

    private static EntityAttribute register(String id, EntityAttribute attribute) {
        return Registry.register(Registries.ATTRIBUTE, id, attribute);
    }
    public static void init() {

    }
}
