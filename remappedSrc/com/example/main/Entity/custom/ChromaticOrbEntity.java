package com.example.main.Entity.custom;

import com.example.main.Entity.ModEntities;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class ChromaticOrbEntity extends ThrownItemEntity {

    public ItemStack sillystack;
    public ChromaticOrbEntity(EntityType<? extends ThrownItemEntity> entityType, LivingEntity livingEntity, World world, ItemStack stack) {
        super(entityType, livingEntity, world);
        this.sillystack = stack;
    }

    public ChromaticOrbEntity(EntityType<ChromaticOrbEntity> chromaticOrbEntityEntityType, World world) {
        super(chromaticOrbEntityEntityType, world);
    }

    public void tick() {
        if (age >= 604) {
            this.kill();
        }
        super.tick();
    }

    @Override
    protected Item getDefaultItem() {
        return Items.FIRE_CHARGE;
    }


    protected void onEntityHit(EntityHitResult entityHitResult) {
    }

    protected void onBlockHit(BlockHitResult result) {
    }

    public void HasHit() {
        this.setInvisible(true);
        this.setVelocity(0,0,0);
        this.noClip = true;
        this.setNoGravity(true);
        this.age = 600;
    }
    public List<LivingEntity> GetNearbyEntities (float range, World world,LivingEntity source) {
        List<LivingEntity> Entities = world.getNonSpectatingEntities(LivingEntity.class, this.getBoundingBox().expand(range));
        List<LivingEntity> ReturnList = new ArrayList<>();
        for (LivingEntity entity : Entities) {
            if (entity == source || this.distanceTo(entity) > range) {
                continue;
            }
            boolean bl = false;
            for (int i = 0; i < 2; ++i) {
                Vec3d vec3d2 = new Vec3d(this.getX(), this.getBodyY(0.5 * (double) i), this.getZ());
                BlockHitResult hitResult = world.raycast(new RaycastContext(this.getPos(), vec3d2, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, source));
                if (((HitResult) hitResult).getType() != HitResult.Type.MISS) continue;
                bl = true;
                break;
            }
            if (!bl) continue;
            ReturnList.add(entity);
        }

        return ReturnList;
    }
}
