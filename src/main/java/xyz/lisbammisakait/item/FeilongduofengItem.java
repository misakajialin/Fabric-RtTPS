package xyz.lisbammisakait.item;


import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;

import java.util.Random;

public class FeilongduofengItem extends SwordItem {
    public static final int PROBABILITY = 20;
    public static final int FIRETIME = 2;
    public FeilongduofengItem(ToolMaterial material, float attackDamage, float attackSpeed, Settings settings) {
        super(material, attackDamage, attackSpeed, settings);
    }
    //不掉耐久
    @Override
    public void postDamageEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.damage(0, attacker, EquipmentSlot.MAINHAND);
    }
    @Override
    public boolean postHit(net.minecraft.item.ItemStack stack, net.minecraft.entity.LivingEntity target, net.minecraft.entity.LivingEntity attacker) {
        Random random = new Random();
        // 生成一个 0 到 9 之间的随机整数
        int randomNumber = random.nextInt(10);
        // 判断随机数是否为 0或1
        if (randomNumber<(PROBABILITY/10)) {
            // 给目标添加火焰效果
            target.setFireTicks(FIRETIME*20);
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
