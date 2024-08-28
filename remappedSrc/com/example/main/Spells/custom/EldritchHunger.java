package com.example.main.Spells.custom;

import com.example.main.SpellUtil.DamageTypes.ModDamageTypes;
import com.example.main.SpellUtil.GenericSpellAbilities;
import com.example.main.SpellUtil.SpellSchool;
import com.example.main.Spells.extra.ContinousUsageSpell;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

public class EldritchHunger extends ContinousUsageSpell {


    public EldritchHunger(int manaCost, SpellSchool school, int chargeTime, String name, int cooldown, Identifier texture, int useTime, boolean needsHeld, int overtimecost, int minlevel, int maxlevel, int upcastCost, int upovertime) {
        super(manaCost, school, chargeTime, name, cooldown, texture, useTime, needsHeld, overtimecost, minlevel, maxlevel, upcastCost, upovertime);
    }

    @Override
    public void tickAction(PlayerEntity player, World world, ItemStack stack, int index, int tick) {
        if (Level() > 1 && tick % 5 == 0) {
            Vec3d pos = player.getEyePos();
            List<Entity> suck = GenericSpellAbilities.GetNearbyEntities(10, world, player, true, true, Entity.class);
            for (int i = 0; i < suck.size(); i++) {
                Entity entity = suck.get(i);
                Vec3d epos = entity.getEyePos();
                GenericSpellAbilities.ParticleNoS2C(world, pos, epos, ParticleTypes.SCULK_CHARGE_POP, 0.2f);
                epos = pos.subtract(epos).normalize().multiply(0.025f);
                if (entity instanceof LivingEntity living) {
                    living.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 4, 0, true, false), player);
                }
                entity.damage(entity.getDamageSources().playerAttack(player), 0.00001f);
                entity.damage(entity.getDamageSources().create(ModDamageTypes.MAGIC_TICK), 1);
                onHit(player, world, entity, 0.25f);
                if (!entity.isAlive()) {
                    onKill(player, world, entity);
                }
                entity.addVelocity(epos);
            }
        }
        Vec3d lookdirection = player.getOppositeRotationVector(0.5F);
        Vec3d sillydirection = lookdirection.multiply(0.1f);
        sillydirection.multiply(3);
        tick++;
        float distance = (float) tick / GetChargeTime(player, stack);
        distance *= distance;
        float progress = 6.2831f * distance;
        distance *= 5f;
        double x = player.getX();
        double y = player.getY() + 0.5f;
        double z = player.getZ();
        if (tick % 7 == 0) {
            for (float i = 0; i < 6.283f; i += 0.2f) {
            }
            if (Level() > 1) {
                for (float i = 0; i < 6.283f; i += 0.3f) {
                    world.addImportantParticle(ParticleTypes.GLOW, true, (MathHelper.sin(i) * 10) + x, y, (MathHelper.cos(i) * 10) + z, 0, 0.05, 0);

                }
            }
        }
    }

}


