package kl1nge5.create_blueprint_tweaks.mixin;

import com.simibubi.create.content.schematics.packet.SchematicPlacePacket;
import kl1nge5.create_blueprint_tweaks.Config;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SchematicPlacePacket.class)
abstract class CreateSchematicPlacePacketMixin {
	@Final
	@Shadow
    private ItemStack stack;

	@Redirect(method = "lambda$handle$2", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerPlayer;isCreative()Z"))
	public boolean isCreativeRedirect(ServerPlayer player) {
		return Config.canSurvivalPrint || player.isCreative();
	}

	@Inject(method = "handle", at = @At("HEAD"), cancellable = true, remap = false)
	private void lambda$handle$2(NetworkEvent.Context context, CallbackInfoReturnable<Boolean> ci) {
        ServerPlayer player = context.getSender();
        if (!(Config.canSurvivalPrint || player.isCreative())) {
            ci.cancel();
            return;
        }
		// 检查玩家背包里是否真的有原理图，防止用神奇的手法作弊
		if (player == null) return;
		Inventory inventory = player.getInventory();
		if (!inventory.contains(stack)){
			ci.cancel();
			return;
		}
		if (!player.isCreative() && Config.survivalPrintConsume) {  // 如果是生存模式则消耗一份原理图
			inventory.getItem(inventory.findSlotMatchingItem(stack)).shrink(1);
		}
	}
}
