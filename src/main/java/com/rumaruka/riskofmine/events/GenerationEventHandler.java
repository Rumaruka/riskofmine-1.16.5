package com.rumaruka.riskofmine.events;

import com.rumaruka.riskofmine.gen.commonchestgen.CommonChestFeatureConfig;
import com.rumaruka.riskofmine.gen.largechestgen.LargeChestFeatureConfig;
import com.rumaruka.riskofmine.init.ROMBlocks;
import com.rumaruka.riskofmine.init.ROMFeatures;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.blockplacer.SimpleBlockPlacer;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;


public class GenerationEventHandler {
    public static final CommonChestFeatureConfig SMALL_CHEST = (new CommonChestFeatureConfig.Builder(new SimpleBlockStateProvider(ROMBlocks.SMALL_CHEST.defaultBlockState()),
            new SimpleBlockPlacer())).tries(25).xspread(35).yspread(128).zspread(1).build();
    public static final LargeChestFeatureConfig LARGE_CHEST = (new LargeChestFeatureConfig.Builder(new SimpleBlockStateProvider(ROMBlocks.SMALL_CHEST.defaultBlockState()),
            new SimpleBlockPlacer())).tries(1).xspread(20).yspread(10).zspread(1).build();

    @SubscribeEvent
    public void onBiomesLoadingEvent(BiomeLoadingEvent event) {
        //spawn depend on biome type
        if (event.getCategory()== Biome.Category.PLAINS|| event.getCategory() == Biome.Category.TAIGA   || event.getCategory() == Biome.Category.EXTREME_HILLS) {
            //spawn
            event.getGeneration().addFeature(
                    GenerationStage.Decoration.LOCAL_MODIFICATIONS,
                    ROMFeatures.SMALL_CHEST.get().configured(SMALL_CHEST));

        }
        if ( event.getCategory() == Biome.Category.EXTREME_HILLS) {
            //spawn
            event.getGeneration().addFeature(
                    GenerationStage.Decoration.LOCAL_MODIFICATIONS,
                    ROMFeatures.LARGE_CHEST.get().configured(LARGE_CHEST));

        }    }
}
