package com.example.main.Item.custom;

import com.example.main.SpellUtil.Spells.Spell;
import com.example.main.Spells.ModSpells;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.MapMaker;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.Base64;
import java.util.HashMap;

public class Tester extends Item {
    public Tester(Settings settings) {
        super(settings);
    }

    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {

        user.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).addPersistentModifier(new EntityAttributeModifier(":2", 2, EntityAttributeModifier.Operation.ADDITION));
        return TypedActionResult.success(user.getStackInHand(hand));
    }

    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (stack.hasNbt() && entity instanceof PlayerEntity player) {
            NbtCompound compound = stack.getNbt();
            Spell spell = ((Spell)compound.get("Spell"));
            player.sendMessage(Text.of(compound.getInt(":3") + spell.name + spell.LeftClickManaCost + compound.getString("<|:3") ));

            if (!world.isClient) {
                spell.LeftClickManaCost--;
            }
        }
    }
}
