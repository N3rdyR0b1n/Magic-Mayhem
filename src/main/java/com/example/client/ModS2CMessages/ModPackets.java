package com.example.client.ModS2CMessages;

import com.example.client.ModS2CMessages.custom.CooldownSyncReq;
import com.example.client.ModS2CMessages.custom.ManaServerPacket;
import com.example.client.ModS2CMessages.custom.ManaSyncPacket;
import com.example.client.ModS2CMessages.custom.SpellFocusSync;
import com.example.main.ModS2CMessages.ModPacketChannels;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

public class ModPackets {
    public static void registerS2CPackets()
    {
        ClientPlayNetworking.registerGlobalReceiver(ModPacketChannels.MAX_MANA_SYNC, ManaSyncPacket::receive);
        ClientPlayNetworking.registerGlobalReceiver(ModPacketChannels.REQUEST_COOLDOWN_SYNC, CooldownSyncReq::receive);
        ClientPlayNetworking.registerGlobalReceiver(ModPacketChannels.ADDMANAFROMSERVER, ManaServerPacket::receive);
        ClientPlayNetworking.registerGlobalReceiver(ModPacketChannels.SPELLFOCUS, SpellFocusSync::receive);
    }
}
