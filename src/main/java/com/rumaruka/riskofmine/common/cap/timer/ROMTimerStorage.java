package com.rumaruka.riskofmine.common.cap.timer;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.FloatNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.IntNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.EnergyStorage;
import org.jetbrains.annotations.Nullable;

public class ROMTimerStorage implements Capability.IStorage<ITimer> {

    @Nullable
    @Override
    public INBT writeNBT(Capability<ITimer> capability, ITimer instance, Direction side) {

        ROMTimer iTimer = (ROMTimer) instance;

        return FloatNBT.valueOf( iTimer.timer.getCurrentTimer());
    }

    @Override
    public void readNBT(Capability<ITimer> capability, ITimer instance, Direction side, INBT nbt) {
        if (!(instance instanceof ROMTimer))
            throw new IllegalArgumentException("Can not deserialize to an instance that isn't the default implementation");
        float currentTimer = ((ROMTimer) instance).timer.getCurrentTimer();
        currentTimer = ((FloatNBT)nbt).getAsFloat();
        ((ROMTimer) instance).deserialize((CompoundNBT) nbt);
    }
}
