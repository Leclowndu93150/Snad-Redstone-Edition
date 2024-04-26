package com.leclowndu93150.snad;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class Lang extends LanguageProvider {
    public Lang(PackOutput output, String locale) {
        super(output, SnadMod.MODID, locale);
    }

    @Override
    protected void addTranslations() {
        add(SnadMod.SNAD_BLOCK.get(), "Snad");
        addBlock(SnadMod.SNAD_BLOCK, "Snad");
        add("itemGroup.snad", "Snad: Redstone Edition");

    }
}