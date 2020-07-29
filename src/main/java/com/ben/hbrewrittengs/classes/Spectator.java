package com.ben.hbrewrittengs.classes;

import com.ben.hbrewrittengs.Main;
import com.ben.hbrewrittengs.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public class Spectator
{
    // Items
    private static ItemStack teleporter, joinNewGame, exitToHub;
    private static ItemMeta teleporterMeta, joinNewGameMeta, exitToHubMeta;
    private static List<String> teleporterLore = new ArrayList<>(),
            joinNewGameLore = new ArrayList<>(),
            exitToHubLore = new ArrayList<>();

    public static void loadItems()
    {
        /* LORES */
        teleporterLore.add("Right click to teleport");
        teleporterLore.add("to any player");

        joinNewGameLore.add("Right click to join a new game");

        exitToHubLore.add("Right click to return to hub");


        /* ITEMS */

        // Weapons and Extras
        teleporter = new ItemStack(Material.COMPASS);
        teleporterMeta = teleporter.getItemMeta();
        teleporterMeta.setDisplayName(ChatColor.LIGHT_PURPLE + "Phoenix Longbow");
        teleporterMeta.setLore(teleporterLore);
        teleporterMeta.setUnbreakable(true);
        teleporter.setItemMeta(teleporterMeta);

        joinNewGame = new ItemStack(Material.MAGMA_CREAM);
        joinNewGameMeta = joinNewGame.getItemMeta();
        joinNewGameMeta.setDisplayName(ChatColor.YELLOW + "Eagle Quill Feather");
        joinNewGameMeta.setLore(joinNewGameLore);
        joinNewGame.setItemMeta(joinNewGameMeta);

        exitToHub = new ItemStack(Material.SLIME_BALL);
        exitToHubMeta = exitToHub.getItemMeta();
        exitToHubMeta.setDisplayName(ChatColor.LIGHT_PURPLE + "Hatchet of War");
        exitToHubMeta.setLore(exitToHubLore);
        exitToHubMeta.setUnbreakable(true);
        exitToHub.setItemMeta(exitToHubMeta);
    }

    public static void giveClass(Player player)
    {
        // Marking the player as a spectator
        PlayerData pd = Main.getInstance().playerDataMap.get(player.getUniqueId());
        if (!(Main.arena.spectatorList.contains(player.getUniqueId())))
        {
            Main.arena.spectatorList.add(player.getUniqueId());
        }
        else
        {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[HBRgs] ERROR: Tried to make player " + player.getName() + " a spectator, even though they were already a spectator!");
            return;
        }

        // Turns the player into a spectator
        player.setAllowFlight(true);
        player.setInvulnerable(true);
        player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 0, false, false));

        // Setting items to the player's inventory slots
        player.getInventory().clear();
        player.getInventory().setItem(0, teleporter);
        player.getInventory().setItem(4, joinNewGame);
        player.getInventory().setItem(8, exitToHub);
    }
}
