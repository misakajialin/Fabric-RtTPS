package xyz.lisbammisakait;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

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
                RelightTheThreePointStrategy.LOGGER.info("VVVVVVV!");
            }
        });
    }
}
