package com.rumaruka.riskofmine.common.cap.barrier;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import org.jetbrains.annotations.Nullable;

public class ROMBarrierStorage implements Capability.IStorage<IBarrier> {
    @Nullable
    @Override
    public INBT writeNBT(Capability<IBarrier> capability, IBarrier instance, Direction side) {
        return new CompoundNBT();
    }

    @Override
    public void readNBT(Capability<IBarrier> capability, IBarrier instance, Direction side, INBT nbt) {


    }
}