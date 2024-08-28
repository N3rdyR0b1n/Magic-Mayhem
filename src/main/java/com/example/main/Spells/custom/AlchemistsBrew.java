package com.example.main.Spells.custom;

import com.example.main.SpellUtil.*;
import com.example.main.SpellUtil.Spells.Spell;
import com.example.main.Spells.ModSpells;
import com.mojang.datafixers.util.Pair;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.PotionEntity;
import net.minecraft.item.*;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.potion.PotionUtil;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import java.util.List;

public class AlchemistsBrew extends Spell {

    public AlchemistsBrew(SpellSchool school, int chargeTime, String name, int cooldown, Identifier texture, int level) {
        super(0, school, chargeTime, name, cooldown, texture, level, level, 0);
    }

    @Override
    public void castSpell(PlayerEntity player, World world, ItemStack stack) {
        ManaCostVariable var = calculatemanacost(player, stack);
        Item item = var.fuel.getItem();
        if (item instanceof ThrowablePotionItem) {
            if (item instanceof LingeringPotionItem) {
                world.playSound(null, player.getBlockPos(), SoundEvents.ENTITY_LINGERING_POTION_THROW, SoundCategory.PLAYERS, 1, 1);
            } else {
                world.playSound(null, player.getBlockPos(), SoundEvents.ENTITY_SPLASH_POTION_THROW, SoundCategory.PLAYERS, 1, 1);
            }
            PotionEntity potionEntity = new PotionEntity(world, player);
            potionEntity.setItem(var.fuel);
            potionEntity.setVelocity(player, player.getPitch(), player.getYaw(), 0.0f, 1.0f, 0.2f);
            world.spawnEntity(potionEntity);
        }
        super.castSpell(player,world,stack);
    }



    @Override
    public String getInfo(PlayerEntity player, ItemStack stack) {
        return ModSpells.formattedBasicSpell(name, calculatemanacost(player, stack).cost);
    }
    private record ManaCostVariable(ItemStack fuel, int cost) {}

    @Override
    public void castReleaseSpell(PlayerEntity player, World world, ItemStack stack, int tick) {
        ManaCostVariable var = calculatemanacost(player, stack);
        Item item = var.fuel.getItem();
        if (item instanceof PotionItem) {
            if (item instanceof ThrowablePotionItem) {

            }
            else {
                List<StatusEffectInstance> list = PotionUtil.getPotionEffects(var.fuel);
                for (StatusEffectInstance statusEffectInstance : list) {
                    if (statusEffectInstance.getEffectType().isInstant()) {
                        statusEffectInstance.getEffectType().applyInstantEffect(player, player, player, statusEffectInstance.getAmplifier(), 1.0);
                        continue;
                    }
                    player.addStatusEffect(new StatusEffectInstance(statusEffectInstance));
                }
            }
        }
        else if (item.isFood()) {
            var.fuel.increment(1);
            player.eatFood(world, var.fuel);
        }
    }
    private ManaCostVariable calculatemanacost(PlayerEntity player, ItemStack stack) {
        ItemStack potionstack = GenericSpellAbilities.GetOppositeHandStack(player, stack);
        int manacost = 10;

        if (potionstack != ItemStack.EMPTY && potionstack.getItem().isFood()) {
            Item item = potionstack.getItem();
            manacost += item.getFoodComponent().getHunger();
            manacost += (int) item.getFoodComponent().getSaturationModifier();
            List<Pair<StatusEffectInstance, Float>> list = item.getFoodComponent().getStatusEffects();
            for (Pair<StatusEffectInstance, Float> pair : list) {
                StatusEffectInstance instance = pair.getFirst();
                int duration = instance.getDuration();
                int amp = instance.getAmplifier();
                if (amp < 1) {
                    amp = 1;
                }
                if (duration < 1) {
                    duration = 1;
                }
                manacost += (int) (amp * duration / 60 * pair.getSecond());
            }

        }
        else if (potionstack.getItem() instanceof PotionItem) {
            List<StatusEffectInstance> list = PotionUtil.getPotionEffects(potionstack);
            for (StatusEffectInstance instance : list) {
                if (instance.getEffectType().isInstant()) {
                    int addedcost = instance.getAmplifier()*35;
                    if (potionstack.getItem() instanceof LingeringPotionItem) {
                        addedcost *= 2;
                    }
                    manacost+=addedcost;
                    continue;
                }
                int duration = instance.getDuration();
                int amp = instance.getAmplifier();
                if (amp < 1) {
                    amp = 1;
                }
                if (duration < 60) {
                    duration = 60;
                }
                manacost += amp * duration / 60;

            }
        }
        return new ManaCostVariable(potionstack, manacost);
    }
    @Override
    public int GetChargeTime(PlayerEntity player, ItemStack stack) {
        if (GenericSpellAbilities.GetOppositeHandStack(player, stack).getItem() instanceof ThrowablePotionItem) {
            return 0;
        }
        return super.GetChargeTime(player,stack);
    }
    @Override
    public boolean canCastSpell(PlayerEntity player, World world, ItemStack stack, NbtCompound nbt) {
        return ((ManaContainer)player).getMana().getStoredMana() >= calculatemanacost(player, GenericSpellAbilities.GetOppositeHandStack(player, stack)).cost && super.canCastSpell(player, world, stack, nbt);
    }

    @Override
    public boolean canCastFinishSpell(PlayerEntity player, World world, ItemStack stack, NbtCompound nbt, int tick) {
        return ((ManaContainer)player).getMana().getStoredMana() >= calculatemanacost(player, GenericSpellAbilities.GetOppositeHandStack(player, stack)).cost && super.canCastFinishSpell(player, world, stack, nbt, tick);
    }
    public void applyCost(PlayerEntity player, ItemStack stack, NbtCompound nbt, int slot) {
        ManaCostVariable var = calculatemanacost(player, stack);
        Item item = var.fuel.getItem();
        if (item instanceof PotionItem) {
            if (item instanceof  ThrowablePotionItem) {
                ((ManaContainer)player).getMana().removeMana(var.cost);
            }
        }
    }
    public void applyFinishCost(PlayerEntity player, ItemStack stack, NbtCompound nbt, int slot) { ManaCostVariable var = calculatemanacost(player, stack);
        Item item = var.fuel.getItem();
        if (item instanceof PotionItem) {
            if (item instanceof ThrowablePotionItem) {
            }
            else {
                ((ManaContainer)player).getMana().removeMana(var.cost);
            }
        }
        else if (item.isFood()) {
            ((ManaContainer)player).getMana().removeMana(var.cost);
        }
    }

}

