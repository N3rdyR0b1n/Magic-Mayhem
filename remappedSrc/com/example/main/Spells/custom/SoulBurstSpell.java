package com.example.main.Spells.custom;

import com.example.main.SpellUtil.*;
import com.example.main.SpellUtil.Spells.NbtS;
import com.example.main.Spells.extra.ContinousUsageSpell;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class SoulBurstSpell extends ContinousUsageSpell {
    public SoulBurstSpell(int manaCost, SpellSchool school, int chargeTime, String name, int cooldown, Identifier texture, int useTime, boolean needsHeld, int overtimecost, int min, int max, int up, int uptime) {
        super(0, school, chargeTime, name, cooldown, texture, useTime, needsHeld, overtimecost, min, max, up, uptime);
        finishManaCost = manaCost;
        text = GetText();
    }

    @Override
    public void castSpell(PlayerEntity player, World world, ItemStack stack) {
        super.castSpell(player, world, stack);
        world.playSound(null, player.getBlockPos(), SoundEvents.ENTITY_WARDEN_SONIC_CHARGE, SoundCategory.PLAYERS, 2, (float) 40 / GetChargeTime(player, stack));

    }

    public void endAction(PlayerEntity player ,World world, ItemStack stack) {
        if (UseTime == 0) {
            int level = Level();
            world.playSound(null, player.getBlockPos(), SoundEvents.ENTITY_WARDEN_SONIC_BOOM, SoundCategory.PLAYERS, 2, 1);
            GenericSpellAbilities.explodeFlatAoe(40 + 10 * level, 2 + 0.5f * level, 1, world, player, player, this, 1, 1);
            double x = player.getX();
            double y = player.getY() + 1;
            double z = player.getZ();
            level++;
            for (int i = 0; i < 100; i++) {
                Vec3d particlevec = new Vec3d(world.random.nextFloat() - 0.5f, world.random.nextFloat() * 0.5f, world.random.nextFloat() - 0.5f).normalize().multiply(world.random.nextFloat() + 0.5f * level);
                world.addImportantParticle(ParticleTypes.SCULK_SOUL, true, x, y, z, particlevec.x, particlevec.y, particlevec.z);
            }
            world.playSound(null, player.getBlockPos(), SoundEvents.ENTITY_WARDEN_SONIC_BOOM, SoundCategory.PLAYERS, 3, 1f);
            world.playSound(null, player.getBlockPos(), SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.PLAYERS, 3, 1f);
            applyFinishCost(player, stack, stack.getNbt(), stack.getNbt().getInt(NbtS.SELECT));
            if (!world.isClient()) {
                applyCooldown(player, stack, stack.getSubNbt(NbtS.getNbt(stack)), stack.getNbt().getInt(NbtS.SELECT));
            }
        }
    }
    @Override
    public void tickAction(PlayerEntity player, World world, ItemStack stack, int index, int tick) {
        float distance = (float) tick / GetChargeTime(player, stack);
        distance *= distance;
        float progress = 6.2831f * distance;
        distance *= -5f;
        double x = player.getX();
        double y = player.getY() + 1f;
        double z = player.getZ();
        if (tick % 7 == 0) {
            for (float i = 0; i < 6.283f; i += 0.2f) {
                Particle(world, x, y, z, i, 5);
            }
        }
        Particle(world, x, y, z, progress, distance);
        Particle(world, x, y, z,progress + 1.5708f, distance);
        Particle(world, x, y, z,progress + 3.1415f, distance);
        Particle(world, x, y, z,progress + 4.7124f, distance);
    }

    private void Particle(World world, double x,double y,double z, float i, float distance) {
        world.addImportantParticle(ParticleTypes.SCULK_SOUL, true, (MathHelper.sin(i) * distance) + x, y, (MathHelper.cos(i) * distance) + z, 0, 0.05, 0);
    }



    @Override
    public void applyCooldown(PlayerEntity player, ItemStack stack, NbtCompound nbt, int slot) {
        if (UseTime >= 0) {
            super.applyCooldown(player,stack,nbt,slot);
        }
    }

    @Override
    public boolean canEnd(PlayerEntity player, ItemStack stack, NbtCompound spellnbt) {
        return ((ManaContainer)player).getMana().getStoredMana() >= FinManacost();
    }
}
