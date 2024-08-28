package com.example.client.item_render.templates;

import com.example.N3rdyR0b1nsSpellEngine;
import com.example.main.Item.spellfocus.SpellFocus;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class mediumSpellBookModel extends GeoModel<SpellFocus> {
    @Override
    public Identifier getModelResource(SpellFocus animatable) {
        return new Identifier(N3rdyR0b1nsSpellEngine.MOD_ID, "geo/medium_spellbook.geo.json");
    }

    @Override
    public Identifier getTextureResource(SpellFocus animatable) {
        return new Identifier(N3rdyR0b1nsSpellEngine.MOD_ID, "textures/item/medium_spellbook.png");
    }

    @Override
    public Identifier getAnimationResource(SpellFocus animatable) {
        return new Identifier(N3rdyR0b1nsSpellEngine.MOD_ID, "animations/medium_spellbook.animation.json");
    }
}
