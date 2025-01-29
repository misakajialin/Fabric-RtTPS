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
}
