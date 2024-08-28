package com.example.main.Item.custom;

import com.example.main.Attributes.ModAttributes;
import com.example.main.Item.ModItems;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ContainerItem extends Item {
    public ContainerItem(Settings settings) {
        super(settings);
    }

    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.of("100 Xp -> 1"));
    }
    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        int i = 1;
        if (user.isInPose(EntityPose.CROUCHING)) {
            i = 64;
        }
        for (int a = 0; a < i; a++) {
            if (user.totalExperience >= 100) {
                user.addExperience(-100);
                user.getInventory().insertStack(new ItemStack(ModItems.EXPERIENCE));
            }
            else {
                break;
            }
        }


        return TypedActionResult.success(user.getStackInHand(hand));
    }

    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (selected && entity instanceof PlayerEntity player) {
            player.sendMessage(Text.literal("Your level : " + (int) player.getAttributeValue(ModAttributes.CASTING_LEVEL))
                    .fillStyle(Style.EMPTY.withColor(Formatting.WHITE)), true);
        }
    }

}
