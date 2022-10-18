package com.rumaruka.riskofmine.common.event;

import com.rumaruka.riskofmine.RiskOfMine;
import com.rumaruka.riskofmine.common.cap.money.ROMMoney;
import com.rumaruka.riskofmine.common.cap.money.data.Money;
import com.rumaruka.riskofmine.common.entity.weapon.EntityStickyBomb;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = RiskOfMine.MODID)
public class MoneyEvent {

    @SubscribeEvent
    public void onEntityDeath(LivingDeathEvent event) {

        if (event.getSource().getEntity() instanceof ServerPlayerEntity) {
            ServerPlayerEntity player = (ServerPlayerEntity) event.getSource().getEntity();
            LivingEntity livingEntity = event.getEntityLiving();
            World level = livingEntity.level;
            ROMMoney romMoney = ROMMoney.from(player);
            if (!level.isClientSide) {
                if (player != null) {
                    Money money = romMoney.money;
                    money.addMoney(player, 10.0f);
                    romMoney.detectAndSendChanges();
                }


            }

        }

        if (event.getSource().getEntity() instanceof CreatureEntity && event.getEntityLiving() instanceof ServerPlayerEntity) {
            CreatureEntity livingEntity = (CreatureEntity) event.getSource().getEntity();
            ServerPlayerEntity player = (ServerPlayerEntity) event.getEntityLiving();
            World world = player.level;
            ROMMoney romMoney = ROMMoney.from(player);
            if (!world.isClientSide) {
                if (event.getSource().isBypassInvul()) {
                    return;
                } else {
                    if (livingEntity != null) {
                        Money money = romMoney.money;
                        money.setMoney(0.0f);
                        romMoney.detectAndSendChanges();

                    }
                }


            }
        }

    }
}
