package com.example.main.Spells.custom;

import com.example.main.SpellUtil.SpellSchool;
import com.example.main.Spells.extra.ContinousUsageSpell;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class MendingSpell extends ContinousUsageSpell {


    public MendingSpell(int manaCost, SpellSchool school, int chargeTime, String name, int cooldown, Identifier texture, int useTime, boolean needsHeld, int overtimecost, int min, int max, int upcast, int upovertime) {
        super(manaCost, school, chargeTime, name, cooldown, texture, useTime, needsHeld, overtimecost, min, max, upcast, upovertime);
    }

    public void tickAction(PlayerEntity user , World world, ItemStack stack, int index, int tick) {
        if (tick % 40 == 0) {
            ArrayList<ItemStack> items = new ArrayList<ItemStack>();
            List<ItemStack> armor = user.getInventory().armor;
            items.add(user.getMainHandStack());
            items.add(user.getOffHandStack());
            for (int i = 0; i < armor.size(); i++) {
                items.add(armor.get(i));
            }
            for (int i = 0; i < items.size(); i++) {
                repair(items.get(i), 4);
            }
        }
    }

    private void repair(ItemStack stack, int restorationamount) {
        if (stack.isDamageable()) {
            if (stack.getDamage() > 0) {
                stack.setDamage(stack.getDamage() - restorationamount);
                    if (stack.getDamage() < 0) {
                        stack.setDamage(0);
                    }
                }
            }
    }

}
