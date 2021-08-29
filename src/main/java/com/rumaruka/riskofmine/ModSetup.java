package com.rumaruka.riskofmine;

import com.rumaruka.riskofmine.init.ROMItems;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;

import static com.rumaruka.riskofmine.RiskOfMine.MODID;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModSetup {


    public static ItemGroup ITEM_GROUP = new ItemGroup(MODID) {


        @Override
        public @NotNull ItemStack makeIcon() {
            return new ItemStack(ROMItems.ARMOR_PIERCING_ROUNDS);
        }
    };


}
