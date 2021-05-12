package com.rumaruka.riskofmine.compat;

import com.rumaruka.riskofmine.RiskOfMine;
import com.rumaruka.riskofmine.init.ROMItems;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;

import static com.rumaruka.riskofmine.RiskOfMine.rl;

@JeiPlugin
public class ROMJeiPlugin implements IModPlugin {
    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        registerIngredientInfo(registration, ROMItems.ENERGY_DRINK);
        registerIngredientInfo(registration, ROMItems.GASOLINE);
        registerIngredientInfo(registration, ROMItems.SHAPED_GLASS);
        registerIngredientInfo(registration, ROMItems.CHRONOBAUBLE);


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
