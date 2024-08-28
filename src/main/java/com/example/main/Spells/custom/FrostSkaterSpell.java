package com.example.main.Spells.custom;

import com.example.main.SpellUtil.GenericSpellAbilities;
import com.example.main.SpellUtil.SpellSchool;
import com.example.main.Spells.extra.ContinousUsageSpell;
import net.minecraft.block.IceBlock;
import net.minecraft.enchantment.FrostWalkerEnchantment;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.damage.DamageSources;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

public class FrostSkaterSpell extends ContinousUsageSpell {
    public FrostSkaterSpell(int manaCost, SpellSchool school, int chargeTime, String name, int cooldown, Identifier texture, int useTime, boolean needsHeld, int overtimecost, int minlevel, int maxlevel, int upcastCost, int upovertime) {
        super(manaCost, school, chargeTime, name, cooldown, texture, useTime, needsHeld, overtimecost, minlevel, maxlevel, upcastCost, upovertime);
    }

    @Override
    public void tickAction(PlayerEntity player, World world, ItemStack stack, int index, int tick) {
        int spelllevel = Level();
        FrostWalkerEnchantment.freezeWater(player, player.getWorld(), player.getBlockPos(), 2 + spelllevel);

        if (player.isOnGround() && player.getSteppingBlockState().getBlock().getSlipperiness() > 0.8f) {
            if (player.isInPose(EntityPose.CROUCHING)) {
                player.setVelocity(0, 0, 0);
            }
            else {
                Vec3d explode = player.getVelocity();
                ParticleEffect effect = new ItemStackParticleEffect(ParticleTypes.ITEM, new ItemStack(Items.PACKED_ICE));
                double x = player.getX() - 0.5f;
                double y = player.getY();
                double z = player.getZ() - 0.5f;
                Random random = world.getRandom();
                Vec3d movement = player.getRotationVec(0.5F);
                movement = new Vec3d(movement.x, 0, movement.z).normalize().multiply(1.25f + 0.1f * spelllevel);
                player.setVelocity(movement.x, 0, movement.z);
                movement = movement.multiply(-0.25f);
                for (int i = 0; i < 5; i++) {
                    world.addParticle(effect, x + random.nextFloat(), y + random.nextFloat()*2, z + random.nextFloat(), movement.x, movement.y, movement.z);
                }
                if (!world.isClient() && (explode.x != 0 || explode.z != 0)) {
                    if (!GenericSpellAbilities.explodeFlatAoe(15 + spelllevel * 5,
                            2, 0.3f, world, player, player, this, 1f, 0.5f, player.getDamageSources().playerAttack(player)).isEmpty()) {
                        player.setVelocity(movement.multiply(3).add(0,0.35 + spelllevel * 0.125f, 0));
                        player.velocityModified = true;
                        player.velocityDirty = true;
                        BlockPos pos = player.getBlockPos();
                        world.playSound(player, pos, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.PLAYERS, 2 + Level(), 1);
                        world.playSound(null, pos, SoundEvents.BLOCK_AMETHYST_CLUSTER_BREAK, SoundCategory.PLAYERS, 1, 1.3f);
                        world.playSound(null, pos, SoundEvents.BLOCK_AMETHYST_CLUSTER_BREAK, SoundCategory.PLAYERS, 2, 1.35f);
                        world.playSound(null, pos, SoundEvents.BLOCK_AMETHYST_CLUSTER_BREAK, SoundCategory.PLAYERS, 1, 1.4f);
                        world.playSound(null, pos, SoundEvents.BLOCK_GLASS_BREAK, SoundCategory.PLAYERS, 1, 0.8f);
                        world.playSound(null, pos, SoundEvents.BLOCK_GLASS_BREAK, SoundCategory.PLAYERS, 2, 1.35f);
                        world.playSound(null, pos, SoundEvents.BLOCK_GLASS_BREAK, SoundCategory.PLAYERS, 1, 1.25f);

                    }

                }

            }
        }
    }
}
