package kl1nge5.create_blueprint_tweaks.mixin;

import com.simibubi.create.foundation.utility.BlockHelper;
import kl1nge5.create_blueprint_tweaks.AllBlocks;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(BlockHelper.class)
public class CreateBlockHelperMixin {
	@ModifyVariable(method = "placeSchematicBlock", at = @At("HEAD"), remap = false, argsOnly = true)
	private static BlockState convertPlaceholderToAir(BlockState state) {
		if (state.is(AllBlocks.AIR_PLACEHOLDER_BLOCK.get())) {
			return Blocks.AIR.defaultBlockState();
		}
		return state;
	}
}
