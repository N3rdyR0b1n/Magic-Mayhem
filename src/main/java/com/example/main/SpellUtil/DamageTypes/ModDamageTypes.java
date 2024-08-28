package com.example.main.SpellUtil.DamageTypes;

import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class ModDamageTypes {


    public static final RegistryKey<DamageType> MAGIC_TICK = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, new Identifier("magic_mayhem_spell", "magic_tick_damage"));

    public static DamageSource of(World world, RegistryKey<DamageType> key) {
        return new DamageSource(world.getRegistryManager().get(RegistryKeys.DAMAGE_TYPE).entryOf(key));
    }
    public static void init() {}

}
