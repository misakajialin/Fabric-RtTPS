package xyz.lisbammisakait.skill;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import xyz.lisbammisakait.RelightTheThreePointStrategy;

import java.util.List;

public class LiuBeiASkill extends Item implements ActiveSkillable {
    private final int EFFECT_DURATION = 10;
    private final int  EFFECT_AMPLIFIER = 1;
    private final int RECOVERHEALTH = 5;
    private final int RANGE = 5;
    private final int COOLDOWN = 30;

    public LiuBeiASkill(Settings settings) {
        super(settings);
    }

    @Override
    public void castSkill(MinecraftClient client, ItemStack stack) {
        ClientPlayerEntity user = client.player;
        if (user.getItemCooldownManager().isCoolingDown(stack)) {
            // 如果物品正在冷却中，直接返回
            return ;
        }

        ServerWorld serverWorld =  client.getServer().getWorld(user.getEntityWorld().getRegistryKey());
        //回血
        float currentHealth = user.getHealth();
        float maxHealth = user.getMaxHealth();
        if (currentHealth < maxHealth) {
            user.setHealth(Math.min(currentHealth + RECOVERHEALTH, maxHealth));
        }
        RelightTheThreePointStrategy.LOGGER.info("给自己添加生命回复效果");
        List<PlayerEntity> nearbyPlayers = getNearbyPlayers(user, serverWorld, RANGE);
        for (PlayerEntity nearbyPlayer : nearbyPlayers) {
//           if (!nearbyPlayer.equals(user)) {
                // 给范围内的其他玩家添加生命回复效果
                RelightTheThreePointStrategy.LOGGER.info("给玩家添加生命回复效果");
                nearbyPlayer.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, EFFECT_DURATION*20, EFFECT_AMPLIFIER));
//           }
        }

    }
    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.translatable("itemskill.relight-the-three-point-strategy.longnudiwei"));
    }
    public List<PlayerEntity> getNearbyPlayers(PlayerEntity player, ServerWorld world,int range) {
        Vec3d pos = player.getPos();
        // 创建一个以玩家为中心的检测范围盒子
        Box box = new Box(
                pos.getX() - range, pos.getY() - range, pos.getZ() - range,
                pos.getX() + range, pos.getY() + range, pos.getZ() + range
        );
        // 获取范围内的所有玩家
        List<PlayerEntity> nearbyPlayers = world.getEntitiesByClass(PlayerEntity.class, box, Entity::isAlive);
        RelightTheThreePointStrategy.LOGGER.info(nearbyPlayers.toString());
        return nearbyPlayers;
    }
}
