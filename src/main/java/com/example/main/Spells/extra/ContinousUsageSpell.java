package com.example.main.Spells.extra;

import com.example.main.SpellUtil.*;
import com.example.main.SpellUtil.Spells.NbtS;
import com.example.main.SpellUtil.Spells.Spell;
import com.example.main.Spells.ModSpells;
import com.example.main.SpellUtil.SpellSchool;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public abstract class ContinousUsageSpell extends Spell {
    public int MaxUseTime;
    public boolean needsHeld;
    public int overtimecost;
    public int leftovertimecost;
    public int UseTime;
    public int LeftUseTime;
    private int overtimeUpcastCost;
    @Override
    public NbtCompound ToNbt() {
        NbtCompound compound = super.ToNbt();
        compound.putInt("MaxUseTime", MaxUseTime);
        compound.putInt("UseTime", 0);
        compound.putBoolean("Held", needsHeld);
        compound.putInt("OverTimeCost", overtimecost);
        return compound;
    }

    public void UpdateFromNbt(NbtCompound compound) {
        super.UpdateFromNbt(compound);
        this.MaxUseTime = compound.getInt("MaxUseTime");
        this.needsHeld = compound.getBoolean("Held");
        this.overtimecost = compound.getInt("OverTimeCost");
    }

    public ContinousUsageSpell(int manaCost, SpellSchool school, int chargeTime, String name, int cooldown, Identifier texture, int useTime, boolean needsHeld, int overtimecost, int minlevel, int maxlevel, int upcastCost, int upovertime) {
        super(manaCost, school, chargeTime, name, cooldown, texture, minlevel, maxlevel, upcastCost);
        this.MaxUseTime = useTime;
        this.UseTime = -1;
        this.needsHeld = needsHeld;
        this.overtimecost = overtimecost;
        this.overtimeUpcastCost = upovertime;
        this.text = GetText();
    }

    public ContinousUsageSpell(int manaCost, SpellSchool school, int chargeTime, String name, int cooldown, Identifier texture, int useTime, boolean needsHeld, int overtimecost) {
        this(manaCost, school, chargeTime, name, cooldown, texture, useTime, needsHeld, overtimecost, 0, 0, 0, 0);
        if (overtimecost > 0) {
            text = ModSpells.formattedBasicSpell(name, manaCost, overtimecost);
        }
        else if (overtimecost == 0) {
            text = ModSpells.formattedBasicSpell(name, manaCost);
        }

    }

    @Override
    public void castSpell(PlayerEntity player, World world, ItemStack stack) {
        if (!world.isClient()) {
            UseTime = MaxUseTime;
        }
        else {
            applyTickCost(player, stack, stack.getSubNbt(NbtS.getNbt(stack)), stack.getNbt().getInt(NbtS.SELECT), 0);
        }
        super.castSpell(player,world,stack);
    }

    public void tickAction(PlayerEntity player, World world, ItemStack stack, int index, int tick) {

    }

    public void tickLeftAction(PlayerEntity player, World world, ItemStack stack, int index, int tick) {

    }

    public void endAction(PlayerEntity player, World world, ItemStack stack) {
        if (!world.isClient()) {
            UseTime = -1;
        }

    }



    public boolean canCastTickSpell(PlayerEntity player, World world, ItemStack stack) {
        return ((ManaContainer) player).getMana().getStoredMana() >= TickCost() && UseTime > 0;
    }

    public void applyTickCost(PlayerEntity player, ItemStack stack, NbtCompound nbt, int slot, int tick) {
        if (tick % 20 == 0) {
            ((ManaContainer) player).getMana().removeMana(TickCost());
        }
        if (!player.getWorld().isClient()) {
            UseTime--;
        }
    }
    public void applyLeftTickCost(PlayerEntity player, ItemStack stack, NbtCompound nbt, int slot, int tick) {
        if (tick % 20 == 0) {
            player.sendMessage(Text.of(player.getWorld().isClient() + " " + ((ManaContainer)player).getMana().getStoredMana()));
            ((ManaContainer) player).getMana().removeMana(leftovertimecost);
            player.sendMessage(Text.of(player.getWorld().isClient() + " " + ((ManaContainer)player).getMana().getStoredMana()));

        }
        LeftUseTime--;
    }
    public boolean canCastLeftTickSpell(PlayerEntity player, World world, ItemStack stack, NbtCompound nbt) {
        return ((ManaContainer) player).getMana().getStoredMana() >= leftovertimecost  && LeftUseTime > 0;
    }

    @Override
    public void applyCooldown(PlayerEntity player, ItemStack stack, NbtCompound nbt, int slot) {
        if (!player.getWorld().isClient()) {
            UseTime = -1;
            super.applyCooldown(player, stack, nbt, slot);
        }
    }
    public boolean shouldEnd(PlayerEntity player, ItemStack stack, NbtCompound compound) {
        return UseTime > 0;
    }
    public boolean canEnd(PlayerEntity player, ItemStack stack, NbtCompound spellnbt) {
        return true;
    }

    public float GetProgress(PlayerEntity player, ItemStack stack, NbtCompound compound) {
        return (float) UseTime /MaxUseTime;
    }
    @Override
    public String GetText() {
        if (Manacost() == 0 && FinManacost() != 0) {
            return ModSpells.FormatSpell(name,FinManacost(), TickCost(), level);
        }
        return ModSpells.FormatSpell(name,Manacost(), TickCost(), level);
    }

    public float TickCost() {
        return overtimecost + overtimeUpcastCost * Level();
    }
    @Override
    public boolean Upcast(PlayerEntity player) {
        if (UseTime >= 0) {
            return false;
        }
        else return super.Upcast(player);
    }
    @Override
    public boolean DownCast(PlayerEntity player) {
        if (UseTime >= 0) {
            return false;
        }
        else return super.DownCast(player);
    }

    @Override
    public Text[] GetDescription(PlayerEntity player, ItemStack stack) {
        Text[] text = super.GetDescription(player,stack);
        Text[] desc = new Text[text.length + 2];
        desc[0] = text[0];
        desc[1] = text[1];
        desc[2] = text[2];
        desc[3] = Text.translatable("Mana/s : " + TickCost()).setStyle(Style.EMPTY.withColor(Formatting.AQUA));
        desc[4] = Text.translatable("Duration : " + ((float)MaxUseTime)/20f + "s").setStyle(Style.EMPTY.withColor(Formatting.GRAY));
        desc[5] = text[3];
        desc[6] = text[4];
        return desc;
    }


}