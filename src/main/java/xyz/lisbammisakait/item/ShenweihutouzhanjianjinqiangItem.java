package xyz.lisbammisakait.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import xyz.lisbammisakait.tools.EntityFinder;

import java.util.List;

public class ShenweihutouzhanjianjinqiangItem extends HutouzhanjinqiangItem {
    private final int RANGE = 4;
    public ShenweihutouzhanjianjinqiangItem(ToolMaterial material, float attackDamage, float attackSpeed, Settings settings) {
        super(material, attackDamage, attackSpeed, settings);
    }
    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        return ActionResult.SUCCESS;
    }
    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (attacker.getWorld().isClient()) {
            return true;
        }
        PlayerEntity user = (PlayerEntity) attacker;
        // 创建一个新的物品栈
        ItemStack newItemStack = new ItemStack(ModItems.HUTOUZHANJINQIANG, 1);
        // 将主手物品更换为新的物品栈
        user.getInventory().main.set(user.getInventory().selectedSlot, newItemStack);

        long currentTime = attacker.getWorld().getTime();
        // 检查玩家是否在技能生效时间内
        if (SKILL_USE_TIME_MAP.containsKey(attacker) && currentTime - SKILL_USE_TIME_MAP.get(attacker) <= SKILL_DURATION*20) {
            // 击飞敌人
            EntityFinder entityFinder = new EntityFinder();
            List<LivingEntity> nearbyEntities = entityFinder.getNearbyEntities(user,(ServerWorld) attacker.getWorld(), RANGE,LivingEntity.class);
            for (LivingEntity nearbyEntity : nearbyEntities) {
                if (!nearbyEntity.equals(user)||!nearbyEntity.equals(target)) {
                    knockup(nearbyEntity, attacker);
                }
            }
        }else {
            attacker.getServer().sendMessage(Text.of("技能未生效"));
        }
        return super.postHit(stack, target, attacker);
    }
    @Override
    protected void knockup(LivingEntity target, LivingEntity attacker) {

        target.addVelocity(0, 1, 0);
        // 设置目标的速度已经被修改
        target.velocityModified = true;
    }
    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        //tooltip.add(Text.translatable("itemskill.relight-the-three-point-strategy.shenweihutouzhanjinqiang").formatted(Formatting.GOLD));
    }
}
