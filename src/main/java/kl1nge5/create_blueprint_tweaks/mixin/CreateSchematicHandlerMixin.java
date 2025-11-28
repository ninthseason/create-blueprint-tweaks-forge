package kl1nge5.create_blueprint_tweaks.mixin;

import com.simibubi.create.content.schematics.client.SchematicHandler;
import kl1nge5.create_blueprint_tweaks.Config;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import static kl1nge5.create_blueprint_tweaks.Config.nbtMode;

@Mixin(SchematicHandler.class)
public class CreateSchematicHandlerMixin {
    @Unique
    private boolean my_creative_check(LocalPlayer player) {
        if (player.isCreative()) {
            return true;
        }
        if (nbtMode && player.getMainHandItem().getTag().contains("canSurvivalPrint")) {
            return player.getMainHandItem().getTag().getBoolean("canSurvivalPrint");
        } else {
            return Config.canSurvivalPrint;
        }
    }

    @Redirect(method = "deploy", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;isCreative()Z"))
    private boolean redirectIsCreative1(LocalPlayer player) {
        return my_creative_check(player);
    }

    @Redirect(method = "init", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;isCreative()Z"))
    private boolean redirectIsCreative2(LocalPlayer player) {
        return my_creative_check(player);
    }
}
