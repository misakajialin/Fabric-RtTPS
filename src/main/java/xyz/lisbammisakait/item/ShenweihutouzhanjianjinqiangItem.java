package xyz.lisbammisakait.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

import java.util.List;

public class ShenweihutouzhanjianjinqiangItem extends HutouzhanjinqiangItem {
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
        // 创建一个新的物品栈，这里以钻石为例
        ItemStack newItemStack = new ItemStack(ModItems.HUTOUZHANJINQIANG, 1);
        // 将主手物品更换为新的物品栈
        user.getInventory().main.set(user.getInventory().selectedSlot, newItemStack);

        return super.postHit(stack, target, attacker);
    }
    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.translatable("itemskill.relight-the-three-point-strategy.shenweihutouzhanjinqiang").formatted(Formatting.GOLD));
    }
}
