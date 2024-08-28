package com.example.main.Item.custom;

import com.example.main.SpellUtil.Mana;
import com.example.main.SpellUtil.ManaContainer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class ManaShard extends Item {
    public ManaShard(Settings settings) {
        super(settings);
    }



    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        Mana mana = ((ManaContainer)user).getMana();
        mana.addMana(10);
        return TypedActionResult.pass(user.getStackInHand(hand));
    }

}
