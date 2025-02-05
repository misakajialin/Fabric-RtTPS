package xyz.lisbammisakait.skill;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.CustomModelDataComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import xyz.lisbammisakait.compoennt.RtTPSComponents;
import xyz.lisbammisakait.item.HutouzhanjinqiangItem;

import java.util.List;

public class HorseSuperASkill extends Item implements ActiveSkillable {
    public HorseSuperASkill(Settings settings) {
        super(settings);
    }

    @Override
    public void processPracticalSkill(MinecraftServer server, ServerPlayerEntity player, ItemStack stack) {
        stack.set(DataComponentTypes.CUSTOM_MODEL_DATA,new CustomModelDataComponent(List.of(), List.of(false),List.of(),List.of()));
        boolean isExhausted = stack.getOrDefault(RtTPSComponents.LIMITEDSKILLEXHAUSTED_TYPE,true);
        if (isExhausted) {
            player.sendMessage(Text.of("你已经使用过该技能"), true);
            return;
        }
        HutouzhanjinqiangItem.recordSkillUseTime(player, server);
        stack.set(RtTPSComponents.LIMITEDSKILLEXHAUSTED_TYPE, true);
    }
}
