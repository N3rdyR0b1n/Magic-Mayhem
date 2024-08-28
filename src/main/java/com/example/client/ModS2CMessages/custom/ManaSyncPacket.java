package com.example.client.ModS2CMessages.custom;

import com.example.main.SpellUtil.Mana;
import com.example.main.SpellUtil.ManaContainer;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.network.PacketByteBuf;

public class ManaSyncPacket {

    public static void receive(MinecraftClient client, ClientPlayNetworkHandler clientPlayNetworkHandler, PacketByteBuf buf, PacketSender sender) {
            Mana mana = ((ManaContainer)client.player).getMana();
            mana.setMaxMana(buf.readInt());
            mana.setStoredMana(buf.readFloat());
    }
}
