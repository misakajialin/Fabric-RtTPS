package xyz.lisbammisakait.network.packet;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.math.Vec3d;
import net.minecraft.network.codec.PacketCodecs;


//public record LiuBeiASkillPayload(Vec3d vec3d,int range) implements CustomPayload {
//    public static final Id<LiuBeiASkillPayload> ID = new CustomPayload.Id<>(RtTPSNetworkingConstants.ZHANGJIAOASKILLPAYLOAD_ID);
////   public static final PacketCodec<PacketByteBuf, LiuBeiASkillPayload> CODEC = PacketCodec.tuple(Vec3d.PACKET_CODEC, LiuBeiASkillPayload::vec3d, LiuBeiASkillPayload::new);
//    public static final PacketCodec<ByteBuf, LiuBeiASkillPayload> CODEC = PacketCodec.tuple(
//            Vec3d.PACKET_CODEC,
//            LiuBeiASkillPayload::vec3d,
//            PacketCodecs.VAR_INT,
//        //PacketCodecs.codec(Codec.INT),
//            LiuBeiASkillPayload::range,
//            LiuBeiASkillPayload::new
//    );
//    // 或者，你也可以这样写：
//    // public static final PacketCodec<PacketByteBuf, BlockHighlightPayload> CODEC = PacketCodec.of((value, buf) -> buf.writeBlockPos(value.blockPos), buf -> new BlockHighlightPayload(buf.readBlockPos()));
//    @Override
//    public Id<? extends CustomPayload> getId() {
//        return ID;
//    }
//}
public record LiuBeiASkillPayload(int range) implements CustomPayload {
    public static final Id<LiuBeiASkillPayload> ID = new CustomPayload.Id<>(RtTPSNetworkingConstants.ZHANGJIAOASKILLPAYLOAD_ID);
//   public static final PacketCodec<PacketByteBuf, LiuBeiASkillPayload> CODEC = PacketCodec.tuple(Vec3d.PACKET_CODEC, LiuBeiASkillPayload::vec3d, LiuBeiASkillPayload::new);
    public static final PacketCodec<ByteBuf, LiuBeiASkillPayload> CODEC = PacketCodec.tuple(
            PacketCodecs.VAR_INT,
        //PacketCodecs.codec(Codec.INT),
            LiuBeiASkillPayload::range,
            LiuBeiASkillPayload::new
    );
    // 或者，你也可以这样写：
    // public static final PacketCodec<PacketByteBuf, BlockHighlightPayload> CODEC = PacketCodec.of((value, buf) -> buf.writeBlockPos(value.blockPos), buf -> new BlockHighlightPayload(buf.readBlockPos()));
    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
