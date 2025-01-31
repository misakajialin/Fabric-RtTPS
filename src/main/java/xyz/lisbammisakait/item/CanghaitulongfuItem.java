package xyz.lisbammisakait.item;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.text.Text;

public class CanghaitulongfuItem extends  RtTPSSwordItem {
    MinecraftClient client = MinecraftClient.getInstance();
    public int TIMES = 0;
    public CanghaitulongfuItem(ToolMaterial material, float attackDamage, float attackSpeed, Item.Settings settings) {
        super(material, attackDamage, attackSpeed, settings);
    }
    @Override
    public boolean postHit(net.minecraft.item.ItemStack stack, net.minecraft.entity.LivingEntity target, net.minecraft.entity.LivingEntity attacker) {
        TIMES++;
        sendAttackMessage();
        if(TIMES>=5){
            TIMES=0;
            // 创建一个新的物品栈
            ItemStack newItemStack = new ItemStack(ModItems.SHENWEIHUTOUZHANJINQIANG, 1);
            // 将主手物品更换为新的物品栈
            client.player.getInventory().main.set(8, newItemStack);
        }
        return super.postHit(stack, target, attacker);
    }
    @Environment(EnvType.CLIENT)
    private void sendAttackMessage() {
        //MinecraftClient client = MinecraftClient.getInstance();
        if (client.player != null) {
            client.player.sendMessage(Text.of("攻击次数: " + TIMES), true);
        }
    }
}

