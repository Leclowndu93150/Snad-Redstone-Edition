package com.leclowndu93150.snad;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

@EventBusSubscriber(modid = SnadMod.MODID, bus = EventBusSubscriber.Bus.MOD)
public final class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    private static final ModConfigSpec.IntValue MAGIC_NUMBER = BUILDER
            .comment("Number of growth ticks on the snad block (default 6)")
            .defineInRange("growthAttempts", 6, 1, 32);

    private static final ModConfigSpec.IntValue MAGIC_NUMBER2 = BUILDER
            .comment("Speed at which Kelp and Bamboo grow on snad (default 2)")
            .defineInRange("growthSpeed", 2, 1, 32);


    static final ModConfigSpec SPEC = BUILDER.build();

    public static int magicNumber;
    public static int magicNumber2;

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {
        magicNumber = MAGIC_NUMBER.get();
        magicNumber2 = MAGIC_NUMBER2.get();
    }
}