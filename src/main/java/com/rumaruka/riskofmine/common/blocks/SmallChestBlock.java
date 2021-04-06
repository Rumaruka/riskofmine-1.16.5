package com.rumaruka.riskofmine.common.blocks;

import com.rumaruka.riskofmine.api.ChestsTypes;
import com.rumaruka.riskofmine.common.config.ModConfig;
import com.rumaruka.riskofmine.common.tiles.CommonChestTE;
import com.rumaruka.riskofmine.init.ROMTiles;
import net.minecraft.block.material.Material;
import net.minecraft.item.Items;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;
import org.jetbrains.annotations.Nullable;

public class SmallChestBlock extends GenericChestBlock {
    public SmallChestBlock() {
        super(Items.GOLD_INGOT, ModConfig.countSmallOpen.get(), ChestsTypes.SMALL, ROMTiles.SMALL_CHEST, Properties.of(Material.STONE).strength(-1.0F, 3600000.0F));
    }

    @Nullable
    @Override
    public TileEntity newBlockEntity(IBlockReader worldIn) {
        return new CommonChestTE();
    }

}
