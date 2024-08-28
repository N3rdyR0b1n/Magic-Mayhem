package com.example.main.Item.custom;

import com.example.main.Attributes.ModAttributes;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class ManaShell extends Item {
    public ManaShell(Settings settings) {
        super(settings);
    }

    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {

        user.getAttributes().getCustomInstance(ModAttributes.MAXIMUM_MANA).
                addTemporaryModifier(new EntityAttributeModifier("Extra_mana", 5, EntityAttributeModifier.Operation.ADDITION ));

        return TypedActionResult.pass(user.getStackInHand(hand));

    }
}
