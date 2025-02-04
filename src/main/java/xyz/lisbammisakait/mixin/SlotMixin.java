package xyz.lisbammisakait.mixin;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.lisbammisakait.item.RtTPSSwordItem;
import xyz.lisbammisakait.skill.ActiveSkillable;
import xyz.lisbammisakait.skill.PassiveSkillable;

@Mixin(Slot.class)
public abstract class SlotMixin {
    @Invoker("getStack")
    public abstract ItemStack getThisStack();
    @Inject(method = "canTakeItems", at = @At("HEAD"), cancellable = true)
    private void cantTakeItems(PlayerEntity playerEntity, CallbackInfoReturnable<Boolean> cir) {
        ItemStack itemStack = this.getThisStack();
        if(itemStack.getItem() instanceof ActiveSkillable || itemStack.getItem() instanceof PassiveSkillable || itemStack.getItem() instanceof RtTPSSwordItem ) {
            cir.setReturnValue(false);
        }
        if( playerEntity.isCreative()){
            cir.setReturnValue(true);
        }
    }
}
