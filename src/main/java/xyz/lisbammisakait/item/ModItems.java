package xyz.lisbammisakait.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.CustomModelDataComponent;
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
import xyz.lisbammisakait.skill.*;
import xyz.lisbammisakait.RelightTheThreePointStrategy;
import xyz.lisbammisakait.compoennt.RtTPSComponents;

import java.util.Arrays;
import java.util.List;

public class ModItems {
    //创建技能物品组
    public static final RegistryKey<ItemGroup> SKILL_GROUP_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), Identifier.of(RelightTheThreePointStrategy.MOD_ID, "skill_group"));
    public static final ItemGroup SKILL_GROUP = FabricItemGroup.builder()
            .icon(() -> new ItemStack(ModItems.YINFENGLAIXIANG))
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
    public static final RegistryKey<Item> MARK_KEY = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(RelightTheThreePointStrategy.MOD_ID, "mark"));
    public static final Item MARK = register(new MarkItem(new Item.Settings().registryKey(MARK_KEY).component(RtTPSComponents.REMAININGRESPAWNCOUNT_TYPE,5).component(RtTPSComponents.ISWITHINRESPAWNPHASE_TYPE,false)), MARK_KEY);
    //注册飞龙夺凤
    public static final RegistryKey<Item> FEILONGDUOFENG_KEY = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(RelightTheThreePointStrategy.MOD_ID, "feilongduofeng"));
    public static final Item FEILONGDUOFENG = register(new FeilongduofengItem(ToolMaterial.GOLD, 5f, 20f, new Item.Settings().registryKey(FEILONGDUOFENG_KEY).component(RtTPSComponents.FACTION_TYPE,0)), FEILONGDUOFENG_KEY);
    //注册虎头湛金枪
    public static final RegistryKey<Item> HUTOUZHANJINQIANG_KEY = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(RelightTheThreePointStrategy.MOD_ID, "hutouzhanjinqiang"));
    public static final Item HUTOUZHANJINQIANG = register(new HutouzhanjinqiangItem(ToolMaterial.GOLD, 6f, 20f, new Item.Settings().registryKey(HUTOUZHANJINQIANG_KEY).component(RtTPSComponents.FACTION_TYPE,0)), HUTOUZHANJINQIANG_KEY);
    //注册雷霆之杖
    public static final RegistryKey<Item> LEITINGZHIZHANG_KEY = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(RelightTheThreePointStrategy.MOD_ID, "leitingzhizhang"));
    public static final Item LEITINGZHIZHANG = register(new LeitingzhizhangItem(ToolMaterial.GOLD, 4f, 20f, new Item.Settings().registryKey(LEITINGZHIZHANG_KEY).component(RtTPSComponents.HITNUMBER_TYPE,0).component(RtTPSComponents.FACTION_TYPE,3)), LEITINGZHIZHANG_KEY);
    //注册神威虎头湛金枪
    public static final RegistryKey<Item> SHENWEIHUTOUZHANJINQIANG_KEY = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(RelightTheThreePointStrategy.MOD_ID, "shenweihutouzhanjinqiang"));
    public static final Item SHENWEIHUTOUZHANJINQIANG = register(new ShenweihutouzhanjianjinqiangItem(ToolMaterial.GOLD, 8f, 20f, new Item.Settings().registryKey(SHENWEIHUTOUZHANJINQIANG_KEY).component(RtTPSComponents.FACTION_TYPE,0)), SHENWEIHUTOUZHANJINQIANG_KEY);
    //注册沧海屠龙斧
    public static final RegistryKey<Item> CANGHAITULONGFU_KEY = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(RelightTheThreePointStrategy.MOD_ID, "canghaitulongfu"));
    public static final Item CANGHAITULONGFU = register(new CanghaitulongfuItem(ToolMaterial.GOLD, 10f, -3f, new Item.Settings().registryKey(CANGHAITULONGFU_KEY).component(RtTPSComponents.HITNUMBER_TYPE,0).component(RtTPSComponents.FACTION_TYPE,2)), CANGHAITULONGFU_KEY);
    //注册破虏
    public static final RegistryKey<Item> POLU_KEY = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(RelightTheThreePointStrategy.MOD_ID, "polu"));
    public static final Item POLU = register(new PoluItem(ToolMaterial.GOLD, 6f, -1f, new Item.Settings().registryKey(POLU_KEY).component(RtTPSComponents.HITNUMBER_TYPE,0).component(RtTPSComponents.FACTION_TYPE,1)), POLU_KEY);

    //-----------------------------------------------------------------------------------------------
    //-----------------------------------------------------------------------------------------------
    //注册引凤来翔
    public static final RegistryKey<Item> YINFENGLAIXIANG_KEY = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(RelightTheThreePointStrategy.MOD_ID, "yinfenglaixiang"));
    public static final Item YINFENGLAIXIANG = register(new YinFengLaiXiangSkill( new Item.Settings().registryKey(YINFENGLAIXIANG_KEY)), YINFENGLAIXIANG_KEY);
    //注册铁骑踏川
    public static final RegistryKey<Item> TIEJITACHUAN_KEY = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(RelightTheThreePointStrategy.MOD_ID, "tiejitachuan"));
    public static final Item TIEJITACHUAN = register(new TieJiTaChuanSkill( new Item.Settings().registryKey(TIEJITACHUAN_KEY)), TIEJITACHUAN_KEY);
    //注册刘备A技能
    public static final RegistryKey<Item> LIUBEIASKILL_KEY = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(RelightTheThreePointStrategy.MOD_ID, "xiangxieyvgong"));
    public static final Item LIUBEIASKILL = register(new LiuBeiASkill( new Item.Settings().registryKey(LIUBEIASKILL_KEY)), LIUBEIASKILL_KEY);
    //注册刘备B技能
    public static final RegistryKey<Item> LIUBEIBSKILL_KEY = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(RelightTheThreePointStrategy.MOD_ID, "longnudiwei"));
//    public static final Item LIUBEIBSKILL = register(new LiuBeiBSkill( new Item.Settings().registryKey(LIUBEIBSKILL_KEY).component(RtTPSComponents.COOLDOWN_TYPE,LiuBeiBSkill.COOLDOWN)), LIUBEIBSKILL_KEY);
    public static final Item LIUBEIBSKILL = register(new LiuBeiBSkill( new Item.Settings().registryKey(LIUBEIBSKILL_KEY)), LIUBEIBSKILL_KEY);
    //注册马超A技能
    public static final RegistryKey<Item> HORSESUPERASKILL_KEY = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(RelightTheThreePointStrategy.MOD_ID, "horsesuperaskill"));
    public static final Item HORSESUPERASKILL = register(new HorseSuperASkill( new Item.Settings()
            .registryKey(HORSESUPERASKILL_KEY)
            .component(RtTPSComponents.LIMITEDSKILLEXHAUSTED_TYPE,false)
            .component(DataComponentTypes.CUSTOM_MODEL_DATA,new CustomModelDataComponent(List.of(), List.of(true),List.of(),List.of()))), HORSESUPERASKILL_KEY);
    //注册张角A技能
    public static final RegistryKey<Item> ZHANGJIAOASKILL_KEY = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(RelightTheThreePointStrategy.MOD_ID, "zhangjiaoaskill"));
    public static final Item ZHANGJIAOASKILL = register(new ZhangJiaoASKill( new Item.Settings()
            .registryKey(ZHANGJIAOASKILL_KEY)
            .component(RtTPSComponents.USENUMBER_TYPE,0)), ZHANGJIAOASKILL_KEY);
    //注册曹操B技能
    public static final RegistryKey<Item> CAOCAOBSKILL_KEY = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(RelightTheThreePointStrategy.MOD_ID, "caocaobskill"));
    public static final Item CAOCAOBSKILL = register(new CaoCaoBSkill( new Item.Settings()
            .registryKey(CAOCAOBSKILL_KEY)
            .component(RtTPSComponents.LIMITEDSKILLEXHAUSTED_TYPE,false)
            .component(DataComponentTypes.CUSTOM_MODEL_DATA,new CustomModelDataComponent(List.of(), List.of(false),List.of(),List.of()))), CAOCAOBSKILL_KEY);
    //注册曹操P技能
    public static final RegistryKey<Item> CAOCAOPSKILL_KEY = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(RelightTheThreePointStrategy.MOD_ID, "caocaopskill"));
    public static final Item CAOCAOPSKILL = register(new CaoCaoPSkill( new Item.Settings().registryKey(CAOCAOPSKILL_KEY)), CAOCAOPSKILL_KEY);
    //注册曹操A技能
    public static final RegistryKey<Item> CAOCAOASKILL_KEY = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(RelightTheThreePointStrategy.MOD_ID, "caocaoaskill"));
    public static final Item CAOCAOASKILL = register(new CaoCaoASkill( new Item.Settings().registryKey(CAOCAOASKILL_KEY)), CAOCAOASKILL_KEY);
    //注册孙坚A技能
    public static final RegistryKey<Item> SUNJIANASKILL_KEY = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(RelightTheThreePointStrategy.MOD_ID, "sunjianaskill"));
    public static final Item SUNJIANASKILL = register(new SunJianASkill( new Item.Settings()
            .registryKey(SUNJIANASKILL_KEY)
            .component(RtTPSComponents.LIMITEDSKILLEXHAUSTED_TYPE,false)
            .component(DataComponentTypes.CUSTOM_MODEL_DATA,new CustomModelDataComponent(List.of(), List.of(true),List.of(),List.of()))), SUNJIANASKILL_KEY);
    //注册孙坚P技能
    public static final RegistryKey<Item> SUNJIANPSKILL_KEY = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(RelightTheThreePointStrategy.MOD_ID, "sunjianpskill"));
    public static final Item SUNJIANPSKILL = register(new SunJianPSkill( new Item.Settings()
            .registryKey(SUNJIANPSKILL_KEY)
            .component(RtTPSComponents.LIMITEDSKILLEXHAUSTED_TYPE,false)), SUNJIANPSKILL_KEY);
    //注册孙坚P2技能
    public static final RegistryKey<Item> SUNJIANP2SKILL_KEY = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(RelightTheThreePointStrategy.MOD_ID, "sunjianp2skill"));
    public static final Item SUNJIANP2SKILL = register(new SunJianP2Skill( new Item.Settings().registryKey(SUNJIANP2SKILL_KEY)), SUNJIANP2SKILL_KEY);

    //注册不可发动
    public static final RegistryKey<Item> UNLAUNCHABLE_KEY = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(RelightTheThreePointStrategy.MOD_ID, "unlaunchable"));
    public static final Item UNLAUNCHABLE = register(new UnLaunchable( new Item.Settings().registryKey(UNLAUNCHABLE_KEY)), UNLAUNCHABLE_KEY);

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
    //注册技能
    public static void registerToSkillGroups() {
        Registry.register(Registries.ITEM_GROUP, SKILL_GROUP_KEY, SKILL_GROUP);
        ItemGroupEvents.modifyEntriesEvent(SKILL_GROUP_KEY).register(itemGroup -> {
            itemGroup.add(ModItems.YINFENGLAIXIANG);
            itemGroup.add(ModItems.TIEJITACHUAN);
            itemGroup.add(ModItems.LIUBEIASKILL);
            itemGroup.add(ModItems.LIUBEIBSKILL);
            itemGroup.add(ModItems.HORSESUPERASKILL);
            itemGroup.add(ModItems.ZHANGJIAOASKILL);
            itemGroup.add(ModItems.CAOCAOBSKILL);
            itemGroup.add(ModItems.CAOCAOPSKILL);
            itemGroup.add(ModItems.CAOCAOASKILL);
            itemGroup.add(ModItems.UNLAUNCHABLE);
            itemGroup.add(ModItems.MARK);
            itemGroup.add(ModItems.SUNJIANASKILL);
            itemGroup.add(ModItems.SUNJIANPSKILL);
            itemGroup.add(ModItems.SUNJIANP2SKILL);
        });
    }
    //注册武器
    public static void registerToRtTPSGroups() {
        Registry.register(Registries.ITEM_GROUP, RTTPS_ITEM_GROUP_KEY, RTTPS_GROUP);
        ItemGroupEvents.modifyEntriesEvent(RTTPS_ITEM_GROUP_KEY).register(itemGroup -> {
            itemGroup.add(ModItems.FEILONGDUOFENG);
            itemGroup.add(ModItems.HUTOUZHANJINQIANG);
            itemGroup.add(ModItems.SHENWEIHUTOUZHANJINQIANG);
            itemGroup.add(ModItems.LEITINGZHIZHANG);
            itemGroup.add(ModItems.CANGHAITULONGFU);
            itemGroup.add(ModItems.POLU);
        });
    }
    public static void initialize() {
        registerToSkillGroups();
        registerToRtTPSGroups();
        //registerToVanillaItemGroups();
    }
}
