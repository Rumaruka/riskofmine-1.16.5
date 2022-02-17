package com.rumaruka.riskofmine.events;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.world.World;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;

import java.util.HashMap;
import java.util.UUID;

@Mod.EventBusSubscriber
public class MovingHandler {
    private static final HashMap<UUID, MoveInfo> moveMap = new HashMap<>();
    private static final HashMap<UUID, MoveEntityInfo> moveEntityInfoMap = new HashMap<>();

    @SubscribeEvent
    public static void onPlayerIn(PlayerEvent.PlayerLoggedInEvent e) {
        moveMap.put(e.getPlayer().getUUID(), new MoveInfo(e.getPlayer()));

    }


    @SubscribeEvent
    public static void onPlayerOut(PlayerEvent.PlayerLoggedOutEvent e) {
        moveMap.remove(e.getPlayer().getUUID());

    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.side == LogicalSide.SERVER && event.phase == TickEvent.Phase.END) {
            MoveInfo moveInfo = moveMap.get(event.player.getUUID());
            if (moveInfo != null) {
                moveInfo.update(event.player);
            }
        }

    }
    @SubscribeEvent
    public static void onLivingTick(LivingEvent.LivingUpdateEvent event) {

            MoveEntityInfo moveInfo = moveEntityInfoMap.get(event.getEntity().getUUID());
            if (moveInfo != null) {
                moveInfo.updateEntity(event.getEntity());

        }

    }

    public static boolean isMoving(ServerPlayerEntity mp) {
        MoveInfo moveInfo = moveMap.get(mp.getUUID());
        if (moveInfo != null) {
            return moveInfo.isPosChanged();
        }
        return false;
    }



    public static boolean isMovingEntity(Entity entity) {
        MoveEntityInfo moveInfo = moveEntityInfoMap.get(entity.getUUID());
        if (moveInfo != null) {
            return moveInfo.isPosChanged();
        }
        return true;
    }


    public static class MoveInfo {
        private double lastPosX;
        private double lastPosY;
        private double lastPosZ;

        private boolean posChanged;

        public MoveInfo(PlayerEntity player) {
            updateLastPos(player);
        }

        private void updateLastPos(PlayerEntity player) {
            lastPosX = player.getX();
            lastPosY = player.getY();
            lastPosZ = player.getZ();
        }

        public void update(PlayerEntity player) {
            posChanged = lastPosX != player.position().x() || lastPosY != player.position().y() || lastPosZ != player.position().z();
            if (posChanged) updateLastPos(player);

        }

        public boolean isPosChanged() {
            return posChanged;
        }

    }

    public static class MoveEntityInfo {
        private double lastPosX;
        private double lastPosY;
        private double lastPosZ;

        private boolean posChanged;

        public MoveEntityInfo(PlayerEntity player) {
            updateLastPos(player);
        }


        private void updateLastPos(PlayerEntity player) {
            lastPosX = player.getX();
            lastPosY = player.getY();
            lastPosZ = player.getZ();
        }
        private void updateEntityLastPos(Entity player) {
            lastPosX = player.getX();
            lastPosY = player.getY();
            lastPosZ = player.getZ();
        }

        public void updateEntity(Entity player) {
            posChanged = lastPosX != player.position().x() || lastPosY != player.position().y() || lastPosZ != player.position().z();
            if (posChanged) updateEntityLastPos(player);

        }
        public boolean isPosChanged() {
            return posChanged;
        }

    }
}
