package com.example.main.Spells.custom;

import com.example.N3rdyR0b1nsSpellEngine;
import com.example.main.Attributes.ModAttributes;
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

public class LifeStealSpell extends ContinousUsageSpell {
    private String id = N3rdyR0b1nsSpellEngine.MOD_ID + name;
    public LifeStealSpell(int manaCost, SpellSchool school, int chargeTime, String name, int cooldown, Identifier texture, int useTime, boolean needsHeld, int overtimecost) {
        super(manaCost, school, chargeTime, name, cooldown, texture, useTime, needsHeld, overtimecost);
    }

    @Override
    public void tickAction(PlayerEntity player, World world, ItemStack stack, int index, int tick) {

    }

    @Override
    public void castSpell(PlayerEntity player, World world, ItemStack stack) {
        if (!world.isClient) {
            stack.addAttributeModifier(ModAttributes.LIFESTEAL,
                    new EntityAttributeModifier(id, 2f, EntityAttributeModifier.Operation.ADDITION),
                    null);
            float hppenalty = 20;
            if (player.getMaxHealth() <= 20) {
                hppenalty = player.getMaxHealth() - 1;
            }
            stack.addAttributeModifier(EntityAttributes.GENERIC_MAX_HEALTH,
                    new EntityAttributeModifier(id, -hppenalty, EntityAttributeModifier.Operation.ADDITION),
                    null);
            stack.addAttributeModifier(ModAttributes.HP_ON_KILL,
                    new EntityAttributeModifier(id, 6, EntityAttributeModifier.Operation.ADDITION),
                    null);
            stack.addAttributeModifier(ModAttributes.MANA_ON_KILL,
                    new EntityAttributeModifier(id, 5, EntityAttributeModifier.Operation.ADDITION),
                    null);
            super.castSpell(player, world, stack);
        }
    }
    @Override
    public void endAction(PlayerEntity player ,World world, ItemStack stack) {
        player.getAttributes().removeModifiers(stack.getAttributeModifiers(EquipmentSlot.FEET));
        if (!world.isClient) {
            GenericSpellAbilities.removeAttributeModifier(stack, id);
            GenericSpellAbilities.removeAttributeModifier(stack, id);
            GenericSpellAbilities.removeAttributeModifier(stack, id);
            GenericSpellAbilities.removeAttributeModifier(stack, id);
        }
    }

}
