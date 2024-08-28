package com.example.mixin;

import com.example.main.SpellUtil.Spells.Spell;
import net.minecraft.nbt.NbtType;
import net.minecraft.nbt.NbtTypes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(NbtTypes.class)
public class NbtTypeMixin {
    @Inject(method = "byId", at = @At("HEAD"), cancellable = true)
    private static void put(int key, CallbackInfoReturnable<NbtType<?>> cir) {
        if (key == Spell.SPELLNBT) {
            cir.setReturnValue(Spell.TYPE);
        }
    }
}
