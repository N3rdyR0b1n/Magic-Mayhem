package com.example.main.Entity.custom;

import com.example.main.Entity.ModEntities;
import com.example.main.SpellUtil.DamageTypes.ModDamageTypes;
import com.example.main.Spells.ModSpells;
import net.minecraft.command.argument.EntityAnchorArgumentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
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
    private static TrackedData<Integer> lockontime = DataTracker.registerData(MagicMissileEntity.class, TrackedDataHandlerRegistry.INTEGER);

    public MagicMissileEntity(Entity target, LivingEntity owner, World world, ItemStack stack, int delay) {
        super(ModEntities.MAGIC_MISSILE, owner, world);
        this.dataTracker.set(entityid, target.getId());
        this.dataTracker.set(lockontime, delay);
        this.setNoGravity(true);
    }
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(entityid,0);
        this.dataTracker.startTracking(lockontime,0);
    }

    public void tick() {
        Entity target = this.getWorld().getEntityById(this.dataTracker.get(entityid));
        super.tick();
        if (target!= null && target.isAlive()) {
            int lock = this.dataTracker.get(lockontime);
            if (age % 4 == 0) {
                this.getWorld().addImportantParticle(ParticleTypes.GLOW, true, this.getX(), this.getY(), this.getZ(), 0, 0, 0);

            }
            if (this.age > lock) {
                Vec3d newdirection = target.getEyePos().subtract(this.getPos()).normalize().multiply(0.05f + this.getVelocity().length());
                this.setVelocity(newdirection);
            }
            else {
                if (age == lock) {
                    playSound(SoundEvents.ENTITY_GENERIC_EXPLODE, 0.75f, 1);
                    this.getWorld().addImportantParticle(ParticleTypes.EXPLOSION, true, this.getX(), this.getY(), this.getZ(), 0, 0, 0);
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
        else if (!this.getWorld().isClient()) {
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
            DamageSource source = this.getDamageSources().arrow(this, owner);
            this.getWorld().playSound(null, this.getBlockPos(), SoundEvents.ENTITY_PLAYER_HURT_SWEET_BERRY_BUSH, SoundCategory.PLAYERS, 1, 0.75f);
            if (entity instanceof LivingEntity living) {
                if (living.blockedByShield(source)) {
                    this.kill();
                    this.getWorld().playSound(null,this.getBlockPos(), SoundEvents.ITEM_SHIELD_BLOCK, SoundCategory.PLAYERS, 1, 0.8f + this.getWorld().random.nextFloat() * 0.4f);
                    living.damageShield(2);
                    return;
                }
            }
            ModSpells.MAGIC_MISSILE.onHit(owner, this.getWorld(), entity, 1f);
            entity.damage(entity.getDamageSources().playerAttack((PlayerEntity) this.getOwner()), 0.00001f);
            entity.damage(ModDamageTypes.of(this.getWorld(), ModDamageTypes.MAGIC_TICK), 6);
            if (!entity.isAlive()) {
                ModSpells.MAGIC_MISSILE.onKill(owner, this.getWorld(), entity);
            }
        }
        this.kill();

    }

    @Override
    protected ItemStack asItemStack() {
        return ItemStack.EMPTY;
    }
}
