package com.ben.hbrewrittengs.enums;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public enum Format
{
    PREFIX_INGAME (ChatColor.GRAY + "▐ Herobrine | "),
    SHARD_SPAWN   (Format.PREFIX_INGAME + ChatColor.LIGHT_PURPLE.toString() + "A new shard has " + ChatColor.WHITE.toString() + ChatColor.BOLD + "been SUMMONED!"),
    HB_KILL       (Format.PREFIX_INGAME + ChatColor.WHITE.toString() + "<name> " + ChatColor.YELLOW + " was killed by " + ChatColor.RED.toString() + ChatColor.BOLD + "the HEROBRINE!");
    private final String STRING;

    private Format(String string)
    {
        this.STRING = string;
    }

    public String toString()
    {
        return STRING + ChatColor.RESET.toString();
    }

    public String deathBroadcast(Player player)
    {
        return STRING.replace("<name>", player.getName());
    }
}
