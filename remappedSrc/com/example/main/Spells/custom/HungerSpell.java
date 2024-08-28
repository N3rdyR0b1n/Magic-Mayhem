package com.example.main.Spells.custom;

import com.example.N3rdyR0b1nsSpellEngine;
import com.example.main.Attributes.ModAttributes;
import com.example.main.SpellUtil.GenericSpellAbilities;
import com.example.main.SpellUtil.SpellSchool;
import com.example.main.Spells.extra.ContinousUsageSpell;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class HungerSpell extends ContinousUsageSpell {
    private String id = N3rdyR0b1nsSpellEngine.MOD_ID + name;

    public HungerSpell(int manaCost, SpellSchool school, int chargeTime, String name, int cooldown, Identifier texture, int useTime, boolean needsHeld, int overtimecost, int min, int max, int cost, int upcost) {
        super(manaCost, school, chargeTime, name, cooldown, texture, useTime, needsHeld, overtimecost, min, max, cost, upcost);
    }


    @Override
    public void castSpell(PlayerEntity player, World world, ItemStack stack) {
        super.castSpell(player, world, stack);
        stack.addAttributeModifier(ModAttributes.MANA_ON_KILL,
                new EntityAttributeModifier(id, 25, EntityAttributeModifier.Operation.ADDITION),
                EquipmentSlot.FEET);
        stack.addAttributeModifier(ModAttributes.HP_ON_KILL,
                new EntityAttributeModifier(id, 10, EntityAttributeModifier.Operation.ADDITION),
                EquipmentSlot.FEET);
    }
    @Override
    public void endAction(PlayerEntity player ,World world, ItemStack stack) {
        player.getAttributes().removeModifiers(stack.getAttributeModifiers(EquipmentSlot.FEET));
        if (!world.isClient) {
            GenericSpellAbilities.removeAttributeModifier(stack, id);
            GenericSpellAbilities.removeAttributeModifier(stack, id);
        }
    }

    @Override
    public void tickAction(PlayerEntity player, World world, ItemStack stack, int index, int tick) {

    }

}
