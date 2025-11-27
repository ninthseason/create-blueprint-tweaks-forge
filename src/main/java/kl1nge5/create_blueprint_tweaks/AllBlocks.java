package kl1nge5.create_blueprint_tweaks;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.RegistryObject;

public class AllBlocks {
    public static final RegistryObject<Block> AIR_PLACEHOLDER_BLOCK = BlueprintTweaks.BLOCKS.register("air_placeholder", () -> new Block(BlockBehaviour.Properties.of().noCollission().forceSolidOn()) );
    public static final RegistryObject<Item> AIR_PLACEHOLDER_BLOCK_ITEM = BlueprintTweaks.ITEMS.register("air_placeholder", () -> new BlockItem(AIR_PLACEHOLDER_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<CreativeModeTab> ITEM_GROUP = BlueprintTweaks.CREATIVE_MODE_TABS.register("create_blueprint_tweaks", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.create_blueprint_tweaks")) //The language key for the title of your CreativeModeTab
//            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> AIR_PLACEHOLDER_BLOCK_ITEM.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                output.accept(AIR_PLACEHOLDER_BLOCK_ITEM.get()); // Add the example item to the tab. For your own tabs, this method is preferred over the event
            }).build());
    public static void init() {

    }
}
