package xyz.lisbammisakait.item;

import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;

public class FeilongduofengItem extends SwordItem {
    public FeilongduofengItem(ToolMaterial material, float attackDamage, float attackSpeed, Settings settings) {
        super(material, attackDamage, attackSpeed, settings);
    }
    @Override
    public boolean postHit(net.minecraft.item.ItemStack stack, net.minecraft.entity.LivingEntity target, net.minecraft.entity.LivingEntity attacker) {
        target.setFireTicks(100);
        return super.postHit(stack, target, attacker);
    }
}
