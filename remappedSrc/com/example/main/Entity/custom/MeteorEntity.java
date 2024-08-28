package com.example.main.Entity.custom;

import com.example.main.Entity.ModEntities;
import com.example.main.SpellUtil.GenericSpellAbilities;
import com.example.main.Spells.ModSpells;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class MeteorEntity extends ThrownItemEntity {
    private ItemStack spellfocus;
    public MeteorEntity(LivingEntity livingEntity, World world, ItemStack spellfocus) {
        super(ModEntities.METEOR, livingEntity, world);
        this.spellfocus = spellfocus;
    }
    public MeteorEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }

    private int hittime = -1;

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        explode(entityHitResult.getPos());
    }
    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        explode(blockHitResult.getPos());
    }

    private void explode(Vec3d pos) {
        if (hittime == -1) {
            World world = this.method_48926();
            this.method_48926().playSound(null, this.getBlockPos(), SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.PLAYERS, 10, 1);
            this.setPosition(pos);
            if (this.getOwner() instanceof PlayerEntity player) {
                GenericSpellAbilities.explodeFlatAoe(8, 0.75f, 1f, this.method_48926(), this, player, ModSpells.METEOR_SHOWER, 0.2f, 0.1f);
            }
            double y = this.getY() + 0.25f;

            for (float i = 0; i < 6.283f; i += 0.3f) {
                world.addImportantParticle(ParticleTypes.FLAME, true, (MathHelper.sin(i) * 5) + this.getX(), y, (MathHelper.cos(i) * 5) + this.getZ(), 0, 0.05f, 0);
            }
            for (float i = 0; i < 6.283f; i += 0.3f) {
                world.addImportantParticle(ParticleTypes.FLAME, true, (MathHelper.sin(i) * 5) + this.getX(), y, (MathHelper.cos(i) * 5) + this.getZ(), 0, 0.3f, 0);
            }
            world.addImportantParticle(ParticleTypes.EXPLOSION, true, this.getX(), this.getY(), this.getZ(), 0, 0, 0);
            hittime++;
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
        Vec3d particlevec = this.getVelocity().multiply(-0.3);
        for (int i = 0; i < 3; i ++) {
            ParticleTimeYIPEE(ParticleTypes.FLAME, particlevec);
        }
        for (int i = 0; i < 2; i ++) {
            ParticleTimeYIPEE(ParticleTypes.LARGE_SMOKE, particlevec);
        }
        super.tick();
    }
    private void ParticleTimeYIPEE(ParticleEffect particleEffect, Vec3d particlevec) {
        World world = this.method_48926();
        world.addImportantParticle(particleEffect, true,
                this.getX() + (random.nextFloat() - 0.5f),
                this.getY() + (random.nextFloat() - 0.5f),
                this.getZ() + (random.nextFloat() - 0.5f),
                (random.nextFloat() - 0.5f)/4 + particlevec.x,
                (random.nextFloat() - 0.5f)/4 + particlevec.y,
                (random.nextFloat() - 0.5f)/4 + particlevec.z);
    }
    @Override
    protected Item getDefaultItem() {
        return Items.FIRE_CHARGE;
    }
}
