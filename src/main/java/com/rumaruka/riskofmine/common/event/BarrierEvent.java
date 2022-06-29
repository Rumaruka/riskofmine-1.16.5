package com.rumaruka.riskofmine.common.event;

import com.rumaruka.riskofmine.common.cap.barrier.ROMBarrier;
import com.rumaruka.riskofmine.common.cap.barrier.data.Barrier;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class BarrierEvent {
    @SubscribeEvent
    public static void onPlayerHurt(LivingHurtEvent event) {
        DamageSource source = event.getSource();
        if (source.getEntity() instanceof MobEntity && event.getEntityLiving() instanceof ServerPlayerEntity) {

            ServerPlayerEntity player = (ServerPlayerEntity) event.getEntityLiving();
            MobEntity mob = (MobEntity) event.getSource().getEntity();
            World world = player.level;
            if (!world.isClientSide) {
                ROMBarrier romBarrier = ROMBarrier.from(player);
                Barrier barrier = romBarrier.barrier;
                if(player.isInvulnerableTo(source)){
                    barrier.addBarrier(player,10);
                }else {
                    barrier.addBarrier(player,-1);
                    event.setCanceled(true);
                }
                romBarrier.detectAndSendChanges();
            }
        }
    }
}
