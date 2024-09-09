package com.example.main.SpellUtil;

import com.example.main.Attributes.ModAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;

public class GenericUtil {

    public static boolean HandleDamage(DamageSource source, float amount, PlayerEntity target) {
        amount -= (float) target.getAttributeValue(ModAttributes.DAMAGE_REDUCTION);



        return target.damage(source, amount);
    }
}
