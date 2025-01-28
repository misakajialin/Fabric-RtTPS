package xyz.lisbammisakait.skill;

import net.minecraft.client.MinecraftClient;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import xyz.lisbammisakait.RelightTheThreePointStrategy;

import java.util.List;

public class TieJiTaChuanSkill extends Item implements Skillable {
    public TieJiTaChuanSkill(Settings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.translatable("skill.relight-the-three-point-strategy.tiejitachuan").formatted(Formatting.GOLD));
    }

    @Override
    public void useSkill(MinecraftClient client) {
        RelightTheThreePointStrategy.LOGGER.info("使用了铁骑踏穿技能");
    }
}
