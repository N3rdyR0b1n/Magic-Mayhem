package com.example.main.Spells.custom;

import com.example.main.Attributes.ModAttributes;
import com.example.main.SpellUtil.GenericSpellAbilities;
import com.example.main.SpellUtil.HitValues;
import com.example.main.SpellUtil.ManaContainer;
import com.example.main.SpellUtil.SpellSchool;
import com.example.main.SpellUtil.Spells.Spell;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class ElectricalZapSpell extends Spell {
    private int seccooldown;
    private int seccooldownprogress = 0;
    public ElectricalZapSpell(int manaCost, SpellSchool school, int chargeTime, int cooldown, Identifier texture, int min, int max, int up, int seccooldown) {
        super(0, school, chargeTime, "Zap", cooldown, texture, min, max, up);
        super.finishManaCost = manaCost;
        super.text = GetText();
        this.seccooldown = seccooldown;
    }

    @Override
    public void castReleaseSpell(PlayerEntity player, World world, ItemStack stack, int tick) {
        float sec = (float) ((((float) tick) / 20) * player.getAttributeValue(ModAttributes.CAST_SPEED));
        int damage = (int) (sec * 4);
        if (damage >= 11) {
            world.playSound(null, player.getBlockPos(), SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.PLAYERS, 0.75f, 1f);
            damage = 11;
        } else {
            world.playSound(null, player.getBlockPos(), SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.PLAYERS, 0.75f, 1.5f);
        }
        HitValues values = GenericSpellAbilities.HitscanAttack(player, world, 24, 8 + sec * 2 + ((2+sec) * Level()) , 10f + damage, true, this, stack);
        GenericSpellAbilities.addParticles(world, player.getEyePos().subtract(0, 0.25f, 0), values.endPos(), ParticleTypes.ELECTRIC_SPARK, 1);
        BlockHitResult blockHitResult = world.raycast(new RaycastContext(player.getEyePos(), player.getEyePos().add(player.getRotationVec(0.5F).multiply(8 + sec * 2)), RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, player));
        if (values.hasHit() || (blockHitResult.getType() == HitResult.Type.BLOCK)) {
            if (damage == 11) {
                world.playSound(null, player.getBlockPos(), SoundEvents.BLOCK_ANVIL_LAND, SoundCategory.PLAYERS, 1, 1f);
            } else {
                world.playSound(null, player.getBlockPos(), SoundEvents.BLOCK_ANVIL_LAND, SoundCategory.PLAYERS, 1, 2f);
            }
        }
        player.addVelocity(player.getRotationVec(0.5F).multiply(-0.2f - damage / 10f - (float) Level() /10));
    }

    public void castSecSpell(PlayerEntity player, World world, ItemStack stack) {
        world.playSound(null, player.getBlockPos(), SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.PLAYERS, 0.75f, 1.5f);

        HitValues values = GenericSpellAbilities.HitscanAttack(player, world, 24, 8, 8, true, this, stack);
        GenericSpellAbilities.addParticles(world, player.getEyePos().subtract(0, 0.25f, 0), values.endPos(), ParticleTypes.ELECTRIC_SPARK, 1);
        if (values.hasHit() || (world.raycast(new RaycastContext(player.getEyePos(), player.getEyePos().add(player.getRotationVec(0.5F).multiply(8)), RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, player)).getType() == HitResult.Type.BLOCK)) {
            world.playSound(null, player.getBlockPos(), SoundEvents.BLOCK_ANVIL_LAND, SoundCategory.PLAYERS, 1, 2f);
        }
    }

    @Override
    public boolean canCastFinishSpell(PlayerEntity player, World world, ItemStack stack, NbtCompound nbt, int tick) {
        return getCooldownProgress(player,stack) <= 0 && ((ManaContainer) player).getMana().getStoredMana() >= FinManacost();
    }

    @Override
    public void onHit(PlayerEntity player, World world, @Nullable Entity target, float hitlvl) {
        Vec3d push = new Vec3d(target.getX() - player.getX(), 0, target.getZ() - player.getZ()).normalize().multiply(0.25f + 0.125f * Level());
        push = push.add(0, 0.2 + 0.1 * Level(), 0);
        if (target instanceof LivingEntity living && living.getAttributes().hasAttribute(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE)) {
            push.multiply(1D - living.getAttributes().getValue(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE));
        }
        target.addVelocity(push);
        target.velocityModified = true;
        target.velocityDirty = true;
        super.onHit(player,world,target,hitlvl);
    }
    @Override
    public void applySecCooldown(PlayerEntity player, ItemStack stack, NbtCompound nbt, int slot) {
        if (!player.getWorld().isClient()) {
            seccooldownprogress = seccooldown;
        }
    }

    public int getCooldownProgress(PlayerEntity player, ItemStack stack) {
        if (CooldownProgress != 0) {
            return CooldownProgress;
        }
        else {
            return seccooldownprogress;
        }
    }

    public int getCooldown(PlayerEntity player, ItemStack stack) {
        if (CooldownProgress != 0) {
            return SpellCooldown;
        }
        else {
            return seccooldown;
        }
    }

    public boolean UpdateCooldown(PlayerEntity player, World world, ItemStack stack, NbtCompound compound) {
        if (!world.isClient()) {
            if (seccooldownprogress > 0) {
                seccooldownprogress--;
                return super.UpdateCooldown(player,world,stack,compound);
            }
            return super.UpdateCooldown(player,world,stack,compound);
        }
        return false;
    }

    public boolean canSecCastSpell(PlayerEntity player, World world, ItemStack stack,NbtCompound nbt) {
        return getCooldownProgress(player,stack) <= 0;
    }

    public void applySecCost(PlayerEntity player, ItemStack stack, NbtCompound nbt, int slot) {
        if (GenericSpellAbilities.HasTarget(nbt)) {
            super.applySecCost(player, stack, nbt, slot);
        }
    }
}
