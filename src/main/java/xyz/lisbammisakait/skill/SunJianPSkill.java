package xyz.lisbammisakait.skill;

import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import xyz.lisbammisakait.RelightTheThreePointStrategy;
import xyz.lisbammisakait.compoennt.RtTPSComponents;
import xyz.lisbammisakait.item.CanghaitulongfuItem;
import xyz.lisbammisakait.item.ModItems;

import java.util.List;

public class SunJianPSkill extends Item implements PassiveSkillable{

    public SunJianPSkill(Settings settings) {
        super(settings);
    }
    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        addCustomTooltip(tooltip);
    }

    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected){
        boolean isExhausted = stack.getOrDefault(RtTPSComponents.LIMITEDSKILLEXHAUSTED_TYPE,true);
        PlayerEntity player = (PlayerEntity) entity;

        if(isMarkInSlot(player))//检测物品栏
        {
            if (player.getInventory().getStack(4).get(RtTPSComponents.REMAININGRESPAWNCOUNT_TYPE) <= 2) {
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.JUMP_BOOST, 1, 3));
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOW_FALLING, 1, 1));
            }
            if (!isExhausted)
            {
                if(player.getInventory().getStack(4).get(RtTPSComponents.REMAININGRESPAWNCOUNT_TYPE) == 2)
                {
                    // 创建一个新的物品栈
                    ItemStack newItemStack = new ItemStack(ModItems.SUNJIANASKILL, 1);
                    // 将技能B位置更换为新的物品栈
                    player.getInventory().main.set(8, newItemStack);
                    stack.set(RtTPSComponents.LIMITEDSKILLEXHAUSTED_TYPE, true);
                }
            }
        }

    }



    //检查玩家快捷栏第5格是否为mark
    public static boolean isMarkInSlot(PlayerEntity player) {
        // 快捷栏第5格对应的索引是4（索引从0开始）
        int slotIndex = 4;
        // 获取玩家的快捷栏物品栈
        ItemStack itemStack = player.getInventory().getStack(slotIndex);
        // 检查物品栈是否为空以及物品是否为钻石剑
        return !itemStack.isEmpty() && itemStack.getItem() == ModItems.MARK;
    }

    @Override
    public void addCustomTooltip(List<Text> tooltip) {
        tooltip.add(Text.translatable("skill.relight-the-three-point-strategy.sunjianpskill", CanghaitulongfuItem.HITNUMBER));
    }
}
