package com.example.main.Spells.custom.weather;


import com.example.main.Spells.custom.actions.ActionPerformable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class NameCostAction extends ActionPerformable {
    public final int price;
    public final String name;
    public final int upprice;

    public NameCostAction(int price, int up, String name) {
        this.name = name;
        this.price = price;
        this.upprice = price;
    }

    public void Perform(ItemStack stack, PlayerEntity player, World world, int level) {

    }


}
