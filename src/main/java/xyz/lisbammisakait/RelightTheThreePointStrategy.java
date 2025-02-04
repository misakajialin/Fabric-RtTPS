package xyz.lisbammisakait;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.scoreboard.ScoreAccess;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.ScoreboardCriterion;
import net.minecraft.scoreboard.ScoreboardObjective;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.SpreadPlayersCommand;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.GameMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.lisbammisakait.compoennt.RtTPSComponents;
import xyz.lisbammisakait.item.ModItems;
import xyz.lisbammisakait.network.packet.SkillSlotPayload;
import xyz.lisbammisakait.skill.ActiveSkillable;
import xyz.lisbammisakait.tools.Pile;

import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicInteger;
import static xyz.lisbammisakait.skill.MarkItem.markSlot;
import static xyz.lisbammisakait.tools.Pile.*;

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
		// 注册服务器启动事件
		ServerLifecycleEvents.SERVER_STARTED.register(this::createScoreboard);
		//下方法即将被废弃,请使用新api
//		ServerPlayerEvents.ALLOW_DEATH.register(this::preventDeath);
		//以下为新api
		ServerLivingEntityEvents.ALLOW_DEATH.register(this::handlePlayerDeath);

//		AttackEntityCallback.EVENT.register(this::)
	// 注册获取剩余复活次数的命令
//		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(literal("getrsc")
//				.then(argument("target", EntityArgumentType.player())
//						.executes(context -> {
//							ServerPlayerEntity player =  EntityArgumentType.getPlayer(context,"target");
//							int rsc;
//                   	 	try {
//                        	VarHandle playerHandle = MethodHandles.lookup().findVarHandle(PlayerEntity.class, "respawnCount", int.class);
//                        	rsc = (int) playerHandle.get(player);
//                    	} catch (NoSuchFieldException | IllegalAccessException e) {
//                        	throw new RuntimeException(e);
//                    	}
//                            context.getSource().sendFeedback(() -> Text.literal("剩余复活次数: %s".formatted(rsc)), false);
//							return rsc;
//						}))));

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
		ServerTickEvents.END_SERVER_TICK.register(this::serverEndTick);
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
	private boolean handlePlayerDeath(LivingEntity livingEntity, DamageSource damageSource, float damageAmount){
		if(livingEntity instanceof ServerPlayerEntity){
			ServerPlayerEntity player = (ServerPlayerEntity) livingEntity;
			ItemStack mark  = player.getInventory().getStack(markSlot);
			int rsc = mark.get(RtTPSComponents.REMAININGRESPAWNCOUNT_TYPE);
			mark.set(RtTPSComponents.REMAININGRESPAWNCOUNT_TYPE, rsc - 1);
			if(rsc==-1) {
				if (!handleGameOver(livingEntity)) player.interactionManager.changeGameMode(GameMode.SPECTATOR);
			}else {
				//复活操作
				respawnPlayer(player);
			}
			return false;
		}else {
			//如果不是玩家则直接返回
			return true;
		}
	}
	private boolean handleGameOver(LivingEntity livingEntity){
		MinecraftServer server =  livingEntity.getServer();
		AtomicInteger deadPlayerCount = new AtomicInteger();
		//检测玩家是否全部死亡
		server.getPlayerManager().getPlayerList().parallelStream().forEach(player -> {
			if(player.getInventory().getStack(markSlot).get(RtTPSComponents.REMAININGRESPAWNCOUNT_TYPE)==0){
				deadPlayerCount.getAndIncrement();
			}
		});
		//如果全部死亡或一人存活则游戏结束
		if (deadPlayerCount.get() >= server.getPlayerManager().getPlayerList().size()-1) {
			//游戏结束后重置玩家状态
			server.getPlayerManager().getPlayerList().forEach(player -> {
				player.sendMessage(Text.of("游戏结束"), true);
				EntityAttributeInstance attribute = player.getAttributeInstance(EntityAttributes.MAX_HEALTH);
				if (attribute != null) {
					attribute.removeModifier(MAX_HEALTH_ID);
				}
				player.teleport(player.getServer().getWorld(player.getWorld().getRegistryKey()), 167, 257, 280, Collections.emptySet(), 0, 0, false);
				player.getInventory().clear();
				player.setOnFire(false);
				player.setHealth(player.getMaxHealth());
				player.clearStatusEffects();
			});
			//设置游戏结束
			Scoreboard scoreboard = server.getScoreboard();
			ScoreboardObjective respawnCountSBO = scoreboard.getNullableObjective("isGameStarted");
			ScoreAccess scoreAccess = scoreboard.getOrCreateScore(() -> "gameStarted", respawnCountSBO);
			scoreAccess.setScore(2);
			return true;
		}
		return false;
	}
	//TODO 死亡缴械
	private void respawnPlayer(ServerPlayerEntity player) {
			player.setHealth(player.getMaxHealth());
			player.clearStatusEffects();
			player.setOnFire(false);
			ServerWorld world = player.getServer().getWorld(player.getWorld().getRegistryKey());
			player.teleport(world, 0, 257, -2, Collections.emptySet(), 0, 0, false);
			player.getServer().getPlayerManager().getPlayerList().forEach(Notifiee -> {
				Notifiee.sendMessage(Text.of(player.getName() + "死亡"), true);
			});
			//设置复活状态
			player.getInventory().getStack(markSlot).set(RtTPSComponents.ISWITHINRESPAWNPHASE_TYPE, true);
			//复活倒计时
			Runnable runnable = () -> {
				try {
					for(int i=5;i>0;i--) {
						player.sendMessage(Text.of("复活倒计时：%ss".formatted(i)), true);
						Thread.sleep(1000);
					}
					player.sendMessage(Text.of("复活倒计时：0s"), true);
					Inventory inventory= player.getInventory();
					inventory.getStack(markSlot).set(RtTPSComponents.ISWITHINRESPAWNPHASE_TYPE, false);
					int faction = inventory.getStack(0).getOrDefault(RtTPSComponents.FACTION_TYPE, 0);
					int maxY = 257;
					switch (faction){
						case 0:
							Pile[] shu = spreadPlayer(world, new Vec2f(0, 60), 1, 25, maxY, false, Collections.singleton(player));
							getMinDistance(Collections.singleton(player), world,shu, maxY, false);
							break;
						case 1:
							Pile[] wu = spreadPlayer(world, new Vec2f(-60, 0), 1, 25, maxY, false, Collections.singleton(player));
							getMinDistance(Collections.singleton(player), world,wu, maxY, false);
							break;
						case 2:
							Pile[] wei = spreadPlayer(world, new Vec2f(0, -60), 1, 25, maxY, false, Collections.singleton(player));
							getMinDistance(Collections.singleton(player), world,wei, maxY, false);
							break;
						case 3:
							Pile[] han = spreadPlayer(world, new Vec2f(60, 0), 1, 25, maxY, false, Collections.singleton(player));
							getMinDistance(Collections.singleton(player), world,han, maxY, false);
							break;
					}
//					player.teleport(player.getServer().getWorld(player.getWorld().getRegistryKey()), 0, 98, 0, Collections.emptySet(), 0, 0, false);
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
			};
			Thread thread = new Thread(runnable);
			thread.start();
    }
	private Pile[] spreadPlayer(ServerWorld serverWorld,Vec2f center, float spreadDistance, float maxRange, int maxY, boolean respectTeams, Collection<? extends Entity> players) {
		int i = serverWorld.getBottomY();
		if (maxY < i) {
			LOGGER.warn("error max y-coordinate");
			return null;
        } else {
			Random random = Random.create();
			double d = (double) (center.x - maxRange);
			double e = (double) (center.y - maxRange);
			double f = (double) (center.x + maxRange);
			double g = (double) (center.y + maxRange);
			//队伍系统暂时不用
//			Pile[] piles = makePiles(random, respectTeams ? getPileCountRespectingTeams(players) : players.size(), d, e, f, g);
			Pile[] piles = makePiles(random, players.size(), d, e, f, g);
			spread(center, (double) spreadDistance, serverWorld, random, d, e, f, g, maxY, piles);
			return piles;
		}
	}
	private void  serverEndTick(MinecraftServer server){
		//检测游戏是否开始
		changeGameStatus(server);
	}
	//-1为游戏进行中,0为游戏未开始，1为游戏开始,2为游戏结束
	private void changeGameStatus(MinecraftServer server){
		Scoreboard scoreboard = server.getScoreboard();
		ScoreboardObjective respawnCountSBO = scoreboard.getNullableObjective("isGameStarted");
		ScoreAccess scoreAccess = scoreboard.getOrCreateScore(() -> "gameStarted", respawnCountSBO);
		if(scoreAccess.getScore()==1){
//				server.getPlayerManager().getPlayerList().forEach(player -> {
//					if(player.getInventory().contains(ModItems.LEITINGZHIZHANG.getDefaultStack())){
//						player.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, LeitingzhizhangItem.SPEED_DURATION *20, 1));
//					}
//				});
			scoreAccess.setScore(-1);
		}
	}
	private void detectGameOver(MinecraftServer server){

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