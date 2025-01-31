package xyz.lisbammisakait;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;
import xyz.lisbammisakait.network.packet.SkillSlotPayload;

public class RelightTheThreePointStrategyClient implements ClientModInitializer {
    private static KeyBinding keyBindingV;
    private static KeyBinding keyBindingB;

    @Override
    public void onInitializeClient() {
        keyBindingV = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.RelightTheThreePointStrategy.SkillA", // The translation key of the keybinding's name
                InputUtil.Type.KEYSYM, // The type of the keybinding, KEYSYM for keyboard, MOUSE for mouse.
                GLFW.GLFW_KEY_V, // The keycode of the key
                "category.RelightTheThreePointStrategy.SkillGroup" // The translation key of the keybinding's category.
        ));
        keyBindingB = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.RelightTheThreePointStrategy.SkillB", // The translation key of the keybinding's name
                InputUtil.Type.KEYSYM, // The type of the keybinding, KEYSYM for keyboard, MOUSE for mouse.
                GLFW.GLFW_KEY_B, // The keycode of the key
                "category.RelightTheThreePointStrategy.SkillGroup" // The translation key of the keybinding's category.
        ));
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (keyBindingV.wasPressed()) {
                ClientPlayNetworking.send(new SkillSlotPayload(7));
            }
            while (keyBindingB.wasPressed()) {
                ClientPlayNetworking.send(new SkillSlotPayload(8));
            }
        });
//        PayloadTypeRegistry.playS2C().register(LiuBeiASkillPayload.ID, LiuBeiASkillPayload.CODEC);
//        ClientPlayNetworking.registerGlobalReceiver(LiuBeiASkillPayload.ID, (payload, context) -> {
//            context.client().execute(() -> {
//                ClientBlockHighlighting.highlightBlock(client, target);
//            });
//        });
    }
}
