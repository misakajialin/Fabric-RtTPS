package xyz.lisbammisakait.item;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.text.Text;
import xyz.lisbammisakait.RelightTheThreePointStrategy;
import xyz.lisbammisakait.compoennt.RtTPSComponents;

import java.util.HashMap;
import java.util.Map;

public class CanghaitulongfuItem extends  RtTPSSwordItem {
    public static final int HITNUMBER = 5;

    public CanghaitulongfuItem(ToolMaterial material, float attackDamage, float attackSpeed, Item.Settings settings) {
        super(material, attackDamage, attackSpeed, settings);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target,LivingEntity attacker) {
        PlayerEntity player =(PlayerEntity)attacker;
        //增加攻击次数
        addHitNumber(player, target, stack);
        if (player != null) {
            player.sendMessage(Text.of("攻击次数: " + stack.getOrDefault(RtTPSComponents.HITNUMBER_TYPE,0)), true);
        }
        if(stack.getOrDefault(RtTPSComponents.HITNUMBER_TYPE, 0) >= HITNUMBER){
            //设置使用次数为0
//            stack.set(RtTPSComponents.HITNUMBER_TYPE,0);
            // 创建一个新的物品栈
            ItemStack newItemStack = new ItemStack(ModItems.CAOCAOBSKILL, 1);
            // 将技能B位置更换为新的物品栈
            player.getInventory().main.set(7, newItemStack);
            }
        return super.postHit(stack, target, attacker);
    }
//    @Override
//    public void postDamageEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
//        stack.set(RtTPSComponents.HITNUMBER_TYPE,stack.getOrDefault(RtTPSComponents.HITNUMBER_TYPE,0)+1);
//        PlayerEntity player = (PlayerEntity) target;
//        player.sendMessage(Text.of("攻击次数: " + stack.getOrDefault(RtTPSComponents.HITNUMBER_TYPE,0)), true);
//        super.postDamageEntity(stack, target,attacker);
//    }
    /*@Environment(EnvType.CLIENT)
    private void sendAttackMessage(int hitnum) {
        //MinecraftClient client = MinecraftClient.getInstance();
        if (client.player != null) {
            client.player.sendMessage(Text.of("攻击次数: " + hitnum), true);
        }
    }*/
}

