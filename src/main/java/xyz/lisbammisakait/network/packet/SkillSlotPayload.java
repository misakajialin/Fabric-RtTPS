package xyz.lisbammisakait.network.packet;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;

public record SkillSlotPayload(int slot) implements CustomPayload {
    public static final Id<SkillSlotPayload> ID = new CustomPayload.Id<>(RtTPSNetworkingConstants.SKILLSLOT_ID);
    public static final PacketCodec<ByteBuf, SkillSlotPayload> CODEC = PacketCodec.tuple(
            //PacketCodecs.codec(Codec.INT),
            PacketCodecs.VAR_INT,
            SkillSlotPayload::slot,
            SkillSlotPayload::new
    );
    //   public static final PacketCodec<PacketByteBuf, LiuBeiASkillPayload> CODEC = PacketCodec.tuple(Vec3d.PACKET_CODEC, LiuBeiASkillPayload::vec3d, LiuBeiASkillPayload::new);
    // 或者，你也可以这样写：
    // public static final PacketCodec<PacketByteBuf, BlockHighlightPayload> CODEC = PacketCodec.of((value, buf) -> buf.writeBlockPos(value.blockPos), buf -> new BlockHighlightPayload(buf.readBlockPos()));
    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}