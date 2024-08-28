package com.example.main.ModS2CMessages.custom;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;

public class UpdateSpellFocus {
    public static void recieve(MinecraftServer server, ServerPlayerEntity player,
                               ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender) {
        byte index = buf.readByte();
        boolean hand = buf.readBoolean();
        ItemStack stack;
        if (hand) {
            stack = player.getMainHandStack();
        }
        else {
            stack = player.getOffHandStack();
        }
        stack.getNbt().putInt("Selected", index);
    }
}
