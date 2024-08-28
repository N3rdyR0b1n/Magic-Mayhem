package com.example.main.Entity.custom;

import com.example.main.Entity.ModEntities;
import com.example.main.SpellUtil.Spells.NbtS;
import com.example.main.Spells.ModSpells;
import com.example.main.Spells.extra.ContinousUsageSpell;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class GravityHelperEntity extends ThrownItemEntity {

    private boolean Effect = true;
    private Entity target;
    private static TrackedData<Byte> speed = DataTracker.registerData(FireBallSpellEntity.class, TrackedDataHandlerRegistry.BYTE);


    public GravityHelperEntity(LivingEntity livingEntity, World world, Entity target, byte level) {
        super(ModEntities.GRAVITY, livingEntity, world);
        this.target = target;
        this.setNoGravity(true);
        this.noClip = true;
        this.dataTracker.set(speed, level);
    }
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(speed,(byte)0);
    }

    public GravityHelperEntity(EntityType<GravityHelperEntity> entityType, World world) {
        super(entityType, world);
    }

    public void stop() {
        this.age = 11995;
        Effect = false;
    }

    @Override
    public void tick() {
        if (Effect && target != null) {
            PlayerEntity player = (PlayerEntity) this.getOwner();
            if (player.isUsingItem()) {
                ItemStack stack = player.getActiveItem();
                if (target instanceof LivingEntity living && living.isDead()) {
                    ModSpells.GRAVITY_GRAB.onKill(player, this.getWorld(), target);
                }
                NbtCompound compound = stack.getSubNbt(NbtS.getNbt(stack));
                if (compound.get(NbtS.SPELL) instanceof ContinousUsageSpell usageSpell && usageSpell.id == ModSpells.GRAVITY_GRAB.id && usageSpell.UseTime > 0) {
                    UseGravity(player);
                }
                else { stop(); }
            }
            else { stop(); }
        }
        if (age >= 12000) {
            this.kill();
        }
    }

    @Override
    protected Item getDefaultItem() {
        return Items.AIR;
    }



    private void UseGravity(PlayerEntity player) {
        Vec3d location = player.getEyePos().add(player.getRotationVec(0.5F).multiply(7));
        if (location.distanceTo(target.getPos()) < 0.25f) {
            target.setVelocity(new Vec3d(0, 0, 0));
            target.velocityDirty = true;
            target.velocityModified = true;
            return;
        }
        float sped = 0.3f + 0.15f * this.dataTracker.get(speed);
        if (location.distanceTo(target.getPos()) < 2f) {
            target.setVelocity(location.subtract(target.getPos()).normalize().multiply(sped));
        } else {
            target.setVelocity(location.subtract(target.getPos()).multiply(sped));
        }
        target.velocityDirty = true;
        target.velocityModified = true;
    }

    public boolean distance(Vec3d vec, Vec3d vec2, float expect) {
        double d = vec.x - vec2.x ;
        double e = vec.y - vec2.y ;
        double f = vec.z - vec2.z ;
        return (d + e + f) < expect;
    }


}
