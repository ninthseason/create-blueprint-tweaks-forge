package kl1nge5.create_blueprint_tweaks.mixin;

import com.simibubi.create.content.schematics.cannon.SchematicannonBlockEntity;
import com.simibubi.create.content.schematics.requirement.ItemRequirement;
import kl1nge5.create_blueprint_tweaks.AllBlocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SchematicannonBlockEntity.class)
abstract class CreateSchematicannonBlockEntityMixin {
    @Inject(method = "grabItemsFromAttachedInventories", at = @At("HEAD"), cancellable = true, remap = false)
    private void grabItemsFromAttachedInventories(ItemRequirement.StackRequirement required, boolean simulate, CallbackInfoReturnable<Boolean> cir) {
        // 如果需要的方块是空气占位符方块，则直接认为已经有了
        if (required.matches(AllBlocks.AIR_PLACEHOLDER_BLOCK.get().asItem().getDefaultInstance())) {
            cir.setReturnValue(true);
        }
    }
}
