package com.leclowndu93150.snad;

import com.leclowndu93150.snad.blocks.SnadBlock;
import com.leclowndu93150.snad.blocks.SoulSnadBlock;
import com.mojang.logging.LogUtils;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(SnadMod.MODID)
public class SnadMod
{
    // Define mod id in a common place for everything to reference
    public static final String MODID = "snad";
    // Directly reference a slf4j logger

    // Create a Deferred Register to hold Blocks which will all be registered under the "examplemod" namespace
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, SnadMod.MODID);
    public static final RegistryObject<Block> SNAD = BLOCKS.register("snad", SnadBlock::new);
    public static final RegistryObject<Block> RED_SNAD = BLOCKS.register("red_snad", SnadBlock::new);
    public static final RegistryObject<Block> SOUL_SNAD = BLOCKS.register("soul_snad", SoulSnadBlock::new);
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, SnadMod.MODID);
    public static final RegistryObject<Item> SNAD_ITEM = ITEMS.register("snad", () -> new BlockItem(SNAD.get(), new Item.Properties()));
    public static final RegistryObject<Item> RED_SNAD_ITEM = ITEMS.register("red_snad", () -> new BlockItem(RED_SNAD.get(), new Item.Properties()));
    public static final RegistryObject<Item> SOUL_SNAD_ITEM = ITEMS.register("soul_snad", () -> new BlockItem(SOUL_SNAD.get(), new Item.Properties()));


    public static final RegistryObject<CreativeModeTab> SNAD_TAB = CREATIVE_MODE_TABS.register("snad", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.snad")) //The language key for the title of your CreativeModeTab
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> SNAD_ITEM.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                output.accept(SNAD_ITEM.get());
                output.accept(RED_SNAD_ITEM.get());
                output.accept(SOUL_SNAD_ITEM.get());
            }).build());

    public SnadMod(){
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        BLOCKS.register(modEventBus);
        ITEMS.register(modEventBus);
        CREATIVE_MODE_TABS.register(modEventBus);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

}

