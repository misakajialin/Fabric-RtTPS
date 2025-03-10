package xyz.lisbammisakait.compoennt;

import com.mojang.serialization.Codec;
import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import xyz.lisbammisakait.RelightTheThreePointStrategy;

public class RtTPSComponents {
    public static void initialize() {
        RelightTheThreePointStrategy.LOGGER.info("Registering {} components", RelightTheThreePointStrategy.MOD_ID);
        // Technically this method can stay empty, but some developers like to notify
        // the console, that certain parts of the mod have been successfully initialized
    }
    //注册限定技是否使用组件
    public static final ComponentType<Boolean> LIMITEDSKILLEXHAUSTED_TYPE = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            Identifier.of(RelightTheThreePointStrategy.MOD_ID, "limitedskillexhausted"),
            ComponentType.<Boolean>builder().codec(Codec.BOOL).build()
    );
    public static final ComponentType<Integer> HITNUMBER_TYPE = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            Identifier.of(RelightTheThreePointStrategy.MOD_ID, "hitnumber"),
            ComponentType.<Integer>builder().codec(Codec.INT).build()
    );
    public static final ComponentType<Integer> USENUMBER_TYPE = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            Identifier.of(RelightTheThreePointStrategy.MOD_ID, "usenumber"),
            ComponentType.<Integer>builder().codec(Codec.INT).build()
    );
    public static final ComponentType<Integer> REMAININGRESPAWNCOUNT_TYPE = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            Identifier.of(RelightTheThreePointStrategy.MOD_ID, "remainingrespawncount"),
            ComponentType.<Integer>builder().codec(Codec.INT).build()
    );
    public static final ComponentType<Boolean> ISWITHINRESPAWNPHASE_TYPE = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            Identifier.of(RelightTheThreePointStrategy.MOD_ID, "iswithinrespawnphase"),
            ComponentType.<Boolean>builder().codec(Codec.BOOL).build()
    );
    //TODO 以后使用枚举类
    public static final ComponentType<Integer> FACTION_TYPE = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            Identifier.of(RelightTheThreePointStrategy.MOD_ID, "faction"),
            ComponentType.<Integer>builder().codec(Codec.INT).build()
            //0 蜀国,1 吴国,2 魏国,3 汉朝
    );
}
