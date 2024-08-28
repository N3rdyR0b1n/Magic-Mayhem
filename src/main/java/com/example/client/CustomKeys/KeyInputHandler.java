package com.example.client.CustomKeys;


import com.example.main.Item.spellfocus.SpellFocus;
import com.example.main.ModS2CMessages.ModPacketChannels;
import com.example.main.SpellUtil.Mana;
import com.example.main.SpellUtil.ManaContainer;
import com.example.main.SpellUtil.Spells.NbtS;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Hand;
import org.lwjgl.glfw.GLFW;

public class KeyInputHandler {
    public static final String KEY_TOGGLE_MANA = "key.magic_mayhem.toggle_mana";
    public static final String KEY_NEXT_SPELL = "key.magic_mayhem.next_spell";
    public static final String KEY_CATEGORY_MAGIC = "key.category.magic_mayhem.mana";
    public static final String KEY_PREV_SPELL = "key.magic_mayhem.previous_spell";
    public static final String UPCAST = "key.magicmayhem.upcast";
    public static final String DOWNCAST = "key.magicmayhem.downcast";
    public static KeyBinding TOGGLEMANAKEY;
    public static KeyBinding NEXTSPELLKEY;
    public static KeyBinding PREVSPELLKEY;
    public static KeyBinding UPCASTKEY;
    public static KeyBinding DOWNCASTKEY;


    public static void registerKeyInputs() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null) {
                return;
            }
            if (TOGGLEMANAKEY.wasPressed() && TOGGLEMANAKEY.isPressed()) {
                PacketByteBuf buf = PacketByteBufs.create();
                Mana mana = ((ManaContainer)client.player).getMana();
                if (mana.visible) {
                    mana.visible = false;
                }
                else {
                    mana.visible = true;
                }
                buf.writeBoolean(mana.visible);
                ClientPlayNetworking.send(ModPacketChannels.MAX_MANA_SYNC_REQUEST,buf);
            }
            if (!client.player.isUsingItem()) {
                if (UPCASTKEY.wasPressed() && UPCASTKEY.isPressed()) {
                    if (client.player.getMainHandStack().getItem() instanceof SpellFocus) {
                        UpCastDownCast(true, true);
                    }
                    else if (client.player.getOffHandStack().getItem() instanceof SpellFocus) {
                        UpCastDownCast(false, true);
                    }
                }
                if (DOWNCASTKEY.wasPressed() && DOWNCASTKEY.isPressed()) {
                    if (client.player.getMainHandStack().getItem() instanceof SpellFocus) {
                        UpCastDownCast(true, false);
                    }
                    else if (client.player.getOffHandStack().getItem() instanceof SpellFocus) {
                        UpCastDownCast(false, false);
                    }
                }
                if (NEXTSPELLKEY.wasPressed() && NEXTSPELLKEY.isPressed()) {
                    if (client.player.getStackInHand(Hand.MAIN_HAND).getItem() instanceof SpellFocus) {
                        updateSelectedSpell(Hand.MAIN_HAND, client.player, true);
                    } else if (client.player.getStackInHand(Hand.OFF_HAND).getItem() instanceof SpellFocus) {
                        updateSelectedSpell(Hand.OFF_HAND, client.player, true);
                    }
                }
                if (PREVSPELLKEY.wasPressed() && PREVSPELLKEY.isPressed()) {
                    if (client.player.getStackInHand(Hand.MAIN_HAND).getItem() instanceof SpellFocus) {
                        updateSelectedSpell(Hand.MAIN_HAND, client.player, false);
                    } else if (client.player.getStackInHand(Hand.OFF_HAND).getItem() instanceof SpellFocus) {
                        updateSelectedSpell(Hand.OFF_HAND, client.player, false);
                    }
                }
            }
        }
        );
    }
    public static void init() {
        TOGGLEMANAKEY = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                KEY_TOGGLE_MANA,
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_Y,
                KEY_CATEGORY_MAGIC
        ));
        NEXTSPELLKEY = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                KEY_NEXT_SPELL,
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_T,
                KEY_CATEGORY_MAGIC
        ));
        PREVSPELLKEY = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                KEY_PREV_SPELL,
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_R,
                KEY_CATEGORY_MAGIC
        ));
        UPCASTKEY = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                UPCAST,
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_V,
                KEY_CATEGORY_MAGIC
        ));
        DOWNCASTKEY = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                DOWNCAST,
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_B,
                KEY_CATEGORY_MAGIC
        ));
        registerKeyInputs();
    }

    private static void updateSelectedSpell(Hand hand, PlayerEntity player, boolean forward) {
        ItemStack stack = player.getStackInHand(hand);
        SpellFocus focusItem = (SpellFocus) stack.getItem();
        if (stack.hasNbt()) {
            NbtCompound compound = stack.getNbt();
            int index = compound.getInt("Selected");
            if (forward) {
                index++;
                if (index >= focusItem.spellPages) {
                    index = 0;
                }
            }
            else {
                index--;
                if (index < 0) {
                    index = (focusItem.spellPages-1);
                }
            }
            PacketByteBuf buf = PacketByteBufs.create();
            buf.writeByte(index);
            buf.writeBoolean(hand==Hand.MAIN_HAND);
            stack.getNbt().putInt(NbtS.SELECT, index);
            ClientPlayNetworking.send(ModPacketChannels.SELECTED_SPELL_SYNC, buf);
        }
        else  {
            ClientPlayNetworking.send(ModPacketChannels.INITIALISE_SPELLBOOK, PacketByteBufs.create());
        }
    }

    private static void UpCastDownCast(boolean mainhand, boolean upcast) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeBoolean(mainhand);
        buf.writeBoolean(upcast);
        ClientPlayNetworking.send(ModPacketChannels.UPCASTDOWNCAST, buf);
    }
}
