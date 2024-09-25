package com.example.main.SpellUtil;

import com.example.main.Attributes.ModAttributes;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.include.com.google.common.collect.Sets;

import java.util.Set;

public class GenericUtil {

    public record DamageInfo(DamageSource source, float amount) {}
    public static DamageInfo HandleDamage(DamageSource source, float amount, LivingEntity target) {
        return new DamageInfo(source, amount);
    }

    public static Set<Enchantment> getLocked() {
        Set<Enchantment> set = Sets.newHashSet();
        set.add(Enchantments.PROTECTION);
        set.add(Enchantments.MENDING);
        return set;
    }

    public static Set<Enchantment> locked = getLocked();
}
