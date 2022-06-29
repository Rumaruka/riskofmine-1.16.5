package com.rumaruka.riskofmine.common.cap.barrier.data;

import net.minecraft.entity.player.PlayerEntity;
import ru.timeconqueror.timecore.common.capability.property.CoffeeProperty;
import ru.timeconqueror.timecore.common.capability.property.container.PropertyContainer;

public class Barrier extends PropertyContainer {
    public final CoffeeProperty<Float> currentBarrier = prop("current_barrier", 0F).synced();
    public final CoffeeProperty<Float> burnoutTime = prop("burnout", 0F).synced();
    public final CoffeeProperty<Integer> timeout = prop("timeout", 0);


    public static float getMaxBarrier(PlayerEntity player) {
        return player.getMaxHealth();
    }

    public float getCurrentBarrier() {
        return currentBarrier.get();
    }

    public void setBurnout(float value) {
        burnoutTime.set(Math.max(value, 0.0F));
    }

    public void setTimeout(int value) {
        timeout.set(value);
    }

    public float getBurnout() {
        return burnoutTime.get();
    }

    public int getTimeout() {
        return timeout.get();
    }

    public void setBarrier(float value) {
        if (getCurrentBarrier() != value) {
            currentBarrier.set(value);
        }
    }

    public boolean isRegenReady() {
        if (getTimeout() <= 0) return getBurnout() <= 0;
        setTimeout(getTimeout() - 1);
        return false;
    }

    public void addBarrier(PlayerEntity player, float add) {
        setBarrier(Math.min(getCurrentBarrier() + add, getMaxBarrier(player)));
    }

    public boolean consumeBarrier(PlayerEntity player, float price) {
        if (!player.isCreative()) {
            if (hasBarrier()) {
                setBarrier(getCurrentBarrier() - price);
                setTimeout(20);
                return true;
            }

            return false;
        }
        return true;
    }

    public boolean hasBarrier() {
        return getCurrentBarrier()!=0;
    }

    public boolean checkBarrierEitherSide(boolean client, PlayerEntity player, float price) {
        if (client) {
            return player.isCreative() || hasBarrier();
        }
        return consumeBarrier(player, price);
    }
}
