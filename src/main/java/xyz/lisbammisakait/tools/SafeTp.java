package xyz.lisbammisakait.tools;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.packet.s2c.play.PositionFlag;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import xyz.lisbammisakait.RelightTheThreePointStrategy;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class SafeTp {
    //定义例外方块
    private static final Set<Block> ADDITIONAL_PASSABLE_BLOCKS = new HashSet<>(Arrays.asList(
            Blocks.LIGHT
    ));

    private static boolean isSafeLocation(BlockView world, BlockPos pos) {
        return isPassable(world, pos) && isPassable(world, pos.up());
    }

    private static boolean isPassable(BlockView world, BlockPos pos) {
        BlockState blockState = world.getBlockState(pos);
        Block block = blockState.getBlock();
//        if (blockState.isAir()) {
//            return true;
//        }
        // 检查方块是否在额外可通过方块集合中
        if (ADDITIONAL_PASSABLE_BLOCKS.contains(block)) {
            return true;
        }
        // 检查方块是否为非实心方块
        return !blockState.isSolidBlock(world, pos);
    }
    public static boolean safeTp(LivingEntity TpMan, ServerWorld world, double destX, double destY, double destZ, Set<PositionFlag> flags, float yaw, float pitch, boolean resetCamera) {
        if(!isSafeLocation(world, new BlockPos((int) destX, (int) destY, (int) destZ))){
            RelightTheThreePointStrategy.LOGGER.info("传送失败，目标位置不安全");
            return false;
        }
        return TpMan.teleport(world, destX, destY, destZ, flags, yaw, pitch, resetCamera);
    }
}
