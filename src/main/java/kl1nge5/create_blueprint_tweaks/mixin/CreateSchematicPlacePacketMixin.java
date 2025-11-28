package kl1nge5.create_blueprint_tweaks.mixin;

import com.simibubi.create.content.schematics.packet.SchematicPlacePacket;
import kl1nge5.create_blueprint_tweaks.BlueprintTweaks;
import kl1nge5.create_blueprint_tweaks.Config;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static kl1nge5.create_blueprint_tweaks.Config.nbtMode;

@Mixin(SchematicPlacePacket.class)
abstract class CreateSchematicPlacePacketMixin {
	@Final
	@Shadow
    private ItemStack stack;

    @Unique
    private boolean my_creative_check(ServerPlayer player) {
        if (player.isCreative()) {
            return true;
        }
        if (nbtMode && player.getMainHandItem().getTag().contains("canSurvivalPrint")) {
            return player.getMainHandItem().getTag().getBoolean("canSurvivalPrint");
        } else {
            return Config.canSurvivalPrint;
        }
    }

    @Unique
    private boolean need_consume_schematic(ServerPlayer player) {
        if (player.isCreative()) {
            return false;
        }
        if (nbtMode && player.getMainHandItem().getTag().contains("survivalPrintConsume")) {
            return player.getMainHandItem().getTag().getBoolean("survivalPrintConsume");
        } else {
            return Config.survivalPrintConsume;
        }
    }

	@Redirect(method = "lambda$handle$2", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerPlayer;isCreative()Z"))
	public boolean isCreativeRedirect(ServerPlayer player) {
        return my_creative_check(player);
	}

	@Inject(method = "lambda$handle$2", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerPlayer;level()Lnet/minecraft/world/level/Level;"), cancellable = true, remap = false)
	private void lambda$handle$2(NetworkEvent.Context context, CallbackInfo ci) {
        ServerPlayer player = context.getSender();
		// 检查玩家主手真的是所宣称的原理图，防止用神奇的手法作弊
        ItemStack mainHandItem = player.getMainHandItem();
        if (!mainHandItem.getTag().toString().equals(stack.getTag().toString())){
            BlueprintTweaks.LOGGER.warn("Invalid main hand item!");
			ci.cancel();
			return;
		}

		if (need_consume_schematic(player)) {  // 如果是生存模式则消耗一份原理图
			player.getMainHandItem().shrink(1);
		}
	}
}
