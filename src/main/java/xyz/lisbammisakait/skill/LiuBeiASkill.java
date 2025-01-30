package xyz.lisbammisakait.skill;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
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
import xyz.lisbammisakait.network.packet.LiuBeiASkillPayload;
import xyz.lisbammisakait.tools.EntityFinder;

import java.util.List;

public class LiuBeiASkill extends Item implements ActiveSkillable {
    public static final int EFFECT_DURATION = 10;
    public static final int  EFFECT_AMPLIFIER = 1;
    private final int RECOVERHEALTH = 5;
    private final int RANGE = 5;

    private final int COOLDOWN = 40;

    public LiuBeiASkill(Settings settings) {
        super(settings);
    }

    @Override
    public void castSkill(MinecraftClient client, ItemStack stack) {
        ClientPlayerEntity user = client.player;
        if (user.getItemCooldownManager().isCoolingDown(stack)) {
            // 如果物品正在冷却中，直接返回
            user.sendMessage(Text.of("技能冷却中"), false);
            return ;
        }

//        ServerWorld serverWorld =  client.getServer().getWorld(user.getEntityWorld().getRegistryKey());
        //回血
        float currentHealth = user.getHealth();
        float maxHealth = user.getMaxHealth();
        if (currentHealth < maxHealth) {
            user.setHealth(Math.min(currentHealth + RECOVERHEALTH, maxHealth));
        }
        RelightTheThreePointStrategy.LOGGER.info("给自己添加生命回复效果");
        ClientPlayNetworking.send(new LiuBeiASkillPayload(RANGE));
//        EntityFinder entityFinder = new EntityFinder();
//        List<LivingEntity> nearbyEntities = entityFinder.getNearbyEntities(user, serverWorld, RANGE,LivingEntity.class);
//        for (LivingEntity nearbyEntity : nearbyEntities) {
//          if (!nearbyEntity.equals(user)) {
//                // 给范围内的其他生物添加生命回复效果
//                RelightTheThreePointStrategy.LOGGER.info("给生物添加生命回复效果");
//                nearbyEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, EFFECT_DURATION*20, EFFECT_AMPLIFIER));
//           }
//        }
        // 设置物品冷却时间
        user.getItemCooldownManager().set(stack, COOLDOWN * 20);

    }
    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.translatable("skill.relight-the-three-point-strategy.xiangxieyvgong"));

//        for (Component component : originalTooltip) {
//            String text = component.getString();
//            // 分割文本，根据 /n 进行换行
//            String[] lines = text.split("/n");
//            for (String line : lines) {
//                modifiedTooltip.add(Component.literal(line));
//            }
//        }

    }
//    public List<PlayerEntity> getNearbyPlayers(PlayerEntity player, ServerWorld world,int range) {
//        Vec3d pos = player.getPos();
//        // 创建一个以玩家为中心的检测范围盒子
//        Box box = new Box(
//                pos.getX() - range, pos.getY() - range, pos.getZ() - range,
//                pos.getX() + range, pos.getY() + range, pos.getZ() + range
//        );
//        // 获取范围内的所有玩家
//        List<PlayerEntity> nearbyPlayers = world.getEntitiesByClass(PlayerEntity.class, box, Entity::isAlive);
//        RelightTheThreePointStrategy.LOGGER.info(nearbyPlayers.toString());
//        return nearbyPlayers;
//    }
}
