package xyz.lisbammisakait.skill;

import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemStack;

public interface ActiveSkillable {
    void castSkill(MinecraftClient client, ItemStack stack);
}
