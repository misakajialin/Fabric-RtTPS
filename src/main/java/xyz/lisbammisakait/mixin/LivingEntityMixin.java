package xyz.lisbammisakait.mixin;

import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.lisbammisakait.RelightTheThreePointStrategy;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
    @Inject(method = "swapHandStacks", at = @At("HEAD"), cancellable = true)
    private void swapHandStacksPrevent(CallbackInfo ci){
        RelightTheThreePointStrategy.LOGGER.info("触发事件");
        if(RelightTheThreePointStrategy.gameStatus==1||RelightTheThreePointStrategy.gameStatus==-1){
            // 取消
            RelightTheThreePointStrategy.LOGGER.info("swapHandStacksPrevent");
            ci.cancel();
        }

    }
}
