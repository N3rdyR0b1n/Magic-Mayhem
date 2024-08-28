package com.example.main.Spells.custom;

import com.example.main.SpellUtil.*;
import com.example.main.SpellUtil.Spells.NbtS;
import com.example.main.SpellUtil.Spells.Spell;
import com.example.main.Spells.custom.weather.*;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class WeatherForecastSpell extends Spell {
    private NameCostAction[] WeatherTypes;
    private byte weather = 0;
    public WeatherForecastSpell(int sun, int rain, int thunder, int manacost, SpellSchool school, int chargeTime, String name, int cooldown, Identifier texture, int min, int max, int up) {
        super(0, school, chargeTime, name, cooldown, texture, min, max, up);
        WeatherTypes = new NameCostAction[4];
        WeatherTypes[0] = new SunshineWeather(sun, 20);
        WeatherTypes[1] = new RainyWeather(rain, 25);
        WeatherTypes[2] = new ThunderWeather(thunder, 45);
        WeatherTypes[3] = new StormWeather(manacost, 60);
    }

    @Override
    public void castSpell(PlayerEntity player, World world, ItemStack stack) {
        String index = NbtS.getNbt(stack);
        NbtCompound nbt = stack.getSubNbt(index);
        if (weather >= 4 || weather < 0) {
            nbt.putByte("Weather", (byte) 0);
        }
        else if (player.isInPose(EntityPose.CROUCHING)) {
            weather++;
            if (weather >= 4) {
                weather = 0;
            }
            nbt.putByte("Weather", weather);
            stack.setSubNbt(NbtS.getNbt(stack), nbt);
        } else {
            WeatherTypes[weather].Perform(stack, player, world, Level());
        }
    }
    @Override
    public String getInfo(PlayerEntity player, ItemStack stack) {
        if (weather < 0 || weather > 3) {return "null";}
        return text;
    }
    @Override
    public String getExtraInfo(ItemStack stack) {
        if (weather < 0 || weather > 3) {return "null";}
        return WeatherTypes[weather].name;
    }

    @Override
    public void applyCooldown(PlayerEntity player, ItemStack stack, NbtCompound nbt, int slot) {
        if (player.isInPose(EntityPose.CROUCHING)) {
            return;
        }
        super.applyCooldown(player,stack,nbt,slot);
    }


    @Override
    public void applyCost(PlayerEntity player, ItemStack stack, NbtCompound nbt, int slot) {
        if (player.isInPose(EntityPose.CROUCHING)){
            return;
        }
        super.applyCost(player,stack,nbt,slot);
    }



    public int Manacost() {
        if (this.WeatherTypes != null) {
            return WeatherTypes[weather].price + WeatherTypes[weather].upprice * Level();
        }
        else {
            return 0;
        }
    }

}
