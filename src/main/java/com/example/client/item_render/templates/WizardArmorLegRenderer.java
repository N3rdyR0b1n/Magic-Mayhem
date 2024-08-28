package com.example.client.item_render.templates;

import com.example.main.Item.customarmor.MagicArmorItem;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.util.Identifier;

public class WizardArmorLegRenderer extends WizardArmorRenderer {
    public WizardArmorLegRenderer(WizardModel model) {
        super(model);
    }

    @Override
    protected void applyBoneVisibilityBySlot(EquipmentSlot currentSlot) {
        setVisible(false);

        switch (currentSlot) {

            case LEGS -> {
                setBoneVisible(this.body, true);
                setBoneVisible(this.rightLeg, true);
                setBoneVisible(this.leftLeg, true);
            }

            default -> {}
        }
    }
    @Override
    public RenderLayer getRenderType(MagicArmorItem animatable, Identifier texture, VertexConsumerProvider bufferSource, float partialTick) {
        return RenderLayer.getEntityTranslucent(texture);
    }
}
