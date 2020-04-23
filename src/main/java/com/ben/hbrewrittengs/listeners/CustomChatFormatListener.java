package com.ben.hbrewrittengs.listeners;

import com.ben.hbrewrittengs.Main;
import com.ben.hbrewrittengs.PlayerData;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.UUID;

public class CustomChatFormatListener implements Listener
{
    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e)
    {
        Player player = e.getPlayer();
        UUID uuid = player.getUniqueId();
        PlayerData pd;

        if (Main.getInstance().playerDataMap.containsKey(uuid))
        {
            pd = Main.getInstance().playerDataMap.get(uuid);


            // Sets chat format for the player
            String format = "<rank> " + ChatColor.BLUE + "<name> " + ChatColor.DARK_GRAY + "»" + "" + ChatColor.RESET + " <message>";
            format = format.replace("<rank>", pd.getRank().getDisplayName());
            format = format.replace("<name>", "%1$s");
            format = format.replace("<message>", "%2$s");

            e.setFormat(format);
            e.setMessage(e.getMessage().replaceAll("&", "§"));
        }
    }
}
