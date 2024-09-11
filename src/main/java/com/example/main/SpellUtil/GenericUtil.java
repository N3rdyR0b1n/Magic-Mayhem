package com.example.main.SpellUtil;

import com.example.main.Attributes.ModAttributes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;

public class GenericUtil {

    public record DamageInfo(DamageSource source, float amount) {}
    public static DamageInfo HandleDamage(DamageSource source, float amount, LivingEntity target) {




        return new DamageInfo(source, amount);
    }
}
