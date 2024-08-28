package com.example.main.Spells.custom.arcanesigils;

import com.example.main.Entity.sigils.ExplosionSigil;
import com.example.main.Spells.custom.actions.ActionPerformable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

public class ExplosionSigilAct extends ActionPerformable {

    public void Perform(ItemStack stack, PlayerEntity player, World world) {
        Vec3d end = player.getEyePos().add(player.getRotationVec(0.5f).multiply(64));
        BlockHitResult hitResult = world.raycast(new RaycastContext(player.getEyePos(), end, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, player));
        if (hitResult.getType() != HitResult.Type.MISS) {

            ExplosionSigil sigil = new ExplosionSigil(player,world);

            sigil.setPosition(hitResult.getBlockPos().toCenterPos().add(0, 0.5f, 0));
            world.spawnEntity(sigil);
        }
    }

}