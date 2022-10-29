package com.rumaruka.riskofmine.common.cap.timer;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import org.jetbrains.annotations.Nullable;

public class ROMTimerStorage implements Capability.IStorage<ITimer>{
    @Nullable
    @Override
    public INBT writeNBT(Capability<ITimer> capability, ITimer instance, Direction side) {
        return new CompoundNBT();
    }

    @Override
    public void readNBT(Capability<ITimer> capability, ITimer instance, Direction side, INBT nbt) {

    }
}
