package com.example.mixin;

import com.example.main.SpellUtil.AttributeBuffable;
import com.google.common.collect.Multimap;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import oshi.util.tuples.Pair;

import java.util.*;

@Mixin(LivingEntity.class)
public abstract class ServerPlayerMixin implements AttributeBuffable {

    @Shadow public abstract AttributeContainer getAttributes();

    private HashMap<String,Multimap<EntityAttribute, EntityAttributeModifier>> attributebuffs = new HashMap();
    private HashMap<String,Integer> durations = new HashMap();
    @Inject(method = "tick", at = @At(value = "HEAD"))
    private void tickStatusEffects(CallbackInfo cir) {
         AttributeContainer container = getAttributes();
         for (Iterator<Map.Entry<String,Multimap<EntityAttribute, EntityAttributeModifier>>> it = attributebuffs.entrySet().iterator(); it.hasNext();) {
             String key = it.next().getKey();
             Multimap<EntityAttribute, EntityAttributeModifier> modifiers = attributebuffs.get(key);
             int duration = durations.get(key) - 1;
             if (duration < 0) {
                 container.removeModifiers(modifiers);
                 durations.remove(key);
                 it.remove();
             }
             else {
                durations.put(key, duration);
             }
         }
    }

    public String AddAttributeModifier(Multimap<EntityAttribute, EntityAttributeModifier> modifier, int duration, boolean stackable, String identifier) {
        if (!stackable) {
            if (attributebuffs.containsKey(identifier)) {
                return "";
            }
            attributebuffs.put(identifier, modifier);
            durations.put(identifier, duration);
            return identifier;
        }
        for (int i = 0; true; i++) {
            if (attributebuffs.containsKey(identifier + i)) {
                continue;
            }
            identifier += i;
            durations.put(identifier, duration);
            attributebuffs.put(identifier, modifier);
            break;
        }
        return identifier;
    }

    @Override
    public boolean maintainModifier(String identifier, int newduration) {
        if (durations.containsKey(identifier)) {
            durations.put(identifier, newduration);
            return true;
        }
        return false;
    }


}
