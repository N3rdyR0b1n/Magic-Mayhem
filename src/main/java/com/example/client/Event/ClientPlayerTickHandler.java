package com.example.client.Event;


import com.example.main.Attributes.ModAttributes;
import com.example.main.Item.spellfocus.SpellFocus;
import com.example.main.ModS2CMessages.ModPacketChannels;
import com.example.main.SpellUtil.Mana;
import com.example.main.SpellUtil.ManaContainer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.math.Vec3d;

public class ClientPlayerTickHandler implements ClientTickEvents.StartTick {
    ;
    boolean simply_a_skill_issue = false;

    @Override
    public void onStartTick(MinecraftClient client) {
        if (client.player == null) {
            return;
        }
        if (client.player.getAttributeValue(ModAttributes.MAXIMUM_MANA) <= 0) {
            return;
        }
        /// manages mana regeneration
        Vec3d speed = client.player.getVelocity();
        if (!isStandingStillEnough(speed, client.player)) {
            simply_a_skill_issue = true;
        }
        Mana mana = ((ManaContainer)client.player).getMana();
        mana.setMaxMana((int) client.player.getAttributeValue(ModAttributes.MAXIMUM_MANA));
        if (mana.getMaxMana() <= 0) {
            return;
        }
        float mathAmount = 0;
        if (isStandingStillEnough(speed, client.player) && !simply_a_skill_issue) {
           mathAmount += (float) ((client.player).getAttributeValue(ModAttributes.MANA_REGENERATION)/20);
        }
        else if (client.player.getAttributeValue(ModAttributes.MANA_LOSS_MULTIPLIER) > 0) {
            mathAmount -= (float) ((((speed.length())* 12 * client.player.getAttributeValue(ModAttributes.MANA_LOSS_MULTIPLIER)))/20);
        }
        mathAmount += (float) (client.player.getAttributeValue(ModAttributes.PASSIVE_MANA_REGENERATION)/20);
        PacketByteBuf clientmessage = PacketByteBufs.create();
        boolean removemana = false;
        if (mathAmount < 0) {
            removemana = true;
            mathAmount = -mathAmount;
            mana.removeMana(mathAmount);
        }
        else {
            mana.addMana(mathAmount);
        }
        clientmessage.writeFloat(mathAmount);
        clientmessage.writeBoolean(removemana);
        ClientPlayNetworking.send(ModPacketChannels.CLIENT_MANA_SYNC, clientmessage);
        simply_a_skill_issue = false;
    }
    public boolean isStandingStillEnough(Vec3d speed, PlayerEntity player) {
        return ((speed.length() <= 0.08f)&& !player.isFallFlying());
    }


    private void decreaseAllItemCooldowns(PlayerEntity player) {
        PlayerInventory inventory = player.getInventory();
        for (int index = 0; index < inventory.size(); index++) {
            ItemStack stack = inventory.getStack(index);
            if (!stack.isEmpty() && stack.hasNbt() && stack.getItem() instanceof SpellFocus focusItem) {
                boolean manager = reduceCooldown(stack, player);
                if (manager == true) {
                    PacketByteBuf buf = PacketByteBufs.create();
                    buf.writeInt(player.getInventory().indexOf(stack));
                    buf.writeIntArray(stack.getNbt().getIntArray("cooldownindex"));
                    ClientPlayNetworking.send(ModPacketChannels.COOLDOWN_SYNC, buf);
                }
            }
        }
    }

    public boolean reduceCooldown(ItemStack stack, PlayerEntity player) {
        boolean update = false;
        int[] array = stack.getNbt().getIntArray("cooldownindex");
        for (int i = 0; i < array.length; i++) {
            if (array[i] > 0) {
                array[i]--;
            }
            else if (array[i] == 0){
                update = true;
                array[i] = -1;
            }

        }
        stack.getNbt().putIntArray("cooldownindex", array);
        return update;
    }


}
