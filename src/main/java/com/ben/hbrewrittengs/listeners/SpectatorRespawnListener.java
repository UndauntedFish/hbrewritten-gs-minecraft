package com.ben.hbrewrittengs.listeners;

import com.ben.hbrewrittengs.Main;
import com.ben.hbrewrittengs.classes.SpectatorItems;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

public class SpectatorRespawnListener extends SpectatorItems implements Listener
{
    public void onPlayerRespawn(PlayerRespawnEvent e)
    {
        Player playerWhoRespawned = e.getPlayer();

        // If this is true, the playerWhoRespawned used to be a Survivor, but died.
        // Now turn them into a spectator.
        if (Main.arena.spectatorManager.isSpectator(playerWhoRespawned))
        {
            giveClass(playerWhoRespawned);
        }
    }
}
