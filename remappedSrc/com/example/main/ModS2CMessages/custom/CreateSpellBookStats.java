package com.example.main.ModS2CMessages.custom;

import com.example.main.Item.spellfocus.SpellFocus;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;

public class CreateSpellBookStats {

    public static void recieve(MinecraftServer server, ServerPlayerEntity player,
                               ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender) {
        if (player.getMainHandStack().getItem() instanceof SpellFocus) {
            NbtCompound compound = player.getMainHandStack().getOrCreateNbt();
            compound.putByte("selected", (byte) 0);
        }
        else {
            NbtCompound compound = player.getOffHandStack().getOrCreateNbt();
            compound.putByte("selected", (byte) 0);
        }


    }


}

