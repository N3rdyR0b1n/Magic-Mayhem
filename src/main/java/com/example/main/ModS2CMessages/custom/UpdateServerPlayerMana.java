package com.example.main.ModS2CMessages.custom;

import com.example.main.Attributes.ModAttributes;
import com.example.main.SpellUtil.Mana;
import com.example.main.SpellUtil.ManaContainer;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;

public class UpdateServerPlayerMana {
    public static void recieve(MinecraftServer server, ServerPlayerEntity player,
                               ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender) {
        Mana mana = ((ManaContainer)player).getMana();
        float addvalue = buf.readFloat();
        mana.setMaxMana((int) player.getAttributeValue(ModAttributes.MAXIMUM_MANA));
        if (buf.readBoolean() == false) {
            mana.addMana(addvalue);
        }
        else {
            mana.removeMana(addvalue);
        }
    }
}
