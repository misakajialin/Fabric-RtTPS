package xyz.lisbammisakait.skill;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.network.packet.s2c.play.PositionFlag;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import xyz.lisbammisakait.RelightTheThreePointStrategy;
import xyz.lisbammisakait.item.ModItems;
import xyz.lisbammisakait.tools.SafeTp;

import java.util.Collections;
import java.util.List;
import java.util.Set;

public class CaoCaoASkill extends Item implements ActiveSkillable {
    public static final int DISTANCE = 8;
    public static final int EFFECT_AMPLIFIER = 1;
    private final int COOLDOWN = 50;

    public CaoCaoASkill(Settings settings) {
        super(settings);
    }

    @Override
    public void processPracticalSkill(MinecraftServer server, ServerPlayerEntity serverplayer, ItemStack stack) {
        teleportPlayersTowardsTarget(serverplayer, server.getWorld(serverplayer.getWorld().getRegistryKey()), DISTANCE);
        if (hasOtherPlayersNearby(serverplayer)) {
            serverplayer.addStatusEffect(new StatusEffectInstance(StatusEffects.INSTANT_HEALTH, 1, EFFECT_AMPLIFIER));
        }
        serverplayer.getItemCooldownManager().set(stack, COOLDOWN * 20);
    }

    public static void teleportPlayersTowardsTarget(PlayerEntity targetPlayer, ServerWorld world, double distance) {
        // 获取指定玩家的位置
        Vec3d targetPos = targetPlayer.getPos();

        // 获取范围内的所有玩家
        Box box = new Box(
                targetPos.getX() - 100, targetPos.getY() - 100, targetPos.getZ() - 100,
                targetPos.getX() + 100, targetPos.getY() + 100, targetPos.getZ() + 100
        );
        List<PlayerEntity> allPlayers = world.getEntitiesByClass(PlayerEntity.class, box, PlayerEntity::isAlive);

        // 遍历所有玩家
        for (PlayerEntity player : allPlayers) {

            if (!player.equals(targetPlayer)) {
                // 计算当前玩家与指定玩家的距离
                double currentDistance = player.getPos().distanceTo(targetPos);

                if (currentDistance <= DISTANCE) {
                    // 8格内的玩家，传送到指定玩家面前
                    Vec3d direction = targetPos.subtract(player.getPos());
                    // 这里假设拉到面前的距离为1格，你可以根据实际需求调整
                    Vec3d offset = direction.normalize();
                    Vec3d targetPosition = targetPos.add(offset.multiply(-1));

                    // 这里简单使用空的标志集合，你可以根据需求修改
                    Set<PositionFlag> flags = Collections.emptySet();

                    // 计算当前玩家面对指定玩家的偏航角和俯仰角
                    double dx = targetPlayer.getX() - player.getX();
                    double dz = targetPlayer.getZ() - player.getZ();
                    double dy = targetPlayer.getY() - player.getY();

                    float yaw = (float) (Math.atan2(dz, dx) * 180 / Math.PI) - 90;
                    float pitch = (float) -(Math.atan2(dy, Math.sqrt(dx * dx + dz * dz)) * 180 / Math.PI);

                    // 不重置相机
                    boolean resetCamera = false;

                    // 调用 teleport 方法将当前玩家传送到目标位置，并设置朝向
                    player.teleport(world, targetPosition.getX(), targetPosition.getY(), targetPosition.getZ(), flags, yaw, pitch, resetCamera);
                } else {
                    // 8格外的玩家，朝指定玩家传送8格
                    // 计算从当前玩家指向指定玩家的方向向量
                    Vec3d direction = targetPos.subtract(player.getPos());
                    // 对方向向量进行归一化，得到单位方向向量
                    Vec3d normalizedDirection = direction.normalize();
                    // 计算偏移量
                    Vec3d offset = normalizedDirection.multiply(distance);
                    // 计算目标位置，即当前位置加上偏移量
                    Vec3d targetPosition = player.getPos().add(offset);

                    // 这里简单使用空的标志集合，你可以根据需求修改
                    Set<PositionFlag> flags = Collections.emptySet();

                    // 计算当前玩家面对指定玩家的偏航角和俯仰角
                    double dx = targetPlayer.getX() - player.getX();
                    double dz = targetPlayer.getZ() - player.getZ();
                    double dy = targetPlayer.getY() - player.getY();

                    float yaw = (float) (Math.atan2(dz, dx) * 180 / Math.PI) - 90;
                    float pitch = (float) -(Math.atan2(dy, Math.sqrt(dx * dx + dz * dz)) * 180 / Math.PI);

                    // 不重置相机
                    boolean resetCamera = false;
                    SafeTp.safeTp(player,world, targetPosition.getX(), targetPosition.getY(), targetPosition.getZ(), flags, yaw, pitch, resetCamera);
                    // 调用 teleport 方法将当前玩家传送到目标位置，并设置朝向
//                    player.teleport(world, targetPosition.getX(), targetPosition.getY(), targetPosition.getZ(), flags, yaw, pitch, resetCamera);
                }
            }
        }
    }

    /**
     * 检测玩家周围5格内是否有其他玩家
     * @param player 要检测的玩家
     * @return 是否有其他玩家在周围5格内
     */
    public static boolean hasOtherPlayersNearby(PlayerEntity player) {
        if (!(player.getWorld() instanceof ServerWorld world)) {
            return false;
        }
//        ServerWorld world = (ServerWorld) player.getWorld();
        // 获取玩家位置
        Vec3d playerPos = player.getPos();
        // 定义检测范围（以玩家为中心，周围5格）
        Box searchBox = new Box(
                playerPos.getX() - 5,
                playerPos.getY() - 5,
                playerPos.getZ() - 5,
                playerPos.getX() + 5,
                playerPos.getY() + 5,
                playerPos.getZ() + 5
        );
        // 获取范围内的所有玩家
        List<PlayerEntity> nearbyPlayers = world.getEntitiesByClass(PlayerEntity.class, searchBox, p -> p != player);
        // 判断是否有其他玩家
        return!nearbyPlayers.isEmpty();
    }
}