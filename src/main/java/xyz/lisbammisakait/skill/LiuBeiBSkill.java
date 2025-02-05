package xyz.lisbammisakait.skill;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import xyz.lisbammisakait.RelightTheThreePointStrategy;

import java.util.List;

public class LiuBeiBSkill extends Item implements ActiveSkillable {
    private final int WITHER_EFFECT_DURATION = 3;
    private final int WITHER_EFFECT_AMPLIFIER = 3;
    private final int STRENGTH_EFFECT_DURATION = 15;
    private final int STRENGTH_EFFECT_AMPLIFIER = 0;
    private final int COOLDOWN = 30;

    public LiuBeiBSkill(Settings settings) {
        super(settings);
    }

    @Override
    public void processPracticalSkill(MinecraftServer server, ServerPlayerEntity player, ItemStack stack) {
        if (player.getItemCooldownManager().isCoolingDown(stack)) {
            // 如果物品正在冷却中，直接返回
            return;
        }
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.WITHER, WITHER_EFFECT_DURATION * 20, WITHER_EFFECT_AMPLIFIER));
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, STRENGTH_EFFECT_DURATION * 20, STRENGTH_EFFECT_AMPLIFIER));
        player.getItemCooldownManager().set(stack, COOLDOWN * 20);
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.translatable("skill.relight-the-three-point-strategy.longnudiwei"));
    }
}
