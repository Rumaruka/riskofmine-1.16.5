package com.rumaruka.riskofmine.common.cap.money;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import org.jetbrains.annotations.Nullable;

public class ROMMoneyStorage implements Capability.IStorage<IMoney>{
    @Nullable
    @Override
    public INBT writeNBT(Capability<IMoney> capability, IMoney instance, Direction side) {
        return new CompoundNBT();
    }

    @Override
    public void readNBT(Capability<IMoney> capability, IMoney instance, Direction side, INBT nbt) {

    }
}
