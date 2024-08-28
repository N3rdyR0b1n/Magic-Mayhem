package com.example.main.Entity.sigils;

import com.example.main.Entity.ModEntities;
import com.example.main.Entity.custom.ArcaneSigilHelperEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class IceSigil extends ArcaneSigilHelperEntity {
    public IceSigil(LivingEntity livingEntity, World world) {
        super(ModEntities.ICE_SIGIL,livingEntity, world,new ItemStackParticleEffect(ParticleTypes.ITEM, new ItemStack(Items.SNOW_BLOCK)));
    }

    public IceSigil(EntityType<IceSigil> iceSigilEntityType, World world) {
        super(ModEntities.ICE_SIGIL, world, new ItemStackParticleEffect(ParticleTypes.ITEM, new ItemStack(Items.SNOW_BLOCK)));
    }
    @Override
    public void weakhit(LivingEntity target) {
        target.damage(target.getDamageSources().playerAttack((PlayerEntity) this.getOwner()), 0.01f);
        target.setFrozenTicks(target.getFrozenTicks() + 150);
        target.damage(target.getDamageSources().freeze(), 7.5f);
    }

    @Override
    public void stronghit(LivingEntity target) {
        target.damage(target.getDamageSources().playerAttack((PlayerEntity) this.getOwner()), 0.01f);
        target.setFrozenTicks(target.getFrozenTicks() + 150);
        target.damage(target.getDamageSources().freeze(), 7.5f);
    }

    @Override
    public void explode() {
        World world = this.method_48926();
        world.playSound(this, this.getBlockPos(), SoundEvents.BLOCK_GLASS_BREAK, SoundCategory.PLAYERS, 1, 1.15f);
        world.playSound(this, this.getBlockPos(), SoundEvents.BLOCK_AMETHYST_CLUSTER_BREAK, SoundCategory.PLAYERS, 1, 0.85f);
        this.explode(2.5f, (PlayerEntity) this.getOwner(), 0.1f, 0.5f, 1);
        double y =  this.getY() + 0.6f;
        for (float i = 0; i < 6.243f; i+=0.25) {
            world.addImportantParticle(GetEffect(), true, (MathHelper.sin(i) * 2.5f) + this.getX(), y, (MathHelper.cos(i) * 2.5f) + this.getZ(), 0, 0.25, 0);
        }
    }

}
