package com.example.client.item_render.templates;


import com.example.main.Item.customarmor.MagicArmorItem;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class WizardArmorRenderer extends GeoArmorRenderer<MagicArmorItem> {

    public WizardArmorRenderer(WizardModel model) {
        super(model);
    }
    @Override
    public RenderLayer getRenderType(MagicArmorItem animatable, Identifier texture, VertexConsumerProvider bufferSource, float partialTick) {
        return RenderLayer.getEntityTranslucent(texture);
    }

}
