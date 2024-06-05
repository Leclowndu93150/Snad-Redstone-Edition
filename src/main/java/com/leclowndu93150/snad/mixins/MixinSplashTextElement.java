package com.leclowndu93150.snad.mixins;

import de.keksuccino.fancymenu.customization.element.elements.splash.SplashTextElement;
import de.keksuccino.fancymenu.util.resource.resources.text.IText;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(value = SplashTextElement.class, remap = false)
public class MixinSplashTextElement {

    @Inject(method = "updateSplash", at = @At(value = "INVOKE",
            target = "Lde/keksuccino/fancymenu/util/resource/resources/text/IText;getTextLines()Ljava/util/List;",
            shift = At.Shift.AFTER))
    private void injectViveLaFrance(CallbackInfo ci) {
        SplashTextElement splashTextElement = (SplashTextElement) (Object) this;
        if (splashTextElement.sourceMode == SplashTextElement.SourceMode.TEXT_FILE && splashTextElement.textFileSupplier != null) {
            IText text = splashTextElement.textFileSupplier.get();
            if (text != null) {
                List<String> l = text.getTextLines();
                if (l != null) {
                    l.add("Vive la France!");
                }
            }
        }
    }
}