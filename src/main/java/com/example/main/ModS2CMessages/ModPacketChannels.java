package com.example.main.ModS2CMessages;

import com.example.N3rdyR0b1nsSpellEngine;
import com.example.main.ModS2CMessages.custom.*;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;

public class ModPacketChannels {
    public static final Identifier INITIALISE_SPELLBOOK = new Identifier(N3rdyR0b1nsSpellEngine.MOD_ID, "initialise_spellbook");
    public static final Identifier MAX_MANA_SYNC = new Identifier(N3rdyR0b1nsSpellEngine.MOD_ID, "mana_sync");
    public static final Identifier CLIENT_MANA_SYNC = new Identifier(N3rdyR0b1nsSpellEngine.MOD_ID, "client_mana_sync");
    public static final Identifier MAX_MANA_SYNC_REQUEST = new Identifier(N3rdyR0b1nsSpellEngine.MOD_ID, "max_mana_sync");
    public static final Identifier SELECTED_SPELL_SYNC = new Identifier(N3rdyR0b1nsSpellEngine.MOD_ID, "spell_sync");
    public static final Identifier UPCASTDOWNCAST = new Identifier(N3rdyR0b1nsSpellEngine.MOD_ID, "upcast_downcast");
    public static final Identifier COOLDOWN_SYNC = new Identifier(N3rdyR0b1nsSpellEngine.MOD_ID, "cooldown_sync");
    public static final Identifier REQUEST_COOLDOWN_SYNC = new Identifier(N3rdyR0b1nsSpellEngine.MOD_ID, "cooldown_sync_req");
    public static final Identifier ADDMANAFROMSERVER = new Identifier(N3rdyR0b1nsSpellEngine.MOD_ID, "manafromserver");
    public static final Identifier LEFT_CLICK = new Identifier(N3rdyR0b1nsSpellEngine.MOD_ID, "left_item_click");
    public static final Identifier LEFT_HOLD = new Identifier(N3rdyR0b1nsSpellEngine.MOD_ID, "left_item_hold");
    public static final Identifier SPELLFOCUS = new Identifier(N3rdyR0b1nsSpellEngine.MOD_ID, "spellfocus_sync");

    public static void registerChannelIds()
    {
        ServerPlayNetworking.registerGlobalReceiver(CLIENT_MANA_SYNC, UpdateServerPlayerMana::recieve);
        ServerPlayNetworking.registerGlobalReceiver(MAX_MANA_SYNC_REQUEST, RequestPlayerMaxMana::recieve);
        ServerPlayNetworking.registerGlobalReceiver(SELECTED_SPELL_SYNC, UpdateSpellFocus::recieve);
        ServerPlayNetworking.registerGlobalReceiver(INITIALISE_SPELLBOOK, CreateSpellBookStats::recieve);
        ServerPlayNetworking.registerGlobalReceiver(COOLDOWN_SYNC, CooldownManagerPacket::recieve);
        ServerPlayNetworking.registerGlobalReceiver(LEFT_CLICK, C2S_Click_LMB::recieve);
        ServerPlayNetworking.registerGlobalReceiver(LEFT_HOLD, C2S_Hold_LMB::recieve);
        ServerPlayNetworking.registerGlobalReceiver(UPCASTDOWNCAST, UpcastSync::recieve);
    }
}
