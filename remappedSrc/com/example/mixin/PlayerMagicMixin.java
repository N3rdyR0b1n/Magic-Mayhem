package com.example.mixin;


import com.example.N3rdyR0b1nsSpellEngine;
import com.example.main.Attributes.ModAttributes;
import com.example.main.SpellUtil.AttributeModifierAbleItem;
import com.example.main.SpellUtil.Mana;
import com.example.main.SpellUtil.ManaContainer;
import com.google.common.collect.Multimap;
import com.mojang.authlib.GameProfile;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.*;

@Mixin(PlayerEntity.class)
public abstract class PlayerMagicMixin implements ManaContainer {

    @Shadow public abstract void playSound(SoundEvent sound, float volume, float pitch);

    @Shadow public abstract void playSound(SoundEvent event, SoundCategory category, float volume, float pitch);

    @Shadow @Final private PlayerInventory inventory;

    @Shadow protected abstract void playStepSound(BlockPos pos, BlockState state);

    @Shadow public abstract boolean canPlaceOn(BlockPos pos, Direction facing, ItemStack stack);

    @Shadow public abstract boolean shouldFilterText();

    private List<int[]> spells = new ArrayList<>();
    private Mana playerMana;
    private NbtCompound persistantSaveData;
    private boolean[] nbtdirty;
    private Multimap<EntityAttribute, EntityAttributeModifier>[] oldmodifiers;



    @Override
    public Mana getMana() {

        if (playerMana == null) {
            if (persistantSaveData == null) {
                playerMana = new Mana(0, false);
            }
            else {
                playerMana = new Mana(persistantSaveData.getInt("maxMana"), true);
                playerMana.setStoredMana(persistantSaveData.getFloat("Mana"));
            }
        }
        return playerMana;
    }

    @Inject(method = "writeCustomDataToNbt", at = @At("TAIL"))
    protected void injectwriteCustomNbt(NbtCompound nbt, CallbackInfo info) {
        if (persistantSaveData == null) {
            persistantSaveData = new NbtCompound();
        }
        if (playerMana == null) {
            playerMana = new Mana(-1, true);
        }
            persistantSaveData.putInt("maxMana", playerMana.getMaxMana());
            persistantSaveData.putFloat("Mana", playerMana.getStoredMana());
            nbt.put("magic_mayhem.spell_data", persistantSaveData);

    }
    @Inject(method = "readCustomDataFromNbt", at = @At("TAIL"))
    protected void injectreadCustomNbt(NbtCompound nbt, CallbackInfo info) {
        if (nbt.contains("magic_mayhem.spell_data")) {
            persistantSaveData = nbt.getCompound("magic_mayhem.spell_data");
            playerMana = new Mana(persistantSaveData.getInt("maxMana"), true);
            playerMana.setStoredMana(persistantSaveData.getFloat("Mana"));
        }
    }

    @Inject(method = "createPlayerAttributes", at = @At(value = "RETURN"))
    private static void addCustomAttribute(CallbackInfoReturnable<DefaultAttributeContainer.Builder> cir) {
        cir.getReturnValue().add(ModAttributes.MAXIMUM_MANA).add(ModAttributes.MANA_REGENERATION).add(ModAttributes.MANA_LOSS_MULTIPLIER).add(ModAttributes.PASSIVE_MANA_REGENERATION).add(ModAttributes.MANA_ON_HIT).add(ModAttributes.MANA_ON_KILL).add(ModAttributes.HP_ON_KILL).add(ModAttributes.HP_ON_HIT).add(ModAttributes.CAST_SPEED).add(ModAttributes.LIFESTEAL).add(ModAttributes.TRUE_INVISIBILITY).add(ModAttributes.CASTING_LEVEL);
    }
    @Inject(method = "tick", at = @At(value = "HEAD"))
    private void injecttick(CallbackInfo cir) {
        PlayerEntity player = (PlayerEntity) (Object) this;
        if (!player.method_48926().isClient() && player.age % 5 == 0) {
            PlayerInventory inventory = player.getInventory();
            for (int i = 0; i < inventory.size(); i++) {
                ItemStack stack = inventory.getStack(i);
                if (stack.isEmpty()) {
                    if (nbtdirty[i]) {
                        handleNbt(i, player);
                    }
                    continue;
                }
                if (stack.getItem() instanceof AttributeModifierAbleItem) {
                    Multimap<EntityAttribute, EntityAttributeModifier> mods = stack.getAttributeModifiers(EquipmentSlot.FEET);
                    player.getAttributes().addTemporaryModifiers(mods);
                    nbtdirty[i] = true;
                    oldmodifiers[i] = mods;
                }
                else if (nbtdirty[i]) {
                    handleNbt(i, player);
                }
            }
        }
    }
    private void handleNbt(int slot, PlayerEntity player) {
        Multimap<EntityAttribute, EntityAttributeModifier> mod = oldmodifiers[slot];
        if (mod != null) {
            player.getAttributes().removeModifiers(mod);
        }
        nbtdirty[slot] = false;
        oldmodifiers[slot] = null;
    }

    private static void setNbtElements(PlayerEntity player) {
        AttributeContainer container = player.getAttributes();
        Collection<EntityAttributeInstance> list = player.getAttributes().getAttributesToSend();
        for (EntityAttributeInstance attributeInstance : list) {
            Set<EntityAttributeModifier> mods = attributeInstance.getModifiers();
            EntityAttributeInstance instance = container.getCustomInstance(attributeInstance.getAttribute());
            instance.clearModifiers();
            for (EntityAttributeModifier mod : mods) {
                if (!mod.getName().contains(N3rdyR0b1nsSpellEngine.MOD_ID)) {
                    instance.addTemporaryModifier(mod);
                }
            }
        }
    }

    @Inject(at = @At("TAIL"), method = "<init>")
    public void init(World world, BlockPos pos, float yaw, GameProfile gameProfile, CallbackInfo info) {
        nbtdirty = new boolean[inventory.size()];
        oldmodifiers = new Multimap[inventory.size()];
    }

}
