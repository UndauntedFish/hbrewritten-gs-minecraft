package com.ben.hbrewrittengs.classes;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Objects;
import java.util.UUID;

public class Spectator
{
    /*
     * spectator is the UUID of the spectator player themself.
     * spectatedPlayer is the UUID of the player that the spectator is currently watching.
     */
    private UUID spectator, spectatedPlayer;

    public Spectator(Player player)
    {
        spectator = player.getUniqueId();
        spectatedPlayer = null;
    }

    // Get the Player that the spectator is currently watching
    public Player getWatching()
    {
        if (Objects.nonNull(spectatedPlayer))
        {
            return Bukkit.getPlayer(spectatedPlayer);
        }
        return null;
    }

    // Teleport the spectator to the target player
    public void setWatching(Player player)
    {
        Player spectatorPlayer = Bukkit.getPlayer(spectator);
        spectatorPlayer.teleport(player);
        spectatedPlayer = player.getUniqueId();
    }

    public UUID getUUID()
    {
        return spectator;
    }
}
