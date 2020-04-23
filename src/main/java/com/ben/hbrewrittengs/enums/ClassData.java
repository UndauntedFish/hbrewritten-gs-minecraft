package com.ben.hbrewrittengs.enums;

import org.bukkit.ChatColor;
import org.bukkit.Color;

public enum ClassData
{
    ARCHER   (Color.fromRGB(102, 127, 51), ChatColor.YELLOW + "Archer"),
    PRIEST   (Color.fromRGB(255, 255, 255), ChatColor.WHITE + "Priest"),
    SCOUT    (Color.fromRGB(229, 229, 51), ChatColor.YELLOW + "Scout"),
    WIZARD   (Color.fromRGB(127, 63, 178), ChatColor.LIGHT_PURPLE + "Wizard"),
    DEMO     (Color.BLACK, ChatColor.GOLD + "Demo"),
    MAGE     (Color.fromRGB(102, 153, 216), ChatColor.WHITE + "Mage"),
    PALADIN  (Color.fromRGB(216, 127, 51), ChatColor.GOLD + "Paladin"),
    SORCERER (Color.fromRGB(153, 51, 51), ChatColor.RED + "Sorceror"),
    ASSASSIN (Color.fromRGB(64, 64, 64), ChatColor.GRAY + "Assassin"); // Assassin will have a player head, not a helmet.

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
        return NAME + ChatColor.RESET.toString();
    }

}
