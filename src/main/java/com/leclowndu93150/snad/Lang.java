package com.leclowndu93150.snad;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class Lang extends LanguageProvider {
    public Lang(PackOutput output) {
        super(
                // Provided by the GatherDataEvent.
                output,
                // Your mod id.
                "snad",
                // The locale to use. You may use multiple language providers for different locales.
                "en_us"
        );
    }
        @Override
        protected void addTranslations () {
            add(SnadMod.SNAD_BLOCK.get(), "Snad");
            add(SnadMod.RED_SNAD_BLOCK.get(),"Red Snad");
            add("itemGroup.snad", "Snad: Redstone Edition");

        }

    }
