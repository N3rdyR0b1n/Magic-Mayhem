package com.example.main.Spells.custom;

import com.example.main.SpellUtil.SpellSchool;
import com.example.main.SpellUtil.Spells.Spell;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class QuickRepairs extends Spell {


    public QuickRepairs(int manaCost, SpellSchool school, int chargeTime, String name, int cooldown, Identifier texture, int min, int max, int upcast) {
        super(manaCost, school, chargeTime, name, cooldown, texture, min, max, upcast);
    }

    @Override
    public void castSpell(PlayerEntity player, World world, ItemStack stack) {
        int restorationamount = 200;
        restorationamount = repair(stack, restorationamount);
        ArrayList<ItemStack> items = new ArrayList<ItemStack>();
        List<ItemStack> armor = player.getInventory().armor;
        items.add(player.getMainHandStack());
        items.add(player.getOffHandStack());
        for (int i = 0; i < armor.size(); i++) {
            items.add(armor.get(i));
        }
        for (int i = 0; i < items.size(); i++) {
            ItemStack repairstack = items.get(i);
            if (!repairstack.isDamageable()) {
                restorationamount +=100;
                continue;
            }
            restorationamount += repair(repairstack, 100);
            if (repairstack.getDamage() < 0) {
                repairstack.setDamage(0);
            }
            if (restorationamount <= 0 || items.isEmpty()) {
                break;
            }
            if (repairstack.getDamage() == repairstack.getMaxDamage()) {
                items.remove(i);
                i--;
            }
        }
        while (restorationamount > 0) {
            for (int i = 0; i < items.size(); i++) {
                ItemStack repairstack = items.get(i);
                restorationamount = repair(repairstack, restorationamount);
                if (repairstack.getDamage() == 0) {
                    items.remove(i);
                    i--;
                }
            }
            if (restorationamount <= 0 || items.isEmpty()) {
                break;
            }
        }
    }

    private int repair(ItemStack stack, int restorationamount) {
        if (stack.isDamageable()) {
            if (stack.getDamage() > 0) {
                if (stack.getDamage() > restorationamount) {
                    stack.setDamage(stack.getDamage() - restorationamount);
                    return 0;
                }
                int returnvalue = restorationamount - stack.getDamage();
                stack.setDamage(stack.getDamage() - restorationamount);
                return returnvalue;
            }
            return restorationamount;
        }
        return restorationamount;
    }

}
