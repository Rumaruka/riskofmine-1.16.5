package com.rumaruka.riskofmine.common.blocks.chests;

import com.rumaruka.riskofmine.api.ChestsTypes;
import com.rumaruka.riskofmine.common.cap.money.ROMMoney;
import com.rumaruka.riskofmine.common.cap.money.data.Money;
import com.rumaruka.riskofmine.common.tiles.BaseShopTE;
import com.rumaruka.riskofmine.common.tiles.EquipmentTripleBarrelTE;
import com.rumaruka.riskofmine.init.ROMSounds;
import com.rumaruka.riskofmine.init.ROMTiles;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.entity.monster.piglin.PiglinTasks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.stats.Stats;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class EquipmentTripleBarrelBlock extends GenericShopBlock {
    public EquipmentTripleBarrelBlock() {
        super(ChestsTypes.EQUIPMENT_TRIPLE_BARREL, ROMTiles.MULTI_SHOP, Properties.of(Material.STONE).strength(5.0F, 5.0F));
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(WATERLOGGED, Boolean.FALSE).setValue(CLOSED,Boolean.FALSE));

    }
    @Override
    public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        ROMMoney romMoney = ROMMoney.from(player);
        Money money = romMoney.money;
        if (worldIn.isClientSide) {
            return ActionResultType.SUCCESS;
        } else {
            TileEntity tileentity = worldIn.getBlockEntity(pos);
            if (tileentity instanceof BaseShopTE &&!player.abilities.instabuild) {
                if(money.getCurrentMoney()==0){
                    worldIn.playSound(null, pos, ROMSounds.ROM_CHEST_NOT_MONEY.get(), SoundCategory.BLOCKS, 2.0F, 1.0F);
                    player.displayClientMessage(new TranslationTextComponent("riskofmine.not_money"), true);
                }
                if(money.getCurrentMoney()>0 && state.getValue(CLOSED)==Boolean.FALSE){
                    money.consumeMoney(player,25);
                    romMoney.detectAndSendChanges();
                    player.openMenu((BaseShopTE) tileentity);
                    player.awardStat(Stats.OPEN_CHEST);
                    PiglinTasks.angerNearbyPiglins(player, true);
                    BlockState blockstate = state.setValue(CLOSED, Boolean.TRUE);
                    worldIn.setBlock(pos,blockstate,3);
                }

                if(state.getValue(CLOSED)==Boolean.TRUE){
                    worldIn.setBlock(pos,Blocks.AIR.defaultBlockState(),3);
                    return ActionResultType.FAIL;
                }
            }



        }

        return ActionResultType.CONSUME;

    }

    @Nullable
    @Override
    public TileEntity newBlockEntity(IBlockReader worldIn) {
        return new EquipmentTripleBarrelTE();
    }

}
