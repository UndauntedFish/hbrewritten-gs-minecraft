package com.ben.hbrewrittengs.customitems.assassin;

import com.ben.hbrewrittengs.Main;
import com.ben.hbrewrittengs.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Cloak
{
    // Activates the players's cloak and removes it from their inventory
    public static void activate(PlayerData pd)
    {
        Player player = Bukkit.getPlayer(pd.getUUID());

        // The player is currently unvanished, cloak them.
        Main.getInstance().vanishedPlayers.add(player);
        Bukkit.getOnlinePlayers().forEach((onlinePlayer) ->
        {
            onlinePlayer.hidePlayer(Main.getInstance(), player);
        });

        // Removing activated cloak from player's inventory
        player.getInventory().getItemInMainHand().setAmount(
                player.getInventory().getItemInMainHand().getAmount() - 1);

        player.sendMessage("You are vanished");

        // Unvanishes them when cloakduration is over.
        Bukkit.getScheduler().runTaskLater(Main.getInstance(), new Runnable()
        {
            public void run()
            {
                Cloak.deactivate(pd);
                pd.setCloaked(false);
            }
        }, Main.getInstance().getConfig().getLong("cloakduration") * 20L);
    }

    public static void deactivate(PlayerData pd)
    {
        Player player = Bukkit.getPlayer(pd.getUUID());

        if (Main.getInstance().vanishedPlayers.contains(player))
        {
            // The player is currently vanished, uncloak them.
            Main.getInstance().vanishedPlayers.remove(player);
            Bukkit.getOnlinePlayers().forEach((onlinePlayer) ->
            {
                onlinePlayer.showPlayer(Main.getInstance(), player);
            });

            player.sendMessage("You are unvanished");
        }
    }
}
