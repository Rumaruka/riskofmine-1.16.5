package com.rumaruka.riskofmine.common.inventory.slots;

import com.rumaruka.riskofmine.common.items.ItemCollectiblesBase;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

public class TripleSlot extends Slot {
    public TripleSlot(IInventory inventoryIn, int slotIndex, int xPosition, int yPosition) {
        super(inventoryIn, slotIndex, xPosition, yPosition);
    }

    @Override
    public boolean mayPlace(ItemStack stack) {

        return stack.getItem() instanceof ItemCollectiblesBase;
    }
}
