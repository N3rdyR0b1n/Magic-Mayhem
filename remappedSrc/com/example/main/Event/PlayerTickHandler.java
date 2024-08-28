package com.example.main.Event;

import com.example.main.ModS2CMessages.ModPacketChannels;
import com.example.main.SpellUtil.Mana;
import com.example.main.SpellUtil.ManaContainer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class PlayerTickHandler implements ServerTickEvents.StartTick {

    @Override
    public void onStartTick(MinecraftServer server) {
        for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
            if (server.getTicks() % 600 == 0) {
                PacketByteBuf buf = PacketByteBufs.create();
                Mana playerMana = ((ManaContainer) player).getMana();
                buf.writeInt(playerMana.getMaxMana());
                buf.writeFloat(playerMana.getStoredMana());
                ServerPlayNetworking.send(player, ModPacketChannels.MAX_MANA_SYNC, buf);
            }
        }
    }
}
