package com.example.mixin;

import com.example.main.Item.spellfocus.LeftMouseItem;
import com.example.main.ModS2CMessages.ModPacketChannels;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.GameOptions;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.concurrent.CompletableFuture;

@Mixin(MinecraftClient.class)
public abstract class ClientLMBMixin {
	@Shadow public abstract CompletableFuture<Void> reloadResources();

	@Nullable
	public ClientPlayerEntity player;
	@Nullable
	public GameOptions options;

	@Inject(method = "doAttack", at = @At("HEAD"), cancellable = true)
	private void LeftClickUtility(CallbackInfoReturnable<Boolean> cir) {
		PlayerEntity player = ((MinecraftClient) (Object) this).player;
		if (player == null) {
			return;
		}
		ItemStack LeftClickstack = player.getStackInHand(player.getActiveHand());
		if (LeftClickstack.getItem() instanceof LeftMouseItem) {
			((LeftMouseItem)LeftClickstack.getItem()).onLmbClickClient(player, LeftClickstack);
			ClientPlayNetworking.send(ModPacketChannels.LEFT_CLICK, PacketByteBufs.create());
			cir.setReturnValue(false);
		}
		// This code is injected into the start of MinecraftClient.run()V
	}

	@Inject(method = "handleInputEvents()V", at = @At("HEAD"), cancellable = true)
	private void LeftHoldUtility(CallbackInfo cir) {

		MinecraftClient client = ((MinecraftClient) (Object) this);
		if (client.options == null && client.options.attackKey.isPressed()) {
			ItemStack LeftClickstack = client.player.getStackInHand(client.player.getActiveHand());
			if (LeftClickstack.getItem() instanceof LeftMouseItem) {
				((LeftMouseItem) LeftClickstack.getItem()).onLmbHoldClient(client.player, LeftClickstack);
				ClientPlayNetworking.send(ModPacketChannels.LEFT_HOLD, PacketByteBufs.create());
			}
		}
		// This code is injected into the start of MinecraftClient.run()V
	}


}