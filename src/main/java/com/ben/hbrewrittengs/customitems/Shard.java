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
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class Shard
{
    private Location spawnLoc;
    private Location altarLoc;
    private UUID shardHolder;
    private BukkitTask lightningStrikeTask, compassTargetTask, shardSoundsTask;
    private boolean isCaptured;

    // Shard Item
    private ShardName shardName;
    private ItemStack shard;
    private ItemMeta shardMeta;
    private Item droppedShard;
    private List<String> shardLore = new ArrayList<>();

    // Shardholder's potion effects
    private PotionEffect blindness = new PotionEffect(PotionEffectType.BLINDNESS, 5 * 20, 0, false, false);
    private PotionEffect slowness = new PotionEffect(PotionEffectType.SLOW, 30 * 20, 0, false, false);
    private PotionEffect nausea = new PotionEffect(PotionEffectType.CONFUSION, 15 * 20, 0, false, false);

    // Capture particle spawning bounds (around spawnLoc)
    private int x1, x2, y1, y2, z1, z2;


    public Shard(Location spawnLoc, Location altarLoc)
    {
        this.spawnLoc = spawnLoc;
        this.altarLoc = altarLoc;

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

        /* RANDOM PARTICLES SPAWING BOX (AROUND THE ALTAR) */
        x1 = altarLoc.getBlockX() - 10; x2 = altarLoc.getBlockX() + 10;
        y1 = altarLoc.getBlockY();      y2 = altarLoc.getBlockY() + 10;
        z1 = altarLoc.getBlockZ() - 10; z2 = altarLoc.getBlockZ() + 10;
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

        playAmbientShardSounds();

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
        }, 0L, 20L * 10L);
    }

    private void playAmbientShardSounds()
    {
        // Play ambient shard sounds every three seconds
        shardSoundsTask = Bukkit.getScheduler().runTaskTimer(Main.getInstance(), new Runnable()
        {
            @Override
            public void run()
            {
                spawnLoc.getWorld().playSound(spawnLoc, Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 0.5F);
            }
        }, 0L, 20L * 3L);
    }

    public void collect(Player player)
    {
        Main.arena.setGameState(GameState.SHARD_PICKEDUP);
        this.shardHolder = player.getUniqueId();

        // Stops ambient shard sounds
        Bukkit.getScheduler().cancelTask(shardSoundsTask.getTaskId());

        // Plays shard collection sounds and effects to the shardholder
        player.playSound(player.getLocation(), Sound.ENTITY_BAT_DEATH, 1.0F, 0.5F);
        player.addPotionEffect(blindness);
        player.addPotionEffect(slowness);
        player.addPotionEffect(nausea);

        // Sets the shardholder's compass target to the altar
        player.setCompassTarget(altarLoc);

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

    public void drop(Location location)
    {
        // Respawn shard at the dead player's location
        shardHolder = null;
        spawnLoc = location;
        spawn();
    }

    public void capture()
    {
        Main.arena.setGameState(GameState.SHARD_CAPTURED);
        shardHolder = null;
        isCaptured = true;

        // Resets every player's compass target to the altar
        setGlobalCompassTargetTo(altarLoc);


        // Plays shard capture sounds and particles to the whole world
        World world = altarLoc.getWorld();
        world.spawnParticle(Particle.ENCHANTMENT_TABLE, altarLoc, 7);
        world.playSound(altarLoc, Sound.BLOCK_PORTAL_TRAVEL, 1.0F, 1.0F);

        int randX, randY, randZ;
        Location particleSpawnLoc;
        for (int i = 0; i < 40; i++) // i < x = Spawn x particles around the altar
        {
            // Gets random (x, y, z) coordinates between the bounds specified above, and spawns particles there
            randX = ThreadLocalRandom.current().nextInt(x1, x2);
            randY = ThreadLocalRandom.current().nextInt(y1, y2);
            randZ = ThreadLocalRandom.current().nextInt(z1, z2);

            particleSpawnLoc = new Location(world, randX, randY, randZ);
            world.playEffect(particleSpawnLoc, Effect.MOBSPAWNER_FLAMES, 1, 20);
            world.playEffect(particleSpawnLoc, Effect.ENDER_SIGNAL, 1);
        }


        Main.arena.setGameState(GameState.IDLE);
    }

    public void despawn()
    {
        Main.arena.setGameState(GameState.IDLE);

        // Set this to true to cancel all runnable tasks
        isCaptured = true;

        // Stops ambient shard sounds
        Bukkit.getScheduler().cancelTask(shardSoundsTask.getTaskId());

        droppedShard.remove();

        // Broadcast to everyone that the shard was destroyed
        Bukkit.getServer().broadcastMessage(Format.SHARD_DESTROY.toString());
    }

    private static void setGlobalCompassTargetTo(Location location)
    {
        Bukkit.getOnlinePlayers().forEach( (onlinePlayer) ->
        {
            onlinePlayer.setCompassTarget(location);
        });
    }
}
