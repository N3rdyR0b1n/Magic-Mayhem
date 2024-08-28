package com.example.main.Entity.sigils;

import com.example.main.Entity.ModEntities;
import com.example.main.Entity.custom.ArcaneSigilHelperEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class KnockbackSigil extends ArcaneSigilHelperEntity {
    public KnockbackSigil(LivingEntity livingEntity, World world) {
        super(ModEntities.PUSH_SIGIL,livingEntity, world, ParticleTypes.FIREWORK);
    }

    public KnockbackSigil(EntityType<KnockbackSigil> knockbackSigilEntityType, World world) {
        super(ModEntities.PUSH_SIGIL, world, ParticleTypes.FIREWORK);
    }
    @Override
    public void weakhit(LivingEntity target) {
        target.addVelocity(target.getPos().add(0, 0.5, 0).subtract(this.getPos()).normalize().multiply(2f));
        target.velocityModified = true;
    }
    @Override
    public void stronghit(LivingEntity target) {
        target.addVelocity(target.getPos().add(0, 0.5, 0).subtract(this.getPos()).normalize().multiply(2f));
    }
    @Override
    public void explode() {
        World world = this.method_48926();
        world.addImportantParticle(ParticleTypes.EXPLOSION, true, this.getX(), this.getY(), this.getZ(), 0 ,0 ,0);
        world.playSound(this, this.getBlockPos(), SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.PLAYERS, 2f, 1.25f);
        world.playSound(this, this.getBlockPos(), SoundEvents.BLOCK_SLIME_BLOCK_PLACE, SoundCategory.PLAYERS, 2f, 0.75f);
        this.explode(getExplosionRange() * 2.5f, (PlayerEntity) this.getOwner(), 0, 0, 0);
        double y =  this.getY() + 0.6f;
        for (float i = 0; i < 6.243f; i+=0.25) {
            world.addImportantParticle(GetEffect(), true, (MathHelper.sin(i) * 2.5f) + this.getX(), y, (MathHelper.cos(i) * 2.5f) + this.getZ(), 0, 0.25, 0);
        }
    }
}
