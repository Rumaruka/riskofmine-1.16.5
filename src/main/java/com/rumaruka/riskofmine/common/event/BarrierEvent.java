package com.rumaruka.riskofmine.common.event;

import com.rumaruka.riskofmine.common.cap.barrier.ROMBarrier;
import com.rumaruka.riskofmine.common.cap.barrier.data.Barrier;
import com.rumaruka.riskofmine.events.MovingHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.HashMap;
import java.util.UUID;

public class BarrierEvent {

    @SubscribeEvent
    public static void onPlayerHurt(LivingHurtEvent event) {

    }
}
