package com.example.main.Item.customarmor;

import com.example.client.item_render.templates.WizardArmorLegRenderer;
import com.example.client.item_render.templates.WizardArmorRenderer;
import com.example.client.item_render.templates.WizardModel;
import com.example.main.Attributes.ModAttributes;
import com.example.main.Item.armor.ArmorTierValues;
import com.example.main.Item.armor.BaseArmorMaterials;
import com.example.main.Item.armor.MagicArmorMaterials;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.*;
import net.minecraft.resource.featuretoggle.FeatureSet;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.client.RenderProvider;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;

import java.awt.*;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class MagicArmorItem extends ArmorItem implements Equipment, GeoItem {
    private final AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);
    private final Supplier<Object> rendererProvider = GeoItem.makeRenderer(this);
    private final WizardModel modelProvider;
    private final ArmorTierValues ARMOR_TIER;



    private final Multimap<EntityAttribute, EntityAttributeModifier> attributemodifiers;
    public MagicArmorItem(MagicArmorMaterials material, ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> multimap, ArmorItem.Type type, Settings settings, WizardModel renderer) {
        super(BaseArmorMaterials.MAGIC, type,settings);
        this.modelProvider = renderer;
        ARMOR_TIER = material.getArmorTier();
        this.attributemodifiers = multimap.build();

    }


    @Override
    public EquipmentSlot getSlotType() {
        return this.type.getEquipmentSlot();
    }
    @Override
    public SoundEvent getEquipSound() {
        return material.getEquipSound();
    }
    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        return this.equipAndSwap(this, world, user, hand);
    }




    public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(ItemStack stack, EquipmentSlot slot) {
        if (slot == this.type.getEquipmentSlot()) {
            return this.attributemodifiers;
        }
        return super.getAttributeModifiers(slot);
    }



    @Override
    public boolean isEnabled(FeatureSet enabledFeatures) {
        return super.isEnabled(enabledFeatures);
    }


    @Override
    public void createRenderer(Consumer<Object> consumer) {
        consumer.accept(new RenderProvider() {
            private WizardArmorRenderer renderer;

            @Override
            public BipedEntityModel<LivingEntity> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack,
                                                                        EquipmentSlot equipmentSlot, BipedEntityModel<LivingEntity> original) {
                if (this.renderer == null) {
                    if (equipmentSlot == EquipmentSlot.LEGS) {
                        this.renderer = new WizardArmorLegRenderer(modelProvider);
                    } else {
                        this.renderer = new WizardArmorRenderer(modelProvider);
                    }
                }
                this.renderer.prepForRender(livingEntity, itemStack, equipmentSlot, original);

                return this.renderer;
            }
        });
    }
    @Override
    public Supplier<Object> getRenderProvider() {
        return this.rendererProvider;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController(this, "controller", 0, this::predicate));
    }

    private PlayState predicate(AnimationState animationState) {
        animationState.getController().setAnimation(RawAnimation.begin().then("idle", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (entity instanceof PlayerEntity player && player.getAttributeValue(ModAttributes.CASTING_LEVEL) < 0) {
            PlayerInventory inventory = player.getInventory();
            DefaultedList<ItemStack> armor = inventory.armor;
            for (int i = 0; i < armor.size(); i++) {
                if (armor.get(i).getItem() instanceof MagicArmorItem) {
                    player.getInventory().insertStack(armor.get(i));
                    armor.set(i, ItemStack.EMPTY);
                }
            }
        }

    }

}
