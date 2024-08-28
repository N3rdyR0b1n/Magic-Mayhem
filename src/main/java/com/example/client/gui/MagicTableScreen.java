package com.example.client.gui;

import com.example.N3rdyR0b1nsSpellEngine;
import com.example.main.Gui.MagicTableScreenHandler;
import com.example.main.Item.spellfocus.SpellFocus;
import com.example.main.SpellUtil.Spells.NbtS;
import com.example.main.SpellUtil.Spells.Spell;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.HopperScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class MagicTableScreen extends HandledScreen<MagicTableScreenHandler> {
    private static final Identifier TEXTURE_LOCATION = new Identifier(N3rdyR0b1nsSpellEngine.MOD_ID, "textures/gui/inventory/spell_gui.png");

    public MagicTableScreen(MagicTableScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        this.backgroundHeight = 136;
        this.playerInventoryTitleY = this.backgroundHeight - 88;

    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexColorProgram);
        RenderSystem.setShaderColor(1,1,1,1);
        RenderSystem.setShaderTexture(0, TEXTURE_LOCATION);
        int x = (this.width - this.backgroundWidth) / 2;
        int y = (this.height - this.backgroundHeight) / 2;
        context.drawTexture(TEXTURE_LOCATION, x, y-10, 0, 0, 212, 150);
    }
    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context);
        super.render(context, mouseX, mouseY, delta);
        int x = (this.width - this.backgroundWidth) / 2;
        int y = (this.height - this.backgroundHeight) / 2;

        RenderSystem.setShader(GameRenderer::getPositionTexColorProgram);
        RenderSystem.setShaderColor(1,1,1,1);
        RenderSystem.setShaderTexture(0, TEXTURE_LOCATION);
        boolean bl = canConvert();
        boolean bl2 = false;
        int emptytext = 14474460;
        if (bl) {
            context.drawTexture(TEXTURE_LOCATION, x + 26, y + 11, 39, 150, 16,9);
        }
        if (this.handler.getSlot(1).hasStack() && bl) {
            context.drawTexture(TEXTURE_LOCATION, x + 26, y + 16, 56, 150, 7,19);
            bl2 = true;
        }
        else if (this.handler.getSlot(3).hasStack() && bl) {
            context.drawTexture(TEXTURE_LOCATION, x + 51, y + 26, 64, 150, 18,12);
            bl2= true;
        }
        if (this.handler.getSlot(2).hasStack()) {
            context.drawTexture(TEXTURE_LOCATION, x + 150, y + 27, 19, 150, 19,19);
            context.drawTexture(TEXTURE_LOCATION, x + 125, y + 27, 19, 150, 19,19);
            context.drawTexture(TEXTURE_LOCATION, x + 150, y + 27, 19, 169, 19,19);
            context.drawTexture(TEXTURE_LOCATION, x + 125, y + 27, 0, 169, 19,19);
            if (bl2) {
                context.drawTexture(TEXTURE_LOCATION, x + 70, y + 6 , 85, 150,127, 19);
                emptytext = 0x404040;
            }
        }
        ItemStack spellbook = this.handler.getSlot(2).getStack();
        if (spellbook.getItem() instanceof SpellFocus focus) {
            if (!spellbook.hasNbt()) {
                focus.Initialise(spellbook);
            }
            NbtCompound compound = spellbook.getSubNbt(NbtS.getNbt(spellbook));
            if (compound != null && compound.get(NbtS.SPELL) instanceof Spell spell) {
                Text text = Text.of(spell.name + " [" + spell.getLevel() + "]");
                if (this.handler.Filled()) {
                    context.drawText(this.textRenderer, text, x + 74, y + 12, 6860866, false);
                    context.drawText(this.textRenderer, text, x + 73, y + 11, 16121743, false);
                }
                else {
                    context.drawText(this.textRenderer, text, x + 74, y + 12, 16121743, false);
                    context.drawText(this.textRenderer, text, x + 73, y + 11, 6860866, false);
                }
            }
            else {

                context.drawText(this.textRenderer, Text.of("Empty"), x + 73, y + 11, emptytext, false);
            }
            Text text = Text.of( focus.spellPages - spellbook.getNbt().getInt(NbtS.SELECT) + "/" + focus.spellPages);
            context.drawText(this.textRenderer, text, x + 91, y + 30, 6860866, false);
            context.drawText(this.textRenderer, text, x + 90, y + 29, 16121743, false);
        }
        else {
            context.drawText(this.textRenderer, Text.of("Empty"), x + 73, y + 11, emptytext, false);
        }
        this.drawMouseoverTooltip(context, mouseX, mouseY);
    }



    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        int x = (this.width - this.backgroundWidth) / 2;
        int y = (this.height - this.backgroundHeight) / 2;
        if ((x+70 <= mouseX && x+197 >= mouseX) && (y + 6 <= mouseY && y + 25 >= mouseY)) {
            this.client.interactionManager.clickButton(this.handler.syncId, 0);
        }
        else if ((x+125 <= mouseX && x+144 >= mouseX) && (y + 27 <= mouseY && y + 46 >= mouseY)) {
            this.client.interactionManager.clickButton(this.handler.syncId, 1);
        }
        else if ((x+150 <= mouseX && x+169 >= mouseX) && (y + 27 <= mouseY && y + 46 >= mouseY)) {
            this.client.interactionManager.clickButton(this.handler.syncId, 2);
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }
    private boolean canConvert() {
        return this.handler.getSlot(0).hasStack() && this.handler.getSlot(2).hasStack();
    }

    @Override
    protected void drawForeground(DrawContext context, int mouseX, int mouseY) {
        context.drawText(this.textRenderer, this.title, this.titleX, this.titleY - 10, 0x404040, false);
        context.drawText(this.textRenderer, this.playerInventoryTitle, this.playerInventoryTitleX, this.playerInventoryTitleY, 0x404040, false);
    }
}
