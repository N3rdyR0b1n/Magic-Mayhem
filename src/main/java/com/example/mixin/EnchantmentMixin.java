package com.example.mixin;

import com.example.main.Item.customarmor.MagicArmorItem;
import com.example.main.SpellUtil.GenericSpellAbilities;
import it.unimi.dsi.fastutil.Hash;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.enchantment.MendingEnchantment;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.struct.InjectorGroupInfo;
import org.spongepowered.include.com.google.common.collect.Sets;

import java.util.*;

import static com.example.main.SpellUtil.GenericUtil.getLocked;
import static com.example.main.SpellUtil.GenericUtil.locked;

@Mixin(value = Enchantment.class)
public abstract class EnchantmentMixin {

    @Inject(method = "isAcceptableItem", at = @At("HEAD"), cancellable = true)
    private void NoEnchantedMagicArmor(ItemStack stack, CallbackInfoReturnable<Boolean> returnable) {
        if (locked.contains((Enchantment) (Object) this)) {
            returnable.setReturnValue(false);
        }
    }




}
