package com.rumaruka.riskofmine.common.cap.timer.data;

import net.minecraft.entity.player.PlayerEntity;
import ru.timeconqueror.timecore.common.capability.property.CoffeeProperty;
import ru.timeconqueror.timecore.common.capability.property.container.PropertyContainer;


public class Timer extends PropertyContainer {
    public final CoffeeProperty<Float> currentTimer = prop("current_Timer", 0F).synced();
    public final CoffeeProperty<Float> burnoutTime = prop("burnout", 0F).synced();
    public final CoffeeProperty<Integer> timeout = prop("timeout", 0);

    public static float getMaxTimer(PlayerEntity player) {
        return 10000;
    }

    public float getCurrentTimer() {
        return currentTimer.get();
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

    public void setTimer(float value) {
        if (getCurrentTimer() != value) {
            currentTimer.set(value);
        }
    }

    public boolean isRegenReady() {
        if (getTimeout() <= 0) return getBurnout() <= 0;
        setTimeout(getTimeout() - 1);
        return false;
    }

    public void addTimer(PlayerEntity player, float add) {
        setTimer(Math.min(getCurrentTimer() + add, getMaxTimer(player)));
    }

    public boolean consumeTimer(PlayerEntity player, float price) {
        if (!player.isCreative()) {
            if (hasTimer(price)) {
                setTimer(getCurrentTimer() - price);
                setTimeout(20);
                return true;
            }

            return false;
        }
        return true;
    }

    public boolean hasTimer(float price) {
        return getCurrentTimer() >= price;
    }

    public boolean checkTimerEitherSide(boolean client, PlayerEntity player, float price) {
        if (client) {
            return player.isCreative() || hasTimer(price);
        }
        return consumeTimer(player, price);
    }


}
