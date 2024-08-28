package com.example.main.Spells.custom;

import com.example.main.SpellUtil.*;
import com.example.main.SpellUtil.Spells.NbtS;
import com.example.main.SpellUtil.Spells.Spell;
import com.example.main.Spells.custom.actions.*;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class ChromaticOrbSpell extends Spell {

    private ActionPerformable[] Orbs;
    private String[] OrbData;

    private static int[] OrbColor;
    public ChromaticOrbSpell(int manaCost, SpellSchool school, int chargeTime, String name, Identifier texture, int min, int max) {
        super(0, school, chargeTime, name, 100, texture, min, max, 0);
        super.finishManaCost = manaCost;
        super.text = GetText();
        OrbData = new String[6];
        OrbData[0] = "Thunder";
        OrbData[1] = "Poison";
        OrbData[2] = "Lightning";
        OrbData[3] = "Fire";
        OrbData[4] = "Cold";
        OrbData[5] = "Acid";

        Orbs = new ActionPerformable[6];
        Orbs[0] = new ChromaticOrbThunder();
        Orbs[1] = new ChromaticOrbPoison();
        Orbs[2] = new ChromaticOrbLightning();
        Orbs[3] = new ChromaticOrbFire();
        Orbs[4] = new ChromaticOrbCold();
        Orbs[5] = new ChromaticOrbAcid();

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
        NbtCompound compound = stack.getNbt();
        NbtCompound nbt = stack.getSubNbt(NbtS.SLOT + compound.getInt(NbtS.SELECT));
        int orb = nbt.getInt("Orb");
        if (orb >= 6 || orb < 0) {
            orb = 0;
            nbt.putInt("Orb", orb);
            return;
        }
        if (player.isInPose(EntityPose.CROUCHING)) {
            orb++;
            if (orb > 5) {
                orb = 0;
            }
            nbt.putInt("Orb", orb);
        } else {
            Orbs[orb].Perform(stack, player, world);
        }

    }

    @Override
    public String getExtraInfo(ItemStack stack) {
        NbtCompound compound = stack.getSubNbt(NbtS.SLOT + stack.getNbt().getInt(NbtS.SELECT));
        if (compound != null) {
            return "Selected element : " + OrbData[compound.getInt("Orb")];
        }
        return "null";
    }

    @Override
    public int getExtraInfoColor(ItemStack stack) {
        if (stack.hasNbt()) {
            NbtCompound compound = (NbtCompound) stack.getNbt().get(NbtS.getNbt(stack));
            int slot = compound.getInt("Orb");
            if (slot >= 0) {
                return OrbColor[slot];
            }
        }
        return 0;

    }
    @Override
    public void applyFinishCost(PlayerEntity player, ItemStack stack, NbtCompound nbt, int slot) {
        if (player.isInPose(EntityPose.CROUCHING)) {
            return;
        }
        ((ManaContainer)player).getMana().removeMana(finishManaCost);
    }
    public void applyCooldown(PlayerEntity player, ItemStack stack, NbtCompound nbt, int slot) {
        if (player.isInPose(EntityPose.CROUCHING)) {
            return;
        }
        super.applyCooldown(player, stack,nbt, slot);
    }
}
