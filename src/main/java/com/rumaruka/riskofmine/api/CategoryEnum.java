package com.rumaruka.riskofmine.api;

import net.minecraft.util.IStringSerializable;
import net.minecraft.util.text.TextFormatting;

public enum CategoryEnum implements IStringSerializable {
    DAMAGE("Damage", "damage", TextFormatting.DARK_RED),
    SCRAP("Scrap", "scrap", TextFormatting.GRAY),
    UTILITY("Utility", "utility", TextFormatting.GOLD),
    HEALING("Healing", "healing", TextFormatting.GREEN),

    ;
    private final String name;
    private final String unlocalizedName;
    private final TextFormatting chatColor;

    CategoryEnum(String name, String unlocalizedName, TextFormatting chatColor) {
        this.name = name;
        this.unlocalizedName = unlocalizedName;
        this.chatColor = chatColor;
    }

    @Override
    public String getSerializedName() {
        return unlocalizedName;
    }

    public String getName() {
        return name;
    }

    public TextFormatting getChatColor() {
        return chatColor;
    }
}
