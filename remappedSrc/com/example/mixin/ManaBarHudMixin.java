package com.example.mixin;

import com.example.N3rdyR0b1nsSpellEngine;
import com.example.main.Item.custom.SpellScrollItem;
import com.example.main.Item.spellfocus.SpellFocus;
import com.example.main.SpellUtil.Mana;
import com.example.main.SpellUtil.ManaContainer;
import com.example.main.SpellUtil.Spells.NbtS;
import com.example.main.SpellUtil.Spells.Spell;
import com.example.main.Spells.extra.ContinousUsageSpell;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
@Environment(EnvType.CLIENT)
@Mixin(InGameHud.class)
public class ManaBarHudMixin {
    private static final Identifier MANA_BAR = new Identifier(N3rdyR0b1nsSpellEngine.MOD_ID, "textures/gui/hud/mana_bar.png");
    private static final Identifier SPELLS = new Identifier(N3rdyR0b1nsSpellEngine.MOD_ID, "textures/gui/hud/spell_slots_gui.png");
    private static final Identifier CROSSHAIR = new Identifier(N3rdyR0b1nsSpellEngine.MOD_ID, "textures/gui/hud/crosshair.png");
    private boolean getData = true;
    @Inject(method = "renderStatusBars", at = @At("TAIL"))
    private void renderStatusBars(DrawContext context, CallbackInfo callbackInfo) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.options.hudHidden || client.interactionManager == null
                || !client.interactionManager.hasStatusBars()
                || !(client.getCameraEntity() instanceof PlayerEntity player)
        ) {
            return;
        }
        Mana playermana = ((ManaContainer)player).getMana();
        if (!playermana.visible) {
            return;
        }
        int x = client.getWindow().getScaledWidth()/2;
        int y = client.getWindow().getScaledHeight();
        RenderSystem.setShader(GameRenderer::getPositionTexColorProgram);
        RenderSystem.setShaderColor(1.0f,1.0f,1.0f,1.0f);
        RenderSystem.setShaderTexture(0, MANA_BAR);
        int a = 0;
        int o = 10;

        float f = (float) Math.max(player.getAttributeValue(EntityAttributes.GENERIC_MAX_HEALTH), player.getHealth());
        int p = MathHelper.ceil(player.getAbsorptionAmount());
        int q = MathHelper.ceil((f + (float)p) / 2.0f / 10.0f);
        int r = Math.max(10 - (q - 2), 3);
        int s = o - (q - 1) * r - 10;
        if (s > -22) {
            s = -22;
        }
        a += s;
        if (player.getAttributeValue(EntityAttributes.GENERIC_ARMOR) > 0 || (a == 0 && (player.getAir() < 20))) {
            a -= 10;
        }
        context.drawTexture(MANA_BAR, x-91, y-45 + a, 0,0,182,5, 182, 12);
        int mana_fill = (int) (playermana.getManaPercent() * 180);
        context.drawTexture(MANA_BAR, x-90, y-44 + a, 1,6,mana_fill,3, 182, 12);
        ItemStack mainhand = player.getMainHandStack();
        ItemStack offhand = player.getOffHandStack();
        Spell selectedSpell = null;
        boolean staydown = true;
        if (mainhand.getItem() instanceof SpellFocus && mainhand.hasNbt()) {
            selectedSpell  = renderSpellHotbar(mainhand, context, x, y, true, player);
            staydown = false;
        }
        if (offhand.getItem() instanceof SpellFocus && offhand.hasNbt()) {
            if (selectedSpell == null && staydown) {
                selectedSpell = renderSpellHotbar(offhand, context, x, y, staydown, player);
                mainhand = offhand;
            }
            else {
                renderSpellHotbar(offhand, context, x, y - 23, staydown, player);
            }
        }
        else if (mainhand.getItem() instanceof SpellScrollItem) {
            selectedSpell = RenderScroll(mainhand, player, x, y, context);
        }
        else if (offhand.getItem() instanceof SpellScrollItem) {
            selectedSpell = RenderScroll(offhand, player, x, y, context);
        }
        {
            String message = (int)playermana.getStoredMana() + "/" + playermana.getMaxMana();
            int k = (((x*2) - (client.textRenderer.getWidth(message)))/2);
            int newY = (y-49+a);
            context.drawText(client.textRenderer, message, k, newY, 0, false);
            context.drawText(client.textRenderer, message, k-1, newY-1, 16777215, false);
            if (selectedSpell != null) {
                renderCrosshair(selectedSpell, player, mainhand, x, y, context);
                newY -= 11;
                String spelldata = selectedSpell.getInfo(player, mainhand);
                k = (((x * 2) - (client.textRenderer.getWidth(spelldata))) / 2);
                context.drawText(client.textRenderer, spelldata, k, newY, 0, false);
                context.drawText(client.textRenderer, spelldata, k-1, newY-1, 16777215, false);
                if (!selectedSpell.getExtraInfo(mainhand).isEmpty()) {
                    newY -= 11;
                    spelldata = selectedSpell.getExtraInfo(mainhand);
                    k = (((x * 2) - (client.textRenderer.getWidth(spelldata))) / 2);
                    context.drawText(client.textRenderer, spelldata, k, newY, selectedSpell.getExtraInfoColor(mainhand), false);
                    context.drawText(client.textRenderer, spelldata, k-1, newY-1, selectedSpell.getInfoColor(mainhand), false);
                }
            }
        }
    }
    private void renderCrosshair(Spell selectedSpell, PlayerEntity player, ItemStack stack, int x, int y, DrawContext matrix) {
        int distance = 6;
        int chargetime = selectedSpell.GetChargeTime(player, stack);
        if (chargetime > 0 && player.getItemUseTime() < chargetime && player.getItemUseTime() > 0) {
            distance -= (6 * player.getItemUseTime() / chargetime);
        }
        y /= 2;
        matrix.drawTexture(CROSSHAIR, x - 9 - distance, y - 9 - distance, 0,0,8,8, 16, 16);
        matrix.drawTexture(CROSSHAIR, x - 9 - distance ,y + distance, 0,8,8,8, 16, 16);
        matrix.drawTexture(CROSSHAIR, x + distance, y - 9 - distance, 8,0,8,8, 16, 16);
        matrix.drawTexture(CROSSHAIR, x + distance, y + distance, 8,8,8,8, 16, 16);

    }
    private Spell renderSpellHotbar(ItemStack stack, DrawContext matrixStack, int x, int y, boolean renderselect, PlayerEntity player) {
        NbtCompound compound = stack.getNbt();
        if (compound == null) {
            return null;
        }
        int selected = compound.getInt("Selected");
        Spell selectedSpell = null;
        if (stack.getItem() instanceof SpellFocus focus) {
            int pages = (focus).spellPages;
            matrixStack.drawTexture(SPELLS, x + 96, y - 22, 0, 0, 2 + 20 * pages, 22, 256, 256);
            boolean bl = false;
            for (int i = 0; i < pages; i++) {
                NbtCompound spellnbt = stack.getSubNbt(NbtS.SLOT + i);
                if (spellnbt != null && spellnbt.get(NbtS.SPELL) instanceof Spell spell) {
                    int texture = 0;
                    if (spell instanceof ContinousUsageSpell usageSpell && usageSpell.UseTime > 0) {
                        texture = 44;
                        matrixStack.drawTexture(SPELLS, x + 96 + i * 20, y - 32, 43, 37, 22, 9, 256, 256);
                        matrixStack.drawTexture(SPELLS, x + 99 + i * 20, y - 29, 46, 33, (int) (16f * usageSpell.GetProgress(player, stack, spellnbt)), 3, 256, 256);
                        bl = (bl || selected == i);
                    } else if (spell.CooldownProgress <= 0) {
                        texture = 22;
                    }
                    matrixStack.drawTexture(SPELLS, x + 97 + i * 20, y - 21, texture, 47, 20, 20, 256, 256);
                    matrixStack.drawTexture(spell.getIdentifier(), x + 99 + i * 20, y - 19, 0, 0, 16, 16, 16, 16);

                    if (i == selected) {
                        selectedSpell = spell;
                    }
                    matrixStack.drawTexture(spell.getIdentifier(), x + 99 + i * 20, y - 19, 0, 0, 16, 16, 16, 16);
                    if (texture == 0 && spell.getCooldown(player, stack) != 0) {
                        int cooldownPercent = 16 * spell.CooldownProgress / spell.getCooldown(player, stack);
                        matrixStack.drawTexture(SPELLS, x + 99 + i * 20, y - 3 - cooldownPercent, 25, 26, 16, cooldownPercent, 256, 256);
                    }
                } else {
                    matrixStack.drawTexture(SPELLS, x + 97 + i * 20, y - 21, 0, 47, 20, 20, 256, 256);
                }
            }

            int selectedtexture = 23;
            if (selectedSpell != null && selectedSpell.CooldownProgress > 0) {
                selectedtexture = 92;
            } else if (bl) {
                selectedtexture = 69;
            }
            if (renderselect) {
                matrixStack.drawTexture(SPELLS, x + 96 + selected * 20, y - 22, 1, selectedtexture, 22, 22, 256, 256);
            }
        }
        return selectedSpell;
    }






    private Spell RenderScroll(ItemStack mainhand, PlayerEntity player, int x, int y, DrawContext context) {
        NbtCompound compound = mainhand.getSubNbt(NbtS.SLOT + 0);
        if (mainhand.hasNbt() &&  compound != null && compound.get(NbtS.SPELL) instanceof Spell spell) {
            renderCrosshair(spell, player, mainhand, x, y, context);
            return spell;
        }
        return null;
    }
}
