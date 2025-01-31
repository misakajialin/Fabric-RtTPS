package xyz.lisbammisakait.mixininterface;

import net.minecraft.item.ItemStack;

public interface RemainingCooldownGetter {
    default int getRemainingCooldown(ItemStack stack) {
        return 0;
    }
}
