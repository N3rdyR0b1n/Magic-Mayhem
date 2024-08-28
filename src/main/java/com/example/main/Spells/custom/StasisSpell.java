package com.example.main.Spells.custom;

import com.example.N3rdyR0b1nsSpellEngine;
import com.example.main.Attributes.ModAttributes;
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
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class StasisSpell extends ContinousUsageSpell {
    private String id = N3rdyR0b1nsSpellEngine.MOD_ID+name;
    public StasisSpell(int manaCost, SpellSchool school, int chargeTime, String name, int cooldown, Identifier texture, int useTime, boolean needsHeld, int overtimecost, int castlevel) {
        super(manaCost, school, chargeTime, name, cooldown, texture, useTime, needsHeld, overtimecost, castlevel, castlevel, 0 ,0);
    }

    @Override
    public void castSpell(PlayerEntity player, World world, ItemStack stack) {
        super.castSpell(player, world, stack);
        stack.addAttributeModifier(EntityAttributes.GENERIC_ARMOR,
                new EntityAttributeModifier(id, 100, EntityAttributeModifier.Operation.ADDITION),
                EquipmentSlot.FEET);
        stack.addAttributeModifier(EntityAttributes.GENERIC_ARMOR_TOUGHNESS,
                new EntityAttributeModifier(id, 100, EntityAttributeModifier.Operation.ADDITION),
                EquipmentSlot.FEET);
        stack.addAttributeModifier(ModAttributes.MANA_REGENERATION,
                new EntityAttributeModifier(id, -1, EntityAttributeModifier.Operation.MULTIPLY_TOTAL),
                EquipmentSlot.FEET);
        stack.addAttributeModifier(ModAttributes.PASSIVE_MANA_REGENERATION,
                new EntityAttributeModifier(id, -1, EntityAttributeModifier.Operation.MULTIPLY_TOTAL),
                EquipmentSlot.FEET);
    }

    @Override
    public void tickAction(PlayerEntity player, World world, ItemStack stack, int index, int tick) {
        player.setVelocity(0,0,0);
        player.fallDistance = 0;
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 2, 255));
        if (tick % 5 == 0) {
            double x = player.getX();
            double y = player.getY() + 1.2f;
            double z = player.getZ();
            float size = 2f;
            if (tick != 0) {
                size = ((float) (tick)/ MaxUseTime * 1.75f) + 0.25f;
            };
            for (float i = 0; i < 6.283f; i += 0.05) {
                world.addImportantParticle(ParticleTypes.ENCHANT, true, (MathHelper.sin(i) * size) + x, y, (MathHelper.cos(i) * size) + z, 0, 0, 0);
            }
            for (float i = 0; i < 6.283f; i += 0.05) {
                world.addImportantParticle(ParticleTypes.ENCHANT, true, (MathHelper.sin(i) * size) + x, (MathHelper.cos(i) * size) + y, z, 0, 0, 0);
            }
            for (float i = 0; i < 6.283f; i += 0.05) {
                world.addImportantParticle(ParticleTypes.ENCHANT, true,  x, (MathHelper.cos(i) * size) + y, (MathHelper.sin(i) * size) + z, 0, 0, 0);
            }
        }
    }

    @Override
    public void endAction(PlayerEntity player, World world, ItemStack stack) {
        if (!world.isClient) {
            player.getAttributes().removeModifiers(stack.getAttributeModifiers(EquipmentSlot.FEET));
            GenericSpellAbilities.removeAttributeModifier(stack, id);
            GenericSpellAbilities.removeAttributeModifier(stack, id);
            GenericSpellAbilities.removeAttributeModifier(stack, id);
            GenericSpellAbilities.removeAttributeModifier(stack, id);
        }
    }
}
