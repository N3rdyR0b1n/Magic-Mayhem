package com.example.main.Entity.sigils;

import com.example.main.Entity.ModEntities;
import com.example.main.Entity.custom.ArcaneSigilHelperEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class CurseSigil extends ArcaneSigilHelperEntity {
    public CurseSigil(LivingEntity livingEntity, World world) {
        super(ModEntities.CURSE_SIGIL,livingEntity, world, ParticleTypes.WITCH);
    }

    @Override
    public void weakhit(LivingEntity target) {
        stronghit(target);
    }
    @Override
    public void stronghit(LivingEntity target) {
        target.damage(target.getDamageSources().playerAttack((PlayerEntity) this.getOwner()), 0.01f);
        int amplifier = 0;
        if (target.hasStatusEffect(StatusEffects.WEAKNESS)) {
            amplifier = target.getStatusEffect(StatusEffects.WEAKNESS).getAmplifier() + 1;
        }
        target.addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, 200,amplifier));
        amplifier = 0;
        if (target.hasStatusEffect(StatusEffects.SLOWNESS)) {
            amplifier = target.getStatusEffect(StatusEffects.SLOWNESS).getAmplifier() + 1;
        }
        target.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS,200,amplifier));
        amplifier = 0;
        if (target.hasStatusEffect(StatusEffects.POISON)) {
            amplifier = target.getStatusEffect(StatusEffects.POISON).getAmplifier() + 1;
        }
        target.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON,100,amplifier));
        amplifier = 0;
        if (target.hasStatusEffect(StatusEffects.WITHER)) {
            amplifier = target.getStatusEffect(StatusEffects.WITHER).getAmplifier() + 1;
        }
        target.addStatusEffect(new StatusEffectInstance(StatusEffects.WITHER,100,amplifier));
    }
    @Override
    public void explode(){
        World world = this.method_48926();
        world.playSound(this, this.getBlockPos(), SoundEvents.ENTITY_SPLASH_POTION_BREAK, SoundCategory.PLAYERS, 1, 1);
        world.playSound(this, this.getBlockPos(), SoundEvents.ENTITY_PLAYER_HURT_SWEET_BERRY_BUSH, SoundCategory.PLAYERS, 1, 1);
        this.explode(2, (PlayerEntity) this.getOwner(), 0.1f, 0.5f, 1);
        double y =  this.getY() + 0.6f;
        for (float i = 0; i < 6.243f; i+=0.25) {
            world.addImportantParticle(GetEffect(), true, (MathHelper.sin(i) * 2) + this.getX(), y, (MathHelper.cos(i) * 2) + this.getZ(), 0, 0.25, 0);
        }
    }

    public CurseSigil(EntityType<CurseSigil> curseSigilEntityType, World world) {
        super(ModEntities.CURSE_SIGIL, world, ParticleTypes.WITCH);
    }


}
