package xyz.lisbammisakait.skill;

import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import xyz.lisbammisakait.compoennt.RtTPSComponents;

import static xyz.lisbammisakait.skill.MarkItem.MARKSLOT;

public interface ActiveSkillable {
    default void castSkill(MinecraftServer server, ServerPlayerEntity player, ItemStack stack){
        if(!player.getInventory().getStack(MARKSLOT).get(RtTPSComponents.ISWITHINRESPAWNPHASE_TYPE)){
            processPracticalSkill(server, player, stack);
        }
    }
    void processPracticalSkill(MinecraftServer server, ServerPlayerEntity player, ItemStack stack);
}
