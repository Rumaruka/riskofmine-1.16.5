package com.rumaruka.riskofmine.common.tiles;

import com.rumaruka.riskofmine.api.ChestsTypes;
import com.rumaruka.riskofmine.common.inventory.ChestShopInventory;
import com.rumaruka.riskofmine.init.ROMBlocks;
import com.rumaruka.riskofmine.init.ROMTiles;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.tileentity.ITickableTileEntity;

public class MultiShopTE extends BaseShopTE implements ITickableTileEntity {
    // private AnimationSystem<MultiShopTE> animationSystem;

    public MultiShopTE() {
        super(ROMTiles.MULTI_SHOP, ChestsTypes.MULTI_SHOP, ROMBlocks.MULTI_SHOP);
    }

    @Override
    protected Container createMenu(int windowId, PlayerInventory playerInventory) {
        return ChestShopInventory.createMultiShopContainer(windowId, playerInventory, this);
    }

//    @Override
//    public void onLoad() {
//
//        animationSystem = AnimationSystemBuilder.forTileEntity(this, level);
//    }

//    @Override
//    public void tick() {
//        BlockState state = getBlockState();
//        if (getLevel().isClientSide) {
//            if (state.getBlock() instanceof GenericShopBlock) {
//                if (state.getValue(GenericShopBlock.CLOSED) == Boolean.TRUE) {
//                    new AnimationStarter(ROMAnimation.CLOSED).setIgnorable(true).startAt(getAnimationManager(), AnimationConstants.MAIN_LAYER_NAME);
//
//                }
//
//
//            }
//
//        }
//
//    }

//    @Override
//    public @NotNull AnimationSystem<MultiShopTE> getSystem() {
//        return animationSystem;
//    }
}
