package com.rumaruka.riskofmine.common.inventory;

import com.rumaruka.riskofmine.api.ChestsTypes;
import com.rumaruka.riskofmine.common.inventory.slots.SingleSlot;
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

public class ChestInventory extends Container {

    private final IInventory inventory;

    private final ChestsTypes chestType;

    public static ChestInventory createLargeContainer(int windowId, PlayerInventory playerInventory) {
        return new ChestInventory(ROMContainerTypes.LARGE_CHEST, windowId, playerInventory, new Inventory(ChestsTypes.LARGE.size), ChestsTypes.LARGE);
    }

    public static ChestInventory createLargeContainer(int windowId, PlayerInventory playerInventory, IInventory inventory) {
        return new ChestInventory(ROMContainerTypes.LARGE_CHEST, windowId, playerInventory, inventory, ChestsTypes.LARGE);
    }

    public static ChestInventory createCommonContainer(int windowId, PlayerInventory playerInventory) {
        return new ChestInventory(ROMContainerTypes.SMALL_CHEST, windowId, playerInventory, new Inventory(ChestsTypes.SMALL.size), ChestsTypes.SMALL);
    }

    public static ChestInventory createCommonContainer(int windowId, PlayerInventory playerInventory, IInventory inventory) {
        return new ChestInventory(ROMContainerTypes.SMALL_CHEST, windowId, playerInventory, inventory, ChestsTypes.SMALL);
    }

    public ChestInventory(ContainerType<?> containerType, int windowId, PlayerInventory playerInventory, IInventory inventory, ChestsTypes chestType) {
        super(containerType, windowId);
        checkContainerSize(inventory, chestType.size);

        this.inventory = inventory;
        this.chestType = chestType;

        inventory.startOpen(playerInventory.player);

        if (chestType == ChestsTypes.SMALL || chestType == ChestsTypes.LARGE) {
            this.addSlot(new SingleSlot(inventory, 0, 12 + 4 * 18, 8 + 2 * 18));
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