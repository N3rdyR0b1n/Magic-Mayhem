package com.example.main.SpellUtil.Spells;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;

public class NbtS {
    public static String SLOT = "SpellSlot:";
    public static String SELECT = "Selected";
    public static String SPELL = "Spell";
    public static String SYNC = " <|:3c ";
    public static String BACKUP = "backup_id";

    public static String getNbt(ItemStack stack) {
        return SLOT + stack.getNbt().getInt(SELECT);
    }
    public static NbtCompound SpellNbt(ItemStack stack) {
        return stack.getSubNbt(getNbt(stack));
    }
    public static void SYNC(ItemStack stack, World world) {
        if (!world.isClient()) {
            stack.getNbt().putByte(NbtS.SYNC, (byte) (stack.getNbt().getByte(NbtS.SYNC) + 1));
        }
    }


}
