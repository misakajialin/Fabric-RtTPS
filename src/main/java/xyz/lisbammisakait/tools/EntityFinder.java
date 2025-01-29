package xyz.lisbammisakait.tools;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import xyz.lisbammisakait.RelightTheThreePointStrategy;

import java.util.List;

public class EntityFinder {

    /**
     * 获取指定玩家附近指定范围内的特定类型实体列表
     * @param player 中心玩家
     * @param world 所在的服务器世界
     * @param range 检测范围
     * @param entityClass 要查找的实体类型
     * @param <T> 实体类型的泛型
     * @return 符合条件的实体列表
     */
    public <T extends LivingEntity> List<T> getNearbyEntities(PlayerEntity player, ServerWorld world, int range, Class<T> entityClass) {
        Vec3d pos = player.getPos();
        // 创建一个以玩家为中心的检测范围盒子
        Box box = new Box(
                pos.getX() - range, pos.getY() - range, pos.getZ() - range,
                pos.getX() + range, pos.getY() + range, pos.getZ() + range
        );
        // 获取范围内的所有指定类型的存活实体
        List<T> nearbyEntities = world.getEntitiesByClass(entityClass, box, LivingEntity::isAlive);
        RelightTheThreePointStrategy.LOGGER.info(nearbyEntities.toString());
        return nearbyEntities;
    }
//     使用示例：
//     获取附近的玩家
//    List<PlayerEntity> nearbyPlayers = entityFinder.getNearbyEntities(player, world, range, PlayerEntity.class);
//
//     获取附近的所有生物实体
//    List<LivingEntity> nearbyLivingEntities = entityFinder.getNearbyEntities(player, world, range, LivingEntity.class);
//
//     获取附近的特定生物实体，例如僵尸
//    List<net.minecraft.entity.mob.ZombieEntity> nearbyZombies = entityFinder.getNearbyEntities(player, world, range, net.minecraft.entity.mob.ZombieEntity.class);
}
