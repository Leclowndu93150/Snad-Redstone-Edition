package com.leclowndu93150.snad;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.minecraft.world.item.CreativeModeTab;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(SnadMod.MODID)
public class SnadMod
{
    // Define mod id in a common place for everything to reference
    public static final String MODID = "snad";
    // Directly reference a slf4j logger

    // Create a Deferred Register to hold Blocks which will all be registered under the "examplemod" namespace
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS,MODID);
    // Create a Deferred Register to hold Items which will all be registered under the "examplemod" namespace
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS,MODID);
    // Create a Deferred Register to hold CreativeModeTabs which will all be registered under the "examplemod" namespace
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

    public static final RegistryObject<Block> SNAD_BLOCK = BLOCKS.register("snad", () -> new SnadBlock(BlockBehaviour.Properties.of()
            .destroyTime(0.7f)
            .explosionResistance(0.7f)
            .sound(SoundType.SAND))
    );

    public static final RegistryObject<BlockItem> SNAD_BLOCK_ITEM = ITEMS.register("snad",() -> new BlockItem(SNAD_BLOCK.get(), new Item.Properties()));

    public static final RegistryObject<Block> RED_SNAD_BLOCK = BLOCKS.register("red_snad", () -> new SnadBlock(BlockBehaviour.Properties.of()
            .destroyTime(0.7f)
            .explosionResistance(0.7f)
            .sound(SoundType.SAND))
    );
    public static final RegistryObject<BlockItem> RED_SNAD_BLOCK_ITEM = ITEMS.register("red_snad",() -> new BlockItem(RED_SNAD_BLOCK.get(), new Item.Properties()));

    public static final RegistryObject<Block> SOUL_SNAD_BLOCK = BLOCKS.register("soul_snad", () -> new SoulSnadBlock(BlockBehaviour.Properties.of()
            .destroyTime(0.7f)
            .explosionResistance(0.7f)
            .sound(SoundType.SOUL_SAND))
    );
    public static final RegistryObject<BlockItem> SOUL_SNAD_BLOCK_ITEM = ITEMS.register("soul_snad",() -> new BlockItem(SOUL_SNAD_BLOCK.get(), new Item.Properties()));

    public static final RegistryObject<CreativeModeTab> SNAD_TAB = CREATIVE_MODE_TABS.register("snad", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.snad")) //The language key for the title of your CreativeModeTab
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> new ItemStack(SNAD_BLOCK_ITEM.get()))
            .displayItems((params, output) -> {
                output.accept(SNAD_BLOCK_ITEM.get());
                output.accept(RED_SNAD_BLOCK_ITEM.get());
                output.accept(SOUL_SNAD_BLOCK_ITEM.get());
            }).build());


    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.



    public SnadMod()
    {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        BLOCKS.register(eventBus);
        ITEMS.register(eventBus);
        CREATIVE_MODE_TABS.register(eventBus);
    }


}

