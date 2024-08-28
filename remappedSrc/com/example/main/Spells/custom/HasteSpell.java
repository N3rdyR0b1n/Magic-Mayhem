package com.example.main.Spells.custom;

import com.example.N3rdyR0b1nsSpellEngine;
import com.example.main.SpellUtil.GenericSpellAbilities;
import com.example.main.SpellUtil.SpellSchool;
import com.example.main.Spells.extra.ContinousUsageSpell;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class HasteSpell extends ContinousUsageSpell {

    private String id = N3rdyR0b1nsSpellEngine.MOD_ID+"Haste";
    public HasteSpell(int manaCost, SpellSchool school, int chargeTime, String name, int cooldown, Identifier texture, int useTime, boolean needsHeld, int overtimecost, int min, int max, int up, int uptime) {
        super(manaCost, school, chargeTime, name, cooldown, texture, useTime, needsHeld, overtimecost, min, max, up, uptime);
    }



    @Override
    public void castSpell(PlayerEntity player, World world, ItemStack stack) {
        if (!world.isClient) {
            stack.addAttributeModifier(EntityAttributes.GENERIC_ATTACK_SPEED,
                    new EntityAttributeModifier(id, 0.25f, EntityAttributeModifier.Operation.MULTIPLY_TOTAL),
                    EquipmentSlot.FEET);
            stack.addAttributeModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED,
                    new EntityAttributeModifier(id, 0.25f, EntityAttributeModifier.Operation.MULTIPLY_TOTAL),
                    EquipmentSlot.FEET);
            stack.addAttributeModifier(EntityAttributes.GENERIC_ARMOR,
                    new EntityAttributeModifier(id, 2, EntityAttributeModifier.Operation.ADDITION),
                    EquipmentSlot.FEET);
            super.castSpell(player, world, stack);
        }
    }
    @Override
    public void endAction(PlayerEntity player ,World world, ItemStack stack) {
        if (!world.isClient) {
            player.getAttributes().removeModifiers(stack.getAttributeModifiers(EquipmentSlot.FEET));
            GenericSpellAbilities.removeAttributeModifier(stack, id);
            GenericSpellAbilities.removeAttributeModifier(stack, id);
            GenericSpellAbilities.removeAttributeModifier(stack, id);
        }
    }


}
