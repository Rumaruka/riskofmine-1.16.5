package com.rumaruka.riskofmine.common.tiles;

import com.rumaruka.riskofmine.api.ChestsTypes;
import com.rumaruka.riskofmine.common.inventory.ChestInventory;
import com.rumaruka.riskofmine.init.ROMBlocks;
import com.rumaruka.riskofmine.init.ROMTiles;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;

public class LunarChestTE extends BaseChestTE{
    public LunarChestTE() {
        super(ROMTiles.LUNAR_CHEST, ChestsTypes.LUNAR, ROMBlocks.LUNAR_CHEST);
    }

    @Override
    protected Container createMenu(int windowId, PlayerInventory playerInventory) {
        return ChestInventory.createLunarContainer(windowId, playerInventory, this);
    }
}
