package xyz.lisbammisakait;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import org.lwjgl.glfw.GLFW;
import xyz.lisbammisakait.skill.Skillable;

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
                useSkill(client,7);
            }
            while (keyBindingB.wasPressed()) {
                useSkill(client,8);
            }
        });
    }
    private void useSkill(MinecraftClient client,int slot) {
        PlayerInventory inventory = client.player.getInventory();
        ItemStack skillStack = inventory.getStack(slot);
        if (skillStack.isEmpty()||!(skillStack.getItem() instanceof Skillable)) {
            RelightTheThreePointStrategy.LOGGER.info("并非技能物品");
            return;
        }
        Skillable skill = (Skillable) skillStack.getItem();
        skill.useSkill(client);
    }
}
