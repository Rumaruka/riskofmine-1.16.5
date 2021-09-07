package com.rumaruka.riskofmine.common.blocks;

import com.rumaruka.riskofmine.api.ChestsTypes;
import com.rumaruka.riskofmine.common.cap.money.ROMMoney;
import com.rumaruka.riskofmine.common.cap.money.data.Money;
import com.rumaruka.riskofmine.common.config.ModConfig;
import com.rumaruka.riskofmine.common.tiles.BaseChestTE;
import com.rumaruka.riskofmine.common.tiles.CommonChestTE;
import com.rumaruka.riskofmine.init.ROMSounds;
import com.rumaruka.riskofmine.init.ROMTiles;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.monster.piglin.PiglinTasks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.stats.Stats;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class SmallChestBlock extends GenericChestBlock {
    public SmallChestBlock() {
        super(ChestsTypes.SMALL, ROMTiles.SMALL_CHEST, Properties.of(Material.STONE).strength(5.0F, 5.0F));
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
                if(money.getCurrentMoney()<ModConfig.priceSmallChest.get()){
                    worldIn.playSound(null, pos, ROMSounds.ROM_CHEST_NOT_MONEY.get(), SoundCategory.BLOCKS, 2.0F, 1.0F);
                    player.displayClientMessage(new TranslationTextComponent("riskofmine.not_money"), true);
                }
            }

                if(money.getCurrentMoney()>=ModConfig.priceSmallChest.get()){
                    money.consumeMoney(player,ModConfig.priceSmallChest.get());
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
        return new CommonChestTE();
    }

}
