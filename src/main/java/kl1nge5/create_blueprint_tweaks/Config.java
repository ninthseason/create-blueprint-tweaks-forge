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

    private static final ForgeConfigSpec.BooleanValue ALLOW_PRINT_IN_SURVIVAL_MODE = BUILDER
            .comment("Whether blueprints can be printed directly in survival mode")
            .define("canSurvivalPrint", true);

    private static final ForgeConfigSpec.BooleanValue SURVIVAL_PRINT_CONSUME = BUILDER
            .comment("Whether blueprints will be consumed when printed in survival mode")
            .define("survivalPrintConsume", true);

    static final ForgeConfigSpec SPEC = BUILDER.build();

    public static boolean canSurvivalPrint;
    public static boolean survivalPrintConsume;

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event)
    {
        canSurvivalPrint = ALLOW_PRINT_IN_SURVIVAL_MODE.get();
        survivalPrintConsume = SURVIVAL_PRINT_CONSUME.get();
    }
}
