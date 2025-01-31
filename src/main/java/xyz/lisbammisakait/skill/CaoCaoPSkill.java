package xyz.lisbammisakait.skill;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import xyz.lisbammisakait.item.CanghaitulongfuItem;

import java.util.List;

public class CaoCaoPSkill extends Item {
    public CaoCaoPSkill(Settings settings) {
        super(settings);
    }
    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.translatable("skill.relight-the-three-point-strategy.caocaopskill", CanghaitulongfuItem.HITNUMBER));
    }
}
