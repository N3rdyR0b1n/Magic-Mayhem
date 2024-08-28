package com.example.client.item_render.custom;

import com.example.N3rdyR0b1nsSpellEngine;
import com.example.client.item_render.templates.WizardModel;
import com.example.main.Item.customarmor.MagicArmorItem;
import net.minecraft.util.Identifier;

public class TheWardenLegArmorModel extends WizardModel {
    @Override
    public Identifier getModelResource(MagicArmorItem animatable) {
        return new Identifier(N3rdyR0b1nsSpellEngine.MOD_ID, "geo/warden_set_2.geo.json");
    }

    @Override
    public Identifier getTextureResource(MagicArmorItem animatable) {
        return new Identifier(N3rdyR0b1nsSpellEngine.MOD_ID, "textures/armor/warden_armor.png");
    }

    @Override
    public Identifier getAnimationResource(MagicArmorItem animatable) {
        return new Identifier(N3rdyR0b1nsSpellEngine.MOD_ID, "animations/warden_model.animation.json");
    }
}
