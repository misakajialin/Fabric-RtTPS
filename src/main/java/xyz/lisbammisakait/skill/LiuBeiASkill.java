package xyz.lisbammisakait.skill;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.entity.FakePlayer;
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
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import xyz.lisbammisakait.RelightTheThreePointStrategy;
import xyz.lisbammisakait.tools.EntityFinder;

import java.util.List;

public class LiuBeiASkill extends Item implements ActiveSkillable {
    public static final int EFFECT_DURATION = 10;
    public static final int  EFFECT_AMPLIFIER = 1;
    private final int RECOVERHEALTH = 10;
    private final int RANGE = 5;

    private final int COOLDOWN = 40;

    public LiuBeiASkill(Settings settings) {
        super(settings);
    }

    @Override
    public void processPracticalSkill(MinecraftServer server, ServerPlayerEntity player, ItemStack stack) {
        if (player.getItemCooldownManager().isCoolingDown(stack)) {
            // 如果物品正在冷却中，直接返回
            player.sendMessage(Text.of("技能冷却中"), false);
            return ;
        }
//        ServerWorld serverWorld =  client.getServer().getWorld(user.getEntityWorld().getRegistryKey());
        //回血
        float currentHealth = player.getHealth();
        float maxHealth = player.getMaxHealth();
        if (currentHealth < maxHealth) {
            player.setHealth(Math.min(currentHealth + RECOVERHEALTH, maxHealth));
        }
        RelightTheThreePointStrategy.LOGGER.info("给自己添加瞬间指令效果");
//        ClientPlayNetworking.send(new LiuBeiASkillPayload(RANGE));
        EntityFinder entityFinder = new EntityFinder();
        List<LivingEntity> nearbyEntities = entityFinder.getNearbyEntities(player,server.getWorld(player.getWorld().getRegistryKey()),RANGE,LivingEntity.class);
        for (LivingEntity nearbyEntity : nearbyEntities) {
            if (!nearbyEntity.equals(player)) {
                // 给范围内的其他生物添加生命回复效果
                nearbyEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, LiuBeiASkill.EFFECT_DURATION * 20, LiuBeiASkill.EFFECT_AMPLIFIER));
            }
        }
        // 设置物品冷却时间
        player.getItemCooldownManager().set(stack, COOLDOWN * 20);

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
