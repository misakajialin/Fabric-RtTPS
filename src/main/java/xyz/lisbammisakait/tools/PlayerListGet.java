package xyz.lisbammisakait.tools;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import xyz.lisbammisakait.compoennt.RtTPSComponents;

import java.util.List;
import java.util.stream.Collectors;

import static xyz.lisbammisakait.skill.MarkItem.MARKSLOT;

public class PlayerListGet {
    public static List<ServerPlayerEntity> getNonSelfAndNonRespawningPlayers(MinecraftServer server,PlayerEntity self){
        List<ServerPlayerEntity> playerList = server.getPlayerManager().getPlayerList().stream()
                .filter(player -> !player.equals(self))
                .filter(player -> !player.getInventory().getStack(MARKSLOT).getOrDefault(RtTPSComponents.ISWITHINRESPAWNPHASE_TYPE,true))
                .collect(Collectors.toList());
        return playerList;
    }

}
