package com.example.main.Spells.custom;

import com.example.N3rdyR0b1nsSpellEngine;
import com.example.main.SpellUtil.GenericSpellAbilities;
import com.example.main.SpellUtil.SpellSchool;
import com.example.main.Spells.extra.ContinousUsageSpell;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class FrostArmorSpell extends ContinousUsageSpell {

    private static String id = N3rdyR0b1nsSpellEngine.MOD_ID+"Frozen Shield";
    private final int amount;
    public FrostArmorSpell(int manaCost, SpellSchool school, int chargeTime, String name, int cooldown, Identifier texture, int useTime, boolean needsHeld, int overtimecost, int armor, int min, int max, int up, int uptime) {
        super(manaCost, school, chargeTime, name, cooldown, texture, useTime, needsHeld, overtimecost, min, max, up, uptime);
        this.amount = armor;
    }

    @Override
    public void tickAction(PlayerEntity player, World world, ItemStack stack, int index, int tick) {
        int freeze = player.getFrozenTicks();
        if (GenericSpellAbilities.InvisibleArmor(player, 5)) {
            player.setFrozenTicks(freeze + 2);
        }
        if (freeze > 200 ) {
            if (tick % 20 == 0) {
                player.damage(player.getDamageSources().freeze(), (float) (freeze / 20 - 9));
            }
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 2, 0), player);
        }
    }

    @Override
    public void castSpell(PlayerEntity player, World world, ItemStack stack) {
        super.castSpell(player, world, stack);
        stack.addAttributeModifier(EntityAttributes.GENERIC_ARMOR,
                new EntityAttributeModifier(id, amount, EntityAttributeModifier.Operation.ADDITION),
                EquipmentSlot.FEET);
        stack.addAttributeModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED,
                new EntityAttributeModifier(id, -0.10, EntityAttributeModifier.Operation.MULTIPLY_TOTAL),
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



}
