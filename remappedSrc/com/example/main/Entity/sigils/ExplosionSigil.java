package com.example.main.Entity.sigils;

import com.example.main.Entity.ModEntities;
import com.example.main.Entity.custom.ArcaneSigilHelperEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class ExplosionSigil extends ArcaneSigilHelperEntity {
    public ExplosionSigil(LivingEntity livingEntity, World world) {
        super(ModEntities.EXPLOSION_SIGIL,livingEntity, world, ParticleTypes.FLAME);
    }
    @Override
    public void weakhit(LivingEntity target) {
        target.damage(target.getDamageSources().playerAttack((PlayerEntity) this.getOwner()), 0.01f);
        target.damage(target.getDamageSources().cramming(), 12f);

    }
    @Override
    public void stronghit(LivingEntity target) {
        target.damage(target.getDamageSources().playerAttack((PlayerEntity) this.getOwner()), 0.01f);
        target.damage(target.getDamageSources().cramming(), 18f);
    }
    @Override
    public void explode(){
        World world = this.method_48926();
        world.addImportantParticle(ParticleTypes.EXPLOSION, true, this.getX(), this.getY(), this.getZ(), 0 ,0 ,0);
        world.playSound(this, this.getBlockPos(), SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.PLAYERS, 2f, 1f);
        this.explode(5, (PlayerEntity) this.getOwner(), 0.5f, 0.5f, 1);
        double y =  this.getY() + 0.6f;
        for (float i = 0; i < 6.243f; i+=0.25) {
            world.addImportantParticle(GetEffect(), true, (MathHelper.sin(i) * 5) + this.getX(), y, (MathHelper.cos(i) * 5) + this.getZ(), 0, 0.25, 0);
        }
    }
    public ExplosionSigil(EntityType<ExplosionSigil> explosionSigilEntityType, World world) {
        super(ModEntities.EXPLOSION_SIGIL, world,ParticleTypes.FLAME);
    }

}
