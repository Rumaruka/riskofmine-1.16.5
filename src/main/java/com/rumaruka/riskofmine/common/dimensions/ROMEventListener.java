package com.rumaruka.riskofmine.common.dimensions;

import com.rumaruka.riskofmine.RiskOfMine;
import com.rumaruka.riskofmine.init.ROMDimensions;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = RiskOfMine.MODID)
public class ROMEventListener {
    private static final String TAG_ROOST_START = "distant_roost";
    @SubscribeEvent
    public  void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        PlayerEntity player =event.getPlayer();
        CompoundNBT nbt = player.getPersistentData();
        CompoundNBT tag = nbt.getCompound(PlayerEntity.PERSISTED_NBT_TAG);
        tag.putBoolean(TAG_ROOST_START,true);
        tag.put(PlayerEntity.PERSISTED_NBT_TAG,tag);
        if(player instanceof ServerPlayerEntity && player.level instanceof ServerWorld){
            ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity) player;
            ServerWorld world = (ServerWorld) player.level;


            serverPlayerEntity.setRespawnPosition(ROMDimensions.DISTANT_ROOST_WORLD, serverPlayerEntity.getRespawnPosition(),serverPlayerEntity.getYHeadRot(),true,false);
            world.setDefaultSpawnPos(serverPlayerEntity.blockPosition(),16);
        }


    }

}
