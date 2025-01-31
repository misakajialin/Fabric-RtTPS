package xyz.lisbammisakait.network.packet;

import net.minecraft.util.Identifier;
import xyz.lisbammisakait.RelightTheThreePointStrategy;

public class RtTPSNetworkingConstants {
    // 存储数据包的 id 以便后面引用
    public static final Identifier SKILLSLOT_ID = Identifier.of(RelightTheThreePointStrategy.MOD_ID, "skillslot_payload");
}