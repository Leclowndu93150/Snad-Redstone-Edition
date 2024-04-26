package com.leclowndu93150.snad;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import static java.security.Security.addProvider;

@Mod.EventBusSubscriber(modid = "snad", bus = Mod.EventBusSubscriber.Bus.MOD)
public final class MyDataGenerator {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        var packOutput = generator.getPackOutput();
        addProvider(generator, event.includeClient(), new Lang(packOutput));
    }
}