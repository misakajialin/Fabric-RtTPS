package xyz.lisbammisakait.mixin;

import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.lisbammisakait.RelightTheThreePointStrategy;

@Mixin(ServerPlayNetworkHandler.class)
public abstract class ServerPlayNetworkHandlerMixin {
    @Inject(at = @At("HEAD"), method = "onPlayerAction", cancellable = true)
    public void preventSwap(PlayerActionC2SPacket packet, CallbackInfo ci) {
        if (packet.getAction() == PlayerActionC2SPacket.Action.SWAP_ITEM_WITH_OFFHAND) {
            if(RelightTheThreePointStrategy.gameStatus==1||RelightTheThreePointStrategy.gameStatus==-1){
                // 取消
                RelightTheThreePointStrategy.LOGGER.info("swapHandStacksPrevent");
                ci.cancel();
            }
        }
    }
}
