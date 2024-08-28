package com.example.main.Spells.custom;

import com.example.main.Entity.custom.IcycleEntity;
import com.example.main.SpellUtil.SpellSchool;
import com.example.main.SpellUtil.Spells.Spell;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.joml.Quaternionf;
import org.joml.Vector3f;


public class IceShardSpell extends Spell {
    public IceShardSpell(int manaCost, SpellSchool school, int chargeTime, String name, int cooldown, Identifier texture, int min, int max, int upcost) {
        super(0, school, chargeTime, name, cooldown, texture, min,max,upcost);
        super.finishManaCost = manaCost;
        super.text = GetText();
    }

    @Override
    public void castReleaseSpell(PlayerEntity player, World world, ItemStack stack, int tick) {
        Shoot(player, world, -10, stack);
        Shoot(player, world, 0, stack);
        Shoot(player, world, 10, stack);
        world.playSound(null, player.getBlockPos(), SoundEvents.BLOCK_AMETHYST_CLUSTER_BREAK, SoundCategory.PLAYERS, 1, 1.0f);
        world.playSound(null, player.getBlockPos(), SoundEvents.BLOCK_AMETHYST_CLUSTER_BREAK, SoundCategory.PLAYERS, 1, 1.1f);
        world.playSound(null, player.getBlockPos(), SoundEvents.BLOCK_AMETHYST_CLUSTER_BREAK, SoundCategory.PLAYERS, 1, 1.05f);
    }
    private void Shoot(PlayerEntity player, World world, float angle, ItemStack stack) {
        Vec3d vec3d = player.getOppositeRotationVector(1.0f);
        Vec3d vec3d2 = player.getRotationVec(1.0f);
        Quaternionf quaternionf = new Quaternionf().setAngleAxis((angle * ((float)Math.PI / 180)), vec3d.x, vec3d.y, vec3d.z);
        Vector3f vector3f = vec3d2.toVector3f().rotate(quaternionf);

        IcycleEntity missile1 = new IcycleEntity(world, player, stack, id, Level());
        missile1.setVelocity(vector3f.x(), vector3f.y(), vector3f.z(), 2 + 0.3f * Level(), 0);
        world.spawnEntity(missile1);
    }
}
