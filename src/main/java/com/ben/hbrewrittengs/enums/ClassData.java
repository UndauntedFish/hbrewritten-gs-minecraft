package com.ben.hbrewrittengs.enums;

import org.bukkit.Color;

public enum ClassData
{
    ARCHER    (Color.fromRGB(102, 127, 51), "ARCHER"),
    PRIEST    (Color.fromRGB(255, 255, 255), "PRIEST"),
    SCOUT     (Color.fromRGB(229, 229, 51), "SCOUT"),
    WIZARD    (Color.fromRGB(127, 63, 178), "WIZARD"),
    DEMO      (Color.BLACK, "DEMO"),
    MAGE      (Color.fromRGB(102, 153, 216), "MAGE"),
    PALADIN   (Color.fromRGB(216, 127, 51), "PALADIN"),
    SORCERER  (Color.fromRGB(153, 51, 51), "SORCERER"),
    ASSASSIN  (Color.fromRGB(64, 64, 64), "ASSASSIN"),
    HEROBRINE (null, "HEROBRINE");

    private final Color HELMETCOLOR;
    private final String NAME;

    private ClassData(Color helmetColor, String name)
    {
        this.HELMETCOLOR = helmetColor;
        this.NAME = name;
    }

    public Color getHelmetColor()
    {
        return HELMETCOLOR;
    }

    public String getName()
    {
        return NAME;
    }

}
