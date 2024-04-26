package com.leclowndu93150.snad;

import com.leclowndu93150.snad.blocks.SnadBlock;
import com.leclowndu93150.snad.blocks.SoulSnadBlock;
import com.mojang.logging.LogUtils;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(SnadMod.MODID)
public class SnadMod
{
    // Define mod id in a common place for everything to reference
    public static final String MODID = "snad";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();
    // Create a Deferred Register to hold Blocks which will all be registered under the "examplemod" namespace
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MODID);
    // Create a Deferred Register to hold Items which will all be registered under the "examplemod" namespace
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MODID);
    // Create a Deferred Register to hold CreativeModeTabs which will all be registered under the "examplemod" namespace
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

    public static final DeferredBlock<Block> SNAD_BLOCK = BLOCKS.register("snad", () -> new SnadBlock(BlockBehaviour.Properties.of()
            .destroyTime(0.7f)
            .explosionResistance(0.7f)
            .sound(SoundType.SAND))
    );

    public static final DeferredItem<BlockItem> SNAD_BLOCK_ITEM = ITEMS.registerSimpleBlockItem("snad", SNAD_BLOCK);

    public static final DeferredBlock<Block> RED_SNAD_BLOCK = BLOCKS.register("red_snad", () -> new SnadBlock(BlockBehaviour.Properties.of()
            .destroyTime(0.7f)
            .explosionResistance(0.7f)
            .sound(SoundType.SAND))
    );
    public static final DeferredItem<BlockItem> RED_SNAD_BLOCK_ITEM = ITEMS.registerSimpleBlockItem("red_snad", RED_SNAD_BLOCK);

    public static final DeferredBlock<Block> SOUL_SNAD_BLOCK = BLOCKS.register("soul_snad", () -> new SoulSnadBlock(BlockBehaviour.Properties.of()
            .destroyTime(0.7f)
            .explosionResistance(0.7f)
            .sound(SoundType.SOUL_SAND))
    );
    public static final DeferredItem<BlockItem> SOUL_SNAD_BLOCK_ITEM = ITEMS.registerSimpleBlockItem("soul_snad", SOUL_SNAD_BLOCK);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> SNAD_TAB = CREATIVE_MODE_TABS.register("snad", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.snad")) //The language key for the title of your CreativeModeTab
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> SNAD_BLOCK_ITEM.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                output.accept(SNAD_BLOCK_ITEM.get());
                output.accept(RED_SNAD_BLOCK_ITEM.get());
                output.accept(SOUL_SNAD_BLOCK_ITEM.get());
            }).build());

    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.



    public SnadMod(IEventBus modEventBus)
    {
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register the Deferred Register to the mod event bus so blocks get registered
        BLOCKS.register(modEventBus);
        // Register the Deferred Register to the mod event bus so items get registered
        ITEMS.register(modEventBus);
        // Register the Deferred Register to the mod event bus so tabs get registered
        CREATIVE_MODE_TABS.register(modEventBus);



    }
    private void commonSetup(final FMLCommonSetupEvent event)
    {
        LOGGER.info("I think im doing things right");
    }

}

