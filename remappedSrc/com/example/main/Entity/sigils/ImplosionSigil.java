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

public class ImplosionSigil extends ArcaneSigilHelperEntity {
    public ImplosionSigil(LivingEntity livingEntity, World world) {
        super(ModEntities.IMPLOSION_SIGIL,livingEntity, world,ParticleTypes.ELECTRIC_SPARK);
    }

    public ImplosionSigil(EntityType<ImplosionSigil> implosionSigilEntityType, World world) {
        super(ModEntities.IMPLOSION_SIGIL, world,ParticleTypes.ELECTRIC_SPARK);
    }

    @Override
    public ParticleEffect GetEffect() {
        return ParticleTypes.ELECTRIC_SPARK;
    }

    @Override
    public void weakhit(LivingEntity target) {
        target.damage(target.getDamageSources().playerAttack((PlayerEntity) this.getOwner()), 0.01f);
        target.damage(target.getDamageSources().cramming(), 25f);
        target.addVelocity(target.getPos().add(0, 3, 0).subtract(this.getPos()).normalize().multiply(-5f));
    }
    @Override
    public void stronghit(LivingEntity target) {
        target.damage(target.getDamageSources().playerAttack((PlayerEntity) this.getOwner()), 0.01f);
        target.damage(target.getDamageSources().cramming(), 35f);
        target.addVelocity(this.getPos().subtract(target.getPos().add(0, 3, 0)).normalize().multiply(5f));
    }

    @Override
    public void explode(){
        World world = this.method_48926();
        world.playSound(this, this.getBlockPos(), SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.PLAYERS, 2f, 0.5f);
        world.playSound(this, this.getBlockPos(), SoundEvents.ENTITY_FIREWORK_ROCKET_BLAST_FAR, SoundCategory.PLAYERS, 2f, 0.75f);
        this.explode(1, (PlayerEntity) this.getOwner(), -0.5f, 0.5f, 1);
        double y =  this.getY() + 0.6f;
        for (float i = 0; i < 6.243f; i+=0.25) {
            world.addImportantParticle(GetEffect(), true, (MathHelper.sin(i)) + this.getX(), y, (MathHelper.cos(i)) + this.getZ(), 0, 0.25, 0);
        }
    }
}
