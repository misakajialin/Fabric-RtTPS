package xyz.lisbammisakait.skill;

import net.minecraft.entity.damage.DamageSources;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.DrownedEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.s2c.play.PositionFlag;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import xyz.lisbammisakait.RelightTheThreePointStrategy;
import xyz.lisbammisakait.compoennt.RtTPSComponents;
import xyz.lisbammisakait.item.HutouzhanjinqiangItem;
import xyz.lisbammisakait.item.ModItems;
import xyz.lisbammisakait.tools.SafeTp;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.LivingEntity;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import static xyz.lisbammisakait.skill.MarkItem.markSlot;

public class SunJianASkill extends Item implements ActiveSkillable {
    public final int SLOWNESSTIME = 4;
    public SunJianASkill(Settings settings) {
        super(settings);
    }

    @Override
    public void processPracticalSkill(MinecraftServer server, ServerPlayerEntity serverplayer, ItemStack stack) {
//        // 创建一个新的物品栈
//        ItemStack newItemStack = new ItemStack(ModItems.UNLAUNCHABLE, 1);
//        // 将技能B位置更换为新的物品栈
//        serverplayer.getInventory().main.set(8, newItemStack);
        boolean isExhausted = stack.getOrDefault(RtTPSComponents.LIMITEDSKILLEXHAUSTED_TYPE,true);
        if (isExhausted) {
            serverplayer.sendMessage(Text.of("你已经使用过该技能"), true);
            return;
        }
        stack.set(RtTPSComponents.LIMITEDSKILLEXHAUSTED_TYPE, true);

        // 获取服务器中的所有玩家列表
        List<ServerPlayerEntity> playerList = server.getPlayerManager().getPlayerList();
        // 移除当前玩家自身
        playerList.remove(serverplayer);
        //移出正常复活的玩家
        //交给家霖

        if (!playerList.isEmpty()) {
            // 随机选择一名其他玩家
            Collections.shuffle(playerList);
            ServerPlayerEntity targetPlayer = playerList.get(0);

            // 获取目标玩家的位置
            Vec3d targetPosition = targetPlayer.getPos();
            ServerWorld targetWorld = (ServerWorld) targetPlayer.getWorld();

            // 传送当前玩家到目标玩家的位置
            Set<PositionFlag> flags = Collections.emptySet();
            float yaw = serverplayer.getYaw();
            float pitch = serverplayer.getPitch();
            boolean resetCamera = false;
            SafeTp.safeTp(serverplayer,targetWorld, targetPosition.getX(), targetPosition.getY() + 5, targetPosition.getZ(),flags, yaw, pitch, resetCamera);
            RelightTheThreePointStrategy.LOGGER.info(serverplayer.getName().getString() + " 传送到了 " + targetPlayer.getName().getString() + " 的位置");

            //赋予减速
            targetPlayer.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, SLOWNESSTIME * 20, 3));

            //检测二段伤害
            if(isMarkInSlot(serverplayer))//检测物品栏
            {
                if (serverplayer.getInventory().getStack(markSlot).get(RtTPSComponents.REMAININGRESPAWNCOUNT_TYPE) <= 2)
                {
                    targetPlayer.addStatusEffect(new StatusEffectInstance(StatusEffects.INSTANT_DAMAGE, 1, 3));
                    /*ServerWorld world = (ServerWorld) serverplayer.getWorld();
                    DamageSource damageSource = new DamageSource(, serverplayer);
                    targetPlayer.damage(world, damageSource,40);*/
                    ServerWorld world = (ServerWorld) serverplayer.getWorld();
                    DamageSource damageSource = new DamageSource(serverplayer.getRegistryManager()
                            .getOrThrow(RegistryKeys.DAMAGE_TYPE)
                            .getEntry(new DamageType("player",0.0F)));
                    targetPlayer.damage(world, damageSource,40F);
                }
            }
        }
    }

    //检查玩家快捷栏第5格是否为mark
    public static boolean isMarkInSlot(PlayerEntity player) {
        // 获取玩家的快捷栏物品栈
        ItemStack itemStack = player.getInventory().getStack(markSlot);
        // 检查物品栈是否为空以及物品是否为mark
        return !itemStack.isEmpty() && itemStack.getItem() == ModItems.MARK;
    }
}