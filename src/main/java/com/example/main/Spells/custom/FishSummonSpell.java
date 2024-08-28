package com.example.main.Spells.custom;

import com.example.main.SpellUtil.GenericSpellAbilities;
import com.example.main.SpellUtil.HitValues;
import com.example.main.SpellUtil.SpellSchool;
import com.example.main.SpellUtil.Spells.Spell;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.SalmonEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class FishSummonSpell extends Spell {
    public FishSummonSpell(int manaCost, SpellSchool school, int chargeTime, String name, int cooldown, Identifier texture, int min, int max, int cost) {
        super(manaCost, school, chargeTime, name, cooldown, texture, min, max, cost);
    }

    public void castSpell(PlayerEntity player, World world, ItemStack stack) {
        for (int i = -1; i < Level(); i++) {
            SalmonEntity salmon = new SalmonEntity(EntityType.SALMON, world);
            Text text = Text.translatable("MaxedCarp").setStyle(Style.EMPTY.withColor(Formatting.AQUA));
            HitValues values = GenericSpellAbilities.HitscanSelect(world, player, 2, false);
            salmon.setCustomName(text);
            salmon.setPosition(values.endPos());
            world.spawnEntity(salmon);
        }

    }

}
