package xyz.lisbammisakait.skill;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import xyz.lisbammisakait.compoennt.RtTPSComponents;
import xyz.lisbammisakait.item.HutouzhanjinqiangItem;

public class HorseSuperASkill extends Item implements ActiveSkillable {
    public HorseSuperASkill(Settings settings) {
        super(settings);
    }

    @Override
    public void castSkill(MinecraftServer server, ServerPlayerEntity player, ItemStack stack) {
        boolean isExhausted = stack.getOrDefault(RtTPSComponents.LIMITEDSKILLEXHAUSTED_TYPE,true);
        if (isExhausted) {
            player.sendMessage(Text.of("你已经使用过该技能"), true);
            return;
        }
        HutouzhanjinqiangItem.recordSkillUseTime(player, server);
        stack.set(RtTPSComponents.LIMITEDSKILLEXHAUSTED_TYPE, true);

    }
}
