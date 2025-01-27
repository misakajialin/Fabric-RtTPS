package xyz.lisbammisakait.item;

import xyz.lisbammisakait.RelightTheThreePointStrategy;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.item.ToolMaterial;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

import java.util.function.Function;

public class ModItems {
//    public static final Item FEILONGDUOFENG = register("feilongduofeng", Item::new, new Item.Settings());
//    public static final Item FEILONGDUOFENG = register();
//    public static Item register(String path, Function<Item.Settings, Item> factory, Item.Settings settings) {
//        final RegistryKey<Item> registryKey = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(RelightTheThreePointStrategy.MOD_ID, path));
//        return Items.register(registryKey, factory, settings);
//    }
    //注册飞龙夺凤
    public static final RegistryKey<Item> FEILONGDUOFENG_KEY = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(RelightTheThreePointStrategy.MOD_ID, "feilongduofeng"));
    public static final Item FEILONGDUOFENG = register(new FeilongduofengItem(ToolMaterial.GOLD, 4f, 1f, new Item.Settings().registryKey(FEILONGDUOFENG_KEY)), FEILONGDUOFENG_KEY);
    //注册虎头湛金枪
    public static final RegistryKey<Item> HUTOUZHANJINQIANG_KEY = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(RelightTheThreePointStrategy.MOD_ID, "hutouzhanjinqiang"));
    public static final Item HUTOUZHANJINQIANG = register(new HutouzhanjinqiangItem(ToolMaterial.GOLD, 6f, 1f, new Item.Settings().registryKey(HUTOUZHANJINQIANG_KEY)), HUTOUZHANJINQIANG_KEY);

    public static Item register(Item item, RegistryKey<Item> registryKey) {
    // Register the item.
        Item registeredItem = Registry.register(Registries.ITEM, registryKey.getValue(), item);
     // Return the registered item!
        return registeredItem;
    }


    public static void registerToVanillaItemGroups() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(content -> {
            content.add(FEILONGDUOFENG);
            content.add(HUTOUZHANJINQIANG);
        });
    }
    public static void initialize() {
        registerToVanillaItemGroups();
    }
}
