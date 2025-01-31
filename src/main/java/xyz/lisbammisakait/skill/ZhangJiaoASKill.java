package xyz.lisbammisakait.skill;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import xyz.lisbammisakait.RelightTheThreePointStrategy;
import xyz.lisbammisakait.compoennt.RtTPSComponents;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.util.List;

public class ZhangJiaoASKill extends Item implements ActiveSkillable {
    private final int COOLDOWN = 40;
    public final int SPNUMBER = 8 ;
    public ZhangJiaoASKill(Settings settings) {
        super(settings);
    }


    @Override
    public void castSkill(MinecraftServer server, ServerPlayerEntity player, ItemStack stack) {

        if (player.getItemCooldownManager().isCoolingDown(stack)) {
            // 如果物品正在冷却中，直接返回
            float cdr2 =  player.getItemCooldownManager().getCooldownProgress(stack, 0.0F)*40;
            player.sendMessage(Text.of("剩余冷却时间："+cdr2), true);
//            client.player.sendMessage(Text.of("剩余冷却时间："+cdr2), true);
//            user.sendMessage(Text.of("技能冷却中"), true);
            return ;
        }
        spawnLightningBolt(server,stack,player);
        // 设置物品冷却时间
//        user.getItemCooldownManager().set(stack, COOLDOWN * 20);
        player.getItemCooldownManager().set(stack, COOLDOWN * 20);
    }
    public void spawnLightningBolt(MinecraftServer server, ItemStack stack,ServerPlayerEntity serverplayer) {

        List<ServerPlayerEntity> playList = server.getPlayerManager().getPlayerList();
        RelightTheThreePointStrategy.LOGGER.info(playList.toString());
        ServerWorld serverWorld =  server.getWorld(serverplayer.getEntityWorld().getRegistryKey());
        for(PlayerEntity player:playList){
            if(!player.equals(serverplayer)){
                BlockPos underneathOfPlayer = player.getBlockPos();
                // Spawn the lightning bolt.
                LightningEntity lightningBolt = new LightningEntity(EntityType.LIGHTNING_BOLT, serverWorld );
                lightningBolt.setPosition(underneathOfPlayer.toCenterPos());
                serverWorld.spawnEntity(lightningBolt);
            }
        }
        stack.set(RtTPSComponents.USENUMBER_TYPE,stack.getOrDefault(RtTPSComponents.USENUMBER_TYPE,0)+1);
        //当发动次数达到次数
        if(stack.getOrDefault(RtTPSComponents.USENUMBER_TYPE,0)==SPNUMBER){
            stack.set(RtTPSComponents.USENUMBER_TYPE,0);
            try {
                //增加玩家的复活次数
                VarHandle playerHandle = MethodHandles.lookup().findVarHandle(PlayerEntity.class, "respawnCount", int.class);
                playerHandle.set(serverplayer,(int)playerHandle.get(serverplayer)+1);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
