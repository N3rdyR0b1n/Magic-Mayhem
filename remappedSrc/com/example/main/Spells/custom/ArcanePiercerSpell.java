package com.example.main.Spells.custom;

import com.example.main.SpellUtil.GenericSpellAbilities;
import com.example.main.SpellUtil.HitValues;
import com.example.main.SpellUtil.SpellSchool;
import com.example.main.SpellUtil.Spells.Spell;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

public class ArcanePiercerSpell extends Spell {
    public ArcanePiercerSpell(int manaCost, SpellSchool school, int chargeTime, String name, int cooldown, Identifier texture, int min, int max, int up) {
        super(0, school, chargeTime, name, cooldown, texture, min, max, up);
        super.finishManaCost = manaCost;
        super.text = GetText();
    }

    @Override
    public void castReleaseSpell(PlayerEntity player, World world, ItemStack stack, int tick) {
        world.playSound(null,player.getBlockPos(), SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.PLAYERS, 1, 0.75f);
        HitValues values = GenericSpellAbilities.HitscanAttack(player, world, 255, 192, 30, true, this, stack);
        GenericSpellAbilities.addParticles(world, player.getEyePos().subtract(0,0.25f,0), values.endPos(), ParticleTypes.SCULK_SOUL, 0.1f);
        Vec3d shockwaveParticleVec = player.getEyePos().add(player.getRotationVec(0.5F).multiply(4));
        GenericSpellAbilities.addParticles(world, player.getEyePos().subtract(0,0.25f,0), shockwaveParticleVec,ParticleTypes.SONIC_BOOM,1f);
        if (Level() > 0) {
            float killpercent = 0.05f * Level();
            List<Entity> entityList = values.getTargets();
            for (int i = 0; i < entityList.size(); i++) {
                if (entityList.get(i) instanceof LivingEntity living) {
                    if (living.getMaxHealth() > 0 && living.getHealth()/living.getMaxHealth() <= killpercent) {
                        int kills = 0;
                        while (living.isAlive()) {
                            if (kills > 255) {
                                break;
                            }
                            living.kill();
                            kills++;
                        }
                    }
                }
            }
        }
        super.castReleaseSpell(player,world,stack,tick);
    }


}
