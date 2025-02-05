package xyz.lisbammisakait.skill;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import xyz.lisbammisakait.item.FeilongduofengItem;

import java.util.List;

public class YinFengLaiXiangSkill  extends Item implements PassiveSkillable{
    public YinFengLaiXiangSkill(Settings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        addCustomTooltip(tooltip);
    }

    @Override
    public void addCustomTooltip(List<Text> tooltip) {
        tooltip.add(Text.translatable("skill.relight-the-three-point-strategy.yinfenglaixiang", FeilongduofengItem.PROBABILITY,FeilongduofengItem.FIRETIME));
    }
}
