package com.example.main.Entity.custom;

import com.example.main.Entity.ModEntities;
import com.example.main.SpellUtil.Spells.Spell;
import com.example.main.SpellUtil.Spells.SpellKeeper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;

public class IcycleEntity extends PersistentProjectileEntity {
    private int hittime = -1;
    private float damage;
    private int usedSpell;
    private int level;
    private ItemStack spellfocus;
    public IcycleEntity(World world, LivingEntity entity, ItemStack stack, int spellid, int level) {
        super(ModEntities.ICYCLE, entity, world);
        this.usedSpell = spellid;
        this.spellfocus = stack;
        this.level = level;
    }
    public IcycleEntity(EntityType<? extends PersistentProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        Entity entity = entityHitResult.getEntity();
        if (hittime == -1 && this.getOwner() instanceof PlayerEntity player) {
            World world = this.getWorld();
            Spell spell = SpellKeeper.getSpell(usedSpell);
            spell.onHit(player, world, entity, 1);
            entity.damage(entity.getDamageSources().playerAttack(player), 0.01f);
            entity.damage(entity.getDamageSources().freeze(), 10);
            if (entity instanceof LivingEntity effectable) {
                if (effectable.isDead()) {
                    spell.onKill(player, world, effectable);
                }
                effectable.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 100 + 40 * level, 0), this);
                effectable.setFrozenTicks(effectable.getFrozenTicks() + 200 + 100 * level);
            }
            hittime = 0;
        }
        Hiteffects();
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        Hiteffects();
    }
    private void Hiteffects() {
        World world = this.getWorld();
        world.playSound(null, this.getBlockPos(), SoundEvents.BLOCK_GLASS_BREAK, SoundCategory.PLAYERS, 2, 1.5f);
        for (int i = 0; i < 5; i++) {
            world.addParticle(new ItemStackParticleEffect(ParticleTypes.ITEM, new ItemStack(Items.ICE)), this.getX() + world.random.nextFloat()/2, this.getY()+world.random.nextFloat()/2, this.getZ() +world.random.nextFloat()/2, world.random.nextFloat()-0.5,world.random.nextFloat()-0.5 ,world.random.nextFloat()-0.5);
        }
        if (this.getWorld().isClient) {
            this.kill();
        }
    }

    @Override
    public void tick() {
        if (hittime > -1) {
            if (hittime > 9) {
                this.kill();
            }
            hittime++;
        }
        super.tick();
    }

    @Override
    protected ItemStack asItemStack() {
        return new ItemStack(Items.ICE);
    }
}
