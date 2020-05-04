package com.ben.hbrewrittengs.customitems.assassin;

import com.ben.hbrewrittengs.Main;
import com.ben.hbrewrittengs.PlayerData;
import com.ben.hbrewrittengs.enums.ClassData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class Cloak
{
    // Activates the players's cloak and removes it from their inventory
    public static void activate(PlayerData pd)
    {
        if (pd.getActiveClass() != ClassData.ASSASSIN)
        {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[HBRgs] CRITICAL ERROR: A player who wasn't assassin tried to cloak!");
            return;
        }

        Player player = Bukkit.getPlayer(pd.getUUID());

        // The player is currently unvanished, cloak them.
        Main.getInstance().toggleVisibilityNative(player);

        // Playing cloak activation sounds
        player.playSound(player.getLocation(), Sound.ENTITY_BLAZE_SHOOT, 1.0F, 0.7F);
        player.playSound(player.getLocation(), Sound.ENTITY_ENDER_DRAGON_FLAP, 1.0F, 1.0F);
        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_BREATH, 1.0F, 0.5F);

        // Unvanishes them when cloakduration is over.
        Bukkit.getScheduler().runTaskLater(Main.getInstance(), new Runnable()
        {
            public void run()
            {
                deactivate(pd);
            }
        }, Main.getInstance().getConfig().getLong("cloakduration") * 20L);
    }

    private static void deactivate(PlayerData pd)
    {
        Player player = Bukkit.getPlayer(pd.getUUID());

        // Playing cloak deactivation sounds
        player.playSound(player.getLocation(), Sound.ENTITY_ENDER_DRAGON_FLAP, 1.0F, 1.0F);
        player.playSound(player.getLocation(), Sound.ENTITY_BLAZE_SHOOT, 1.0F, 0.7F);

        Main.getInstance().toggleVisibilityNative(player);
    }
}
