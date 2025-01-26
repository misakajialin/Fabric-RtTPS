package xyz.lisbammisakait.item;


import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import xyz.lisbammisakait.RelightTheThreePointStrategy;

public class FeilongduofengItem extends SwordItem {
    public FeilongduofengItem(ToolMaterial material, float attackDamage, float attackSpeed, Settings settings) {
        super(material, attackDamage, attackSpeed, settings);
    }
    @Override
    public boolean postHit(net.minecraft.item.ItemStack stack, net.minecraft.entity.LivingEntity target, net.minecraft.entity.LivingEntity attacker) {
        target.setFireTicks(100);
        double motionX = attacker.getRotationVector().x;
        double motionZ = attacker.getRotationVector().z;

        // 计算击退强度，这里假设一个简单的强度值
        double knockbackStrength = 0.4;

        // 给目标添加击退效果
        target.addVelocity(-motionX * knockbackStrength, 0.2, -motionZ * knockbackStrength);
        return super.postHit(stack, target, attacker);
    }

}
