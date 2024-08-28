package com.example.client.item_render.custom;

import com.example.N3rdyR0b1nsSpellEngine;
import com.example.client.item_render.templates.WizardModel;
import com.example.main.Item.customarmor.MagicArmorItem;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

public class TheClassicArmorModel extends WizardModel {
    @Override
    public Identifier getModelResource(MagicArmorItem animatable) {
        return new Identifier(N3rdyR0b1nsSpellEngine.MOD_ID, "geo/theclassicset_1.geo.json");
    }

    @Override
    public Identifier getTextureResource(MagicArmorItem animatable) {
        return new Identifier(N3rdyR0b1nsSpellEngine.MOD_ID, "textures/armor/theclassicset.png");
    }

    @Override
    public Identifier getAnimationResource(MagicArmorItem animatable) {
        return new Identifier(N3rdyR0b1nsSpellEngine.MOD_ID, "animations/theclassicset.animation.json");
    }

}
