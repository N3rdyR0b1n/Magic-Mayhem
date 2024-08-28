package com.example.main.Spells.custom;

import com.example.main.Entity.custom.MagicMissileEntity;
import com.example.main.SpellUtil.*;
import com.example.main.SpellUtil.Spells.NbtS;
import com.example.main.SpellUtil.Spells.Spell;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class MagicMissileSpell extends Spell {


    public MagicMissileSpell(int manaCost, SpellSchool school, int chargeTime, String name, int cooldown, Identifier texture, int min, int max, int cost) {
        super(0, school, chargeTime, name, cooldown, texture, min, max, cost);
        super.finishManaCost = manaCost;
        super.text = GetText();
    }

    @Override
    public void castReleaseSpell(PlayerEntity player, World world, ItemStack stack, int tick) {
        Entity entity = GenericSpellAbilities.HitscanSelect(world, player, 128,true).getTarget();
        if (entity != null) {
            NbtCompound compound = stack.getSubNbt(NbtS.getNbt(stack));
            compound.putBoolean("Hit", true);

            world.playSound(null, player.getBlockPos(), SoundEvents.ENTITY_FIREWORK_ROCKET_LAUNCH, SoundCategory.PLAYERS, 1, 0.95f);
            world.playSound(null, player.getBlockPos(), SoundEvents.ENTITY_FIREWORK_ROCKET_LAUNCH, SoundCategory.PLAYERS, 1, 1);
            world.playSound(null, player.getBlockPos(), SoundEvents.ENTITY_FIREWORK_ROCKET_LAUNCH, SoundCategory.PLAYERS, 1, 1.05f);


            MagicMissileEntity missile1 = new MagicMissileEntity(entity, player, world, stack);
            MagicMissileEntity missile2 = new MagicMissileEntity(entity, player, world, stack);
            MagicMissileEntity missile3 = new MagicMissileEntity(entity, player, world, stack);

            shoot(world, player, missile1, 2f, 0);
            shoot(world, player, missile2, 2f, 60);
            shoot(world, player, missile3, 2F, -60);


            for (int i = 0; i < Level(); i ++) {
                MagicMissileEntity missile = new MagicMissileEntity(entity, player, world, stack);
                int amp = i/2;
                int degree = 75 + amp * 5;
                if (i % 2 == 0) {
                    degree *= -1;
                }
                shoot(world, player, missile, 2.5F + 0.5f * amp, degree);
            }
        }
    }

    @Override
    public void applyFinishCost(PlayerEntity player, ItemStack stack, NbtCompound nbt, int slot) {
        if (nbt.getBoolean("Hit")) {
            super.applyFinishCost(player, stack, nbt, slot);
        }
    }
    @Override
    public void applyCooldown(PlayerEntity player, ItemStack stack, NbtCompound nbt, int slot) {
        if (nbt.getBoolean("Hit")) {
            super.applyCooldown(player, stack, nbt, slot);
            nbt.putBoolean("Hit", false);
        }
    }
    private void shoot(World world, LivingEntity shooter, Entity projectile, float speed, float simulated) {
        if (!world.isClient) {
            Vec3d vec3d = shooter.getOppositeRotationVector(1.0f);
            Quaternionf quaternionf = new Quaternionf().setAngleAxis((double)(simulated * ((float)Math.PI / 180)), vec3d.x, vec3d.y, vec3d.z);
            Vec3d vec3d2 = shooter.getRotationVec(1.0f);
            Vector3f vector3f = vec3d2.toVector3f().rotate(quaternionf);
            vector3f.mul(speed);
            projectile.setVelocity(vector3f.x(), vector3f.y(), vector3f.z());
            world.spawnEntity(projectile);
        }
    }
}
