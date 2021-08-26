package com.rumaruka.riskofmine.common.event;

import com.rumaruka.riskofmine.RiskOfMine;
import com.rumaruka.riskofmine.common.cap.ROMMoney;
import com.rumaruka.riskofmine.common.cap.data.Money;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = RiskOfMine.MODID)
public class MoneyEvent {

    @SubscribeEvent
    public  void onEntityDeath(LivingDeathEvent event) {

        if (event.getSource().getEntity() instanceof ServerPlayerEntity) {
            ServerPlayerEntity player = (ServerPlayerEntity) event.getSource().getEntity();
            LivingEntity livingEntity = event.getEntityLiving();
            World level = livingEntity.level;
            ROMMoney romMoney = ROMMoney.from(player);
            if (!level.isClientSide) {
                if (player != null) {
                    assert romMoney != null;
                    Money money = romMoney.money;

                    money.addMoney(player, 10.0f);

                    System.out.println("Money->" + money.getCurrentMoney());
                    romMoney.detectAndSendChanges();

                }


            }

        }
    }

}
