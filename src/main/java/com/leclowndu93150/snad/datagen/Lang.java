package com.leclowndu93150.snad.datagen;

import com.leclowndu93150.snad.SnadMod;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;

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
            add(SnadMod.SNAD.get(), "Snad");
            add(SnadMod.RED_SNAD.get(),"Red Snad");
            add(SnadMod.SOUL_SNAD.get(),"Soul Snad");
            add("itemGroup.snad", "Snad: Redstone Edition");

        }

    }
