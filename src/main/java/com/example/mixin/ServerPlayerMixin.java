package com.example.mixin;

import com.example.main.SpellUtil.AttributeBuffable;
import com.example.main.SpellUtil.GenericUtil;
import com.example.main.SpellUtil.ProcessedDamageSource;
import com.google.common.collect.Multimap;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.security.auth.callback.Callback;
import java.util.*;

@Mixin(PlayerEntity.class)
public abstract class ServerPlayerMixin implements AttributeBuffable {

    @Shadow public abstract boolean damage(DamageSource source, float amount);

    private HashMap<String,Multimap<EntityAttribute, EntityAttributeModifier>> attributebuffs = new HashMap();
    private HashMap<String,Integer> durations = new HashMap();
    @Inject(method = "tick", at = @At(value = "HEAD"))
    private void tickStatusEffects(CallbackInfo cir) {
         AttributeContainer container = ((PlayerEntity) (Object) (this)).getAttributes();
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

    @Inject(method = "damage", at = @At(value = "HEAD"))
    private void tickStatusEffects(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if ((source instanceof ProcessedDamageSource)) {
            return;
        }
        ProcessedDamageSource newsource = new ProcessedDamageSource(source.getTypeRegistryEntry(), source.getSource(), source.getAttacker(), source.getPosition());
        cir.setReturnValue(GenericUtil.HandleDamage(newsource, amount, (PlayerEntity) (Object) this));

    }

}
