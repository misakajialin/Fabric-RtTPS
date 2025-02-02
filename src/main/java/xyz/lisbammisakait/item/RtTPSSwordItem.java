package xyz.lisbammisakait.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import xyz.lisbammisakait.compoennt.RtTPSComponents;

import java.util.HashMap;
import java.util.Map;

public class RtTPSSwordItem extends SwordItem {
    public final Map<Entity, Long> lastHitTime = new HashMap<>();
    public RtTPSSwordItem(ToolMaterial material, float attackDamage, float attackSpeed, Settings settings) {
        super(material, attackDamage, attackSpeed, settings);
    }
    //不掉耐久
    @Override
    public void postDamageEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.damage(0, attacker, EquipmentSlot.MAINHAND);
    }
    public void addHitNumber(PlayerEntity player, LivingEntity target, ItemStack stack){
        long currentTime = player.getServer().getWorld(player.getEntityWorld().getRegistryKey()).getTime();
        if(currentTime-lastHitTime.getOrDefault(target, 0L)>10){
            stack.set(RtTPSComponents.HITNUMBER_TYPE,stack.getOrDefault(RtTPSComponents.HITNUMBER_TYPE,0)+1);
            lastHitTime.put(target, currentTime);
        }
    }
}
