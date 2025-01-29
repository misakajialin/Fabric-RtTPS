package xyz.lisbammisakait;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.lisbammisakait.compoennt.RtTPSComponents;
import xyz.lisbammisakait.item.ModItems;

public class RelightTheThreePointStrategy implements ModInitializer {
	public static final String MOD_ID = "relight-the-three-point-strategy";

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
		// 注册服务器tick事件
		ServerTickEvents.END_SERVER_TICK.register(server -> {
			for (PlayerEntity player : server.getPlayerManager().getPlayerList()) {
				ItemStack mainHandStack = player.getMainHandStack();
				ItemStack offHandStack = player.getOffHandStack();
				// 如果玩家的主手或副手拿着虎头湛金枪
				if (mainHandStack.getItem() == ModItems.HUTOUZHANJINQIANG || offHandStack.getItem() == ModItems.HUTOUZHANJINQIANG) {
					// 为玩家添加加速效果
					player.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 2, 1));
				}
				// 如果玩家的主手或副手拿着神威虎头湛金枪
				if(mainHandStack.getItem() == ModItems.SHENWEIHUTOUZHANJINQIANG || offHandStack.getItem() == ModItems.SHENWEIHUTOUZHANJINQIANG){
					player.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 2, 2));
				}
			}
		});
		LOGGER.info("Hello Fabric world!");

	}
}