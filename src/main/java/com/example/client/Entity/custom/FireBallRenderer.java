package com.example.client.Entity.custom;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;

public class FireBallRenderer extends FlyingItemEntityRenderer {
    public FireBallRenderer(EntityRendererFactory.Context ctx, float scale, boolean lit) {
        super(ctx, scale, lit);
    }
    public FireBallRenderer(EntityRendererFactory.Context context) {
        this(context, 3.0f, false);
    }

}
