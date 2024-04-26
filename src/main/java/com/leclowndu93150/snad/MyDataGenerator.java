package com.leclowndu93150.snad;

import net.minecraft.data.DataGenerator;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.GAME)


public final class  MyDataGenerator{
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        event.getGenerator().addProvider(event.includeServer(), new Lang(generator.getPackOutput()));
    }
}