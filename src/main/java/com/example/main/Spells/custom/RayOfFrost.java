package com.example.main.Spells.custom;

import com.example.main.SpellUtil.GenericSpellAbilities;
import com.example.main.SpellUtil.HitValues;
import com.example.main.SpellUtil.SpellSchool;
import com.example.main.SpellUtil.Spells.Spell;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.List;

public class RayOfFrost extends Spell {
    public RayOfFrost(int manaCost, SpellSchool school, int chargeTime, String name, int cooldown, Identifier texture, int min, int max, int cost) {
        super(0, school, chargeTime, name, cooldown, texture, min, max, cost);
        super.finishManaCost = manaCost;
        super.text = GetText();
    }

    @Override
    public void castReleaseSpell(PlayerEntity player, World world, ItemStack stack, int tick) {
        HitValues values = GenericSpellAbilities.HitscanAttack(player, world, 1 + Level(), 35 + 5*Level(), 17.5f, true, this, stack);

        GenericSpellAbilities.addParticles(world, player.getEyePos().subtract(0,0.25f,0), values.endPos(), new ItemStackParticleEffect(ParticleTypes.ITEM, new ItemStack(Items.PACKED_ICE)), 0.125f);

        double y = player.getEyeY() - 0.35f;
        player.setPitch(player.getPitch(1.0f) + 90);
        Vec3d lookdirection = player.getOppositeRotationVector(0.5F).multiply(1);
        Vec3d sillydirection = lookdirection.multiply(0.1f);
        ParticleEffect effect = new ItemStackParticleEffect(ParticleTypes.ITEM, new ItemStack(Items.SNOW_BLOCK));
        for (float i = -180; i < 180f; i += 5) {
            Vec3d vec3d = player.getOppositeRotationVector(1.0f);
            Vec3d vec3d2 = player.getRotationVec(1.0f);
            Quaternionf quaternionf = new Quaternionf().setAngleAxis((i * ((float)Math.PI / 180)), vec3d.x, vec3d.y, vec3d.z);
            Vec3d direction = lookdirection;
            Vector3f vector3f = vec3d2.toVector3f().rotate(quaternionf).mul(0.3f);
            world.addImportantParticle(effect, true, vector3f.x + player.getX() + direction.x, y + vector3f.y + direction.y, vector3f.z + player.getZ() + direction.z, sillydirection.x,sillydirection.y,sillydirection.z);
            direction = lookdirection.multiply(2);
            vector3f.mul(1.5f);
            world.addImportantParticle(effect, true, vector3f.x + player.getX() + direction.x, y + vector3f.y + direction.y, vector3f.z + player.getZ() + direction.z, sillydirection.x,sillydirection.y,sillydirection.z);
            direction = lookdirection.multiply(3);
            vector3f.mul(1.25f);
            world.addImportantParticle(effect, true, vector3f.x + player.getX() + direction.x, y + vector3f.y + direction.y, vector3f.z + player.getZ() + direction.z, sillydirection.x,sillydirection.y,sillydirection.z);
        }
        player.setPitch(player.getPitch(1.0f) - 90);
        world.playSound(null, player.getBlockPos(), SoundEvents.BLOCK_AMETHYST_CLUSTER_BREAK, SoundCategory.PLAYERS, 1, 1.3f);
        world.playSound(null, player.getBlockPos(), SoundEvents.BLOCK_AMETHYST_CLUSTER_BREAK, SoundCategory.PLAYERS, 1, 1.35f);
        world.playSound(null, player.getBlockPos(), SoundEvents.BLOCK_AMETHYST_CLUSTER_BREAK, SoundCategory.PLAYERS, 1, 1.4f);
        world.playSound(null, player.getBlockPos(), SoundEvents.BLOCK_GLASS_BREAK, SoundCategory.PLAYERS, 1, 0.8f);
        world.playSound(null, player.getBlockPos(), SoundEvents.BLOCK_GLASS_BREAK, SoundCategory.PLAYERS, 2, 1.35f);
        world.playSound(null, player.getBlockPos(), SoundEvents.BLOCK_GLASS_BREAK, SoundCategory.PLAYERS, 1, 1.25f);

        List<Entity> entities = values.getTargets();
        if (entities.isEmpty()) {
            return;
        }
        for (Entity entity : entities) {
            if (entity instanceof LivingEntity target) {
                int amplifier = -1;
                if (target.hasStatusEffect(StatusEffects.SLOWNESS)) {
                    amplifier = target.getStatusEffect(StatusEffects.SLOWNESS).getAmplifier();
                }
                target.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 60, amplifier + 1));
                target.setFrozenTicks(target.getFrozenTicks() + 200 + 40 * Level());
            }
        }
    }
}
