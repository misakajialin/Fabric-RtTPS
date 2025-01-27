package xyz.lisbammisakait.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import xyz.lisbammisakait.RelightTheThreePointStrategy;
import xyz.lisbammisakait.Skill.YinFengLaiXiangSkill;
import xyz.lisbammisakait.compoennt.RtTPSComponents;

public class ModItems {
    //创建技能物品组
    public static final RegistryKey<ItemGroup> SKILL_GROUP_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), Identifier.of(RelightTheThreePointStrategy.MOD_ID, "skill_group"));
    public static final ItemGroup SKILL_GROUP = FabricItemGroup.builder()
            .icon(() -> new ItemStack(ModItems.HUTOUZHANJINQIANG))
            .displayName(Text.translatable("skillGroup.RelightTheThreePointStrategy"))
            .build();
    //创建武器物品组
    public static final RegistryKey<ItemGroup> RTTPS_ITEM_GROUP_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), Identifier.of(RelightTheThreePointStrategy.MOD_ID, "rttps_group"));
    public static final ItemGroup RTTPS_GROUP = FabricItemGroup.builder()
            .icon(() -> new ItemStack(ModItems.FEILONGDUOFENG))
            .displayName(Text.translatable("rttpsGroup.RelightTheThreePointStrategy"))
            .build();

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
    public static final Item HUTOUZHANJINQIANG = register(new HutouzhanjinqiangItem(ToolMaterial.GOLD, 6f, 1f, new Item.Settings().registryKey(HUTOUZHANJINQIANG_KEY).component(RtTPSComponents.COOLDOWN_TYPE,HutouzhanjinqiangItem.COOLDOWN)), HUTOUZHANJINQIANG_KEY);
    //-----------------------------------------------------------------------------------------------
    //注册引凤来翔
    public static final RegistryKey<Item> YINFENGLAIXIANG_KEY = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(RelightTheThreePointStrategy.MOD_ID, "yinfenglaixiang"));
    public static final Item YINFENGLAIXIANG = register(new YinFengLaiXiangSkill( new Item.Settings().registryKey(YINFENGLAIXIANG_KEY)), YINFENGLAIXIANG_KEY);

    public static Item register(Item item, RegistryKey<Item> registryKey) {
    // Register the item.
        Item registeredItem = Registry.register(Registries.ITEM, registryKey.getValue(), item);
     // Return the registered item!
        return registeredItem;
    }


    /*public static void registerToVanillaItemGroups() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(content -> {
            content.add(FEILONGDUOFENG);
            content.add(HUTOUZHANJINQIANG);
        });
    }*/
    public static void registerToSkillGroups() {
        Registry.register(Registries.ITEM_GROUP, SKILL_GROUP_KEY, SKILL_GROUP);
        ItemGroupEvents.modifyEntriesEvent(SKILL_GROUP_KEY).register(itemGroup -> {
            itemGroup.add(ModItems.YINFENGLAIXIANG);
        });
    }
    public static void registerToRtTPSGroups() {
        Registry.register(Registries.ITEM_GROUP, RTTPS_ITEM_GROUP_KEY, RTTPS_GROUP);
        ItemGroupEvents.modifyEntriesEvent(RTTPS_ITEM_GROUP_KEY).register(itemGroup -> {
            itemGroup.add(ModItems.FEILONGDUOFENG);
            itemGroup.add(ModItems.HUTOUZHANJINQIANG);
        });
    }
    public static void initialize() {
        registerToSkillGroups();
        registerToRtTPSGroups();
        //registerToVanillaItemGroups();
    }
}
