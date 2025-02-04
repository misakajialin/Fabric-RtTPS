package xyz.lisbammisakait;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.scoreboard.ScoreAccess;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.ScoreboardCriterion;
import net.minecraft.scoreboard.ScoreboardObjective;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.lisbammisakait.compoennt.RtTPSComponents;
import xyz.lisbammisakait.item.LeitingzhizhangItem;
import xyz.lisbammisakait.item.ModItems;
import xyz.lisbammisakait.network.packet.SkillSlotPayload;
import xyz.lisbammisakait.skill.ActiveSkillable;
import xyz.lisbammisakait.tools.SafeTp;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.util.Collections;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class RelightTheThreePointStrategy implements ModInitializer {
	public static final String MOD_ID = "relight-the-three-point-strategy";
	public static final Identifier MAX_HEALTH_ID = Identifier.of(RelightTheThreePointStrategy.MOD_ID,"max_health_modifier");
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		ModItems.initialize();
		RtTPSComponents.initialize();

//		AttackEntityCallback.EVENT.register((PlayerEntity player, World world, Hand hand, Entity entity, EntityHitResult hitResult)->{
//			RelightTheThreePointStrategy.LOGGER.info("全局拦截到攻击"+entity.getName());
//			return ActionResult.PASS;
//		});

		// 注册服务器启动事件
		ServerLifecycleEvents.SERVER_STARTED.register(this::createScoreboard);
		//下方法即将被废弃,请使用新api
//		ServerPlayerEvents.ALLOW_DEATH.register(this::preventDeath);
		//以下为新api
		ServerLivingEntityEvents.ALLOW_DEATH.register(this::preventDeath);
	// 注册获取剩余复活次数的命令
		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(literal("getrsc")
				.then(argument("target", EntityArgumentType.player())
						.executes(context -> {
							ServerPlayerEntity player =  EntityArgumentType.getPlayer(context,"target");
							int rsc;
                   	 	try {
                        	VarHandle playerHandle = MethodHandles.lookup().findVarHandle(PlayerEntity.class, "respawnCount", int.class);
                        	rsc = (int) playerHandle.get(player);
                    	} catch (NoSuchFieldException | IllegalAccessException e) {
                        	throw new RuntimeException(e);
                    	}
                            context.getSource().sendFeedback(() -> Text.literal("剩余复活次数: %s".formatted(rsc)), false);
							return rsc;
						}))));

		// 注册发动技能的数据包
		PayloadTypeRegistry.playC2S().register(SkillSlotPayload.ID, SkillSlotPayload.CODEC);
		ServerPlayNetworking.registerGlobalReceiver(SkillSlotPayload.ID, (payload, context) -> {
			context.server().execute(() -> {
				context.player().sendMessage(Text.of("使用技能"), true);
				useSkill(context.server(),context.player(), payload.slot());
			});
		});

		LOGGER.info("Hello Fabric world!");
		// 注册服务器tick事件
		ServerTickEvents.END_SERVER_TICK.register(server -> {
			Scoreboard scoreboard = server.getScoreboard();
			ScoreboardObjective respawnCountSBO = scoreboard.getNullableObjective("isGameStarted");
			ScoreAccess scoreAccess = scoreboard.getOrCreateScore(() -> "gameStarted", respawnCountSBO);
			if(scoreAccess.getScore()==1){
				server.getPlayerManager().getPlayerList().forEach(player -> {
					if(player.getInventory().contains(ModItems.LEITINGZHIZHANG.getDefaultStack())){
						player.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, LeitingzhizhangItem.SPEED_DURATION *20, 1));
					}
				});
				scoreAccess.setScore(-1);
			}
		});
		// 注册发动刘备技能的数据包
//		PayloadTypeRegistry.playC2S().register(LiuBeiASkillPayload.ID, LiuBeiASkillPayload.CODEC);
//        ServerPlayNetworking.registerGlobalReceiver(LiuBeiASkillPayload.ID, (payload, context) -> {
//            context.server().execute(() -> {
//				EntityFinder entityFinder = new EntityFinder();
//				List<LivingEntity> nearbyEntities = entityFinder.getNearbyEntities(context.player(), context.server().getWorld(context.player().getEntityWorld().getRegistryKey()),payload.range(),LivingEntity.class);
//				for (LivingEntity nearbyEntity : nearbyEntities) {
//					if (!nearbyEntity.equals(context.player())) {
//						// 给范围内的其他生物添加生命回复效果
//						nearbyEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, LiuBeiASkill.EFFECT_DURATION*20, LiuBeiASkill.EFFECT_AMPLIFIER));
//					}
//				}
//            });
//        });
	}
	// 阻止玩家死亡
	private boolean preventDeath(LivingEntity livingEntity, DamageSource damageSource, float damageAmount) {
		if(livingEntity instanceof ServerPlayerEntity){
			livingEntity.setHealth(livingEntity.getMaxHealth());
			livingEntity.clearStatusEffects();
			return false;
		}
        return true;
    }


	private void createScoreboard(MinecraftServer server) {
		Scoreboard scoreboard = server.getScoreboard();
		if(scoreboard.getNullableObjective("isGameStarted")==null){
			scoreboard.addObjective("isGameStarted", ScoreboardCriterion.DUMMY, Text.of("游戏是否开始"), ScoreboardCriterion.RenderType.INTEGER,true,null);
		}
		ScoreboardObjective respawnCountSBO = scoreboard.getNullableObjective("isGameStarted");
		ScoreAccess scoreAccess = scoreboard.getOrCreateScore(() -> "gameStarted", respawnCountSBO);
		scoreAccess.setScore(0);
	}

	private void useSkill(MinecraftServer server, ServerPlayerEntity player, int slot) {
		PlayerInventory inventory = player.getInventory();
		ItemStack skillStack = inventory.getStack(slot);
		//
		if (skillStack.isEmpty()||!(skillStack.getItem() instanceof ActiveSkillable)) {
			RelightTheThreePointStrategy.LOGGER.info("并非技能物品");
			return;
		}
		ActiveSkillable skill = (ActiveSkillable) skillStack.getItem();
		skill.castSkill(server,player,skillStack);
	}
}