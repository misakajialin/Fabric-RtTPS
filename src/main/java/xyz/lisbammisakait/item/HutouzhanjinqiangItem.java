package xyz.lisbammisakait.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.network.packet.s2c.play.PositionFlag;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import xyz.lisbammisakait.RelightTheThreePointStrategy;
import xyz.lisbammisakait.compoennt.RtTPSComponents;
import xyz.lisbammisakait.tools.SafeTp;

import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.List;
import java.util.Set;



public class HutouzhanjinqiangItem extends SwordItem {
    public static final int COOLDOWN = 30;
    public HutouzhanjinqiangItem(ToolMaterial material, float attackDamage, float attackSpeed, Settings settings) {
        super(material, attackDamage, attackSpeed, settings);
    }
    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.translatable("itemskill.relight-the-three-point-strategy.hutouzhanjinqiang",COOLDOWN).formatted(Formatting.GOLD));
    }
    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        // 计算从攻击者指向被攻击者的方向向量
        Vec3d direction = target.getPos().subtract(attacker.getPos());
        // 对方向向量进行归一化，得到单位方向向量
        Vec3d normalizedDirection = direction.normalize();

        // 计算三格的偏移量
        Vec3d offset = normalizedDirection.multiply(3);

        // 获取攻击者当前的位置
        Vec3d currentPosition = attacker.getPos();
        // 计算目标位置，即当前位置加上偏移量
        Vec3d targetPosition = currentPosition.add(offset);

        // 获取攻击者所在的世界
        ServerWorld world = (ServerWorld) attacker.getWorld();

        // 这里简单使用空的标志集合，你可以根据需求修改
        Set<PositionFlag> flags = Collections.emptySet();

        // 计算攻击者面对被攻击者的偏航角和俯仰角
        double dx = target.getX() - attacker.getX();
        double dz = target.getZ() - attacker.getZ();
        double dy = target.getY() - attacker.getY();

        float yaw = (float) (Math.atan2(dz, dx) * 180 / Math.PI) - 90;
        float pitch = (float) -(Math.atan2(dy, Math.sqrt(dx * dx + dz * dz)) * 180 / Math.PI);

        // 不重置相机
        boolean resetCamera = false;
        // 调用 teleport 方法将攻击者传送到目标位置，并设置朝向
//        attacker.teleport(world, targetPosition.getX(), targetPosition.getY(), targetPosition.getZ(), flags, yaw, pitch, resetCamera);
        SafeTp.safeTp(attacker, world, targetPosition.getX(), targetPosition.getY(), targetPosition.getZ(), flags, yaw, pitch, resetCamera);
        RelightTheThreePointStrategy.LOGGER.info("攻击者传送到目标位置");
        return super.postHit(stack, target, attacker);
    }
//    @Override
//    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
//        if (stack.contains(RtTPSComponents.COOLDOWN_TYPE)) {
//            //int rct = stack.get(RtTPSComponents.COOLDOWN_TYPE);
//            int rct = stack.getOrDefault(RtTPSComponents.COOLDOWN_TYPE, COOLDOWN);
//            tooltip.add(Text.translatable("item.relight-the-three-point-strategy.hutouzhanjinqiang.remaining-cooldown-time", rct).formatted(Formatting.GOLD));
//        }
//    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
       ItemStack stack = user.getStackInHand(hand);
//         Don't do anything on the client
        if (world.isClient()) {
            return ActionResult.SUCCESS;
        }

        // 获取物品冷却管理器
        if (user.getItemCooldownManager().isCoolingDown(stack)) {
            // 如果物品正在冷却中，返回失败
            return ActionResult.FAIL;
        }

        if (user != null) {
            // 创建一个新的物品栈
            ItemStack newItemStack = new ItemStack(ModItems.SHENWEIHUTOUZHANJINQIANG, 1);
            // 将主手物品更换为新的物品栈
            user.getInventory().main.set(user.getInventory().selectedSlot, newItemStack);

            // 设置物品进入冷却状态
            user.getItemCooldownManager().set(stack, COOLDOWN * 20); // 注意：冷却时间单位是游戏刻，1 秒 = 20 游戏刻

            return ActionResult.SUCCESS;
        }
        return ActionResult.FAIL;
    }
}
