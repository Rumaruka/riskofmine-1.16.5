package com.rumaruka.riskofmine.common.cap.shields.data;

import net.minecraft.entity.player.PlayerEntity;
import ru.timeconqueror.timecore.common.capability.property.CoffeeProperty;
import ru.timeconqueror.timecore.common.capability.property.container.PropertyContainer;

public class Shields extends PropertyContainer {
    public final CoffeeProperty<Float> currentShields = prop("current_barrier", 0F).synced();
    public final CoffeeProperty<Float> burnoutTime = prop("burnout", 0F).synced();
    public final CoffeeProperty<Integer> timeout = prop("timeout", 0);


    public static float getMaxShields(PlayerEntity player) {
        return player.getMaxHealth();
    }

    public float getCurrentShields() {
        return currentShields.get();
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

    public void setShields(float value) {
        if (getCurrentShields() != value) {
            currentShields.set(value);
        }
    }

    public boolean isRegenReady() {
        if (getTimeout() <= 0) return getBurnout() <= 0;
        setTimeout(getTimeout() - 1);
        return false;
    }

    public void addShields(PlayerEntity player, float add) {
        setShields(Math.min(getCurrentShields() + add, getMaxShields(player)));
    }
    public void decreaseShields(PlayerEntity player){
        for(float i = 0; i<this.getCurrentShields(); i-- ){
            addShields(player,i);
            if(i<=getMaxShields(player)){
                setShields(0);
            }

        }
    }

    public boolean consumeShields(PlayerEntity player, float price) {
        if (!player.isCreative()) {
            if (hasShields()) {
                setShields(getCurrentShields() - price);
                setTimeout(20);
                return true;
            }

            return false;
        }
        return true;
    }

    public boolean hasShields() {
        return getCurrentShields()!=0;
    }

    public boolean checkShieldsEitherSide(boolean client, PlayerEntity player, float price) {
        if (client) {
            return player.isCreative() || hasShields();
        }
        return consumeShields(player, price);
    }
}
