package com.ben.hbrewrittengs;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.concurrent.ThreadLocalRandom;

public class Config
{
    private static Main main;

    public Config(Main main)
    {
        this.main = main;

        main.getConfig().options().copyDefaults();
        main.saveDefaultConfig();
    }

    public static Location getMapSpawn(int mapId)
    {
        return new Location(
                Bukkit.getWorld(main.getConfig().getString("maps." + mapId + ".level-name")),
                main.getConfig().getDouble("maps." + mapId + ".spawn-x"),
                main.getConfig().getDouble("maps." + mapId + ".spawn-y"),
                main.getConfig().getDouble("maps." + mapId + ".spawn-z"),
                main.getConfig().getInt("maps." + mapId + ".spawn-yaw"),
                main.getConfig().getInt("maps." + mapId + ".spawn-pitch"));
    }

    public static Location getAltarLocation(int mapId)
    {
        return new Location(
                Bukkit.getWorld(main.getConfig().getString("maps." + mapId + ".level-name")),
                main.getConfig().getDouble("maps." + mapId + ".altarblock-x"),
                main.getConfig().getDouble("maps." + mapId + ".altarblock-y"),
                main.getConfig().getDouble("maps." + mapId + ".altarblock-z"),
                0,
                0);
    }

    public static Location getRandomShardSpawn(int mapId)
    {
        // Gets a random shardID between 0 and the amount of shardIDs appearing in config.yml
        int shardID = ThreadLocalRandom.current().nextInt(0, main.getConfig().getConfigurationSection("maps." + mapId + ".shard-spawns.").getKeys(false).size());

        // Returns the spawn location of the shardID as defined in the config.
        return new Location(
                Bukkit.getWorld(main.getConfig().getString("maps." + mapId + ".level-name")),
                main.getConfig().getDouble("maps." + mapId + ".shard-spawns." + shardID + ".x"),
                main.getConfig().getDouble("maps." + mapId + ".shard-spawns." + shardID + ".y"),
                main.getConfig().getDouble("maps." + mapId + ".shard-spawns." + shardID + ".z"),
                0,
                0);
    }

    public static long getShardTicksLived()
    {
        return (long) (20.0 * main.getConfig().getDouble("shard_lifespan"));
    }
}
