package com.example.main.Spells.custom.weather;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;

public class SunshineWeather extends NameCostAction{
    public SunshineWeather(int price, int up) {
        super(price,up, "Weather : [Sunshine]");
    }

    @Override
    public void Perform(ItemStack stack, PlayerEntity player, World world, int level) {
        if (world instanceof ServerWorld server) {
            server.setWeather(2400 + 900 * level, 0, false, false);
        }

    }
}
