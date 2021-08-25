package com.rumaruka.riskofmine.init;

import com.rumaruka.riskofmine.client.tesr.CommonChestTESR;
import com.rumaruka.riskofmine.common.tiles.CommonChestTE;
import com.rumaruka.riskofmine.common.tiles.LargeChestTE;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.registries.ObjectHolder;
import ru.timeconqueror.timecore.api.registry.TileEntityRegister;
import ru.timeconqueror.timecore.api.registry.util.AutoRegistrable;

import static com.rumaruka.riskofmine.RiskOfMine.MODID;
import static ru.timeconqueror.timecore.api.util.Hacks.promise;

@ObjectHolder(MODID)
public class ROMTiles {


    public static final TileEntityType<CommonChestTE> SMALL_CHEST = promise();
    public static final TileEntityType<LargeChestTE> LARGE_CHEST = promise();

    private static class Setup {

        @AutoRegistrable
        private static final TileEntityRegister REGISTER = new TileEntityRegister(MODID);

        @AutoRegistrable.InitMethod
        private static void register() {

            REGISTER.registerSingleBound("small_chest", CommonChestTE::new, () -> ROMBlocks.SMALL_CHEST).regCustomRenderer(() -> CommonChestTESR::new);

            REGISTER.registerSingleBound("large_chest", LargeChestTE::new, () -> ROMBlocks.LARGE_CHEST);

        }
    }
}


