package com.ben.hbrewrittengs;

import com.ben.hbrewrittengs.customitems.Shard;
import com.ben.hbrewrittengs.enums.Format;
import com.ben.hbrewrittengs.enums.GameState;
import com.ben.hbrewrittengs.enums.HerobrineState;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

public class Arena
{
    private int mapId;
    private Location spawn;
    private Location altarLocation;

    private GameState gameState;
    private int survivorsLeft;

    private Player herobrine;
    private HerobrineState herobrineState;

    private Shard activeShard;

    private HashMap<UUID, PlayerData> playerDataMap;
    public ArrayList<UUID> spectatorList = new ArrayList<>();
    public SpectatorManager spectatorManager = new SpectatorManager();

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
        this.survivorsLeft = 0;
    }

    public void spawnShard()
    {
        // Prevents shard rain
        if (herobrineState == HerobrineState.HUMAN ||
                 gameState == GameState.SHARD_SPAWNED ||
                 gameState == GameState.SHARD_PICKEDUP ||
                 gameState == GameState.SHARD_CAPTURED)
        {
            return;
        }

        if (!Objects.isNull(activeShard))
        {
            activeShard = null;
        }
        activeShard = new Shard(Config.getRandomShardSpawn(mapId), altarLocation);
        activeShard.spawn();

        // Destroys the shard after x seconds (defined in config), if the shard isn't collected yet.
        Bukkit.getScheduler().runTaskLater(Main.getInstance(), new Runnable()
        {
            @Override
            public void run()
            {
                if (gameState == GameState.SHARD_SPAWNED)
                {
                    activeShard.despawn();
                }
            }
        }, Config.getShardTicksLived());
    }

    public void captureActiveShard()
    {
        activeShard.capture();

        // Strikes ambient lightning after 5 seconds
        Bukkit.getScheduler().runTaskLater(Main.getInstance(), new Runnable()
        {
            @Override
            public void run()
            {
                Location ambientLightningLoc = new Location(altarLocation.getWorld(), altarLocation.getX(), 255.0, altarLocation.getZ());
                ambientLightningLoc.getWorld().strikeLightningEffect(ambientLightningLoc);
            }
        }, 20L * 5L);
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
        return activeShard;
    }

    public void setSurvivorsLeft(int survivorsLeft)
    {
        this.survivorsLeft = survivorsLeft;
    }

    public int getSurvivorsLeft()
    {
        return survivorsLeft;
    }

    // Resets the player's health, invisibility, vanish, invulnerability, inventory, etc.
    // Good for when players leave the game
    public void removeAllEffectsAndItems(Player player)
    {
        PlayerData pd = Main.getInstance().playerDataMap.get(player.getUniqueId());

        // Resets health to 20.0
        AttributeInstance healthAttribute = player.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        if (healthAttribute.getBaseValue() != 20.0)
        {
            healthAttribute.setBaseValue(20.0);
        }

        // Removes ALL potion effects from the player
        for (PotionEffect effect : player.getActivePotionEffects())
        {
            player.removePotionEffect(effect.getType());
        }

        // Unvanishes the player if they're vanished. Else, does nothing.
        if (pd.isVanished())
        {
            Main.getInstance().toggleVisibilityNative(player);
        }

        // Disables flying if the player is allowed to fly
        if(player.getAllowFlight())
        {
            player.setAllowFlight(false);
        }

        // If the player was invulnerable, set that to false
        if (player.isInvulnerable())
        {
            player.setInvulnerable(false);
        }

        // Clears the player's inventory
        player.getInventory().clear();
    }

    public Player getHerobrine()
    {
        return herobrine;
    }

    public void setHerobrine(Player player)
    {
        herobrine = player;
    }
}
