package com.rumaruka.riskofmine.gen.lunarchestgen;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.world.gen.blockplacer.BlockPlacer;
import net.minecraft.world.gen.blockstateprovider.BlockStateProvider;
import net.minecraft.world.gen.feature.IFeatureConfig;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class LunarChestFeatureConfig implements IFeatureConfig {
    public static final Codec<LunarChestFeatureConfig> CODEC = RecordCodecBuilder.create((instance_) -> {
        return instance_.group(BlockStateProvider.CODEC.fieldOf("state_provider").forGetter((blockClusterFeatureConfig10_) -> {
            return blockClusterFeatureConfig10_.stateProvider;
        }), BlockPlacer.CODEC.fieldOf("block_placer").forGetter((blockClusterFeatureConfig9_) -> {
            return blockClusterFeatureConfig9_.blockPlacer;
        }), BlockState.CODEC.listOf().fieldOf("whitelist").forGetter((blockClusterFeatureConfig8_) -> {
            return blockClusterFeatureConfig8_.whitelist.stream().map(Block::defaultBlockState).collect(Collectors.toList());
        }), BlockState.CODEC.listOf().fieldOf("blacklist").forGetter((blockClusterFeatureConfig7_) -> {
            return ImmutableList.copyOf(blockClusterFeatureConfig7_.blacklist);
        }), Codec.INT.fieldOf("tries").orElse(128).forGetter((blockClusterFeatureConfig6_) -> {
            return blockClusterFeatureConfig6_.tries;
        }), Codec.INT.fieldOf("xspread").orElse(7).forGetter((blockClusterFeatureConfig5_) -> {
            return blockClusterFeatureConfig5_.xspread;
        }), Codec.INT.fieldOf("yspread").orElse(3).forGetter((blockClusterFeatureConfig4_) -> {
            return blockClusterFeatureConfig4_.yspread;
        }), Codec.INT.fieldOf("zspread").orElse(7).forGetter((blockClusterFeatureConfig3_) -> {
            return blockClusterFeatureConfig3_.zspread;
        }), Codec.BOOL.fieldOf("can_replace").orElse(false).forGetter((blockClusterFeatureConfig2_) -> {
            return blockClusterFeatureConfig2_.canReplace;
        }), Codec.BOOL.fieldOf("project").orElse(true).forGetter((blockClusterFeatureConfig1_) -> {
            return blockClusterFeatureConfig1_.project;
        }), Codec.BOOL.fieldOf("need_water").orElse(false).forGetter((blockClusterFeatureConfig_) -> {
            return blockClusterFeatureConfig_.needWater;

        })).apply(instance_, LunarChestFeatureConfig::new);

    });
    public final BlockStateProvider stateProvider;
    public final BlockPlacer blockPlacer;
    public final Set<Block> whitelist;
    public final Set<BlockState> blacklist;
    public final int tries;

    public final int xspread;
    public final int yspread;
    public final int zspread;
    public final boolean canReplace;
    public final boolean project;
    public final boolean needWater;

    private LunarChestFeatureConfig(BlockStateProvider blockStateProvider_, BlockPlacer blockPlacer_, List<BlockState> list_, List<BlockState> list1_, int int_, int int1_, int int2_, int int3_, boolean boolean2_, boolean boolean_, boolean boolean1_) {
        this(blockStateProvider_, blockPlacer_, list_.stream().map(AbstractBlock.AbstractBlockState::getBlock).collect(Collectors.toSet()), ImmutableSet.copyOf(list1_), int_, int1_, int2_, int3_, boolean2_, boolean_, boolean1_);
    }

    private LunarChestFeatureConfig(BlockStateProvider stateProvider, BlockPlacer blockPlacer, Set<Block> whitelistIn, Set<BlockState> set_, int int_, int int1_, int int2_, int int3_, boolean boolean2_, boolean boolean_, boolean boolean1_) {
        this.stateProvider = stateProvider;
        this.blockPlacer = blockPlacer;
        this.whitelist = whitelistIn;
        this.blacklist = set_;
        this.tries = int_;

        this.xspread = int1_;
        this.yspread = int2_;
        this.zspread = int3_;
        this.canReplace = boolean2_;
        this.project = boolean_;
        this.needWater = boolean1_;
    }


    public static class Builder {
        private final BlockStateProvider stateProvider;
        private final BlockPlacer blockPlacer;
        private Set<Block> whitelist = ImmutableSet.of();
        private Set<BlockState> blacklist = ImmutableSet.of();
        private int tries = 64;

        private int xspread = 7;
        private int yspread = 3;
        private int zspread = 7;
        private boolean canReplace;
        private boolean project = true;
        private boolean needWater = false;

        public Builder(BlockStateProvider blockStateProvider_, BlockPlacer blockPlacer_) {
            this.stateProvider = blockStateProvider_;
            this.blockPlacer = blockPlacer_;
        }

        public LunarChestFeatureConfig.Builder whitelist(Set<Block> set_) {
            this.whitelist = set_;
            return this;
        }

        public LunarChestFeatureConfig.Builder blacklist(Set<BlockState> set_) {
            this.blacklist = set_;
            return this;
        }

        public LunarChestFeatureConfig.Builder tries(int int_) {
            this.tries = int_;
            return this;
        }


        public LunarChestFeatureConfig.Builder xspread(int int_) {
            this.xspread = int_;
            return this;
        }

        public LunarChestFeatureConfig.Builder yspread(int int_) {
            this.yspread = int_;
            return this;
        }

        public LunarChestFeatureConfig.Builder zspread(int int_) {
            this.zspread = int_;
            return this;
        }

        public LunarChestFeatureConfig.Builder canReplace() {
            this.canReplace = true;
            return this;
        }

        public LunarChestFeatureConfig.Builder noProjection() {
            this.project = false;
            return this;
        }

        public LunarChestFeatureConfig.Builder needWater() {
            this.needWater = true;
            return this;
        }

        public LunarChestFeatureConfig build() {
            return new LunarChestFeatureConfig(this.stateProvider, this.blockPlacer, this.whitelist, this.blacklist, this.tries, this.xspread, this.yspread, this.zspread, this.canReplace, this.project, this.needWater);
        }
    }
}
