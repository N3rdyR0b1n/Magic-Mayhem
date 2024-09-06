package com.example.main.Spells.custom;

import com.example.main.SpellUtil.SpellSchool;
import com.example.main.Spells.ModSpells;
import com.example.main.Spells.custom.actions.ActionPerformable;
import com.example.main.Spells.custom.actions.TickHeal;
import com.example.main.Spells.extra.ContinousUsageSpell;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class ToggleHealling extends ContinousUsageSpell {
    private ActionPerformable heal = new TickHeal(1);
    public ToggleHealling(int manaCost, SpellSchool school, int chargeTime, String name, int cooldown, Identifier texture, int useTime, boolean needsHeld, int overtimecost, int min, int max, int up, int uptime) {
        super(manaCost, school, chargeTime, name, cooldown, texture, useTime, needsHeld, overtimecost, min, max, up,uptime);
        text = ModSpells.FormatSpell(name, manaCost,level);
    }

    @Override
    public void tickAction(PlayerEntity player, World world, ItemStack stack, int index, int tick) {
        if (tick % 20 == 0) {
            heal.Perform(stack, player, world);
        }
    }
    public String GetText() {
        return ModSpells.FormatSpell(name, Manacost(), level);
    }

}
