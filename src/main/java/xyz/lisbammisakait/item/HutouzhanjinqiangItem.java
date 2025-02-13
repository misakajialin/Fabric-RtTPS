package xyz.lisbammisakait.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.network.packet.s2c.play.PositionFlag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import xyz.lisbammisakait.RelightTheThreePointStrategy;
import xyz.lisbammisakait.tools.SafeTp;

import java.util.*;


public class HutouzhanjinqiangItem extends RtTPSSwordItem {
    public final int COOLDOWN = 30;
    public final int MAX_HEALTH = 60;
    // 存储玩家使用技能的时间
    protected static final Map<PlayerEntity, Long> SKILL_USE_TIME_MAP = new HashMap<>();
    // 技能持续时间,单位秒
    protected final int SKILL_DURATION = 80 ;

    public HutouzhanjinqiangItem(ToolMaterial material, float attackDamage, float attackSpeed, Settings settings) {
        super(material, attackDamage, attackSpeed, settings);
    }
    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.translatable("itemskill.relight-the-three-point-strategy.hutouzhanjinqiang",COOLDOWN).formatted(Formatting.GOLD));

    }

    @Override
    public void specialAbility(ItemStack stack, LivingEntity target, LivingEntity attacker) {
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

        //击飞敌人
        long currentTime = world.getTime();
        RelightTheThreePointStrategy.LOGGER.info("玩家使用攻击的时间：" + currentTime);
        // 检查玩家是否在技能生效时间内
        if (SKILL_USE_TIME_MAP.containsKey(attacker) && currentTime - SKILL_USE_TIME_MAP.get(attacker) <= SKILL_DURATION*20) {
            // 击飞敌人
            knockup(target, attacker);
        }else {
            attacker.getServer().sendMessage(Text.of("技能未生效"));
        }
    }
    public static void recordSkillUseTime(PlayerEntity player, MinecraftServer server) {
        // 获取当前时间
        long currentTime = server.getWorld(player.getEntityWorld().getRegistryKey()).getTime();
        SKILL_USE_TIME_MAP.put(player, currentTime);
    }
    protected void knockup(LivingEntity target, LivingEntity attacker) {
//        Vec3d attackerPos = attacker.getPos();
//        Vec3d targetPos = target.getPos();
//        Vec3d knockbackVector = targetPos.subtract(attackerPos).normalize().multiply(0.5);
//        target.addVelocity(knockbackVector.x, 0.2, knockbackVector.z);
        target.addVelocity(0, 1, 0);
        // 设置目标的速度已经被修改
        target.velocityModified = true;
    }

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
            // 创建一个新的物品栈
            ItemStack newItemStack = new ItemStack(ModItems.SHENWEIHUTOUZHANJINQIANG, 1);
            // 将主手物品更换为新的物品栈
            user.getInventory().main.set(user.getInventory().selectedSlot, newItemStack);
            // 设置物品进入冷却状态,注意：冷却时间单位是游戏刻，1 秒 = 20 游戏刻
            user.getItemCooldownManager().set(stack, COOLDOWN * 20);
            return ActionResult.SUCCESS;

    }
    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected){
        if(world.isClient()){
            return;
        }
        PlayerEntity player = (PlayerEntity) entity;
        ItemStack mainHandStack = player.getMainHandStack();
        // 如果玩家的主手拿着[神威]虎头湛金枪
        if (mainHandStack == stack) {
            // 为玩家添加加速效果
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 2, 1));
            long currentTime = world.getTime();
            if (SKILL_USE_TIME_MAP.containsKey(player) && currentTime - SKILL_USE_TIME_MAP.get(player) <= SKILL_DURATION*20) {
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 2, 2));
            }
        }
        boostMaxHealth(player, MAX_HEALTH);
    }
}
