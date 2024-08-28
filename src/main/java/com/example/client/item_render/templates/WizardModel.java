package com.example.client.item_render.templates;

import com.example.N3rdyR0b1nsSpellEngine;
import com.example.main.Item.customarmor.MagicArmorItem;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.model.GeoModel;

public class WizardModel extends GeoModel<MagicArmorItem> {
    @Override
    public Identifier getModelResource(MagicArmorItem animatable) {
        return new Identifier(N3rdyR0b1nsSpellEngine.MOD_ID, "geo/amethyst_armor.geo.json");
    }

    @Override
    public Identifier getTextureResource(MagicArmorItem animatable) {
        return new Identifier(N3rdyR0b1nsSpellEngine.MOD_ID, "textures/armor/amethyst_armor.png");
    }

    @Override
    public Identifier getAnimationResource(MagicArmorItem animatable) {
        return new Identifier(N3rdyR0b1nsSpellEngine.MOD_ID, "animations/amethyst_armor.animation.json");
    }



}
