package com.leclowndu93150.snad.mixins;

import net.minecraft.client.resources.SplashManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(SplashManager.class)
public class MixinSplashManager {
    @Inject(method = "apply", at = @At("RETURN"))
    private void onApply(List<String> splashes, net.minecraft.server.packs.resources.ResourceManager resourceManager, net.minecraft.util.profiling.ProfilerFiller profiler, CallbackInfo ci) {
        splashes.add("Vive la France!");
    }
}