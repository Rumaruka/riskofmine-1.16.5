package com.rumaruka.riskofmine.client.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.rumaruka.riskofmine.common.items.ItemCollectiblesBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.DeathScreen;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

@OnlyIn(Dist.CLIENT)
public class NewDeathScreen extends DeathScreen {

    private ItemStack romStack;
    private ITextComponent deathItems;


    public NewDeathScreen(@Nullable ITextComponent textComponent, boolean isHardcoreMode) {
        super(textComponent, isHardcoreMode);
    }

    @Override
    protected void init() {
        if (romStack.getItem() instanceof ItemCollectiblesBase) {
            this.deathItems = (new TranslationTextComponent("deathScreen.items")).append(": ");
        }
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        Minecraft.getInstance().getItemRenderer().renderGuiItem(romStack, 1, 1);
    }
}
