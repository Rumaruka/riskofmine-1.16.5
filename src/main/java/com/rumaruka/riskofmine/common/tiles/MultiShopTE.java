package com.rumaruka.riskofmine.common.tiles;

import com.rumaruka.riskofmine.api.ChestsTypes;
import com.rumaruka.riskofmine.common.inventory.ChestInventory;
import com.rumaruka.riskofmine.common.inventory.ChestShopInventory;
import com.rumaruka.riskofmine.init.ROMBlocks;
import com.rumaruka.riskofmine.init.ROMTiles;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.tileentity.TileEntityType;

public class MultiShopTE extends BaseShopTE{
    public MultiShopTE() {
        super(ROMTiles.MULTI_SHOP, ChestsTypes.MULTI_SHOP, ROMBlocks.MULTI_SHOP);
    }
    @Override
    protected Container createMenu(int windowId, PlayerInventory playerInventory) {
        return ChestShopInventory.createMultiShopContainer(windowId, playerInventory, this);
    }
}
