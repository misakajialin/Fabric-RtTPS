package xyz.lisbammisakait.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import xyz.lisbammisakait.RelightTheThreePointStrategy;
import xyz.lisbammisakait.compoennt.RtTPSComponents;

import java.util.HashMap;
import java.util.Map;

public abstract class RtTPSSwordItem extends SwordItem {
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
    @Override
    public abstract void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected);
    public void boostMaxHealth(PlayerEntity player,int maxHealth){
        //增加最大生命值
        EntityAttributeInstance attribute = player.getAttributeInstance(EntityAttributes.MAX_HEALTH);
        if(attribute.hasModifier(RelightTheThreePointStrategy.MAX_HEALTH_ID)){
            return;
        }
//       attribute.removeModifier(EntityeventHandlerServer.HEALTH_MODIFIER_ID);
        attribute.addPersistentModifier(new EntityAttributeModifier(RelightTheThreePointStrategy.MAX_HEALTH_ID, maxHealth, EntityAttributeModifier.Operation.ADD_VALUE));
    }
}
