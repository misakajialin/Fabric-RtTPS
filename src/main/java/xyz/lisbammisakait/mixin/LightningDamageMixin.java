package xyz.lisbammisakait.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.lisbammisakait.item.ModItems;

import java.util.function.Predicate;

@Mixin(Entity.class)
public abstract class LightningDamageMixin {
    /**
     * 在玩家受到雷电伤害之前检查是否拥有特定物品
     * @param ci 回调信息
     */
    @Inject(method = "onStruckByLightning", at = @At("HEAD"), cancellable = true)
    private void onStruckByLightning(CallbackInfo ci) {
        // 检查当前实体是否为玩家
        if ((Object) this instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) (Object) this;
            // 检查玩家是否拥有特定物品
            if (player.getInventory().contains( ModItems.LEITINGZHIZHANG.getDefaultStack())) {
                // 取消雷电伤害
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 20,1 ));
                ci.cancel();
            }
        }
    }
}
