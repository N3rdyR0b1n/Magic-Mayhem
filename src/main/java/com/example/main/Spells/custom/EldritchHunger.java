package com.example.main.Spells.custom;

import com.example.main.Attributes.ModAttributes;
import com.example.main.ModS2CMessages.ModPacketChannels;
import com.example.main.SpellUtil.DamageTypes.ModDamageTypes;
import com.example.main.SpellUtil.GenericSpellAbilities;
import com.example.main.SpellUtil.ManaContainer;
import com.example.main.SpellUtil.SpellSchool;
import com.example.main.Spells.extra.ContinousUsageSpell;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class EldritchHunger extends ContinousUsageSpell {


    public EldritchHunger(int manaCost, SpellSchool school, int chargeTime, String name, int cooldown, Identifier texture, int useTime, boolean needsHeld, int overtimecost, int minlevel, int maxlevel, int upcastCost, int upovertime) {
        super(manaCost, school, chargeTime, name, cooldown, texture, useTime, needsHeld, overtimecost, minlevel, maxlevel, upcastCost, upovertime);
    }

    @Override
    public void tickAction(PlayerEntity player, World world, ItemStack stack, int index, int tick) {
        if (tick % 5 == 0) {
            Vec3d pos = player.getEyePos();
            float a = 0;
            float b = 0;
            float range = 10 + 2 * Level();
            List<Entity> suck = GenericSpellAbilities.GetNearbyEntities(range, world, player, true, true, Entity.class);
            for (int i = 0; i < suck.size(); i++) {
                Entity entity = suck.get(i);
                if (entity.isAlive()) {
                    a++;
                    Vec3d epos = entity.getEyePos();
                    GenericSpellAbilities.ParticleNoS2C(world, pos, epos, ParticleTypes.SCULK_CHARGE_POP, 0.2f);
                    epos = pos.subtract(epos).normalize().multiply(0.025f + 0.025f * Level());
                    if (entity instanceof LivingEntity living) {
                        living.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 6, 0, true, false), player);
                    }
                    entity.damage(entity.getDamageSources().playerAttack(player), 0.00001f);
                    entity.damage(entity.getDamageSources().create(ModDamageTypes.MAGIC_TICK), 2);
                    onHit(player, world, entity, 0.25f);
                    if (!entity.isAlive()) {
                        onKill(player, world, entity);
                        b++;
                        continue;
                    }
                    entity.addVelocity(epos);
                }
            }
            double x = player.getX();
            double y = player.getY() + 0.5f;
            double z = player.getZ();
            for (float i = 0; i < 6.283f; i += 0.1f) {
                world.addImportantParticle(ParticleTypes.GLOW, true, (MathHelper.sin(i) * range) + x, y, (MathHelper.cos(i) * range) + z, 0, 0.05, 0);
            }
            if (!world.isClient) {
                if (a > 0) {
                    float combined = a * 2 + b * 10;
                    player.heal(a/2 + b);
                    ((ManaContainer)player).getMana().addMana(combined);
                    PacketByteBuf sync = PacketByteBufs.create();
                    sync.writeFloat(combined);
                    ServerPlayNetworking.send((ServerPlayerEntity) player, ModPacketChannels.ADDMANAFROMSERVER, sync);
                }
            }
        }

    }

}


