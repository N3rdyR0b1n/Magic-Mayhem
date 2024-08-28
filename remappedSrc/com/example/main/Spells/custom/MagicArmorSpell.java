package com.example.main.Spells.custom;

import com.example.N3rdyR0b1nsSpellEngine;
import com.example.main.SpellUtil.GenericSpellAbilities;
import com.example.main.SpellUtil.SpellSchool;
import com.example.main.Spells.ModSpells;
import com.example.main.Spells.extra.ContinousUsageSpell;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class MagicArmorSpell extends ContinousUsageSpell {

    private String id = N3rdyR0b1nsSpellEngine.MOD_ID+name;
    private final int amount;
    public MagicArmorSpell(int manaCost, SpellSchool school, int chargeTime, String name, int cooldown, Identifier texture, int useTime, boolean needsHeld, int overtimecost, int armor, int min, int max, int up, int uptime) {
        super(manaCost, school, chargeTime, name, cooldown, texture, useTime, needsHeld, overtimecost, min, max, up, uptime);
        this.amount = armor;
    }

    @Override
    public void tickAction(PlayerEntity player, World world, ItemStack stack, int index, int tick) {

    }

    @Override
    public void castSpell(PlayerEntity player, World world, ItemStack stack) {
        if (!world.isClient()) {
            super.castSpell(player, world, stack);
            stack.addAttributeModifier(EntityAttributes.GENERIC_ARMOR,
                    new EntityAttributeModifier(id, amount, EntityAttributeModifier.Operation.ADDITION),
                    EquipmentSlot.FEET);
        }
    }
    @Override
    public void endAction(PlayerEntity player ,World world, ItemStack stack) {
        player.getAttributes().removeModifiers(stack.getAttributeModifiers(EquipmentSlot.FEET));
        GenericSpellAbilities.removeAttributeModifier(stack, id);
    }
    @Override
    public String GetText() {
        return ModSpells.FormatSpell(name, Manacost(), level);
    }


}
