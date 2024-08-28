package com.example.client;


import com.example.client.CustomKeys.KeyInputHandler;
import com.example.client.Entity.custom.FireBallRenderer;
import com.example.client.Entity.custom.HeavenlySmiteRenderer;
import com.example.client.Entity.custom.IcycleRenderer;
import com.example.client.Entity.custom.MagicMissileRenderer;
import com.example.client.Event.ClientPlayerTickHandler;
import com.example.client.ModS2CMessages.ModPackets;
import com.example.client.gui.MagicTableScreen;
import com.example.main.Entity.ModEntities;
import com.example.main.Entity.custom.MagicMissileEntity;
import com.example.main.Gui.ModScreenHandler;
import com.example.main.ModS2CMessages.ModPacketChannels;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraft.client.render.entity.SalmonEntityRenderer;

public class N3rdyR0b1nsSpellEngineClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		ModPacketChannels.registerChannelIds();
		ModPackets.registerS2CPackets();
		ClientTickEvents.START_CLIENT_TICK.register(new ClientPlayerTickHandler());
		KeyInputHandler.init();
		EntityRendererRegistry.register(ModEntities.FIREBALL, FireBallRenderer::new);
		EntityRendererRegistry.register(ModEntities.CHROMATIC_ORB, FlyingItemEntityRenderer::new);
		EntityRendererRegistry.register(ModEntities.MAGIC_MISSILE, MagicMissileRenderer::new);
		EntityRendererRegistry.register(ModEntities.ICYCLE, IcycleRenderer::new);
		EntityRendererRegistry.register(ModEntities.HEAVENLY_SMITE, HeavenlySmiteRenderer::new);
		EntityRendererRegistry.register(ModEntities.METEOR, FlyingItemEntityRenderer::new);

		EntityRendererRegistry.register(ModEntities.GRAVITY, FlyingItemEntityRenderer::new);
		EntityRendererRegistry.register(ModEntities.FIRE_SIGIL, FlyingItemEntityRenderer::new);
		EntityRendererRegistry.register(ModEntities.ICE_SIGIL, FlyingItemEntityRenderer::new);
		EntityRendererRegistry.register(ModEntities.CURSE_SIGIL, FlyingItemEntityRenderer::new);
		EntityRendererRegistry.register(ModEntities.EXPLOSION_SIGIL, FlyingItemEntityRenderer::new);
		EntityRendererRegistry.register(ModEntities.IMPLOSION_SIGIL, FlyingItemEntityRenderer::new);
		EntityRendererRegistry.register(ModEntities.PUSH_SIGIL, FlyingItemEntityRenderer::new);

		HandledScreens.register(ModScreenHandler.SPELL_APPLICATION_SCREEN_HANDLER, MagicTableScreen::new);
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.
	}
}