package com.example.enchantchaos.mixin;

import com.example.enchantchaos.EnchantChaosMod;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {
    
    @Inject(method = "jump", at = @At("HEAD"))
    private void onJump(CallbackInfo ci) {
        PlayerEntity player = (PlayerEntity)(Object)this;
        
        // Only run on server side for players and if enchanting is enabled
        if (player instanceof ServerPlayerEntity && EnchantChaosMod.isJumpEnchantingEnabled()) {
            ServerPlayerEntity serverPlayer = (ServerPlayerEntity) player;
            EnchantChaosMod.enchantRandomItem(serverPlayer);
        }
    }
}
