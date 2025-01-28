package xyz.lisbammisakait.skill;

import net.minecraft.client.MinecraftClient;
//所有技能类请实现此接口
public interface Skillable {
    public void useSkill(MinecraftClient client);
}
