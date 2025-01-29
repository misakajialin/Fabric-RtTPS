package xyz.lisbammisakait.skill;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;

import java.util.List;

public class ZhangJiaoASKill extends Item implements ActiveSkillable {
    private final int COOLDOWN = 50;
    public ZhangJiaoASKill(Settings settings) {
        super(settings);
    }

    @Override
    public void castSkill(MinecraftClient client, ItemStack stack) {
        ClientPlayerEntity user = client.player;
        if (user.getItemCooldownManager().isCoolingDown(stack)) {
            // 如果物品正在冷却中，直接返回
            user.sendMessage(Text.of("技能冷却中"), true);
            return ;
        }
        MinecraftServer server = client.getServer();
        List<ServerPlayerEntity> playList = server.getPlayerManager().getPlayerList();
        ServerWorld serverWorld =  server.getWorld(user.getEntityWorld().getRegistryKey());
        for(PlayerEntity player:playList){
            if(!player.equals(user)){
                BlockPos underneathOfPlayer = user.getBlockPos();
                // Spawn the lightning bolt.
                LightningEntity lightningBolt = new LightningEntity(EntityType.LIGHTNING_BOLT, serverWorld );
                lightningBolt.setPosition(underneathOfPlayer.toCenterPos());
                serverWorld.spawnEntity(lightningBolt);
            }
        }
        // 设置物品冷却时间
        user.getItemCooldownManager().set(stack, COOLDOWN * 20);
    }
}
