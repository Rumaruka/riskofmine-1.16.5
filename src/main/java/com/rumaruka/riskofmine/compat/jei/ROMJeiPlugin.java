package com.rumaruka.riskofmine.compat.jei;

import com.google.common.collect.Lists;
import com.rumaruka.riskofmine.RiskOfMine;
import com.rumaruka.riskofmine.init.ROMItems;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;

import static com.rumaruka.riskofmine.RiskOfMine.rl;
import static com.rumaruka.riskofmine.init.ROMItems.*;


@JeiPlugin
public class ROMJeiPlugin implements IModPlugin {
    @Override
    public void registerRecipes(IRecipeRegistration registration) {




        ROMItems.getAllItem().forEach(item -> {
            registerIngredientInfo(registration, item);
        });
    }



    @Override
    public ResourceLocation getPluginUid() {
        return rl("riskofmine");
    }

    public void registerIngredientInfo(IRecipeRegistration registration, IItemProvider ingredient) {

        registration.addIngredientInfo(new ItemStack(ingredient.asItem()), VanillaTypes.ITEM,
                "jei." + RiskOfMine.MODID + ".item." + ingredient.asItem().getRegistryName().getPath() + ".desc");


    }
}
