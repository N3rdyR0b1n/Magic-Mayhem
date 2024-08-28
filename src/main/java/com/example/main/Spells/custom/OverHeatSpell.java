package com.example.main.Spells.custom;

import com.example.N3rdyR0b1nsSpellEngine;
import com.example.main.Attributes.ModAttributes;
import com.example.main.SpellUtil.DamageTypes.ModDamageTypes;
import com.example.main.SpellUtil.GenericSpellAbilities;
import com.example.main.SpellUtil.SpellSchool;
import com.example.main.Spells.extra.ContinousUsageSpell;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class OverHeatSpell extends ContinousUsageSpell {
    private String id = N3rdyR0b1nsSpellEngine.MOD_ID+name;
    public OverHeatSpell(int manaCost, SpellSchool school, int chargeTime, String name, int cooldown, Identifier texture, int useTime, boolean needsHeld, int overtimecost, int min, int max, int upc, int upt) {
        super(manaCost, school, chargeTime, name, cooldown, texture, useTime, needsHeld, overtimecost, min, max, upc, upt);
    }

    @Override
    public void castSpell(PlayerEntity player, World world, ItemStack stack) {
        if (!world.isClient) {
            super.castSpell(player, world, stack);
            stack.addAttributeModifier(ModAttributes.HP_ON_KILL,
                    new EntityAttributeModifier(id, 10f, EntityAttributeModifier.Operation.ADDITION),
                    EquipmentSlot.FEET);
            stack.addAttributeModifier(ModAttributes.MANA_ON_KILL,
                    new EntityAttributeModifier(id, 10f, EntityAttributeModifier.Operation.ADDITION),
                    EquipmentSlot.FEET);
            stack.addAttributeModifier(ModAttributes.MANA_ON_HIT,
                    new EntityAttributeModifier(id, 1, EntityAttributeModifier.Operation.ADDITION),
                    EquipmentSlot.FEET);
            stack.addAttributeModifier(ModAttributes.HP_ON_HIT,
                    new EntityAttributeModifier(id, 1, EntityAttributeModifier.Operation.ADDITION),
                    EquipmentSlot.FEET);
        }
    }

    @Override
    public void tickAction(PlayerEntity player, World world, ItemStack stack, int index, int tick) {
        if (tick % 40 == 0) {
            player.damage(player.getDamageSources().create(ModDamageTypes.MAGIC_TICK), 2);
        }
    }
    @Override
    public void endAction(PlayerEntity player ,World world, ItemStack stack) {
        if (!world.isClient) {
            player.getAttributes().removeModifiers(stack.getAttributeModifiers(EquipmentSlot.FEET));
            GenericSpellAbilities.removeAttributeModifier(stack, id);
            GenericSpellAbilities.removeAttributeModifier(stack, id);
            GenericSpellAbilities.removeAttributeModifier(stack, id);
            GenericSpellAbilities.removeAttributeModifier(stack, id);
        }
    }
    @Override
    public String GetText() {
        StringBuilder builder = new StringBuilder();
        builder.append(name);
        builder.append(" - ");
        builder.append("LV : [");
        builder.append(level);
        builder.append("]");
        return builder.toString();
    }
}
