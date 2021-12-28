package com.rumaruka.riskofmine.common.event;


import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class MobEvent {

    @SubscribeEvent
    public static void updateMob(LivingEvent.LivingUpdateEvent event){
        LivingEntity livingEntity = event.getEntityLiving();
        IElite elite = (IElite) livingEntity;
        elite.setEliteMobs(livingEntity.addEffect(new EffectInstance(Effects.ABSORPTION)));
    }
}
