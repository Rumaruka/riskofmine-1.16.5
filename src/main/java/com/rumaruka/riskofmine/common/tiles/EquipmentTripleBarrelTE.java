package com.rumaruka.riskofmine.common.tiles;

import com.rumaruka.riskofmine.api.ChestsTypes;
import com.rumaruka.riskofmine.common.inventory.ChestShopInventory;
import com.rumaruka.riskofmine.init.ROMBlocks;
import com.rumaruka.riskofmine.init.ROMTiles;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;

public class EquipmentTripleBarrelTE extends BaseShopTE{
    public EquipmentTripleBarrelTE() {
        super(ROMTiles.EQUIPMENT_TRIPLE_BARREL, ChestsTypes.EQUIPMENT_TRIPLE_BARREL, ROMBlocks.EQUIPMENT_TRIPLE_BARREL);
    }
    @Override
    protected Container createMenu(int windowId, PlayerInventory playerInventory) {
        return ChestShopInventory.createEquipmentTripleBarrelContainer(windowId, playerInventory, this);
    }
}
