package xyz.lisbammisakait.skill;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.boss.dragon.phase.StrafePlayerPhase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import xyz.lisbammisakait.RelightTheThreePointStrategy;
import xyz.lisbammisakait.item.FeilongduofengItem;

import java.util.List;

public class YinFengLaiXiangSkill extends Item implements Skillable{
    public YinFengLaiXiangSkill(Settings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.translatable("skill.relight-the-three-point-strategy.yinfenglaixiang", FeilongduofengItem.PROBABILITY,FeilongduofengItem.FIRETIME));
    }

    @Override
    public void useSkill(MinecraftClient client) {
        RelightTheThreePointStrategy.LOGGER.info("使用了引风来翔技能");
    }
}
