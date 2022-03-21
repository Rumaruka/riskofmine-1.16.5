package com.rumaruka.riskofmine.common.event;

import com.rumaruka.riskofmine.RiskOfMine;
import com.rumaruka.riskofmine.common.cap.shields.ROMShields;
import com.rumaruka.riskofmine.common.cap.shields.data.Shields;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.world.World;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = RiskOfMine.MODID)
public class ShieldsEvent {

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        PlayerEntity player = event.player;
        ROMShields romShields = ROMShields.from(player);
        Shields shields = romShields.shields;


        shields.setShields(event.player.tickCount--);
        romShields.detectAndSendChanges();
    }


    @SubscribeEvent
    public void onEntityDeath(LivingDeathEvent event) {

        if (event.getSource().getEntity() instanceof ServerPlayerEntity) {
            ServerPlayerEntity player = (ServerPlayerEntity) event.getSource().getEntity();
            LivingEntity livingEntity = event.getEntityLiving();
            World level = livingEntity.level;
            ROMShields romShields = ROMShields.from(player);

            if (!level.isClientSide) {
                if (player != null) {
                    Shields shields = romShields.shields;
                    shields.addShields(player, player.tickCount);
                    romShields.detectAndSendChanges();
                }


            }

        }
    }

    @SubscribeEvent
    public static void onPlayerHurt(LivingHurtEvent event) {
        event.setCanceled(true);
    }
}
