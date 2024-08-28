package com.example.main.Spells.custom;

import com.example.main.SpellUtil.SpellSchool;
import com.example.main.SpellUtil.Spells.Spell;
import com.example.main.Spells.custom.actions.ActionPerformable;
import com.example.main.Spells.custom.actions.TickHeal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class HealingSpell extends Spell {
    private ActionPerformable heal = new TickHeal(6);
    public HealingSpell(int manaCost, SpellSchool schools, int chargeTime, String name, int cooldown, Identifier texture, int min, int max, int cost) {
        super(manaCost, schools, chargeTime,  name, cooldown, texture, min, max, cost);
    }

    @Override
    public void castSpell(PlayerEntity player, World world, ItemStack stack) {
        heal.Perform(stack, player, world);
    }

    public boolean DownCast(PlayerEntity player) {
        boolean succes = super.DownCast(player);
        if (succes) {
            heal = new TickHeal(6 + Level() * 8);
        }
        return succes;
    }

    @Override
    public boolean Upcast(PlayerEntity player) {
        boolean succes = super.Upcast(player);
        if (succes) {
            heal = new TickHeal(6 + Level() * 8);
        }
        return succes;
    }
}
