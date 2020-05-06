package com.ben.hbrewrittengs;

import com.ben.hbrewrittengs.classes.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ClassCommand implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        Player player = null;
        if (sender instanceof Player)
        {
            player = (Player) sender;
        }
        else
        {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "/class can only be used in game!");
        }

        if (args.length > 0)
        {
            String chosenClass = args[0].toUpperCase();
            switch (chosenClass)
            {
                case "ARCHER":
                    Archer.giveClass(player);
                    break;
                case "PRIEST":
                    Priest.giveClass(player);
                    break;
                case "SCOUT":
                    Scout.giveClass(player);
                    break;
                case "WIZARD":
                    Wizard.giveClass(player);
                    break;
                case "DEMO":
                    Demo.giveClass(player);
                    break;
                case "MAGE":
                    Mage.giveClass(player);
                    break;
                case "PALADIN":
                    Paladin.giveClass(player);
                    break;
                case "SORCEROR":
                    Sorcerer.giveClass(player);
                    break;
                case "ASSASSIN":
                    Assassin.giveClass(player);
                    break;
                case "GET":
                    PlayerData pd = Main.getInstance().playerDataMap.get(player.getUniqueId());
                    player.sendMessage(ChatColor.GOLD.toString() + pd.getActiveClass().getName());
                    break;
            }
        }
        else
        {
            player.sendMessage("/class <classname>");
        }

        return false;
    }
}
