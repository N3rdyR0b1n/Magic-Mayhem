package com.example.main.Spells.custom.weather;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;

public class ThunderWeather extends NameCostAction{
    public ThunderWeather(int pric , int up ) {
        super(pric,up, "Weather : [Thunder]");
    }

    @Override
    public void Perform(ItemStack stack, PlayerEntity player, World world, int level) {
        if (world instanceof ServerWorld server) {
            server.setWeather(0, 2400+ 900 * level, true, true);
        }
    }
}
