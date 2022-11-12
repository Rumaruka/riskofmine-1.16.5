package com.rumaruka.riskofmine.common.event;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.rumaruka.riskofmine.common.cap.timer.ROMTimer;
import com.rumaruka.riskofmine.common.cap.timer.data.Timer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.monster.SkeletonEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Objects;
import java.util.UUID;

@Mod.EventBusSubscriber
public class DifficultyEvent {
    public static final UUID healthModifierID = UUID.fromString("5D6F0BA2-1186-46AC-B896-C61C5CEE99CC");

   @SubscribeEvent
   public static void onUpdate(LivingEvent.LivingUpdateEvent event){
       LivingEntity allEntity = event.getEntityLiving();
       PlayerEntity player = Minecraft.getInstance().player;


       ROMTimer romTimer = ROMTimer.from(player);

              if (romTimer!=null){
               Timer timer = romTimer.timer;

               if(player.tickCount % 1000 == 0){
                   timer.addTimer(player,1);


               }
                  if (timer.getCurrentTimer()>1000 && !(allEntity instanceof PlayerEntity)){
                      if (allEntity instanceof ZombieEntity||allEntity instanceof SkeletonEntity){
                          allEntity.addEffect(new EffectInstance(Effects.HARM,2,1,true,false));
                      }else {
                          allEntity.addEffect(new EffectInstance(Effects.HEAL,2,1,true,false));

                      }


                  }



               romTimer.detectAndSendChanges();
           }
       }








}
