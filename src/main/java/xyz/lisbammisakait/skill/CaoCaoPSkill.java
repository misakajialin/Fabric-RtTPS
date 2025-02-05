package xyz.lisbammisakait.skill;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import xyz.lisbammisakait.item.CanghaitulongfuItem;

import java.util.List;

public class CaoCaoPSkill extends Item implements PassiveSkillable{
    public CaoCaoPSkill(Settings settings) {
        super(settings);
    }
    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        addCustomTooltip(tooltip);
    }

    @Override
    public void addCustomTooltip(List<Text> tooltip) {
        tooltip.add(Text.translatable("skill.relight-the-three-point-strategy.caocaopskill", CanghaitulongfuItem.HITNUMBER));
    }
}
