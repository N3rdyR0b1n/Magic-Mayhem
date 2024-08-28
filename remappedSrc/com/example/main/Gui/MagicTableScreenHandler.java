package com.example.main.Gui;


import com.example.main.Item.ModItems;
import com.example.main.Item.custom.SpellContainerItem;
import com.example.main.Item.custom.SpellPaperItem;
import com.example.main.Item.custom.SpellScrollItem;
import com.example.main.Item.spellfocus.SpellFocus;
import com.example.main.SpellUtil.Spells.NbtS;
import com.example.main.SpellUtil.Spells.Spell;
import com.example.main.Spells.extra.ContinousUsageSpell;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.slot.Slot;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;

public class MagicTableScreenHandler extends ScreenHandler {
    private final Inventory inventory = new SimpleInventory(4);
    private final ScreenHandlerContext context;

    public MagicTableScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, ScreenHandlerContext.EMPTY);
    }

    public MagicTableScreenHandler(int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
        super(ModScreenHandler.SPELL_APPLICATION_SCREEN_HANDLER, syncId);
        int j;
        this.context = context;
        int i = 51;
        addSlot(0, 8, 8, 64);
        addSlot(1, 8, 26, 1);
        addSlot(2, 44, 8, 1);
        addSlot(3, 71, 26, 1);
        for (j = 0; j < 3; ++j) {
            for (int k = 0; k < 9; ++k) {
                this.addSlot(new Slot(playerInventory, k + j * 9 + 9, 8 + k * 18, j * 18 + 58));
            }
        }
        for (j = 0; j < 9; ++j) {
            this.addSlot(new Slot(playerInventory, j, 8 + j * 18, 116));
        }
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }


    @Override
    public ItemStack quickMove(PlayerEntity player, int slot) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot2 = (Slot)this.slots.get(slot);
        if (slot2 != null && slot2.hasStack()) {
            ItemStack itemStack2 = slot2.getStack();
            itemStack = itemStack2.copy();
            if (slot >= 0 && slot <= 2 || slot == 3 ) {
                if (!this.insertItem(itemStack2, 4, 40, true)) {
                    return ItemStack.EMPTY;
                }
                slot2.onQuickTransfer(itemStack2, itemStack);
            } else if (this.slots.get(0).canInsert(itemStack) ? this.insertItem(itemStack2, 0, 1, false) || this.slots.get(0).canInsert(itemStack2) && !this.insertItem(itemStack2, 0, 1, false) : (this.getSlot(1).canInsert(itemStack2) ? !this.insertItem(itemStack2, 1,2, false) : (this.getSlot(2).canInsert(itemStack) && itemStack.getCount() == 1 ? !this.insertItem(itemStack2, 2, 3, false) : (slot >= 4 && slot < 31 ? !this.insertItem(itemStack2, 31, 40, false) : (slot >= 31 && slot < 40 ? !this.insertItem(itemStack2, 4, 31, false) : !this.insertItem(itemStack2, 4, 40, false)))))) {
                return ItemStack.EMPTY;
            }
            if (itemStack2.isEmpty()) {
                slot2.setStack(ItemStack.EMPTY);
            } else {
                slot2.markDirty();
            }
            if (itemStack2.getCount() == itemStack.getCount()) {
                return ItemStack.EMPTY;
            }
            slot2.onTakeItem(player, itemStack2);
        }
        return itemStack;
    }

    @Override
    public void onClosed(PlayerEntity player) {
        super.onClosed(player);
        this.dropInventory(player, this.inventory);
    }

    public boolean canInsertItem(ItemStack stack) {
        Item item = stack.getItem();
        return item == Items.LAPIS_LAZULI;
    }
    private boolean scrollableItem(ItemStack stack) {
        Item item = stack.getItem();
        return item == Items.PAPER || item == Items.BOOK || item == Items.WRITABLE_BOOK;
    }
    private void addSlot(int slots, int x, int y, int maxItemCount) {
        this.addSlot(new Slot(inventory, slots, x, y) {
            @Override
            public boolean canInsert(ItemStack stack) {
                if (slots == 0) {
                    return canInsertItem(stack);
                }
                else if (slots == 1) {
                    return stack.getItem() instanceof SpellContainerItem;
                }
                else if (slots == 2){
                    return stack.getItem() instanceof SpellFocus;
                }
                else {
                    return scrollableItem(stack);
                }
            }

            @Override
            public int getMaxItemCount() {
                return maxItemCount;
            }
        });
    }
    @Override
    public boolean onButtonClick(PlayerEntity player, int id) {
        ItemStack stack = this.getSlot(2).getStack();
        if (stack.isEmpty()) {
            return false;
        }
        if (stack.hasNbt()) {
            if (id == 0) {
                if (tryCraft(stack, player)) {
                    player.playSound(SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, SoundCategory.MASTER, 1, 1);
                }
            } else if (id == 1) {
                ((SpellFocus) stack.getItem()).nextPage(stack);
                player.playSound(SoundEvents.UI_BUTTON_CLICK.value(), SoundCategory.MASTER, 1, 1);
            } else if (id == 2) {
                ((SpellFocus) stack.getItem()).previousPage(stack);
                player.playSound(SoundEvents.UI_BUTTON_CLICK.value(), SoundCategory.MASTER, 1, 1);
            }
            return true;
        }
        return false;
    }
    private boolean tryCraft(ItemStack stack, PlayerEntity player) {
        if (player.method_48926().isClient()) {
            player.sendMessage(Text.of(":3"));
        }
        boolean success = false;
        String text = NbtS.SLOT + stack.getNbt().getInt(NbtS.SELECT);
        if ((this.getSlot(0).hasStack() && this.getSlot(2).hasStack())) {
            ItemStack scrollStack = this.getSlot(1).getStack();
            if (!scrollStack.isEmpty()) {
                //putting spellscroll on book
                Managebuffs(player, stack, text);
                NbtCompound compound = stack.getNbt();
                Spell spell = ((SpellContainerItem)scrollStack.getItem()).getSelectedSpell(scrollStack).clone();
                NbtCompound spelldata = new NbtCompound();
                spelldata.putInt(NbtS.BACKUP, spell.id);
                spelldata.put(NbtS.SPELL, spell);
                compound.put(text, spelldata);
                scrollStack.decrement(1);
                success = true;
            }
            else if (this.getSlot(3).hasStack()) {
                //converting spell to scroll
                NbtCompound compound = stack.getSubNbt(text);
                if (compound != null) {
                    if (compound.get(NbtS.SPELL) instanceof Spell spell) {
                        Managebuffs(player, stack, text);
                        ItemStack scrollify = this.getSlot(3).getStack();
                        scrollify.decrement(1);
                        ItemStack newScroll = new ItemStack(ModItems.SPELL_PAPER);
                        NbtCompound spellnbt = new NbtCompound();
                        NbtCompound nbt = new NbtCompound();
                        nbt.put(NbtS.SPELL, spell.clone());
                        nbt.putInt(NbtS.BACKUP, spell.id);
                        spellnbt.putInt(NbtS.SELECT, 0);
                        spellnbt.put(NbtS.SLOT + 0, nbt);
                        newScroll.setNbt(spellnbt);
                        stack.removeSubNbt(text);
                        this.getSlot(3).setStack(newScroll);
                        success = true;
                    }
                }
            }
        }
        return  success;
    }
    private void Managebuffs(PlayerEntity player, ItemStack stack, String index) {
        NbtCompound compound = stack.getSubNbt(index);
        if (compound != null && compound.get(NbtS.SPELL) instanceof ContinousUsageSpell usageSpell && usageSpell.UseTime > 0) {;
            usageSpell.endAction(player, player.method_48926(), stack);
        }
    }
    public boolean Filled() {
        for (int i = 0; i < 3; i++) {
            if (this.slots.get(i).hasStack()) {
                continue;
            }
            return false;
        }
        return true;
    }
}
