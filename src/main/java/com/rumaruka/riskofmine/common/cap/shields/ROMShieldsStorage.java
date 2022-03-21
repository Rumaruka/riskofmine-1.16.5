package com.rumaruka.riskofmine.common.cap.shields;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import org.jetbrains.annotations.Nullable;

public class ROMShieldsStorage implements Capability.IStorage<IShields> {
    @Nullable
    @Override
    public INBT writeNBT(Capability<IShields> capability, IShields instance, Direction side) {
        return new CompoundNBT();
    }

    @Override
    public void readNBT(Capability<IShields> capability, IShields instance, Direction side, INBT nbt) {


    }
}