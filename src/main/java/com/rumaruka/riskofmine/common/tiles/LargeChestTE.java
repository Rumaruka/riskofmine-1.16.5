package com.rumaruka.riskofmine.common.tiles;

import com.rumaruka.riskofmine.api.ChestsTypes;
import com.rumaruka.riskofmine.common.inventory.ChestInventory;
import com.rumaruka.riskofmine.init.ROMBlocks;
import com.rumaruka.riskofmine.init.ROMTiles;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;

public class LargeChestTE extends BaseChestTE {
    public LargeChestTE() {
        super(ROMTiles.LARGE_CHEST, ChestsTypes.LARGE, ROMBlocks.LARGE_CHEST);
    }

    @Override
    protected Container createMenu(int windowId, PlayerInventory playerInventory) {
        return ChestInventory.createLargeContainer(windowId, playerInventory, this);
    }

}
