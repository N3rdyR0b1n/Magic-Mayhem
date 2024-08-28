package com.example.main.Item.custom;

import com.example.main.SpellUtil.AttributeModifierAbleItem;
import com.example.main.SpellUtil.Spells.NbtS;
import com.example.main.SpellUtil.Spells.NullSpell;
import com.example.main.SpellUtil.Spells.Spell;
import com.example.main.SpellUtil.Spells.SpellKeeper;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class SpellPaperItem extends Item implements SpellContainerItem, AttributeModifierAbleItem {
    public SpellPaperItem(Settings settings) {
        super(settings);
    }


    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        NbtCompound comp = NbtS.SpellNbt(stack);
        if (comp.get(NbtS.SPELL) instanceof NullSpell spell) {
            spell.castSpell(user, world, stack);
        }
        return TypedActionResult.success(stack);
    }

    @Override
    public Spell getSelectedSpell(ItemStack stack) {
        NbtCompound comp = NbtS.SpellNbt(stack);
        if (comp.get(NbtS.SPELL) instanceof Spell spell) {
            return spell;
        }
        return null;
    }

    @Override
    public void addTooltip(ItemStack stack, World world, ArrayList<Text> list, TooltipContext context, PlayerEntity player) {
        NbtCompound compound = NbtS.SpellNbt(stack);
        if (compound.get(NbtS.SPELL) instanceof Spell spell) {
            Text[] texts = spell.GetDescription(player, stack);
            for (Text text : texts) {
                list.add(text);
            }
        }
    }
}
