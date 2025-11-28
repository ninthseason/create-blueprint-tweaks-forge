package kl1nge5.create_blueprint_tweaks;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

// An example config class. This is not required, but it's a good idea to have one to keep your config organized.
// Demonstrates how to use Forge's config APIs
@Mod.EventBusSubscriber(modid = BlueprintTweaks.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config {
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    private static final ForgeConfigSpec.BooleanValue NBT_MODE = BUILDER
            .comment(
                    "When enabled, allow to use item's nbt as settings. Nbt settings will override global settings.",
                    "You can give item following nbts:",
                    "canSurvivalPrint: true/false",
                    "survivalPrintConsume: true/false",
                    "For example, if you want blueprints created by players through the blueprint table to not be printed directly,",
                    "and only the blueprints you give them through special means (such as FTB quests) to be printable,",
                    "you can set nbtMode = true, canSurvivalPrint = false, and survivalPrintConsume = true in this configuration file.",
                    "With these settings, blueprints will not be printable by default unless you set canSurvivalPrint: true in the blueprint’s NBT."
            )
            .define("nbtMode", false);
    private static final ForgeConfigSpec.BooleanValue ALLOW_PRINT_IN_SURVIVAL_MODE = BUILDER
            .comment("Whether blueprints can be printed directly in survival mode")
            .define("canSurvivalPrint", true);

    private static final ForgeConfigSpec.BooleanValue SURVIVAL_PRINT_CONSUME = BUILDER
            .comment("Whether blueprints will be consumed when printed in survival mode")
            .define("survivalPrintConsume", true);

    static final ForgeConfigSpec SPEC = BUILDER.build();

    public static boolean canSurvivalPrint;
    public static boolean survivalPrintConsume;
    public static boolean nbtMode;

    @SubscribeEvent
    static void onLoad(final ModConfigEvent.Loading event) {
        bakeConfig();
    }

    @SubscribeEvent
    static void onReloading(final ModConfigEvent.Reloading event) {
        bakeConfig();
    }

    private static void bakeConfig() {
        canSurvivalPrint = ALLOW_PRINT_IN_SURVIVAL_MODE.get();
        survivalPrintConsume = SURVIVAL_PRINT_CONSUME.get();
        nbtMode = NBT_MODE.get();
    }
}
