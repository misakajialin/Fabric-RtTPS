package xyz.lisbammisakait.skill;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import xyz.lisbammisakait.RelightTheThreePointStrategy;

import java.util.List;

public class LiuBeiBSkill extends Item implements ActiveSkillable {
    private final int WITHER_EFFECT_DURATION = 3;
    private final int WITHER_EFFECT_AMPLIFIER = 3;
    private final int STRENGTH_EFFECT_DURATION = 15;
    private final int STRENGTH_EFFECT_AMPLIFIER = 0;
    public static final int COOLDOWN = 30;

    public LiuBeiBSkill(Settings settings) {
        super(settings);
    }

    @Override
    public void castSkill(MinecraftClient client, ItemStack stack) {
        ClientPlayerEntity user = client.player;
        if (user.getItemCooldownManager().isCoolingDown(stack)) {
            // 如果物品正在冷却中，直接返回
            return ;
        }
        RelightTheThreePointStrategy.LOGGER.info("给自己添加凋零与力量效果");
        user.addStatusEffect(new StatusEffectInstance(StatusEffects.WITHER, WITHER_EFFECT_DURATION*20, WITHER_EFFECT_AMPLIFIER));
        user.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, STRENGTH_EFFECT_DURATION*20, STRENGTH_EFFECT_AMPLIFIER));
        user.getItemCooldownManager().set(stack, COOLDOWN * 20);
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.translatable("skill.relight-the-three-point-strategy.xiangxieyvgong"));
    }
}
