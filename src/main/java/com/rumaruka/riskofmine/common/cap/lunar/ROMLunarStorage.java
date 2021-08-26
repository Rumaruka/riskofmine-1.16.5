package com.rumaruka.riskofmine.common.cap.lunar;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import org.jetbrains.annotations.Nullable;

public class ROMLunarStorage implements Capability.IStorage<ILunar> {
    @Nullable
    @Override
    public INBT writeNBT(Capability<ILunar> capability, ILunar instance, Direction side) {
        return new CompoundNBT();
    }

    @Override
    public void readNBT(Capability<ILunar> capability, ILunar instance, Direction side, INBT nbt) {

    }
}
