package com.rumaruka.riskofmine.common.inventory;

import com.rumaruka.riskofmine.api.ChestsTypes;
import com.rumaruka.riskofmine.common.inventory.slots.SingleSlot;
import com.rumaruka.riskofmine.common.inventory.slots.TripleSlot;
import com.rumaruka.riskofmine.init.ROMContainerTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

public class ChestShopInventory extends Container {

    private final IInventory inventory;

    private final ChestsTypes chestType;



    public static @NotNull ChestShopInventory createEquipmentTripleBarrelContainer(int windowId, PlayerInventory playerInventory) {
        return new ChestShopInventory(ROMContainerTypes.EQUIPMENT_TRIPLE_BARREL, windowId, playerInventory, new Inventory(ChestsTypes.EQUIPMENT_TRIPLE_BARREL.size), ChestsTypes.EQUIPMENT_TRIPLE_BARREL);
    }

    public static ChestShopInventory createEquipmentTripleBarrelContainer(int windowId, PlayerInventory playerInventory, IInventory inventory) {
        return new ChestShopInventory(ROMContainerTypes.EQUIPMENT_TRIPLE_BARREL, windowId, playerInventory, inventory, ChestsTypes.EQUIPMENT_TRIPLE_BARREL);
    }


    public static @NotNull ChestShopInventory createMultiShopContainer(int windowId, PlayerInventory playerInventory) {
        return new ChestShopInventory(ROMContainerTypes.MULTI_SHOP, windowId, playerInventory, new Inventory(ChestsTypes.MULTI_SHOP.size), ChestsTypes.MULTI_SHOP);
    }

    public static ChestShopInventory createMultiShopContainer(int windowId, PlayerInventory playerInventory, IInventory inventory) {
        return new ChestShopInventory(ROMContainerTypes.MULTI_SHOP, windowId, playerInventory, inventory, ChestsTypes.MULTI_SHOP);
    }



    public ChestShopInventory(ContainerType<?> containerType, int windowId, PlayerInventory playerInventory, IInventory inventory, ChestsTypes chestType) {
        super(containerType, windowId);
        checkContainerSize(inventory, chestType.size);

        this.inventory = inventory;
        this.chestType = chestType;

        inventory.startOpen(playerInventory.player);
        if (chestType == ChestsTypes.EQUIPMENT_TRIPLE_BARREL || chestType == ChestsTypes.MULTI_SHOP ) {
            this.addSlot(new TripleSlot(inventory, 0, 12 + 4 * 18, 8 + 2 * 18));
            this.addSlot(new TripleSlot(inventory, 1, 12 + 3 * 18, 8 + 3 * 18));
            this.addSlot(new TripleSlot(inventory, 2, 12 + 2 * 18, 8 + 4 * 18));
        } else {
            for (int chestRow = 0; chestRow < chestType.getRowCount(); chestRow++) {
                for (int chestCol = 0; chestCol < chestType.rowLength; chestCol++) {
                    this.addSlot(new Slot(inventory, chestCol + chestRow * chestType.rowLength, 12 + chestCol * 18, 18 + chestRow * 18));
                }
            }
        }

        int leftCol = (chestType.xSize - 162) / 2 + 1;

        for (int playerInvRow = 0; playerInvRow < 3; playerInvRow++) {
            for (int playerInvCol = 0; playerInvCol < 9; playerInvCol++) {
                this.addSlot(new Slot(playerInventory, playerInvCol + playerInvRow * 9 + 9, leftCol + playerInvCol * 18, chestType.ySize - (4 - playerInvRow) * 18 - 10));
            }

        }

        for (int hotbarSlot = 0; hotbarSlot < 9; hotbarSlot++) {
            this.addSlot(new Slot(playerInventory, hotbarSlot, leftCol + hotbarSlot * 18, chestType.ySize - 24));
        }
    }

    @Override
    public boolean stillValid(PlayerEntity playerIn) {
        return this.inventory.stillValid(playerIn);
    }

    @Override
    public ItemStack quickMoveStack(PlayerEntity playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (index < this.chestType.size) {
                if (!this.moveItemStackTo(itemstack1, this.chestType.size, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemstack1, 0, this.chestType.size, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }
        return itemstack;


    }

    @Override
    public void removed(PlayerEntity playerIn) {
        super.removed(playerIn);
        this.inventory.stopOpen(playerIn);
    }

    @OnlyIn(Dist.CLIENT)
    public ChestsTypes getChestType() {
        return this.chestType;
    }

    public IInventory getInventory() {
        return this.inventory;
    }
}