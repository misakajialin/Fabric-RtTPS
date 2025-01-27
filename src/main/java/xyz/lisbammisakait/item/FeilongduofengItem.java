package xyz.lisbammisakait.item;


import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import xyz.lisbammisakait.RelightTheThreePointStrategy;

import java.util.List;
import java.util.Random;

public class FeilongduofengItem extends SwordItem {
    public FeilongduofengItem(ToolMaterial material, float attackDamage, float attackSpeed, Settings settings) {
        super(material, attackDamage, attackSpeed, settings);
    }
    @Override
    public boolean postHit(net.minecraft.item.ItemStack stack, net.minecraft.entity.LivingEntity target, net.minecraft.entity.LivingEntity attacker) {
        Random random = new Random();
        // 生成一个 0 到 9 之间的随机整数
        int randomNumber = random.nextInt(10);
        // 判断随机数是否为 0
        if (randomNumber<10) {
            // 给目标添加火焰效果
            target.setFireTicks(60);
            double motionX = attacker.getRotationVector().x;
            double motionZ = attacker.getRotationVector().z;
            // 计算击退强度，这里假设一个简单的强度值
            double knockbackStrength = 1.5;
            // 给目标添加击退效果
           //target.addVelocity(motionX * knockbackStrength, 0.5, motionZ * knockbackStrength);
            target.takeKnockback(knockbackStrength, -motionX, -motionZ);
        }

        return super.postHit(stack, target, attacker);
    }

}
