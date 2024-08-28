package com.example.main.Entity.custom;

import com.example.main.Entity.ModEntities;
import com.example.main.SpellUtil.DamageTypes.ModDamageTypes;
import com.example.main.Spells.ModSpells;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class MagicMissileEntity extends PersistentProjectileEntity {
    private static TrackedData<Integer> entityid = DataTracker.registerData(MagicMissileEntity.class, TrackedDataHandlerRegistry.INTEGER);
    public MagicMissileEntity(Entity target, LivingEntity owner, World world, ItemStack stack) {
        super(ModEntities.MAGIC_MISSILE, owner, world);
        this.dataTracker.set(entityid, target.getId());
        this.setNoGravity(true);
    }
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(entityid,0);
    }

    public void tick() {
        Entity target = this.method_48926().getEntityById(this.dataTracker.get(entityid));
        super.tick();
        if (target!= null && target.isAlive()) {
            if (age % 4 == 0) {
                this.method_48926().addImportantParticle(ParticleTypes.GLOW, true, this.getX(), this.getY(), this.getZ(), 0, 0, 0);
            }
            if (this.age > 30) {
                Vec3d newdirection = target.getEyePos().subtract(this.getPos()).normalize().multiply(0.05f + this.getVelocity().length());
                this.setVelocity(newdirection);
            }
            else {
                if (age == 30) {
                    playSound(SoundEvents.ENTITY_GENERIC_EXPLODE, 0.75f, 1);
                    this.method_48926().addImportantParticle(ParticleTypes.EXPLOSION, true, this.getX(), this.getY(), this.getZ(), 0, 0, 0);
                    Vec3d newdirection = target.getEyePos().subtract(this.getPos()).normalize().multiply(2f);
                    this.setVelocity(newdirection);
                }
                else {
                    Vec3d velocity = this.getVelocity();
                    velocity = new Vec3d(velocity.x * 0.75, 0, velocity.z * 0.75);
                    this.setVelocity(velocity);
                }
            }
        }
        else if (!this.method_48926().isClient()) {
            this.kill();
        }
        else if (this.getVelocity().length() == 0) {
            this.kill();
        }


    }

    public MagicMissileEntity(EntityType<MagicMissileEntity> magicMissileEntityEntityType, World world) {
        super(magicMissileEntityEntityType, world);
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        Entity entity = entityHitResult.getEntity();
        if (this.getOwner() instanceof PlayerEntity owner) {
            ModSpells.MAGIC_MISSILE.onHit(owner, this.method_48926(), entity, 1f);
            entity.damage(entity.getDamageSources().playerAttack((PlayerEntity) this.getOwner()), 0.01f);
            entity.damage(ModDamageTypes.of(this.method_48926(), ModDamageTypes.MAGIC_TICK), 6);
            if (!entity.isAlive()) {
                ModSpells.MAGIC_MISSILE.onKill(owner, this.method_48926(), entity);
            }
            this.method_48926().playSound(null, this.getBlockPos(), SoundEvents.ENTITY_PLAYER_HURT_SWEET_BERRY_BUSH, SoundCategory.PLAYERS, 1, 0.75f);
        }
        this.kill();

    }

    @Override
    protected ItemStack asItemStack() {
        return ItemStack.EMPTY;
    }
}
