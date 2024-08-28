package com.example.main.Spells.custom;

import com.example.main.SpellUtil.GenericSpellAbilities;
import com.example.main.SpellUtil.SpellSchool;
import com.example.main.Spells.ModSpells;
import com.example.main.Spells.extra.ContinousUsageSpell;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.List;

public class HealingCircle extends ContinousUsageSpell {

    public HealingCircle(int manaCost, SpellSchool school, int chargeTime, String name, int cooldown, Identifier texture, int useTime, boolean needsHeld, int overtimecost, int min, int max, int cost , int uptime) {
        super(manaCost, school, chargeTime, name, cooldown, texture, useTime, needsHeld, overtimecost, min, max, cost, uptime);

    }


    @Override
    public void tickAction(PlayerEntity player, World world, ItemStack stack, int index, int tick) {
        if (tick % 10 == 0) {
            world.playSound(null, player.getBlockPos(), SoundEvents.BLOCK_BEACON_AMBIENT, SoundCategory.PLAYERS, 2, 2);
            double y = player.getY() + 0.75f;
            int dist = 5 + 2 * Level();
            for (float i = 0; i < 6.283f; i += 0.05f) {
                world.addParticle(ParticleTypes.HAPPY_VILLAGER, true, (MathHelper.sin(i) * dist) + player.getX(), y, (MathHelper.cos(i) * dist) + player.getZ(), 0, 0.05, 0);
            }
            List<LivingEntity> targets = GenericSpellAbilities.GetNearbyEntities(dist, world, player, true, true, LivingEntity.class);
            targets.add(player);
            for (LivingEntity entity : targets) {
                if (entity.isUndead()) {
                    this.onHit(player, world, entity, 0.2f);
                    entity.damage(entity.getDamageSources().indirectMagic(player, player), 4);
                    if (!entity.isAlive()) {
                        this.onKill(player,world,entity);
                    }
                    continue;
                }
                entity.heal(2);
            }
        }
    }
    @Override
    public void castReleaseSpell(PlayerEntity player ,World world, ItemStack stack, int tick) {
        world.playSound(null, player.getBlockPos(), SoundEvents.BLOCK_BEACON_ACTIVATE, SoundCategory.PLAYERS, 2, 1);
        int dist = 5 + 2 * Level();
        double y = player.getY() + 0.75f;
        for (float i = 0; i < 6.283f; i += 0.05F) {
            world.addParticle(ParticleTypes.COMPOSTER, true, (MathHelper.sin(i) * dist) + player.getX(), y, (MathHelper.cos(i) * dist) + player.getZ(), 0, 0.2, 0);
        }
        List<LivingEntity> targets = GenericSpellAbilities.GetNearbyEntities(dist, world, player, true, true, LivingEntity.class);
        targets.add(player);
        for (LivingEntity entity : targets) {
            if (entity.isUndead()) {
                onHit(player, world, entity,1f);
                entity.damage(entity.getDamageSources().indirectMagic(player, player), 15);
                if (entity.isDead()) {
                    onKill(player, world, entity);
                }
                continue;
            }
            entity.heal(10);
        }
    }
    @Override
    public String GetText() {
        return ModSpells.FormatSpell(name, Manacost(), level);
    }



}
