package xyz.lisbammisakait.mixin;

import net.minecraft.entity.player.ItemCooldownManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import xyz.lisbammisakait.mixininterface.RemainingCooldownGetter;

import java.util.Map;

@Mixin(ItemCooldownManager.class)
public abstract class ItemCooldownManagerMixin implements RemainingCooldownGetter {

    @Shadow
    private int tick;

    @Shadow
    private Map<Identifier, ItemCooldownManager.Entry> entries;
    @Shadow
    public abstract Identifier getGroup(ItemStack stack);
    // 添加 getCooldown 方法
    @Override
    public int getRemainingCooldown(ItemStack stack) {
        Identifier identifier = this.getGroup(stack);
        ItemCooldownManager.Entry entry = entries.get(identifier);
        if (entry != null) {
            int remainingCooldown = entry.endTick() - tick;
            return Math.max(0, remainingCooldown);
        }
        return 0;
    }
}
