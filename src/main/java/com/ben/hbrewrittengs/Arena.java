package com.ben.hbrewrittengs;

import com.ben.hbrewrittengs.customitems.Shard;
import com.ben.hbrewrittengs.enums.Format;
import com.ben.hbrewrittengs.enums.GameState;
import com.ben.hbrewrittengs.enums.HerobrineState;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;

import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

public class Arena
{
    private int mapId;
    private Location spawn;
    private Location altarLocation;
    private HashMap<UUID, PlayerData> playerDataMap;
    private GameState gameState;
    private HerobrineState herobrineState;
    private Shard activeShard;

    public Arena(int mapId)
    {
        this.mapId = mapId;

        this.altarLocation = Config.getAltarLocation(mapId);
        if (altarLocation.getBlock().getType() != Material.ENCHANTING_TABLE)
        {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[HBRgs] CRITICAL ERROR: Unable to detect the altar (enchantment table block) in the map with mapid: " + mapId);
            Bukkit.getServer().broadcastMessage(Format.PREFIX_INGAME.toString() + ChatColor.RED.toString() + "CRITICAL ERROR: Unable to detect the altar on this map! Contact the dev asap this is kind of a big problem.");
        }

        this.playerDataMap = new HashMap<>();
        this.gameState = GameState.IDLE;
        this.herobrineState = HerobrineState.DEFAULT;
    }

    public void spawnShard()
    {
        if (herobrineState == HerobrineState.HUMAN || !Objects.equals(activeShard, null))
        {
            return;
        }

        Location shardSpawnLoc = Config.getRandomShardSpawn(mapId);
        activeShard = new Shard(shardSpawnLoc);
        activeShard.spawn();

        // Destroys the shard after x seconds (defined in config), if the shard isn't collected yet.
        Bukkit.getScheduler().runTaskLater(Main.getInstance(), new Runnable()
        {
            @Override
            public void run()
            {
                if (!Objects.equals(activeShard, null))
                {
                    if (!activeShard.isCollected())
                    {
                        activeShard.despawn();
                        activeShard = null;
                    }
                }
            }
        }, Config.getShardTicksLived());
    }

    public void captureActiveShard()
    {
        if (Objects.equals(activeShard, null))
        {
            return;
        }

        activeShard.capture();
        activeShard = null;
    }

    public GameState getGameState()
    {
        return gameState;
    }

    public void setGameState(GameState gameState)
    {
        this.gameState = gameState;
    }

    public Location getAltarLocation()
    {
        return altarLocation;
    }

    public Shard getActiveShard()
    {
        if (Objects.equals(activeShard, null))
        {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[HBRgs] CRITICAL ERROR: Tried to call Arena.getActiveShard() when Arena.activeShard was empty! Contact a dev asap, this is a big deal.");
        }
        return activeShard;
    }
}
