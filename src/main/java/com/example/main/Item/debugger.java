package com.example.main.Item;

import com.example.main.SpellUtil.GenericSpellAbilities;
import com.example.main.Spells.extra.ContinousUsageSpell;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.UUID;

public class debugger extends Item {
    public debugger(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        EntityAttributeModifier modifier =  new EntityAttributeModifier("rizz", 2, EntityAttributeModifier.Operation.ADDITION);

        ItemStack itemStack = user.getStackInHand(hand);
        if (user.isInPose(EntityPose.CROUCHING)) {
            GenericSpellAbilities.removeAttributeModifier(itemStack, "rizz");
        }
        else {
            itemStack.addAttributeModifier(EntityAttributes.GENERIC_ATTACK_SPEED,
                   modifier, EquipmentSlot.FEET); }
        return TypedActionResult.success(itemStack);

    }

}
