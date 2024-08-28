package com.example.main.Spells.custom;

import com.example.main.SpellUtil.SpellSchool;
import com.example.main.SpellUtil.Spells.Spell;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

public class TelleportationSpell extends Spell {
    public TelleportationSpell(int manaCost, SpellSchool school, int chargeTime, String name, int cooldown, Identifier texture, int min, int max, int upcastcost) {
        super(manaCost, school, chargeTime, name, cooldown, texture, min, max, upcastcost);
    }

    @Override
    public void castSpell(PlayerEntity player, World world, ItemStack stack) {
        Vec3d start = player.getEyePos();
        Vec3d end = player.getEyePos().add(player.getRotationVec(0.5f).multiply(16 + 24 * Level()));
        BlockHitResult hitResult = world.raycast(new RaycastContext(start, end, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, player));
        if (hitResult.getType() != HitResult.Type.MISS) {
            end =hitResult.getPos();
        }
        player.setPosition(end);
        player.fallDistance = 0;
        world.playSound(null, end.x, end.y, end.z, SoundEvents.ENTITY_ENDERMAN_TELEPORT, SoundCategory.PLAYERS, 1,1);
        world.addImportantParticle(ParticleTypes.PORTAL, player.getX(), player.getY(), player.getZ(), 0.1,0,0 );
        world.addImportantParticle(ParticleTypes.PORTAL, player.getX(), player.getY(), player.getZ(), 0.07,0,0.07 );
        world.addImportantParticle(ParticleTypes.PORTAL, player.getX(), player.getY(), player.getZ(), 0,0,0.1 );
        world.addImportantParticle(ParticleTypes.PORTAL, player.getX(), player.getY(), player.getZ(), 0.07,0,-0.07 );
        world.addImportantParticle(ParticleTypes.PORTAL, player.getX(), player.getY(), player.getZ(), -0.1,0,0 );
        world.addImportantParticle(ParticleTypes.PORTAL, player.getX(), player.getY(), player.getZ(), -0.07,0,0.07 );
        world.addImportantParticle(ParticleTypes.PORTAL, player.getX(), player.getY(), player.getZ(), 0,0,-1.0 );
        world.addImportantParticle(ParticleTypes.PORTAL, player.getX(), player.getY(), player.getZ(), -0.07,0,-0.07 );
    }
}
