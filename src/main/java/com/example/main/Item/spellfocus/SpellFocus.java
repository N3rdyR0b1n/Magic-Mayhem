package com.example.main.Item.spellfocus;

import com.example.client.item_render.templates.SpellBookRenderer;
import com.example.main.Attributes.ModAttributes;
import com.example.main.SpellUtil.AttributeModifierAbleItem;
import com.example.main.SpellUtil.Spells.NbtS;
import com.example.main.SpellUtil.Spells.Spell;
import com.example.main.Spells.extra.ContinousUsageSpell;
import com.google.common.collect.Multimap;
import net.fabricmc.fabric.api.item.v1.FabricItem;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.client.RenderProvider;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.model.GeoModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class SpellFocus extends Item implements LeftMouseItem, AttributeModifierAbleItem, GeoItem, FabricItem {
    public final int spellPages;
    private GeoModel<SpellFocus> model;
    private AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);
    private final Supplier<Object> renderProvider = GeoItem.makeRenderer(this);

    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if (stack.hasNbt()) {
            int selected = stack.getNbt().getInt("Selected");
            for (int i = 0; i <spellPages; i++) {
                StringBuilder builder = new StringBuilder();
                if (i == selected) {
                    builder.append(">  ");
                } else {
                    builder.append("   ");
                }
                NbtCompound compound = stack.getSubNbt(NbtS.SLOT + i);
                Text text;
                if (compound != null && compound.get(NbtS.SPELL) instanceof Spell spell) {
                    builder.append("[");
                    builder.append(spell.name);
                    builder.append("]");
                    text = Text.translatable(builder.toString()).setStyle(Style.EMPTY.withColor(spell.getInventoryColor()));
                }
                else {
                    builder.append("[empty]");
                    text = Text.translatable(builder.toString()).setStyle(Style.EMPTY.withColor(11382189));
                }
                tooltip.add(text);
            }
        }
        else {
            Initialise(stack);
        }
    }

    public SpellFocus(Settings settings, int spellPages, GeoModel<SpellFocus> model) {
        super(settings);
        this.spellPages = spellPages;
        this.model = model;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        if (!itemStack.hasNbt()) {
            Initialise(itemStack);
        }
        int i = itemStack.getNbt().getInt("Selected");
        CastWithNbt(world, user, itemStack, itemStack.getSubNbt(NbtS.SLOT + i), hand);
        NbtS.SYNC(itemStack,world);
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
            int index = itemStack.getNbt().getInt(NbtS.SELECT);
            if (spell instanceof ContinousUsageSpell usageSpell && !usageSpell.needsHeld) {
                 if (usageSpell.shouldEnd(user, itemStack, nbt) && usageSpell.canEnd(user, itemStack, nbt)) {
                     if (usageSpell.canEnd(user, itemStack, nbt)) {
                         usageSpell.endAction(user, world, itemStack);
                     }
                    usageSpell.applyCooldown(user, itemStack, itemStack.getSubNbt(NbtS.SLOT + index), index);
                    user.setCurrentHand(hand);
                    return;
                }
            }
            if (spell.canCastSpell(user, world, itemStack, nbt)) {
                spell.castSpell(user, world, itemStack);
                if (spell instanceof ContinousUsageSpell || spell.GetChargeTime(user, itemStack) > 0) {
                    user.setCurrentHand(hand);
                } else {
                    spell.applyCooldown(user, itemStack, itemStack.getSubNbt(NbtS.SLOT + index), index);
                }
                spell.applyCost(user, itemStack, nbt, itemStack.getNbt().getInt("Selected"));
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
            Spell spell = (Spell) nbt.get(NbtS.SPELL);
            if (spell instanceof ContinousUsageSpell continousUsageSpell && !continousUsageSpell.needsHeld) {
                return;
            }
            int tick = getMaxUseTime(stack) - remainingUseTicks;
            if (spell.canCastFinishSpell(player, world, stack, nbt, tick)) {
                spell.castReleaseSpell(player, world, stack, tick);
                spell.applyFinishCost(player, stack, nbt, index);
                spell.applyCooldown(player, stack,nbt,index);
            }
            if (spell instanceof ContinousUsageSpell usageSpell) {
                if (usageSpell.canEnd(player, stack, nbt)) {
                    usageSpell.endAction(player, world, stack);
                }
                usageSpell.applyCooldown(player,stack, nbt, nbt.getInt(NbtS.SELECT));
            }
            NbtS.SYNC(stack,world);
        }
    }




    public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
        if (user instanceof PlayerEntity player) {
            int i = stack.getNbt().getInt(NbtS.SELECT);
            String slot = NbtS.SLOT + i;
            NbtCompound compound = stack.getSubNbt(slot);
            if (compound != null && compound.get(NbtS.SPELL) instanceof ContinousUsageSpell continousUsageSpell && continousUsageSpell.needsHeld && continousUsageSpell.UseTime >= 0) {
                if (continousUsageSpell.canCastTickSpell(player, world, stack) && player.isAlive()) {
                    int tick = continousUsageSpell.UseTime;
                    continousUsageSpell.tickAction(player, world, stack, i, tick);
                    continousUsageSpell.applyTickCost(player, stack, compound, i, tick);
                } else {
                    if (continousUsageSpell.canEnd(player, stack, compound)) {
                        continousUsageSpell.endAction(player, world, stack);
                    }
                    continousUsageSpell.applyCooldown(player, stack,  stack.getSubNbt(slot),i);
                }

            }
        }
    }
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (!stack.hasNbt()) {
            return;
        }
        if (entity instanceof PlayerEntity player) {
            if (!player.isAlive()) {
                DeathClear(player,world,stack);
            }
            byte castlvl = (byte) player.getAttributeValue(ModAttributes.CASTING_LEVEL);
            boolean update = false;
            for (int i = 0; i < spellPages; i++) {
                String index = NbtS.SLOT + i;
                NbtCompound compound = stack.getSubNbt(index);
                if (compound == null) {
                    continue;
                }
                if (compound.get(NbtS.SPELL) instanceof Spell spell) {
                    if (spell instanceof ContinousUsageSpell continousUsageSpell && continousUsageSpell.UseTime >= 0) {
                        update = true;
                        if (spell.getLevel() > castlvl) {
                            continousUsageSpell.UseTime = 0;
                            if (continousUsageSpell.canEnd(player, stack, compound)) {
                                continousUsageSpell.endAction(player, world, stack);
                            }
                            continousUsageSpell.applyCooldown(player, stack, compound, i);
                            continue;
                        }
                        if (!continousUsageSpell.needsHeld) {
                            if (continousUsageSpell.canCastTickSpell(player, world, stack)) {
                                continousUsageSpell.tickAction(player, world, stack, i, continousUsageSpell.UseTime);
                                continousUsageSpell.applyTickCost(player, stack, compound, i, continousUsageSpell.UseTime);
                            } else {
                                if (continousUsageSpell.canEnd(player, stack, compound)) {
                                    continousUsageSpell.endAction(player, world, stack);
                                }
                                spell.applyCooldown(player, stack, compound, i);
                            }
                        }
                    }
                    boolean up = spell.UpdateCooldown(player,world,stack,compound);
                    update = up ? true : update;
                }
            }
            if (update) {
                stack.getNbt().putInt(NbtS.SYNC, stack.getNbt().getInt(NbtS.SYNC) - 1);
            }
        }
    }


    private boolean ShouldCancel(PlayerEntity player, ItemStack stack) {
        NbtCompound compound = stack.getSubNbt(NbtS.getNbt(stack));
        if (compound != null && compound.get(NbtS.SPELL) instanceof Spell spell && !(spell.getLevel() > player.getAttributeValue(ModAttributes.CASTING_LEVEL))) {
            return  false;

        }
        return  true;
    }
    @Override
    public void onLmbClickClient(PlayerEntity player, ItemStack stack) {
        if (ShouldCancel(player, stack)) {
            return;
        }
        onLmbClick(player, stack, player.getWorld());
    }

    @Override
    public void onLmbClickServer(PlayerEntity player, ItemStack stack, ServerWorld world) {
        if (ShouldCancel(player, stack)) {
            return;
        }
        onLmbClick(player, stack, world);
    }

    @Override
    public void onLmbHoldClient(PlayerEntity player, ItemStack stack) {
        if (ShouldCancel(player, stack)) {
            return;
        }
        onLmbHold(player, stack, player.getWorld());
    }

    @Override
    public void onLmbHoldServer(PlayerEntity player, ItemStack stack, ServerWorld world) {
        if (ShouldCancel(player, stack)) {
            return;
        }
        onLmbHold(player, stack, world);
    }


    private void onLmbClick(PlayerEntity player, ItemStack stack, World world) {
        if (!stack.hasNbt()) {
            return;
        }
        int index = stack.getNbt().getInt("Selected");
        NbtCompound nbt = stack.getSubNbt("SpellSlot:" + index);
        if (nbt == null) {
            return;
        }
        if (nbt.get(NbtS.SPELL) instanceof Spell spell) {
            if (spell.canSecCastSpell(player, world, stack, nbt)) {
                spell.castSecSpell(player, world, stack);
                spell.applySecCost(player, stack, nbt, index);
                spell.applySecCooldown(player, stack, nbt, index);
            }
        }
        if (!world.isClient()) {
            stack.getNbt().putByte(NbtS.SYNC, (byte) (stack.getNbt().getByte(NbtS.SYNC) + 1));
        }
    }
    private void onLmbHold(PlayerEntity player, ItemStack stack, World world) {
        if (!stack.hasNbt()) {
            return;
        }
        int i = stack.getNbt().getInt(NbtS.SELECT);
        NbtCompound compound = stack.getSubNbt(NbtS.SLOT + i);
        if (compound == null) {
            return;
        }
        if (compound.get(NbtS.SPELL) instanceof ContinousUsageSpell usageSpell) {
            if (usageSpell.canCastLeftTickSpell(player, world, stack, compound)) {
                usageSpell.tickLeftAction(player, world, stack, i, usageSpell.LeftUseTime);
                usageSpell.applyLeftTickCost(player, stack, compound, i, usageSpell.LeftUseTime);
            }
            else {
                usageSpell.applySecCooldown(player, stack, compound, i);
            }
        }
        if (!world.isClient()) {
            stack.getNbt().putByte(NbtS.SYNC, (byte) (stack.getNbt().getByte(NbtS.SYNC) + 1));
        }
    }

    private void DeathClear(PlayerEntity player, World world, ItemStack stack) {
        for (int i = 0; i < spellPages; i++) {
            String index = NbtS.SLOT + i;
            NbtCompound compound = stack.getSubNbt(index);
            if (compound == null) {
                continue;
            }
            if (compound.get(NbtS.SPELL) instanceof Spell spell) {
                if (spell instanceof ContinousUsageSpell continousUsageSpell && continousUsageSpell.UseTime >= 0) {
                    if (continousUsageSpell.canEnd(player, stack, compound)) {
                        continousUsageSpell.endAction(player, world, stack);
                    }
                    spell.applyCooldown(player, stack, compound, i);
                }
            }
        }
        stack.getNbt().putInt(NbtS.SYNC, stack.getNbt().getInt(NbtS.SYNC) + 1);

    }

    public void Initialise(ItemStack stack) {
        NbtCompound compound = new NbtCompound();
        compound.putInt("Selected", 0);
        stack.setNbt(compound);
    }
    public void nextPage(ItemStack stack) {
        int index = stack.getNbt().getInt("Selected");
        index++;
        if (index >= spellPages) {
            index = 0;
        }
        stack.getNbt().putInt("Selected", index);

    }
    public void previousPage(ItemStack stack) {
        int index = stack.getNbt().getInt("Selected");
        if (index == 0) {
            index = spellPages;
        }
        index--;
        stack.getNbt().putInt("Selected", index);
    }
    @Override
    public void createRenderer(Consumer<Object> consumer) {
        consumer.accept(new RenderProvider() {
            private final SpellBookRenderer renderer = new SpellBookRenderer(model);

            @Override
            public BuiltinModelItemRenderer getCustomRenderer() {
                return this.renderer;
            }
        });
    }

    @Override
    public Supplier<Object> getRenderProvider() {
        return renderProvider;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "controller", 0, this::predicate));
    }

    private <T extends GeoAnimatable> PlayState predicate(AnimationState<T> tAnimationState) {
        tAnimationState.getController().setAnimation(RawAnimation.begin().then("idle", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }



    @Override
    public void addTooltip(ItemStack stack, World world, ArrayList<Text> list, TooltipContext context, PlayerEntity player) {
        appendTooltip(stack, world, list, context);
        AppendAttributes(list, EquipmentSlot.FEET, stack, player);
    }

    public void AppendAttributes(List<Text> list, EquipmentSlot equipmentSlot, ItemStack stack, @Nullable PlayerEntity player) {
        Multimap<EntityAttribute, EntityAttributeModifier> multimap = stack.getAttributeModifiers(equipmentSlot);
        if (multimap.isEmpty()) return;
        list.add(ScreenTexts.EMPTY);
        list.add(Text.translatable("item.modifiers.spell.inventory").formatted(Formatting.GRAY));
        for (Map.Entry<EntityAttribute, EntityAttributeModifier> entry : multimap.entries()) {
            EntityAttributeModifier entityAttributeModifier = entry.getValue();
            double d = entityAttributeModifier.getValue();
            boolean bl = false;
            if (player != null) {
                if (entityAttributeModifier.getId() == Item.ATTACK_DAMAGE_MODIFIER_ID) {
                    d += player.getAttributeBaseValue(EntityAttributes.GENERIC_ATTACK_DAMAGE);
                    d += EnchantmentHelper.getAttackDamage(stack, EntityGroup.DEFAULT);
                    bl = true;
                } else if (entityAttributeModifier.getId() == Item.ATTACK_SPEED_MODIFIER_ID) {
                    d += player.getAttributeBaseValue(EntityAttributes.GENERIC_ATTACK_SPEED);
                    bl = true;
                }
            }
            double e = entityAttributeModifier.getOperation() == EntityAttributeModifier.Operation.MULTIPLY_BASE || entityAttributeModifier.getOperation() == EntityAttributeModifier.Operation.MULTIPLY_TOTAL ? d * 100.0 : (entry.getKey().equals(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE) ? d * 10.0 : d);
            if (bl) {
                list.add(ScreenTexts.space().append(Text.translatable("attribute.modifier.equals." + entityAttributeModifier.getOperation().getId(), ItemStack.MODIFIER_FORMAT.format(e), Text.translatable(entry.getKey().getTranslationKey()))).formatted(Formatting.DARK_GREEN));
                continue;
            }
            if (d > 0.0) {
                list.add(Text.translatable("attribute.modifier.plus." + entityAttributeModifier.getOperation().getId(), ItemStack.MODIFIER_FORMAT.format(e), Text.translatable(entry.getKey().getTranslationKey())).formatted(Formatting.BLUE));
                continue;
            }
            if (!(d < 0.0)) continue;
            list.add(Text.translatable("attribute.modifier.take." + entityAttributeModifier.getOperation().getId(), ItemStack.MODIFIER_FORMAT.format(e *= -1.0), Text.translatable(entry.getKey().getTranslationKey())).formatted(Formatting.RED));
        }
    }

    @Override
    public boolean allowNbtUpdateAnimation(PlayerEntity player, Hand hand, ItemStack oldStack, ItemStack newStack) {
        return false;
    }
}
