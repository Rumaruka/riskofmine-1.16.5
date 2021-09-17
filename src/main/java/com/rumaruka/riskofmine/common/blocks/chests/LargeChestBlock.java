package com.rumaruka.riskofmine.common.blocks.chests;

import com.rumaruka.riskofmine.api.ChestsTypes;
import com.rumaruka.riskofmine.common.cap.money.ROMMoney;
import com.rumaruka.riskofmine.common.cap.money.data.Money;
import com.rumaruka.riskofmine.common.config.ROMConfig;
import com.rumaruka.riskofmine.common.tiles.BaseChestTE;
import com.rumaruka.riskofmine.common.tiles.LargeChestTE;
import com.rumaruka.riskofmine.init.ROMSounds;
import com.rumaruka.riskofmine.init.ROMTiles;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.monster.piglin.PiglinTasks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.stats.Stats;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class LargeChestBlock extends GenericChestBlock {
    public LargeChestBlock() {
        super(ChestsTypes.LARGE, ROMTiles.LARGE_CHEST, Properties.of(Material.STONE).strength(-1.0F, 3600000.0F));
    }
    @Override
    public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        ROMMoney romMoney = ROMMoney.from(player);
        Money money = romMoney.money;
        if (worldIn.isClientSide) {

            return ActionResultType.SUCCESS;

        } else {
            TileEntity tileentity = worldIn.getBlockEntity(pos);
            if (tileentity instanceof BaseChestTE &&!player.abilities.instabuild) {
                if(money.getCurrentMoney()< ROMConfig.General.priceLargeChest.get()){
                    worldIn.playSound(null, pos, ROMSounds.ROM_CHEST_NOT_MONEY.get(), SoundCategory.BLOCKS, 2.0F, 1.0F);
                    player.displayClientMessage(new TranslationTextComponent("riskofmine.not_money"), true);
                }
            }

            if(money.getCurrentMoney()>= ROMConfig.General.priceLargeChest.get()){
                money.consumeMoney(player, ROMConfig.General.priceLargeChest.get());
                romMoney.detectAndSendChanges();
                player.openMenu((BaseChestTE) tileentity);
                player.awardStat(Stats.OPEN_CHEST);
                PiglinTasks.angerNearbyPiglins(player, true);
            }

        }

        return ActionResultType.CONSUME;

    }

    @Nullable
    @Override
    public TileEntity newBlockEntity(IBlockReader worldIn) {
        return new LargeChestTE();
    }

}
