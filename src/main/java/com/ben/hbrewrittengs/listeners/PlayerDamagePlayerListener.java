package com.ben.hbrewrittengs.listeners;

import com.ben.hbrewrittengs.Main;
import com.ben.hbrewrittengs.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class PlayerDamagePlayerListener implements Listener
{
    @EventHandler
    public void onPlayerDamagePlayer(EntityDamageByEntityEvent e)
    {
        Player damaged = null, damager = null;
        if (e.getEntity() instanceof Player && e.getDamager() instanceof  Player)
        {
            damaged = (Player) e.getEntity();
            damager = (Player) e.getDamager();
        }
        PlayerData pdDamaged = Main.getInstance().playerDataMap.get(damaged.getUniqueId());
        PlayerData pdDamager = Main.getInstance().playerDataMap.get(damager.getUniqueId());

        if ()
    }
}
