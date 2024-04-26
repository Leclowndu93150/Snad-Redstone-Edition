package com.leclowndu93150.snad.datagen;

import com.leclowndu93150.snad.SnadMod;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class LangProvider extends LanguageProvider {
    public LangProvider(PackOutput output) {
        super(
                output,
                "snad",
                "en_us"
        );
    }
        @Override
        protected void addTranslations () {
            add(SnadMod.SNAD_BLOCK.get(), "Snad");
            add(SnadMod.RED_SNAD_BLOCK.get(),"Red Snad");
            add(SnadMod.SOUL_SNAD_BLOCK.get(),"Soul Snad");
            add("itemGroup.snad", "Snad: Redstone Edition");

        }

    }
