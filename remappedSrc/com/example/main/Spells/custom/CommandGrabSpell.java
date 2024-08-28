package com.example.main.Spells.custom;

import com.example.main.Entity.custom.GravityHelperEntity;
import com.example.main.SpellUtil.*;
import com.example.main.SpellUtil.Spells.NbtS;
import com.example.main.Spells.ModSpells;
import com.example.main.Spells.extra.ContinousUsageSpell;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShieldItem;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class CommandGrabSpell extends ContinousUsageSpell {

    public CommandGrabSpell(int manaCost, SpellSchool school, int chargeTime, String name, int cooldown, Identifier texture, int useTime, boolean needsHeld, int overtimecost, int levels) {
        super(manaCost, school, chargeTime, name, cooldown, texture, useTime, needsHeld, overtimecost, levels, levels, 0, 0);
        text = ModSpells.formattedBasicSpell(name, manaCost, overtimecost * 4);
    }

    @Override
    public void castSpell(PlayerEntity player, World world, ItemStack stack) {
        super.castSpell(player, world, stack);
        String index = NbtS.getNbt(stack);
        NbtCompound nbt = stack.getSubNbt(index);
        Entity entity = GenericSpellAbilities.HitscanSelect(world, player, 10, true).getTarget();
        if (entity == null) {
            return;
        }
        if (entity instanceof LivingEntity living && living.isUsingItem() && living.getActiveItem().getItem() instanceof ShieldItem) {
            return;
        }
        nbt.putBoolean("Hit", true);
        if (!world.isClient()) {
            stack.setSubNbt(index, nbt);
            GravityHelperEntity gravity = new GravityHelperEntity(player, world, entity);
            world.spawnEntity(gravity);
            world.playSound(null, player.getBlockPos(), SoundEvents.BLOCK_END_PORTAL_FRAME_FILL, SoundCategory.PLAYERS, 2, 2);
        }

    }
    @Override
    public void tickAction(PlayerEntity player, World world, ItemStack stack, int index, int tick) {
        if (tick % 20 == 0) {
            world.playSound(null, player.getBlockPos(), SoundEvents.BLOCK_BEACON_AMBIENT, SoundCategory.PLAYERS, 2, 0.5f);
        }
    }

    @Override
    public void applyCooldown(PlayerEntity player, ItemStack stack, NbtCompound nbt, int slot) {
        if (!player.method_48926().isClient() && nbt.getBoolean("Hit")) {
            UseTime = -1;
            nbt.putBoolean("Hit", false);
            super.applyCooldown(player, stack, nbt, slot);
        }
    }
    @Override
    public void applyCost(PlayerEntity player, ItemStack stack, NbtCompound nbt, int slot) {
        if (!nbt.getBoolean("Hit")) {
            return;
        }
        ((ManaContainer)player).getMana().removeMana(manaCost);
    }
    @Override
    public void applyTickCost(PlayerEntity player, ItemStack stack, NbtCompound nbt, int slot, int tick) {
        if (tick % 4 == 0) {
            ((ManaContainer) player).getMana().removeMana(overtimecost);
        }
        if (!player.method_48926().isClient()) {
            UseTime--;
        }
    }
    @Override
    public boolean canCastTickSpell(PlayerEntity player, World world, ItemStack stack) {
        return ((ManaContainer) player).getMana().getStoredMana() >= overtimecost && UseTime > 0;
    }

    @Override
    public float TickCost() {
        return 5 * super.TickCost();
    }
}
