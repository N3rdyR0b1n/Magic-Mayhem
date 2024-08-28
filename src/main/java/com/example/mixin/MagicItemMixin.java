package com.example.mixin;

import com.example.main.SpellUtil.AttributeModifierAbleItem;
import com.example.main.SpellUtil.Spells.NbtS;
import com.google.common.collect.Lists;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FilledMapItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.registry.Registries;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;

@Mixin(ItemStack.class)
public abstract class MagicItemMixin {


    @Shadow public abstract @Nullable NbtCompound getNbt();

    @Inject(method = "getTooltip", at = @At("HEAD"), cancellable = true)
    private void injecttooltip(@Nullable PlayerEntity player, TooltipContext context,  CallbackInfoReturnable<List<Text>> returnable) {
        ItemStack stack = (ItemStack) (Object) this;
        if (stack.getItem() instanceof AttributeModifierAbleItem) {
            int i;
            Integer integer;
            ArrayList<Text> list = Lists.newArrayList();
            MutableText mutableText = Text.empty().append(stack.getName()).formatted(stack.getRarity().formatting);
            if (stack.hasCustomName()) {
                mutableText.formatted(Formatting.ITALIC);
            }
            list.add(mutableText);
            if (!context.isAdvanced() && !stack.hasCustomName() && stack.isOf(Items.FILLED_MAP) && (integer = FilledMapItem.getMapId(stack)) != null) {
                list.add(Text.literal("#" + integer).formatted(Formatting.GRAY));
            }
            if (MagicItemMixin.isSectionVisible(i = MagicItemMixin.getHideFlags(stack), ItemStack.TooltipSection.ADDITIONAL)) {
                ((AttributeModifierAbleItem)stack.getItem()).addTooltip(stack, player == null ? null : player.getWorld(), list, context, player);
            }
            returnable.setReturnValue(list);
        }
    }


    private static int getHideFlags(ItemStack stack) {
        if (stack.hasNbt() && stack.getNbt().contains("HideFlags", NbtElement.NUMBER_TYPE)) {
            return stack.getNbt().getInt("HideFlags");
        }
        return 0;
    }

    private static boolean isSectionVisible(int flags, ItemStack.TooltipSection tooltipSection) {
        return (flags & tooltipSection.getFlag()) == 0;
    }
    @Inject(method = "writeNbt", at = @At("HEAD"), cancellable = true)
    private void injecttooltip(NbtCompound nbt, CallbackInfoReturnable<NbtCompound> returnable) {
        NbtCompound compound = ((ItemStack) (Object) this).getNbt();
        if (compound != null && compound.get(NbtS.SLOT + 0) != null) {
            ItemStack stack = ((ItemStack) (Object) this);
            Identifier identifier = Registries.ITEM.getId(stack.getItem());
            nbt.putString("id", identifier == null ? "minecraft:air" : identifier.toString());
            nbt.putByte("Count", (byte)stack.getCount());
            try {
                nbt.put("tag", compound.copy());
            }
            catch (NullPointerException exception) {
                nbt.put("tag", new NbtCompound());
            }
            returnable.setReturnValue(nbt);
        }
    }
}
