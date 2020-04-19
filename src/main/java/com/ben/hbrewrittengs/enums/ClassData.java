package com.ben.hbrewrittengs.enums;

import org.bukkit.Color;

public enum ClassData
{
    ARCHER   (Color.fromRGB(102, 127, 51), "Archer"),
    PRIEST   (Color.fromRGB(255, 255, 255), "Priest"),
    SCOUT    (Color.fromRGB(229, 229, 51), "Scout"),
    WIZARD   (Color.fromRGB(127, 63, 178), "Wizard"),
    DEMO     (Color.BLACK, "Demo"),
    MAGE     (Color.fromRGB(102, 153, 216), "Mage"),
    PALADIN  (Color.fromRGB(216, 127, 51), "Paladin"),
    SORCEROR (Color.fromRGB(153, 51, 51), "Sorceror"),
    ASSASSIN (null, "Assassin"); // Assassin will have a player head, not a helmet.

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
