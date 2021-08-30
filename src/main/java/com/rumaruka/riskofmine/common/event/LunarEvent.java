package com.rumaruka.riskofmine.common.event;

import com.rumaruka.riskofmine.RiskOfMine;
import com.rumaruka.riskofmine.common.cap.lunar.ROMLunar;
import com.rumaruka.riskofmine.common.cap.lunar.data.Lunar;
import com.rumaruka.riskofmine.common.cap.money.ROMMoney;
import com.rumaruka.riskofmine.common.cap.money.data.Money;
import com.rumaruka.riskofmine.init.ROMItems;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = RiskOfMine.MODID)
public class LunarEvent {
    @SubscribeEvent
    public void onEntityDeath(LivingDeathEvent event) {

        if (event.getSource().getEntity() instanceof ServerPlayerEntity) {
            ServerPlayerEntity player = (ServerPlayerEntity) event.getSource().getEntity();
            LivingEntity livingEntity = event.getEntityLiving();
            World level = livingEntity.level;

            ROMLunar romLunar = ROMLunar.from(player);
            if (!level.isClientSide) {
                if (player != null) {
                    Lunar lunar = romLunar.lunar;

                    if(livingEntity.tickCount % 10==0){
                        ItemEntity itemEntity = new ItemEntity(level,livingEntity.getX(),livingEntity.getY(),livingEntity.getZ(),new ItemStack(ROMItems.LUNAR_COIN));
                        level.addFreshEntity(itemEntity);
                        romLunar.detectAndSendChanges();

                    }


                }


            }

        }
        if (event.getSource().getEntity() instanceof CreatureEntity && event.getEntityLiving() instanceof ServerPlayerEntity) {
            CreatureEntity livingEntity = (CreatureEntity) event.getSource().getEntity();
            ServerPlayerEntity player = (ServerPlayerEntity) event.getEntityLiving();
            World world = player.level;
            ROMLunar romLunar = ROMLunar.from(player);
            if (!world.isClientSide) {
                if (event.getSource().isBypassInvul()) {
                    return;
                } else {
                    if (livingEntity != null) {
                        Lunar lunar = romLunar.lunar;
                        lunar.setLunar(0.0f);
                        romLunar.detectAndSendChanges();

                    }
                }


            }
        }
    }
}
