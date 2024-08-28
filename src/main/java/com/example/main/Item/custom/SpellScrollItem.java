package com.example.main.Item.custom;

import com.example.main.Attributes.ModAttributes;
import com.example.main.Item.ModItems;
import com.example.main.Item.spellfocus.SpellFocus;
import com.example.main.SpellUtil.AttributeModifierAbleItem;
import com.example.main.SpellUtil.Spells.NbtS;
import com.example.main.SpellUtil.Spells.Spell;
import com.example.main.Spells.extra.ContinousUsageSpell;
import net.fabricmc.fabric.api.item.v1.FabricItem;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class SpellScrollItem extends Item implements SpellContainerItem, AttributeModifierAbleItem, FabricItem {
    public final Spell spell;

    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context, PlayerEntity player) {
        Text[] text = spell.GetDescription(player, stack);
        for(Text t : text) {
            tooltip.add(t);
        }
    }

    public SpellScrollItem(Settings settings, Spell spell) {
        super(settings);
        this.spell = spell;

    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        if (!itemStack.hasNbt() || itemStack.getSubNbt(NbtS.SLOT + 0) == null) {
            Initialise(itemStack);
        }
        int i = itemStack.getNbt().getInt("Selected");
        CastWithNbt(world, user, itemStack, itemStack.getSubNbt(NbtS.SLOT + i), hand);
        return TypedActionResult.success(itemStack, world.isClient);
    }
    private void CastWithNbt(World world, PlayerEntity user, ItemStack itemStack, NbtCompound nbt, Hand hand) {
        if (nbt == null) {
            return;
        }
        if(nbt.get(NbtS.SPELL) instanceof Spell spell) {
            if (spell.getLevel() > user.getAttributeValue(ModAttributes.CASTING_LEVEL)) {
                return;
            }
            if (spell instanceof ContinousUsageSpell usageSpell && !usageSpell.needsHeld) {
                if (usageSpell.shouldEnd(user, itemStack, nbt)) {
                    usageSpell.endAction(user, world, itemStack);
                    BreakScroll(world, user, itemStack);
                    user.setCurrentHand(hand);
                    return;
                }
            }
            spell.castSpell(user, world, itemStack);
            if (spell instanceof ContinousUsageSpell || spell.GetChargeTime(user, itemStack) > 0) {
                user.setCurrentHand(hand);
            } else {
                BreakScroll(world, user, itemStack);
            }

        }
    }
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.BOW;
    }
    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        if (user instanceof PlayerEntity player) {
            int index = stack.getNbt().getInt(NbtS.SELECT);
            NbtCompound nbt =stack.getSubNbt(NbtS.SLOT + index);
            if (nbt == null) {
                return;
            }
            Spell spell = (Spell) nbt.get(NbtS.SPELL);

            if (spell instanceof ContinousUsageSpell continousUsageSpell && !continousUsageSpell.needsHeld) {
                return;
            }
            int tick = getMaxUseTime(stack) - remainingUseTicks;
            boolean break_it = false;
            if (tick >= spell.GetChargeTime(player, stack)) {
                spell.castReleaseSpell(player, world, stack, tick);
                break_it = true;
            }
            if (spell instanceof ContinousUsageSpell usageSpell) {
                usageSpell.endAction(player, world , stack);
                break_it = true;
            }
            if (nbt.contains("Hit")) {
                break_it = nbt.getBoolean("Hit");
            }
            if (break_it) {
                BreakScroll(world, player, stack);
            }
        }
    }




    public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
        if (user instanceof PlayerEntity player) {
            int i = 0;
            String slot = NbtS.SLOT + 0;
            NbtCompound compound = stack.getSubNbt(slot);
            if (compound != null && compound.get(NbtS.SPELL) instanceof ContinousUsageSpell continousUsageSpell && continousUsageSpell.needsHeld) {
                if (continousUsageSpell.UseTime > 0) {
                    continousUsageSpell.tickAction(player, world, stack, i, continousUsageSpell.UseTime);
                    if (!world.isClient()) {
                        continousUsageSpell.UseTime--;
                    }
                    stack.setDamage(stack.getDamage() + 1);
                } else {
                    continousUsageSpell.endAction(player,world,stack);
                    BreakScroll(world,player, stack);
                }

            }
        }
    }
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (!stack.hasNbt()) {
            Initialise(stack);
        }
        if (entity instanceof PlayerEntity player) {
            if (player.getAttributeValue(ModAttributes.CASTING_LEVEL) > 0) {
                String index = NbtS.SLOT + 0;
                NbtCompound compound = stack.getSubNbt(index);
                if (compound == null) {
                    return;
                }
                if (compound.get(NbtS.SPELL) instanceof Spell spell) {
                    if (spell instanceof ContinousUsageSpell continousUsageSpell && !continousUsageSpell.needsHeld && continousUsageSpell.UseTime >= 0) {
                        if (continousUsageSpell.UseTime > 0) {
                            continousUsageSpell.tickAction(player, world, stack, 0, continousUsageSpell.UseTime);
                            if (!world.isClient()) {
                                continousUsageSpell.UseTime--;
                            }
                            stack.setDamage(stack.getDamage() + 1);
                        } else {
                            continousUsageSpell.endAction(player, world, stack);
                            BreakScroll(world, player, stack);
                        }

                    }
                }
            }
            else {
                String index = NbtS.SLOT + 0;
                NbtCompound compound = stack.getSubNbt(index);
                if (compound == null) {
                    return;
                }
                if (compound.get(NbtS.SPELL) instanceof ContinousUsageSpell usageSpell && usageSpell.UseTime >= 0) {
                    usageSpell.UseTime = 0;
                    usageSpell.endAction(player, world, stack);
                    BreakScroll(world, player, stack);
                }

            }
        }
    }

    private void BreakScroll(World world, PlayerEntity player, ItemStack stack) {
        world.playSound(null, player.getBlockPos(), SoundEvents.ITEM_BOOK_PAGE_TURN, SoundCategory.PLAYERS, 1, 1.75f);
        world.playSound(null, player.getBlockPos(), SoundEvents.ITEM_BOOK_PUT, SoundCategory.PLAYERS, 1, 1.75f);
        world.playSound(null, player.getBlockPos(), SoundEvents.ENTITY_GOAT_HORN_BREAK, SoundCategory.PLAYERS, 1, 2);
        stack.decrement(1);
    }
    private void Initialise(ItemStack stack) {
        Spell copy = spell.clone();
        if (copy instanceof ContinousUsageSpell usageSpell) {
            if (usageSpell.MaxUseTime > stack.getMaxDamage()) {
                usageSpell.MaxUseTime = stack.getMaxDamage();
            }
        }
        stack.getOrCreateNbt().putInt(NbtS.SELECT, 0);
        stack.getOrCreateSubNbt(NbtS.SLOT+ 0).put(NbtS.SPELL, copy);

    }
    @Override
    public Spell getSelectedSpell(ItemStack stack) {
        return spell;
    }

    @Override
    public void addTooltip(ItemStack stack, World world, ArrayList<Text> list, TooltipContext context, PlayerEntity player) {
        appendTooltip(stack,world, list, context, player);
        ((SpellFocus)ModItems.SIMPLE_SPELL_BOOK).AppendAttributes(list, EquipmentSlot.FEET, stack, player);
    }
    @Override
    public boolean allowNbtUpdateAnimation(PlayerEntity player, Hand hand, ItemStack oldStack, ItemStack newStack) {
        return false;
    }
}
