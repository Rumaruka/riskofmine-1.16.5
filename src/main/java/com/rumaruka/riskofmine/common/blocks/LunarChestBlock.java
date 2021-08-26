package com.rumaruka.riskofmine.common.blocks;

import com.rumaruka.riskofmine.api.ChestsTypes;
import com.rumaruka.riskofmine.common.cap.lunar.ROMLunar;
import com.rumaruka.riskofmine.common.cap.lunar.data.Lunar;
import com.rumaruka.riskofmine.common.cap.money.ROMMoney;
import com.rumaruka.riskofmine.common.cap.money.data.Money;
import com.rumaruka.riskofmine.common.tiles.BaseChestTE;
import com.rumaruka.riskofmine.common.tiles.CommonChestTE;
import net.minecraft.block.BlockState;
import net.minecraft.entity.monster.piglin.PiglinTasks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.stats.Stats;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class LunarChestBlock extends GenericChestBlock{
    public LunarChestBlock(ChestsTypes typeIn, TileEntityType<? extends BaseChestTE> tileEntityTypeSupplierIn, Properties propertiesIn) {
        super(typeIn, tileEntityTypeSupplierIn, propertiesIn);
    }

    @Override
    public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        ROMLunar romLunar = ROMLunar.from(player);
        Lunar money = romLunar.lunar;
        if (worldIn.isClientSide) {

            return ActionResultType.SUCCESS;

        } else {
            TileEntity tileentity = worldIn.getBlockEntity(pos);

            if (tileentity instanceof BaseChestTE &&!player.abilities.instabuild) {
                if(money.getCurrentLunar()>0){
                    money.consumeLunar(player,1.0f);
                    player.openMenu((BaseChestTE) tileentity);
                    player.awardStat(Stats.OPEN_BARREL);
                    PiglinTasks.angerNearbyPiglins(player, true);

                }


            }
        }

        return ActionResultType.CONSUME;
    }
    @Nullable
    @Override
    public TileEntity newBlockEntity(IBlockReader worldIn) {
        return null;
    }
}
