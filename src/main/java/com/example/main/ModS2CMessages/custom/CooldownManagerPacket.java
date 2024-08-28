package com.example.main.ModS2CMessages.custom;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;

public class CooldownManagerPacket {
    public static void recieve(MinecraftServer server, ServerPlayerEntity player,
                               ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender) {
        int slot = buf.readInt();
        ItemStack stack;
        if (slot >= 0) {
           stack = player.getInventory().getStack(slot);
        }
        else {
            stack = player.getOffHandStack();
        }
        if (stack.hasNbt()) {
            int[] array = buf.readIntArray();
            for (int i = 0; i < array.length; i++) {
                if (array[i] < 0) {
                    array[i] = -2;
                }
            }
            stack.getNbt().putIntArray("cooldownindex", array);
        }

    }

}
