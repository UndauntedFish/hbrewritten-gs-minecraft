package com.ben.hbrewrittengs.listeners;

import com.ben.hbrewrittengs.Main;
import com.ben.hbrewrittengs.PlayerData;
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
        if (e.getEntity() instanceof Player)
        {
            Player player = (Player) e.getEntity();
            PlayerData pd = Main.getInstance().playerDataMap.get(player.getUniqueId());

            // Stops spectators from picking up anything
            if (Main.arena.spectatorManager.isSpectator(player))
            {
                e.setCancelled(true);
                return;
            }

            // Allows survivors to pick up shards
            if (e.getItem().getItemStack().getType() == Material.NETHER_STAR &&
                    Main.arena.getGameState() == GameState.SHARD_SPAWNED)
            {
                if (!pd.isHerobrine())
                {
                    Main.arena.getActiveShard().collect(player);
                    return;
                }
            }
        }

        e.setCancelled(true);
    }
}
