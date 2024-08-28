package com.example.main.Item.custom;

import com.example.main.Attributes.ModAttributes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class LimitBreakerItem extends Item {
    private final int min;
    private final int max;
    public LimitBreakerItem(Settings settings, int min, int max) {
        super(settings);
        this.min = min;
        this.max = max;
    }
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        double lvl = user.getAttributeValue(ModAttributes.CASTING_LEVEL);
        if (lvl >= min &&  lvl <= max) {
            user.getAttributeInstance(ModAttributes.CASTING_LEVEL).setBaseValue(user.getAttributeBaseValue(ModAttributes.CASTING_LEVEL) + 1);
            user.getStackInHand(hand).decrement(1);
        }
        return TypedActionResult.success(user.getStackInHand(hand));
    }

    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (selected && entity instanceof PlayerEntity player) {
            int lvl = (int) player.getAttributeValue(ModAttributes.CASTING_LEVEL);
            String a = "Cannot gain power from this item.";
            if (lvl >= min && lvl <= max) {
                a = "LV : " + lvl +  " -> " + (lvl + 1);
            }
            player.sendMessage(Text.literal(a)
                    .fillStyle(Style.EMPTY.withColor(Formatting.WHITE)), true);
        }
    }

}
