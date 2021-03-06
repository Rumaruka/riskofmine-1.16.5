package com.rumaruka.riskofmine.common.tiles;

import com.rumaruka.riskofmine.api.ChestsTypes;
import com.rumaruka.riskofmine.common.inventory.ChestShopInventory;
import com.rumaruka.riskofmine.init.ROMBlocks;
import com.rumaruka.riskofmine.init.ROMTiles;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;

public class MultiShopTE extends BaseShopTE{
    public MultiShopTE() {
        super(ROMTiles.MULTI_SHOP, ChestsTypes.MULTI_SHOP, ROMBlocks.MULTI_SHOP);
    }
    @Override
    protected Container createMenu(int windowId, PlayerInventory playerInventory) {
        return ChestShopInventory.createMultiShopContainer(windowId, playerInventory, this);
    }
}
