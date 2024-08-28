package com.example.main.Spells.custom;

import com.example.N3rdyR0b1nsSpellEngine;
import com.example.main.Attributes.ModAttributes;
import com.example.main.SpellUtil.GenericSpellAbilities;
import com.example.main.SpellUtil.SpellSchool;
import com.example.main.Spells.extra.ContinousUsageSpell;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class ChargeSpell extends ContinousUsageSpell {
    private String id = N3rdyR0b1nsSpellEngine.MOD_ID + name;
    public ChargeSpell(int manaCost, SpellSchool school, int chargeTime, String name, int cooldown, Identifier texture, int useTime, boolean needsHeld, int overtimecost, int min, int max, int upcast, int uptime) {
        super(manaCost, school, chargeTime, name, cooldown, texture, useTime, needsHeld, overtimecost, min, max, upcast, uptime);
    }
    @Override
    public void castSpell(PlayerEntity player, World world, ItemStack stack) {
        if (!world.isClient()) {
            stack.addAttributeModifier(ModAttributes.CAST_SPEED,
                    new EntityAttributeModifier(id, 0.25f, EntityAttributeModifier.Operation.MULTIPLY_TOTAL),
                    EquipmentSlot.FEET);
            stack.addAttributeModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED,
                    new EntityAttributeModifier(id, 0.25f, EntityAttributeModifier.Operation.MULTIPLY_TOTAL),
                    EquipmentSlot.FEET);
        }
        int Level = Level();
        GenericSpellAbilities.explodeFlatAoe(21f + Level, 0.5f + 0.2f * Level, 0.5f + 0.2f * Level, world, player, player, this, 2f, 1f);
        double x = player.getX();
        double y = player.getY() + 0.1f;
        double z = player.getZ();
        float radius = 5 + Level;
        for (float i = 0; i < 6.283f; i += 0.2f) {
            world.addImportantParticle(ParticleTypes.ELECTRIC_SPARK, true, (MathHelper.sin(i) * radius) + x, y, (MathHelper.cos(i) * radius) + z, 0, 0.05f, 0);
        }
        for (float i = 0; i < 6.283f; i += 0.2f) {
            world.addImportantParticle(ParticleTypes.ENCHANTED_HIT, true, (MathHelper.sin(i) * radius) +x, y, (MathHelper.cos(i) * radius) + z, 0, 0.3f, 0);
        }
        LightningEntity entity = new LightningEntity(EntityType.LIGHTNING_BOLT, world);
        entity.setPosition(player.getPos());
        world.spawnEntity(entity);
        super.castSpell(player,world,stack);
    }
    @Override
    public void tickAction(PlayerEntity player, World world, ItemStack stack, int index, int tick) {
        if (tick % 4 == 0) {
            for (int i = 0; i < 3; i++) {
                GenericSpellAbilities.ApplyAbsorption(player,stack,0.05f);
                world.addImportantParticle(ParticleTypes.ENCHANTED_HIT, true, player.getX() + world.random.nextFloat() - 0.5f, player.getY() + world.random.nextFloat() * 2.5, player.getZ() + world.random.nextFloat() - 0.5f, (world.random.nextFloat() - 0.5f), (world.random.nextFloat() - 0.5f), (world.random.nextFloat() - 0.5f));
            }
        }
        super.tickAction(player,world,stack,index,tick);
    }
    @Override
    public void endAction(PlayerEntity player ,World world, ItemStack stack) {
         if (!world.isClient) {
            player.getAttributes().removeModifiers(stack.getAttributeModifiers(EquipmentSlot.FEET));
            GenericSpellAbilities.removeAttributeModifier(stack, id);
            GenericSpellAbilities.removeAttributeModifier(stack, id);
         }
         super.endAction(player,world,stack);
    }
}
