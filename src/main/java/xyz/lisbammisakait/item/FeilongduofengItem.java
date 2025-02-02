package xyz.lisbammisakait.item;


import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.world.World;

import java.util.Random;

public class FeilongduofengItem extends RtTPSSwordItem {
    public static final int PROBABILITY = 30;
    public static final int FIRETIME = 2;
    public FeilongduofengItem(ToolMaterial material, float attackDamage, float attackSpeed, Settings settings) {
        super(material, attackDamage, attackSpeed, settings);
    }
    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        PlayerEntity player = (PlayerEntity) attacker;
//        RelightTheThreePointStrategy.LOGGER.info(String.valueOf(player.respawnCount));
        Random random = new Random();
        // 生成一个 0 到 9 之间的随机整数
        int randomNumber = random.nextInt(10);
        // 判断随机数是否为 0或1
        if (randomNumber<(PROBABILITY/10)) {
            // 给目标添加火焰效果
            target.setFireTicks(FIRETIME*20);

            // 在目标实体位置生成火焰粒子效果
            //获取服务器世界
//            ServerWorld serverWorld =  target.getServer().getWorld(target.getEntityWorld().getRegistryKey());
//            Vec3d pos = target.getPos();
//            for (int i = 0; i < 10; i++) {
//                double offsetX = (Math.random() - 0.5) * 2;
//                double offsetY = (Math.random() - 0.5) * 2;
//                double offsetZ = (Math.random() - 0.5) * 2;
//                serverWorld.spawnParticles(ParticleTypes.FLAME, pos.getX() + offsetX, pos.getY() + offsetY, pos.getZ() + offsetZ, 1, 0, 0, 0, 0);
//
//            }

            // 计算击退强度，这里假设一个简单的强度值
            double knockbackStrength = 1.5;
            // 给目标添加击退效果
            double motionX = attacker.getRotationVector().x;
            double motionZ = attacker.getRotationVector().z;
           //target.addVelocity(motionX * knockbackStrength, 0.5, motionZ * knockbackStrength);
            target.takeKnockback(knockbackStrength, -motionX, -motionZ);
        }

        return super.postHit(stack, target, attacker);
    }
    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected){
        if(world.isClient()){
            return;
        }
        PlayerEntity player = (PlayerEntity) entity;
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.HEALTH_BOOST, 2, 10));
    }

}
