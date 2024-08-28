package com.example.main.Spells.custom;

import com.example.main.SpellUtil.*;
import com.example.main.SpellUtil.Spells.NbtS;
import com.example.main.Spells.extra.ContinousUsageSpell;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class WardenBlastSpell extends ContinousUsageSpell {
    private static ParticleEffect effect = ParticleTypes.SCULK_SOUL;

    public WardenBlastSpell(int manaCost, SpellSchool school, int chargeTime, String name, int cooldown, Identifier texture, int useTime, boolean needsHeld, int overtimecost, int min, int max, int up, int uptime) {
        super(0, school, chargeTime, name, cooldown, texture, useTime, needsHeld, overtimecost, min, max, up, uptime);
        finishManaCost = manaCost;
        text = GetText();
    }

    @Override
    public void castSpell(PlayerEntity player, World world, ItemStack stack) {
        super.castSpell(player, world, stack);
        world.playSound(null, player.getBlockPos(), SoundEvents.ENTITY_WARDEN_SONIC_CHARGE, SoundCategory.PLAYERS, 2, (float) 40 / GetChargeTime(player, stack));
    }

    @Override
    public void endAction(PlayerEntity player, World world, ItemStack stack) {
        if (UseTime == 0) {
            world.playSound(null, player.getBlockPos(), SoundEvents.ENTITY_WARDEN_SONIC_BOOM, SoundCategory.PLAYERS, 2, 1);
            HitValues values = GenericSpellAbilities.HitscanAttack(player, world, 255, 512, 40f + 10 * Level(), true, this, stack);
            GenericSpellAbilities.addParticles(world, player.getEyePos().subtract(0, 0.25f, 0), values.endPos(), ParticleTypes.SONIC_BOOM, 2);
            applyFinishCost(player, stack, stack.getNbt(), stack.getNbt().getInt(NbtS.SELECT));
            if (!world.isClient()) {
                applyCooldown(player, stack, stack.getSubNbt(NbtS.getNbt(stack)), stack.getNbt().getInt(NbtS.SELECT));
            }
        }
    }

    @Override
    public void tickAction(PlayerEntity player, World world, ItemStack stack, int index, int tick) {
        player.setPitch(player.getPitch(1.0f) + 90);
        Vec3d lookdirection = player.getOppositeRotationVector(0.5F);
        Vec3d sillydirection = lookdirection.multiply(0.1f);
        sillydirection.multiply(3);
        float ftick = (float) tick /GetChargeTime(player, stack) * -360;
        double y = player.getEyeY() - 0.25f;
        Particle(world, player, ftick+ 180, lookdirection, y, sillydirection);
        Particle(world, player,  ftick , lookdirection, y, sillydirection);
        player.setPitch(player.getPitch(1.0f) - 90);

    }

    private void Particle(World world, PlayerEntity player, float angle,Vec3d lookdirection, double y, Vec3d sillydirection) {
        Vec3d vec3d = player.getOppositeRotationVector(1.0f);
        Vec3d vec3d2 = player.getRotationVec(1.0f);
        Quaternionf quaternionf = new Quaternionf().setAngleAxis((angle * ((float)Math.PI / 180)), vec3d.x, vec3d.y, vec3d.z);
        Vector3f vector3f = vec3d2.toVector3f().rotate(quaternionf);
        world.addImportantParticle(effect, true, vector3f.x + player.getX() + lookdirection.x, y + vector3f.y + lookdirection.y, vector3f.z + player.getZ() + lookdirection.z, sillydirection.x,sillydirection.y,sillydirection.z);
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
