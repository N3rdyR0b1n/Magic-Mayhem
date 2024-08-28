package com.example.main.Spells.custom;

import com.example.main.SpellUtil.Spells.NbtS;
import com.example.main.SpellUtil.Spells.Spell;
import com.example.main.SpellUtil.SpellSchool;
import com.example.main.Spells.extra.ContinousUsageSpell;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class SaveLoadSpell extends ContinousUsageSpell {
    public SaveLoadSpell(int manaCost, SpellSchool school, int chargeTime, String name, int cooldown, Identifier texture, int useTime, boolean needsHeld, int overtimecost, int minlevel, int maxlevel, int upcastCost, int upovertime) {
        super(manaCost, school, chargeTime, name, cooldown, texture, useTime, needsHeld, overtimecost, minlevel, maxlevel, upcastCost, upovertime);
    }

    private double x;
    private double y;
    private double z;

    public void endAction(PlayerEntity player, World world, ItemStack stack) {
        player.fallDistance = 0;
        if (player.hasVehicle()) {
            player.requestTeleportAndDismount(x, y, z);
        } else {
            player.requestTeleport(x, y, z);
        }
        player.requestTeleport(x, y, z);
        world.playSound(null, player.getBlockPos(), SoundEvents.ENTITY_ENDERMAN_TELEPORT, SoundCategory.PLAYERS, 2f, 1f);
        for (float i = 0; i < 6.2826f; i+=0.2f) {
            world.addImportantParticle(ParticleTypes.PORTAL, true, Math.sin(i) + x, y + 1, Math.cos(i) + z, 0d, 0.5d, 0d);
        }
        super.endAction(player,world,stack);
    }

    @Override
    public void castSpell(PlayerEntity player, World world, ItemStack stack) {
        if (!world.isClient() && UseTime <= 0) {
            world.playSound(null, player.getBlockPos(), SoundEvents.BLOCK_END_PORTAL_FRAME_FILL, SoundCategory.PLAYERS, 2, 2);
            Vec3d location = player.getPos();
            if (stack.getSubNbt(NbtS.getNbt(stack)).get(NbtS.SPELL) instanceof Spell spell) {
                if (this == spell) {
                    x = location.x;
                    y = location.y;
                    z = location.z;
                }
            }
            super.castSpell(player,world,stack);
            UseTime *= (Level() + 1);
        }

    }
    @Override
    public float GetProgress(PlayerEntity player, ItemStack stack, NbtCompound compound) {
        return super.GetProgress(player,stack,compound) / (Level() + 1);
    }
    @Override
    public void tickAction(PlayerEntity player, World world, ItemStack stack, int index, int tick) {
        if (tick % 4 == 0) {
            world.addImportantParticle(ParticleTypes.PORTAL, true, x, y, z, 0d, 0.5d,0d);
        }
    }


}
