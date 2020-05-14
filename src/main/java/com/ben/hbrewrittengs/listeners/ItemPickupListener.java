package com.ben.hbrewrittengs.listeners;

import com.ben.hbrewrittengs.Main;
import com.ben.hbrewrittengs.enums.GameState;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;

public class ItemPickupListener implements Listener
{
    @EventHandler
    public void onItemPickup(EntityPickupItemEvent e)
    {
        // Allows players to pick up shards
        if (e.getEntity() instanceof Player)
        {
            if (e.getItem().getItemStack().getType() == Material.NETHER_STAR &&
                    Main.arena.getGameState() == GameState.SHARD_SPAWNED)
            {
                Main.arena.getActiveShard().collect((Player) e.getEntity());
                return;
            }
        }

        e.setCancelled(true);
    }
}
