package com.example.main.Entity.custom;

import com.example.main.Entity.ModEntities;
import com.example.main.SpellUtil.GenericSpellAbilities;
import net.minecraft.client.particle.Particle;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

public class HeavenlySmiteEntity extends PersistentProjectileEntity {
    private int hit = -1;
    private static TrackedData<Integer> Level = DataTracker.registerData(HeavenlySmiteEntity.class, TrackedDataHandlerRegistry.INTEGER);

    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(Level,0);
    }
    public HeavenlySmiteEntity(World world, PlayerEntity player, int level) {
        this(ModEntities.HEAVENLY_SMITE, world);
        this.setOwner(player);
        this.dataTracker.set(Level, level);
    }
    public HeavenlySmiteEntity(EntityType<? extends PersistentProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected ItemStack asItemStack() {
        return ItemStack.EMPTY;
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        ExplodeOnContact(entityHitResult.getPos());
    }

    @Override
    protected void onBlockHit(BlockHitResult entityHitResult) {
        ExplodeOnContact(entityHitResult.getPos());
    }

    @Override
    public void tick() {
        super.tick();
        if (age % 15 == 0) {
            World world = this.method_48926();
            double x = this.getX();
            double y = this.getY();
            double z = this.getZ();
            for (float i = 0; i < 6.283f; i += 0.1f) {
                world.addImportantParticle(ParticleTypes.FIREWORK, true, (MathHelper.sin(i) * 0.75) + z, y, (MathHelper.cos(i) * 0.75) + y, 0, 0.1, 0);
            }
        }
        if (hit >= 0) {
            if (hit > 10) {
                this.kill();
            }
            hit++;
        }

    }
    private void ExplodeOnContact(Vec3d pos) {
        this.setPosition(pos);
        if (hit == -1) {
            World world = this.method_48926();
            List<Entity> targets = GenericSpellAbilities.GetNearbyEntities(5, this.method_48926(), this, true, true, Entity.class);
            if (this.getOwner() != null && this.getOwner() instanceof PlayerEntity player) {
                for (Entity entity : targets) {
                    entity.damage(this.getDamageSources().playerAttack(player), 0.01f);
                }
            }
            int level = this.dataTracker.get(Level);
            for (Entity entity : targets) {
                entity.damage(this.getDamageSources().lightningBolt(), 40 + 7.5f * level);
            }
            world.addImportantParticle(ParticleTypes.EXPLOSION, true, this.getX(), this.getY(), this.getZ(), 0, 0,0);
            double y = this.getY() + 0.25;
            float rad = 1f /(20f + 4f * level);
            float t_rad = 5f + level;
            for (float i = 0; i < 6.283f; i += rad) {
                world.addImportantParticle(ParticleTypes.FLAME, true, (MathHelper.sin(i) * t_rad) + this.getX(), y, (MathHelper.cos(i) * t_rad) + this.getZ(), 0, 0.05, 0);
            }
            for (int i = -1; i < level; i++) {
                LightningEntity entity = new LightningEntity(EntityType.LIGHTNING_BOLT, this.method_48926());
                entity.setPosition(this.getPos());
                world.spawnEntity(entity);
            }
            hit++;
            world.playSound(null, this.getBlockPos(), SoundEvents.ITEM_TRIDENT_THUNDER, SoundCategory.PLAYERS, 10, 1);
        }
    }

}
