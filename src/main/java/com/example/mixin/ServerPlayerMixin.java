package com.example.mixin;

import com.example.main.Attributes.ModAttributes;
import com.example.main.SpellUtil.AttributeBuffable;
import com.example.main.SpellUtil.DodgeContainer;
import com.example.main.SpellUtil.ProcessedDamageSource;
import com.google.common.collect.Multimap;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.*;

@Mixin(LivingEntity.class)
public abstract class ServerPlayerMixin implements AttributeBuffable {


    @Shadow public abstract boolean damage(DamageSource source, float amount);

    @Shadow public abstract AttributeContainer getAttributes();

    @Shadow public abstract double getAttributeValue(RegistryEntry<EntityAttribute> attribute);

    @Shadow public abstract double getAttributeValue(EntityAttribute attribute);

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

    @Inject(method = "damage", at = @At(value = "HEAD"), cancellable = true)
    private void damage2(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
       if ((source instanceof ProcessedDamageSource)) {
           return;
       }
       ProcessedDamageSource newsource = new ProcessedDamageSource(source.getTypeRegistryEntry(), source.getSource(), source.getAttacker(), source.getPosition());
       amount -= (float) getAttributeValue(ModAttributes.DAMAGE_REDUCTION);
       if (amount < 0f) {
           amount = 0f;
       }
       if (this instanceof DodgeContainer container) {
           PlayerEntity player = (PlayerEntity) (Object) this;
           int dodges = container.getDodges();
           if (dodges > 0) {
               amount=0;
               container.setDodges(dodges - 1);
               World world = player.getWorld();
               world.playSound(null, player.getBlockPos(), SoundEvents.ENTITY_ENDERMAN_TELEPORT, SoundCategory.PLAYERS, 1f,  0.5f + 0.1f * dodges);
           }
       }
       cir.setReturnValue(damage(newsource, amount));

    }

    @Inject(method = "createLivingAttributes", at = @At(value = "RETURN"))
    private static void setAttributebuffs(CallbackInfoReturnable<DefaultAttributeContainer.Builder> callback) {
        callback.getReturnValue().add(ModAttributes.DAMAGE_REDUCTION);
    }

}
