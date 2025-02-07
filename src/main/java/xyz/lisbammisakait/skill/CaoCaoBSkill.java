package xyz.lisbammisakait.skill;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.CustomModelDataComponent;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import xyz.lisbammisakait.RelightTheThreePointStrategy;
import xyz.lisbammisakait.compoennt.RtTPSComponents;
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
    public void processPracticalSkill(MinecraftServer server, ServerPlayerEntity player, ItemStack stack) {
        //获取是否使用
        boolean isExhausted = stack.getOrDefault(RtTPSComponents.LIMITEDSKILLEXHAUSTED_TYPE,true);
        if (isExhausted) {
            player.sendMessage(Text.of("该技能暂未解锁"), true);
            return;
        }
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, SPEED_EFFECT_DURATION * 20, SPEED_EFFECT_AMPLIFIER));
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, ABSORPTION_EFFECT_DURATION * 20, ABSORPTION_EFFECT_AMPLIFIER));
        stack.set(RtTPSComponents.LIMITEDSKILLEXHAUSTED_TYPE, true);
        //改变纹理为不可发动
        stack.set(DataComponentTypes.CUSTOM_MODEL_DATA,new CustomModelDataComponent(List.of(), List.of(false),List.of(),List.of()));
    }
}
