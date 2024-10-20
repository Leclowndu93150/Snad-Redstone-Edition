package com.leclowndu93150.snad;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

// An example config class. This is not required, but it's a good idea to have one to keep your config organized.
// Demonstrates how to use Forge's config APIs
@Mod.EventBusSubscriber(modid = SnadMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config
{
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    private static final ForgeConfigSpec.IntValue MAGIC_NUMBER = BUILDER
            .comment("Number of growth ticks on the snad block (default 6)")
            .defineInRange("growthAttempts", 6, 1, 32);

    private static final ForgeConfigSpec.IntValue MAGIC_NUMBER2 = BUILDER
            .comment("Speed at which Kelp and Bamboo grow on snad (default 2)")
            .defineInRange("growthSpeed", 2, 1, 32);



    static final ForgeConfigSpec SPEC = BUILDER.build();
    public static int magicNumber;
    public static int magicNumber2;
    @SubscribeEvent
    static void onLoad(final ModConfigEvent event)
    {
        magicNumber = MAGIC_NUMBER.get();
        magicNumber2 = MAGIC_NUMBER2.get();
    }
}
