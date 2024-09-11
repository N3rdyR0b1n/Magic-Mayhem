package com.example.main.Spells.custom;

import com.example.N3rdyR0b1nsSpellEngine;
import com.example.main.Attributes.ModAttributes;
import com.example.main.SpellUtil.AttributeBuffable;
import com.example.main.SpellUtil.GenericSpellAbilities;
import com.example.main.SpellUtil.SpellSchool;
import com.example.main.SpellUtil.Spells.NbtS;
import com.example.main.Spells.extra.ContinousUsageSpell;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class HasteSpell extends ContinousUsageSpell {

    private static String id = N3rdyR0b1nsSpellEngine.MOD_ID+"Haste";

    private String usid = N3rdyR0b1nsSpellEngine.MOD_ID+"Haste";
    private Integer entityid = null;
    public boolean self = true;
    public HasteSpell(int manaCost, SpellSchool school, int chargeTime, String name, int cooldown, Identifier texture, int useTime, boolean needsHeld, int overtimecost, int min, int max, int up, int uptime) {
        super(manaCost, school, chargeTime, name, cooldown, texture, useTime, needsHeld, overtimecost, min, max, up, uptime);
    }

    public void castSecSpell(PlayerEntity player, World world, ItemStack stack) {
        if (!world.isClient()) {
            if (self) {
                world.playSound(null, player.getBlockPos(), SoundEvents.BLOCK_LEVER_CLICK, SoundCategory.PLAYERS, 0.3f, 0.6f);
                self = false;
                return;
            }
            world.playSound(null, player.getBlockPos(), SoundEvents.BLOCK_LEVER_CLICK, SoundCategory.PLAYERS, 0.3f, 0.5f);
            self = true;
        }
    }

    public boolean canSecCastSpell(PlayerEntity player, World world, ItemStack stack, NbtCompound nbt) {
        return UseTime < 0;
    }

    @Override
    public void castSpell(PlayerEntity player, World world, ItemStack stack) {
        if (!world.isClient) {
            if (self) {
                stack.addAttributeModifier(EntityAttributes.GENERIC_ATTACK_SPEED,
                        new EntityAttributeModifier(id, 0.25f, EntityAttributeModifier.Operation.MULTIPLY_TOTAL),
                        EquipmentSlot.FEET);
                stack.addAttributeModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED,
                        new EntityAttributeModifier(id, 0.25f, EntityAttributeModifier.Operation.MULTIPLY_TOTAL),
                        EquipmentSlot.FEET);
                stack.addAttributeModifier(ModAttributes.CAST_SPEED,
                        new EntityAttributeModifier(id, 0.15f, EntityAttributeModifier.Operation.MULTIPLY_TOTAL),
                        EquipmentSlot.FEET);
                stack.addAttributeModifier(EntityAttributes.GENERIC_ARMOR,
                        new EntityAttributeModifier(id, 2, EntityAttributeModifier.Operation.ADDITION),
                        EquipmentSlot.FEET);
            }
            else {
                if (GenericSpellAbilities.MarkHitscanSelect(world, player, NbtS.SpellNbt(stack),10, true).getTarget() instanceof LivingEntity living) {
                    AttributeBuffable buffable = ((AttributeBuffable) living);
                    Multimap<EntityAttribute, EntityAttributeModifier> builder = HashMultimap.create();
                    AttributeContainer container = living.getAttributes();
                    if (container.hasAttribute(ModAttributes.CAST_SPEED)) {
                        builder.put(ModAttributes.CAST_SPEED,
                                new EntityAttributeModifier(id, 0.15f, EntityAttributeModifier.Operation.MULTIPLY_TOTAL));
                    }
                    if (container.hasAttribute(EntityAttributes.GENERIC_ATTACK_SPEED)) {
                        builder.put(EntityAttributes.GENERIC_ATTACK_SPEED,
                                new EntityAttributeModifier(id, 0.25f, EntityAttributeModifier.Operation.MULTIPLY_TOTAL));
                    }
                    if (container.hasAttribute(EntityAttributes.GENERIC_MOVEMENT_SPEED)) {
                        builder.put(EntityAttributes.GENERIC_MOVEMENT_SPEED,
                                new EntityAttributeModifier(id, 0.15f, EntityAttributeModifier.Operation.MULTIPLY_TOTAL));
                    }
                    if (container.hasAttribute(EntityAttributes.GENERIC_ARMOR)) {
                        builder.put(EntityAttributes.GENERIC_ARMOR,
                                new EntityAttributeModifier(id, 2, EntityAttributeModifier.Operation.ADDITION));
                    }
                    usid = buffable.AddAttributeModifier(builder, 10, true, id);
                    container.addTemporaryModifiers(builder);
                    entityid = living.getId();
                }
            }
            super.castSpell(player, world, stack);
        }
    }
    @Override
    public void endAction(PlayerEntity player ,World world, ItemStack stack) {
        if (!world.isClient) {
            if (self) {
                player.getAttributes().removeModifiers(stack.getAttributeModifiers(EquipmentSlot.FEET));
                GenericSpellAbilities.removeAttributeModifier(stack, id);
                GenericSpellAbilities.removeAttributeModifier(stack, id);
                GenericSpellAbilities.removeAttributeModifier(stack, id);
                GenericSpellAbilities.removeAttributeModifier(stack, id);
            }
            super.endAction(player,world,stack);
        }
    }
    @Override
    public void tickAction(PlayerEntity player, World world, ItemStack stack, int index, int tick) {
        if (!world.isClient() && !self && entityid != null) {
            if (world.getEntityById(entityid) instanceof AttributeBuffable buffable) {
                if (!buffable.maintainModifier(usid, 2)) {
                    endAction(player,world,stack);
                    applyCooldown(player,stack, stack.getSubNbt(NbtS.SLOT + index), index);
                }
            }
        }
    }

    @Override
    public String getExtraInfo(ItemStack stack) {
        return self ? "Current mode : Self" : "Current mode : Target";
    }
    @Override
    public void applyCost(PlayerEntity player, ItemStack stack, NbtCompound nbt, int slot) {
        if (GenericSpellAbilities.HasTarget(nbt) || self) {
            super.applyCost(player, stack, nbt, slot);
        }
    }
    @Override
    public void applyCooldown(PlayerEntity player, ItemStack stack, NbtCompound nbt, int slot) {
        if (GenericSpellAbilities.HasTarget(nbt) || self) {
            super.applyCooldown(player, stack, nbt, slot);
            GenericSpellAbilities.ClearTarget(nbt);
        }
    }
    @Override
    public void applyTickCost(PlayerEntity player, ItemStack stack, NbtCompound nbt, int slot, int tick) {
        if (self || (GenericSpellAbilities.HasTarget(nbt))) {
            super.applyTickCost(player,stack,nbt,slot,tick);
        }
    }

}
