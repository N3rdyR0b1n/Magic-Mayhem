package com.example.client.ModS2CMessages.custom;

import com.example.main.ModS2CMessages.ModPacketChannels;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.Text;

public class CooldownSyncReq {

    public static void receive(MinecraftClient client, ClientPlayNetworkHandler clientPlayNetworkHandler, PacketByteBuf buf, PacketSender sender) {
        int slot = buf.readInt();
        int cooldown = buf.readInt();
        ItemStack stack;
        if (slot <= -1) {
           stack = client.player.getOffHandStack();
        }
        else {
            stack = client.player.getInventory().getStack(slot);
        }
        int[] array = stack.getNbt().getIntArray("cooldownindex");
        array[stack.getNbt().getByte("selected")] = cooldown;
        for (int i = 0; i < array.length; i++) {
            if (array[i] < 0) {
                array[i] = -2;
            }
        }

        PacketByteBuf returner = PacketByteBufs.create();
        returner.writeInt(slot);
        returner.writeIntArray(array);
        ClientPlayNetworking.send(ModPacketChannels.COOLDOWN_SYNC, returner);
    }

}
