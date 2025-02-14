package xyz.lisbammisakait.mixin;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.scoreboard.ScoreAccess;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.ScoreboardObjective;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.lisbammisakait.RelightTheThreePointStrategy;

@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin {
    @Inject(at = @At("HEAD"), method = "dropSelectedItem(Z)Z", cancellable = true)
    private void dropSelectedItemCancel(boolean entireStack, CallbackInfoReturnable<Boolean> cir) {
        if(RelightTheThreePointStrategy.gameStatus==1||RelightTheThreePointStrategy.gameStatus==-1){
            // 取消丢弃物品
            RelightTheThreePointStrategy.LOGGER.info("dropSelectedItemCancel");
            cir.setReturnValue(false);
        }
    }
}
