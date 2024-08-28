package com.example.main.Spells.custom;

import com.example.main.SpellUtil.GenericSpellAbilities;
import com.example.main.SpellUtil.HitValues;
import com.example.main.SpellUtil.SpellSchool;
import com.example.main.Spells.extra.ContinousUsageSpell;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class AmethystBarrage extends ContinousUsageSpell {

    public AmethystBarrage(int manaCost, SpellSchool school, int chargeTime, String name, int cooldown, Identifier texture, int useTime, boolean needsHeld, int overtimecost, int min, int max, int upcast, int uptime) {
        super(manaCost, school, chargeTime, name, cooldown, texture, useTime, needsHeld, overtimecost, min, max, upcast, uptime);
    }

    @Override
    public void tickAction(PlayerEntity player, World world, ItemStack stack, int index, int tick) {
        if (tick % 2 == 0) {
            world.playSound(null, player.getBlockPos(), SoundEvents.BLOCK_AMETHYST_BLOCK_HIT, SoundCategory.PLAYERS, 2, 1.4f);
            HitValues values = GenericSpellAbilities.HitscanAttack(player, world, 255, 32, 1.5f,true, this, stack);
            GenericSpellAbilities.addParticles(world, player.getEyePos().subtract(0,0.25f,0), values.endPos(), ParticleTypes.WAX_OFF, 1);
        }
        super.tickAction(player, world, stack, index, tick);
    }

    @Override
    public void onHit(PlayerEntity player, World world, @Nullable Entity target, float hitlvl) {
        Vec3d push = new Vec3d(target.getX() - player.getX(), 0, target.getZ() - player.getZ()).normalize().multiply(0.05f);
        push = push.add(0, 0.025f, 0);
        if (target instanceof LivingEntity living && living.getAttributes().hasAttribute(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE)) {
            push = push.multiply(1D - living.getAttributes().getValue(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE));
        }
        target.addVelocity(push);
        target.velocityModified = true;
        target.velocityDirty = true;
        super.onHit(player,world,target,hitlvl);
    }

}
