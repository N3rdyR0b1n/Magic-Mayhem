package com.example.main.Spells.custom;

import com.example.main.SpellUtil.DamageTypes.ModDamageTypes;
import com.example.main.SpellUtil.GenericSpellAbilities;
import com.example.main.SpellUtil.SpellSchool;
import com.example.main.SpellUtil.Spells.NbtS;
import com.example.main.SpellUtil.Spells.Spell;
import com.example.main.Spells.ModSpells;
import com.google.common.base.MoreObjects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ShulkerBulletEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class ForceLevitate extends Spell {
    public ForceLevitate(int manaCost, SpellSchool school, int chargeTime, String name, int cooldown, Identifier texture, int min, int max, int cost) {
        super(manaCost, school, chargeTime, name, cooldown, texture, min, max, cost);
    }

    @Override
    public void castSpell(PlayerEntity player, World world, ItemStack stack) {
        Entity target = GenericSpellAbilities.HitscanSelect(world, player, 256, true).getTarget();
        if (target == null) {
            return;
        }
        NbtCompound compound = (stack.getSubNbt(NbtS.getNbt(stack)));
        if (stack.getSubNbt(NbtS.getNbt(stack)) != null) {
            compound.putBoolean("Hit", true);
        }
        else {
            return;
        }
        world.playSound(null, player.getBlockPos(), SoundEvents.ENTITY_SHULKER_SHOOT, SoundCategory.PLAYERS, 1, 0.5f);
        world.playSound(null, player.getBlockPos(), SoundEvents.ENTITY_SHULKER_SHOOT, SoundCategory.PLAYERS, 1, 2f);
        new ShulkerBulletEntity(world, player, target, Direction.Axis.Y);
        for (int i = 0; i < Level() + 3; i++) {
            world.spawnEntity(new ShulkerBulletEntity(world,player, target, Direction.Axis.Y) {
                @Override  protected void onEntityHit(EntityHitResult entityHitResult) {
                super.onEntityHit(entityHitResult);
                Entity entity = entityHitResult.getEntity();
                Entity entity2 = this.getOwner();
                LivingEntity livingEntity = entity2 instanceof LivingEntity ? (LivingEntity)entity2 : null;
                ModSpells.FORCE_LEVITATE.onHit(((PlayerEntity) this.getOwner()), this.method_48926(), entity, 1);
                if (entity2 instanceof PlayerEntity playerentity) {
                    entity.damage(entity.getDamageSources().playerAttack(playerentity), 0.0001f);
                }
                boolean bl = entity.damage(ModDamageTypes.of(world, ModDamageTypes.MAGIC_TICK), 6);

                if (entity instanceof LivingEntity living && living.isDead()) {
                    ModSpells.FORCE_LEVITATE.onKill((PlayerEntity) this.getOwner(), world, living);
                }
                if (bl) {
                    this.applyDamageEffects(livingEntity, entity);
                    if (entity instanceof LivingEntity) {
                        LivingEntity livingEntity2 = (LivingEntity)entity;
                        livingEntity2.addStatusEffect(new StatusEffectInstance(StatusEffects.LEVITATION, 200), MoreObjects.firstNonNull(entity2, this));
                    }
                }

            }});
        }
    }
    @Override
    public void applyCost(PlayerEntity player, ItemStack stack, NbtCompound nbt, int slot) {
        if (nbt.getBoolean("Hit")) {
            super.applyFinishCost(player, stack, nbt, slot);
        }
    }
    @Override
    public void applyCooldown(PlayerEntity player, ItemStack stack,NbtCompound compound, int slot) {
        if (stack.getSubNbt(NbtS.SLOT + slot).getBoolean("Hit")) {
            super.applyCooldown(player, stack, compound ,slot);
            stack.getSubNbt(NbtS.SLOT + slot).putBoolean("Hit", false);
        }
    }


}
