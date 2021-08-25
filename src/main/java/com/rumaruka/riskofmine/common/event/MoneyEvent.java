package com.rumaruka.riskofmine.common.event;

import com.rumaruka.riskofmine.RiskOfMine;
import com.rumaruka.riskofmine.common.cap.ROMMoney;
import com.rumaruka.riskofmine.common.cap.data.Money;
import com.rumaruka.riskofmine.init.ROMAttributes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Objects;

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
                if (Money.getMaxMoney(player) > 0) {
                    assert romMoney != null;
                    Money money = romMoney.money;
                    if (money.isRegenReady()) {
                        money.addMoney(player, (float) Objects.requireNonNull(player.getAttribute(ROMAttributes.MONEY_REGEN_SPEED.get())).getValue());
                    } else {
                        money.setBurnout(money.getBurnout() - 0.1f);
                    }
                    romMoney.detectAndSendChanges();
                }

            }
        }
    }
}
