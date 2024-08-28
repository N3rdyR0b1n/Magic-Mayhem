package com.example.main.Item.spellfocus;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;

public interface LeftMouseItem {

    public void onLmbClickClient(PlayerEntity player, ItemStack stack);
    public void onLmbClickServer(PlayerEntity player, ItemStack stack, ServerWorld world);

    public void onLmbHoldClient(PlayerEntity player, ItemStack stack);

    public void onLmbHoldServer(PlayerEntity player, ItemStack stack, ServerWorld world);


}
