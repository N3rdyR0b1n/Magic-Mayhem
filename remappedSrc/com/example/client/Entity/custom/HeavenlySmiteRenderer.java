package com.example.client.Entity.custom;

import com.example.N3rdyR0b1nsSpellEngine;
import com.example.main.Entity.custom.HeavenlySmiteEntity;
import com.example.main.Entity.custom.MagicMissileEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.ProjectileEntityRenderer;
import net.minecraft.util.Identifier;

public class HeavenlySmiteRenderer extends ProjectileEntityRenderer<HeavenlySmiteEntity> {
    public static final Identifier TEXTURE = new Identifier(N3rdyR0b1nsSpellEngine.MOD_ID, "textures/entity/projectiles/heavenly_smite.png");

    public HeavenlySmiteRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    public Identifier getTexture(HeavenlySmiteEntity entity) {
        return TEXTURE;
    }
}
