package com.rumaruka.riskofmine.common.event;

import com.rumaruka.riskofmine.init.ROMEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class EffectEvent {

    @SubscribeEvent
    public static void onEntityHurt(LivingHurtEvent event) {
        Entity entity = event.getSource().getEntity();
        if (entity instanceof LivingEntity){

        }

    }
    @SubscribeEvent
    public static void onEntityAttack(LivingAttackEvent event) {

    }
    @SubscribeEvent
    public static void onEntityDeath(LivingDeathEvent event) {

    }
    @SubscribeEvent
    public static void onEntityUpdate(LivingEvent.LivingUpdateEvent event) {


    }
}
