package com.ben.hbrewrittengs.listeners;

import com.ben.hbrewrittengs.Main;
import com.ben.hbrewrittengs.PlayerData;
import com.ben.hbrewrittengs.enums.GameState;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class SurvivorDeathListener implements Listener
{
    @EventHandler
    public void onSurvivorDeath(PlayerDeathEvent e)
    {
        Player player = e.getEntity();
        PlayerData pd = Main.getInstance().playerDataMap.get(player.getUniqueId());

        e.setKeepLevel(true);

        if (!(pd.isHerobrine()))
        {
            survivorDeathRoutine(player);
        }
    }

    // Transforms a dead survivor into a spectator.
    private void survivorDeathRoutine(Player player)
    {
        // If an active shard has been collected by the now-dead player, drop the shard.
        if (player.getInventory().contains(Material.NETHER_STAR) && Main.arena.getGameState() == GameState.SHARD_PICKEDUP)
        {
            Main.arena.getActiveShard().drop(player.getLocation());
        }

        // Clear the player's inventory and give them the spectator inventory (compass, slimeball, etc.)
        player.getInventory().clear();
        player.setGameMode(GameMode.SPECTATOR);
    }
}
