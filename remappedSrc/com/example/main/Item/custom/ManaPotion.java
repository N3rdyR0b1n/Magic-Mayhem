package com.example.main.Item.custom;

import com.example.main.SpellUtil.ManaContainer;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ManaPotion extends Item {
    public ManaPotion(Settings settings) {
        super(settings);
    }

    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {

    }

    public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
        if (user instanceof PlayerEntity player) {
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 2, 2));
            float progress = ((getMaxUseTime(stack) - remainingUseTicks)) / 20;

            if (progress > 10) {
                progress = 0.75f;
            }
            else if (progress > 3) {
                progress= 0.25f;
            }
            else {
                progress = 0.05f;
            }
            ((ManaContainer) player).getMana().addMana(progress);
        }
    }


    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 3600;
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.DRINK;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        user.setCurrentHand(hand);

        stack.getOrCreateNbt().putFloat("Absorption" ,user.getAbsorptionAmount());
        stack.getNbt().putBoolean("Drinking", true);
        user.setAbsorptionAmount(0);
        return ItemUsage.consumeHeldItem(world, user, hand);
    }
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (stack.hasNbt() && entity instanceof PlayerEntity player && !player.isUsingItem() && stack.getNbt().getBoolean("Drinking")) {
            stack.decrement(1);
            player.getInventory().insertStack(new ItemStack(Items.GLASS_BOTTLE));
            world.playSound(player, player.getBlockPos(), SoundEvents.BLOCK_BEACON_POWER_SELECT, SoundCategory.PLAYERS, 1, 2);
        }
    }
}
