package xyz.lisbammisakait.skill;

import net.minecraft.client.MinecraftClient;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import xyz.lisbammisakait.compoennt.RtTPSComponents;
import xyz.lisbammisakait.item.HutouzhanjinqiangItem;

public class HorseSuperASkill extends Item implements ActiveSkillable {
    public HorseSuperASkill(Settings settings) {
        super(settings);
    }

    @Override
    public void castSkill(MinecraftClient client, ItemStack stack) {
        boolean isExhausted = stack.getOrDefault(RtTPSComponents.LIMITEDSKILLEXHAUSTED_TYPE,true);
        if (isExhausted) {
            client.player.sendMessage(Text.of("你已经使用过该技能"), true);
            return;
        }
        HutouzhanjinqiangItem.recordSkillUseTime(client.player, client.getServer());
        stack.set(RtTPSComponents.LIMITEDSKILLEXHAUSTED_TYPE, true);

    }
}
