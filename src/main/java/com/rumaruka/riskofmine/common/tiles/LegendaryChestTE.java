package com.rumaruka.riskofmine.common.tiles;

import com.rumaruka.riskofmine.api.ChestsTypes;
import com.rumaruka.riskofmine.common.inventory.ChestInventory;
import com.rumaruka.riskofmine.init.ROMBlocks;
import com.rumaruka.riskofmine.init.ROMTiles;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;

public class LegendaryChestTE extends BaseChestTE {
    public LegendaryChestTE() {
        super(ROMTiles.LEGENDARY_CHEST, ChestsTypes.LEGENDARY, ROMBlocks.LEGENDARY_CHEST);
    }

    @Override
    protected Container createMenu(int windowId, PlayerInventory playerInventory) {
        return ChestInventory.createLegendaryContainer(windowId, playerInventory, this);
    }

}
