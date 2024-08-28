package com.example.client.Entity.custom;

import com.example.N3rdyR0b1nsSpellEngine;
import com.example.main.Entity.custom.MagicMissileEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.ProjectileEntityRenderer;
import net.minecraft.util.Identifier;

public class MagicMissileRenderer extends ProjectileEntityRenderer<MagicMissileEntity> {
    public static final Identifier TEXTURE = new Identifier(N3rdyR0b1nsSpellEngine.MOD_ID, "textures/entity/projectiles/magic_missile.png");

    public MagicMissileRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    public Identifier getTexture(MagicMissileEntity entity) {
        return TEXTURE;
    }
}
