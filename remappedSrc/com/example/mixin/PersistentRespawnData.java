package com.example.mixin;

import com.example.main.Attributes.ModAttributes;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public class PersistentRespawnData {

    @Inject(method = "copyFrom", at = @At("TAIL"))
    private void onCopyFrom(ServerPlayerEntity oldPlayer, boolean alive, CallbackInfo ci) {
        if (oldPlayer.getAttributeBaseValue(ModAttributes.CASTING_LEVEL) >= 0) {
            ServerPlayerEntity user = ((ServerPlayerEntity) (Object) this);
            user.getAttributeInstance(ModAttributes.CASTING_LEVEL).setBaseValue(oldPlayer.getAttributeBaseValue(ModAttributes.CASTING_LEVEL));

        }
    }
}
