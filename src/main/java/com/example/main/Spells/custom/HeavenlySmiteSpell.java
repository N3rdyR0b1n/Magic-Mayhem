package com.example.main.Spells.custom;

import com.example.main.Entity.custom.HeavenlySmiteEntity;
import com.example.main.SpellUtil.GenericSpellAbilities;
import com.example.main.SpellUtil.SpellSchool;
import com.example.main.SpellUtil.Spells.Spell;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class HeavenlySmiteSpell extends Spell {
    public HeavenlySmiteSpell(int manaCost, SpellSchool school, int chargeTime, String name, Identifier texture, int min, int max, int upcost) {
        super(0, school, chargeTime, name, 40, texture, min, max, upcost);
        super.finishManaCost = manaCost;
        super.text = GetText();
    }

    @Override
    public void castReleaseSpell(PlayerEntity player, World world, ItemStack stack, int tick) {
        HeavenlySmiteEntity smite = new HeavenlySmiteEntity(world, player, Level());
        smite.setPosition(GenericSpellAbilities.HitscanSelect(world, player, 256, false).endPos().add(0, 256 + 64 * Level(), 0));
        world.spawnEntity(smite);
    }
}
