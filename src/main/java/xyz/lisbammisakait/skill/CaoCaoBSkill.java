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
import xyz.lisbammisakait.item.ModItems;

import java.util.List;

public class CaoCaoBSkill extends Item implements ActiveSkillable {
    private final int SPEED_EFFECT_DURATION = 3;
    private final int ABSORPTION_EFFECT_DURATION = 3;
    private final int SPEED_EFFECT_AMPLIFIER = 2;
    private final int ABSORPTION_EFFECT_AMPLIFIER = 1;

    public CaoCaoBSkill(Settings settings) {
        super(settings);
    }

    @Override
    public void castSkill(MinecraftServer server, ServerPlayerEntity player, ItemStack stack) {
        RelightTheThreePointStrategy.LOGGER.info("给自己添加速度与伤害吸收效果");
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, SPEED_EFFECT_DURATION * 20, SPEED_EFFECT_AMPLIFIER));
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, ABSORPTION_EFFECT_DURATION * 20, ABSORPTION_EFFECT_AMPLIFIER));
        // 创建一个新的物品栈
        ItemStack newItemStack = new ItemStack(ModItems.UNLAUNCHABLE, 1);
        // 将技能B位置更换为新的物品栈
        player.getInventory().main.set(7, newItemStack);

    }
}
