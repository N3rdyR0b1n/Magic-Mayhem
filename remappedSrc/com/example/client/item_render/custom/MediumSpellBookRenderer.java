package com.example.client.item_render.custom;

import com.example.client.item_render.templates.mediumSpellBookModel;
import com.example.client.item_render.templates.SpellBookRenderer;
import com.example.main.Item.spellfocus.SpellFocus;
import software.bernie.geckolib.model.GeoModel;

public class MediumSpellBookRenderer extends SpellBookRenderer {
    public MediumSpellBookRenderer() {
        super(new mediumSpellBookModel());
    }

    public MediumSpellBookRenderer(GeoModel<SpellFocus> model) {
        super(model);
    }
}
