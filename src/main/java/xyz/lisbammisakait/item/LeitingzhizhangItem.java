package xyz.lisbammisakait.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import xyz.lisbammisakait.compoennt.RtTPSComponents;
import xyz.lisbammisakait.mixininterface.RemainingCooldownGetter;
import xyz.lisbammisakait.skill.ZhangJiaoASKill;

import java.util.Random;

public class LeitingzhizhangItem extends  RtTPSSwordItem {
    private final int SPEED_DURATION = 5;
    private final int SPEED_AMPLIFIER = 1;
    public static int PROBABILITY = 50;
    public static final float COOLDOWN_REDUCTION = 7.0F;
    public static final int HITNUMBER = 3;
    public LeitingzhizhangItem(ToolMaterial material, float attackDamage, float attackSpeed, Settings settings) {
        super(material, attackDamage, attackSpeed, settings);
    }
    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker){
        stack.set(RtTPSComponents.HITNUMBER_TYPE,stack.getOrDefault(RtTPSComponents.HITNUMBER_TYPE,0)+1);
        if(stack.getOrDefault(RtTPSComponents.HITNUMBER_TYPE, 0) == HITNUMBER){
            //设置使用次数为0
            stack.set(RtTPSComponents.HITNUMBER_TYPE,0);
            //给攻击者添加速度效果
            //attacker.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, SPEED_DURATION*20, SPEED_AMPLIFIER));

            PlayerEntity player = (PlayerEntity) attacker;
            ItemStack skillstack = player.getInventory().getStack(7);
            RemainingCooldownGetter itemCooldownManager = (RemainingCooldownGetter) player.getItemCooldownManager();
            float cdr =  (float) itemCooldownManager.getRemainingCooldown(skillstack) /20;
//            RelightTheThreePointStrategy.LOGGER.info("剩余冷却时间："+cdr);
            if(cdr-COOLDOWN_REDUCTION>0){
                player.getItemCooldownManager().set(skillstack, Math.round (cdr-COOLDOWN_REDUCTION)*20);
//               float cdr3 =  player.getItemCooldownManager().getRemainingCooldown(skillstack);
//               RelightTheThreePointStrategy.LOGGER.info("剩余冷却时间："+cdr3/20);
//                player.sendMessage(Text.of("剩余冷却时间："+cdr3), true);
            }else {
                player.getItemCooldownManager().set(skillstack, 0);
            }
            Random random = new Random();
            // 生成一个 0 到 9 之间的随机整数
            int randomNumber = random.nextInt(10);

            if (randomNumber<(PROBABILITY/10)) {
                ZhangJiaoASKill skill = (ZhangJiaoASKill) skillstack.getItem();
                skill.spawnLightningBolt(attacker.getServer(),stack, (ServerPlayerEntity) player);
            }
        }
        return super.postHit(stack, target, attacker);
    }



    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        return ActionResult.SUCCESS;
    }
}
