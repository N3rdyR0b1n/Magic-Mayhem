package com.example.main.ModS2CMessages.custom;


import com.example.main.Item.spellfocus.LeftMouseItem;
import com.example.main.Item.spellfocus.SpellFocus;
import com.example.main.SpellUtil.Spells.NbtS;
import com.example.main.SpellUtil.Spells.Spell;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;

public class UpcastSync {
    public static void recieve(MinecraftServer server, ServerPlayerEntity player,
                               ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender) {
        boolean hand = buf.readBoolean();
        boolean up = buf.readBoolean();
        ItemStack stack;
        if (hand) {
            stack = player.getMainHandStack();
        }
        else {
            stack = player.getOffHandStack();
        }
        NbtCompound compound = stack.getSubNbt(NbtS.getNbt(stack));
        if (compound != null && compound.get(NbtS.SPELL) instanceof Spell spell) {
            if (up) {
                if (spell.Upcast(player)) {
                    Update(spell, stack);
                }
            }
            else {
                if (spell.DownCast(player)) {
                    Update(spell, stack);
                }
            }
        }
    }
    private static void Update(Spell spell, ItemStack stack) {
        spell.text = spell.GetText();
        NbtCompound compound = stack.getNbt();
        compound.putInt(NbtS.SYNC, compound.getInt(NbtS.SYNC) - 1);
    }
}
