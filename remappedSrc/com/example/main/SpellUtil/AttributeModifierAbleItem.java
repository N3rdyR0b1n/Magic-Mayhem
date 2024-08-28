package com.example.main.SpellUtil;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public interface AttributeModifierAbleItem {



    void addTooltip(ItemStack stack, World world, ArrayList<Text> list, TooltipContext context, PlayerEntity player);
}
