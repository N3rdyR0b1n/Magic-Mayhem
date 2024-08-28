package com.example.main.Entity.custom;

import com.example.main.Entity.ModEntities;
import com.example.main.SpellUtil.Spells.NbtS;
import com.example.main.Spells.ModSpells;
import com.example.main.Spells.extra.ContinousUsageSpell;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
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

    public GravityHelperEntity(LivingEntity livingEntity, World world, Entity target) {
        super(ModEntities.GRAVITY, livingEntity, world);
        this.target = target;
        this.setNoGravity(true);
        this.noClip = true;

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
                    ModSpells.GRAVITY_GRAB.onKill(player, this.method_48926(), target);
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
        if (target.getPos() == location) {
            target.setVelocity(new Vec3d(0, 0, 0));
        }
        else if (target.getPos().distanceTo(location) < 3) {
            target.setVelocity(location.subtract(target.getPos()).normalize().multiply(0.3f));
        } else {
            target.setVelocity(location.subtract(target.getPos()).multiply(0.3f));
        }
        target.velocityDirty = true;
        target.velocityModified = true;
    }


}
