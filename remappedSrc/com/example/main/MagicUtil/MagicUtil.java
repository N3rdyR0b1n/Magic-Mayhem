package com.example.main.MagicUtil;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

import java.util.List;

import static net.minecraft.world.explosion.Explosion.getExposure;

public class MagicUtil {
    public static void explode(LivingEntity shooter, float damagemultiplier, float knockbackmultiplier, float areamultiplier, World world) {
        Vec3d vec3d = shooter.getPos();
        List<LivingEntity> list = world.getNonSpectatingEntities(LivingEntity.class, shooter.getBoundingBox().expand(5.0 * areamultiplier));
        for (LivingEntity livingEntity : list) {
            if (livingEntity == shooter || ((shooter.distanceTo(livingEntity) / areamultiplier)*(shooter.distanceTo(livingEntity) / areamultiplier)) > 25.0)
                continue;
            boolean bl = false;
            for (int i = 0; i < 2; ++i) {
                Vec3d vec3d2 = new Vec3d(livingEntity.getX(), livingEntity.getBodyY(0.5 * (double) i), livingEntity.getZ());
                BlockHitResult hitResult = world.raycast(new RaycastContext(vec3d, vec3d2, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, shooter));
                if (((HitResult) hitResult).getType() != HitResult.Type.MISS) continue;
                bl = true;
                break;
            }
            if (!bl) continue;
            double x = livingEntity.getX() - shooter.getX();
            double y = (livingEntity.getEyeY() - shooter.getY());
            double z = livingEntity.getZ() - shooter.getZ();
            double aa = Math.sqrt(x * x + y * y + z * z);
            if (aa != 0.0) {
                x /= aa;
                y /= aa;
                z /= aa;
                double area = (5 * areamultiplier);
                double w = Math.sqrt(livingEntity.squaredDistanceTo(vec3d)) / (area * area);
                double ab = getExposure(vec3d, livingEntity);
                double ac = (1.0 - w) * ab;
                float g = damagemultiplier * ((float) Math.sqrt((25.0 - (shooter.distanceTo(livingEntity) / areamultiplier) * (shooter.distanceTo(livingEntity) / areamultiplier))));
                livingEntity.damage(livingEntity.getDamageSources().playerAttack((PlayerEntity) shooter), g);
                livingEntity.addVelocity(
                        x * ac * knockbackmultiplier,
                        y * ac * knockbackmultiplier,
                        z * ac * knockbackmultiplier
                );
            }
        }
    }
}
