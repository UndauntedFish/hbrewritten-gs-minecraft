package com.ben.hbrewrittengs.customitems;

import com.ben.hbrewrittengs.Main;
import com.ben.hbrewrittengs.enums.Format;
import com.ben.hbrewrittengs.enums.GameState;
import com.ben.hbrewrittengs.enums.ShardName;
import org.bukkit.*;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Shard
{
    private Location spawnLoc;
    private UUID shardHolder;
    private BukkitTask lightningStrikeTask, compassTargetTask;
    private boolean isCaptured;

    // Shard Item
    private ShardName shardName;
    private ItemStack shard;
    private ItemMeta shardMeta;
    private Item droppedShard;

    // Lore
    private List<String> shardLore = new ArrayList<>();

    public Shard(Location spawnLoc)
    {
        this.spawnLoc = spawnLoc;
        shardName = ShardName.random();

        /* LORE */
        shardLore.add("It feels..." + shardName.getLoreWord() + "!");
        shardLore.add("Go capture this shard by holding it");
        shardLore.add("and right clicking the enchantment table!");

        /* ITEM */
        shard = new ItemStack(Material.NETHER_STAR);
        shardMeta = shard.getItemMeta();
        shardMeta.setDisplayName(ChatColor.RED + shardName.getName());
        shardMeta.setLore(shardLore);
        shard.setItemMeta(shardMeta);
    }

    public void spawn()
    {
        // Sets the arena's gamestate to shard spawned
        Main.arena.setGameState(GameState.SHARD_SPAWNED);

        // Spawns the shard in the world
        droppedShard = spawnLoc.getWorld().dropItemNaturally(spawnLoc, shard);
        droppedShard.setInvulnerable(true);

        // Sets every player's compass target to the shard spawn location
        setGlobalCompassTargetTo(spawnLoc);

        // Strikes the shard with lightning every 4 seconds
        lightningStrikeTask = Bukkit.getScheduler().runTaskTimer(Main.getInstance(), new Runnable()
        {
            @Override
            public void run()
            {
                if (isCaptured)
                {
                    Bukkit.getScheduler().cancelTask(lightningStrikeTask.getTaskId());
                }
                else
                {
                    if (Objects.equals(shardHolder, null))
                    {
                        spawnLoc.getWorld().strikeLightningEffect(spawnLoc);
                    }
                }
            }
        }, 0L, 20L * 4L);
    }

    public void collect(Player player)
    {
        Main.arena.setGameState(GameState.SHARD_PICKEDUP);
        this.shardHolder = player.getUniqueId();

        // Play collection sounds



        // Sets the shardholder's compass target to the altar
        player.setCompassTarget(Main.arena.getAltarLocation());

        // Sets everyone else's compass target to the target player (updated every 5 ticks)
        compassTargetTask = Bukkit.getScheduler().runTaskTimer(Main.getInstance(), new Runnable()
        {
            @Override
            public void run()
            {
                if (isCaptured)
                {
                    Bukkit.getScheduler().cancelTask(compassTargetTask.getTaskId());
                }
                else
                {
                    Bukkit.getOnlinePlayers().forEach( (onlinePlayer) ->
                    {
                        if (!onlinePlayer.getUniqueId().equals(player.getUniqueId()))
                        {
                            onlinePlayer.setCompassTarget(player.getLocation());
                        }
                    });
                }
            }
        }, 0L, 5L);
    }

    public void drop()
    {
        // Respawn shard at the dead player's location
        shardHolder = null;
        spawnLoc = Bukkit.getPlayer(shardHolder).getLocation();
        spawn();
    }

    public void capture()
    {
        // Send async query to database updating their points/tokens
        // Arena.GameState = idle
        // play shard capture sounds to the whole world
        Main.arena.setGameState(GameState.SHARD_CAPTURED);
        shardHolder = null;
        isCaptured = true;
        Main.arena.setGameState(GameState.IDLE);

        // Plays shard capture sounds and particles to the whole world
        Location altarLocation = Main.arena.getAltarLocation();
        altarLocation.getWorld().playSound(altarLocation, Sound.BLOCK_PORTAL_TRAVEL, 1.0F, 1.0F);
        altarLocation.getWorld().playEffect(altarLocation, Effect.MOBSPAWNER_FLAMES, 1, 20);

        // Resets every player's compass target to the altar
        setGlobalCompassTargetTo(Main.arena.getAltarLocation());
    }

    public void despawn()
    {
        Main.arena.setGameState(GameState.IDLE);
        isCaptured = true; // set this to true to cancel all runnable tasks

        // Broadcast to everyone that the shard was destroyed
        Bukkit.getServer().broadcastMessage(Format.SHARD_DESTROY.toString());
    }

    public boolean isCollected()
    {
        if (Objects.equals(shardHolder, null))
        {
            return false;
        }
        return true;
    }

    private static void setGlobalCompassTargetTo(Location location)
    {
        Bukkit.getOnlinePlayers().forEach( (onlinePlayer) ->
        {
            onlinePlayer.setCompassTarget(location);
        });
    }
}
