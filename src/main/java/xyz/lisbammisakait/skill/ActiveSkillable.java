package xyz.lisbammisakait.skill;

import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;

public interface ActiveSkillable {
    void castSkill(MinecraftServer server, ServerPlayerEntity player, ItemStack stack);
}
