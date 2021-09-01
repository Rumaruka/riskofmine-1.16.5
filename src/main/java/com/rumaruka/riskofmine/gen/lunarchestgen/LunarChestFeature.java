package com.rumaruka.riskofmine.gen.lunarchestgen;

import com.mojang.serialization.Codec;
import com.rumaruka.riskofmine.datagen.loot.ROMLootTables;
import com.rumaruka.riskofmine.init.ROMBlocks;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.Feature;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class LunarChestFeature<C> extends Feature<LunarChestFeatureConfig> {
    public LunarChestFeature(Codec<LunarChestFeatureConfig> codec) {
        super(codec);
    }


    @Override
    public boolean place(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, LunarChestFeatureConfig config) {
        ChunkPos chunkpos = new ChunkPos(pos);
        List<Integer> list = IntStream.rangeClosed(chunkpos.getMinBlockX(), chunkpos.getMaxBlockX()).boxed().collect(Collectors.toList());
        Collections.shuffle(list, rand);
        List<Integer> list1 = IntStream.rangeClosed(chunkpos.getMinBlockZ(), chunkpos.getMaxBlockZ()).boxed().collect(Collectors.toList());
        Collections.shuffle(list1, rand);
        BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();

        for (Integer integer : list) {
            for (Integer integer1 : list1) {
                blockpos$mutable.set(integer, 0, integer1);
                BlockPos blockpos = reader.getHeightmapPos(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, blockpos$mutable);
                if (reader.isEmptyBlock(blockpos) || reader.getBlockState(blockpos).getCollisionShape(reader, blockpos).isEmpty()) {
                    reader.setBlock(blockpos, ROMBlocks.LUNAR_CHEST.defaultBlockState(), 2);
                    LockableLootTileEntity.setLootTable(reader, rand, blockpos, ROMLootTables.LUNAR_CHEST);

                    return true;
                }
            }
        }

        return false;
    }
}
