package com.example.main.SpellUtil.Spells;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class NullSpell extends Spell{
    public static byte[] Serialised = new byte[0];
    public NullSpell() {
        super(0, SpellSchools.ICE, 0, "NULL", 0, new Identifier("null", "null"));
        Serialised = this.Serialise();
    }

    public void castSpell(PlayerEntity player, World world, ItemStack stack) {
        NbtCompound compound = stack.getSubNbt(NbtS.getNbt(stack));
        if (compound != null) {
            Spell recoveredspell = SpellKeeper.getSpell(compound.getInt(NbtS.BACKUP));
            if (recoveredspell != null) {
                compound.put(NbtS.SPELL, recoveredspell.clone());
            }
        }


    }
    public int getInventoryColor() {
        return 16777215;
    }
}
