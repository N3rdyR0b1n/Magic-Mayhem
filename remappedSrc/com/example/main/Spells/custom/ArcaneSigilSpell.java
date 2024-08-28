package com.example.main.Spells.custom;

import com.example.main.Attributes.ModAttributes;
import com.example.main.SpellUtil.SpellSchool;
import com.example.main.SpellUtil.Spells.NbtS;
import com.example.main.SpellUtil.Spells.Spell;
import com.example.main.Spells.custom.actions.*;
import com.example.main.Spells.custom.arcanesigils.*;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class ArcaneSigilSpell extends Spell {


    private ActionPerformable[] Sigils;
    private String[] OrbData;

    private int[] OrbColor;
    public ArcaneSigilSpell(int manaCost, SpellSchool school, int chargeTime, String name, int cooldown, Identifier texture) {
        super(0, school, chargeTime, name, cooldown, texture);
        super.finishManaCost = manaCost;
        super.text = GetText();
        OrbData = new String[6];
        OrbData[0] = "Curse";
        OrbData[1] = "Burn";
        OrbData[2] = "Freeze";
        OrbData[3] = "Explode";
        OrbData[4] = "Implode";
        OrbData[5] = "Push";

        Sigils = new ActionPerformable[6];
        Sigils[0] = new CurseSigilAct();
        Sigils[1] = new FireSigilAct();
        Sigils[2] = new IceSigilAct();
        Sigils[3] = new ExplosionSigilAct();
        Sigils[4] = new ImplosionSigilAct();
        Sigils[5] = new KnockbackSigilAct();

        OrbColor = new int[6];
        OrbColor[0] = 7541903;
        OrbColor[1] = 8560940;
        OrbColor[2] = 16777088;
        OrbColor[3] = 16728079;
        OrbColor[4] = 13893628;
        OrbColor[5] = 3604244;

    }



    @Override
    public void castReleaseSpell(PlayerEntity player, World world, ItemStack stack, int tick) {
        NbtCompound compound = (NbtCompound) stack.getNbt().get(NbtS.getNbt(stack));
        byte sigil = compound.getByte("Sigil");
        if (sigil >= 6 || sigil <0) {
            compound.putByte("Sigil", (byte) 0);
            return;
        }
        if (player.isInPose(EntityPose.CROUCHING)) {
            sigil++;
            if (sigil > 5) {
                sigil = 0;
            }
            compound.putByte("Sigil", sigil);
        }
        else {
            if (!world.isClient) {
                Sigils[sigil].Perform(stack, player, world);
            }
        }
        super.castReleaseSpell(player,world,stack,tick);
    }

    @Override
    public String getExtraInfo(ItemStack stack) {
        if (stack.hasNbt()) {
            NbtCompound compound = (NbtCompound) stack.getNbt().get(NbtS.getNbt(stack));
            int slot = compound.getByte("Sigil");
            if (slot >= 0) {
                return "Selected element : " + OrbData[slot];
            }
        }
        return "null";
    }

    @Override
    public int getExtraInfoColor(ItemStack stack) {
        if (stack.hasNbt()) {
            NbtCompound compound = stack.getSubNbt(NbtS.getNbt(stack));
            int slot = compound.getByte("Sigil");
            if (slot >= 0) {
                return OrbColor[slot];
            }
        }
        return 0;
    }
    public int GetChargeTime(PlayerEntity player, ItemStack stack) {
        if (player.isInPose(EntityPose.CROUCHING)) {
            return 1;
        }
        int chargetime = (int) (chargeTime / player.getAttributeValue(ModAttributes.CAST_SPEED));
        if (chargetime == 0 && this.chargeTime != 0) {
            return 1;
        }
        return chargetime;
    }
    @Override
    public void applyCooldown(PlayerEntity player, ItemStack stack, NbtCompound nbt, int slot) {
        if (player.isInPose(EntityPose.CROUCHING)) {
            return;
        }
        super.applyCooldown(player,stack,nbt,slot);
    }
    @Override
    public void applyFinishCost(PlayerEntity player, ItemStack stack, NbtCompound nbt, int slot) {
        if (player.isInPose(EntityPose.CROUCHING)) {
            return;
        }
        super.applyFinishCost(player, stack, nbt, slot);
    }
}
