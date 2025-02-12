package xyz.lisbammisakait.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import xyz.lisbammisakait.compoennt.RtTPSComponents;

public class PoluItem extends  RtTPSSwordItem {
    public static final int HITNUMBER = 30;
    public final int MAX_HEALTH = 60;
    public PoluItem(ToolMaterial material, float attackDamage, float attackSpeed, Settings settings) {
        super(material, attackDamage, attackSpeed, settings);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        boostMaxHealth((PlayerEntity) entity, MAX_HEALTH);
    }

    @Override
    public void specialAbility(ItemStack stack, net.minecraft.entity.LivingEntity target, net.minecraft.entity.LivingEntity attacker) {
        PlayerEntity player =(PlayerEntity)attacker;
        addHitNumber(player, target, stack);
        player.sendMessage(Text.of("攻击次数: " + stack.getOrDefault(RtTPSComponents.HITNUMBER_TYPE, 0)), true);
        if(stack.getOrDefault(RtTPSComponents.HITNUMBER_TYPE, 0) >= HITNUMBER){
            //设置使用次数为0
            stack.set(RtTPSComponents.HITNUMBER_TYPE,0);
            // 获取玩家当前的生命值
            float currentHealth = attacker.getHealth();
            // 计算扣除 20 点生命值后的结果
            float newHealth = Math.max(currentHealth - 20, 1);
            // 调整血量
            attacker.setHealth(newHealth);
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, Integer.MAX_VALUE, 1));
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, Integer.MAX_VALUE, 2));
            /*// 获取 DamageType 的 RegistryEntry
            RegistryEntry<DamageType> damageTypeEntry = Registry.DAMAGE_TYPE.getEntry(RegistryKeys.DAMAGE_TYPE).orElseThrow();
            // 创建带有攻击者的 DamageSource 对象
            DamageSource damageSource = new DamageSource(damageTypeEntry, attacker);

            player.damage(attacker.getServer().getWorld(player.getWorld().getRegistryKey()), DamageSource.OUT_OF_WORLD, 2);*/
            }
    }

}

