package com.example.main.Spells.custom.weather;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;

public class RainyWeather extends NameCostAction{
    public RainyWeather(int price, int up) {
        super(price,up, "Weather : [Rain]");
    }

    @Override
    public void Perform(ItemStack stack, PlayerEntity player, World world, int level) {
        if (world instanceof ServerWorld server) {
            server.setWeather(0, 2400+ 900 * level, true, false);
        }
    }
}
