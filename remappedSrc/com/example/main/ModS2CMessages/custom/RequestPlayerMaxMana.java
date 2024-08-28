package com.example.main.ModS2CMessages.custom;

import com.example.main.Attributes.ModAttributes;
import com.example.main.ModS2CMessages.ModPacketChannels;
import com.example.main.SpellUtil.Mana;
import com.example.main.SpellUtil.ManaContainer;
import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;

public class RequestPlayerMaxMana {
    public static void recieve(MinecraftServer server, ServerPlayerEntity player,
                               ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender) {
        PacketByteBuf sync = PacketByteBufs.create();
        Mana mana = ((ManaContainer)player).getMana();
        mana.visible = buf.readBoolean();
        mana.setMaxMana((int) player.getAttributeValue(ModAttributes.MAXIMUM_MANA));
        sync.writeInt(mana.getMaxMana());
        sync.writeFloat(mana.getStoredMana());
        ServerPlayNetworking.send(player, ModPacketChannels.MAX_MANA_SYNC, sync);
    }
}
