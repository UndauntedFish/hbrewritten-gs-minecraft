package com.ben.hbrewrittengs;

import com.ben.hbrewrittengs.classes.*;
import com.ben.hbrewrittengs.customevents.PlayerUnvanishEvent;
import com.ben.hbrewrittengs.customevents.PlayerVanishEvent;
import com.ben.hbrewrittengs.customevents.VanishedPlayerHitByPlayerEvent;
import com.ben.hbrewrittengs.database.AsyncPlayerDataLoader;
import com.ben.hbrewrittengs.database.BaseFields;
import com.ben.hbrewrittengs.listeners.CustomChatFormatListener;
import com.ben.hbrewrittengs.listeners.PlayerDamagePlayerListener;
import com.ben.hbrewrittengs.listeners.PlayerJoinListener;
import com.ben.hbrewrittengs.listeners.PlayerLeaveListener;
import com.ben.hbrewrittengs.listeners.itemlisteners.*;
import com.ben.hbrewrittengs.utils.Vector3D;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.zaxxer.hikari.HikariDataSource;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

public class Main extends JavaPlugin
{
    private static Main instance;

    public HashMap<UUID, PlayerData> playerDataMap;

    private ProtocolManager protocolManager;

    // Database setup class used to send queries. Used throughout all classes.
    private HikariDataSource hikari;
    private String host, database, username, password;
    private int port;

    @Override
    public void onEnable()
    {
        instance = this;
        playerDataMap = new HashMap<>();
        protocolManager = ProtocolLibrary.getProtocolManager();

        loadConfig();
        connectToDatabase();
        loadClassItems();
        registerListeners();

        // ProtocolLib Packet Listeners
        vanishPacketListener();

        getCommand("class").setExecutor(new ClassCommand());
    }

    // Loads info from config.yml
    private void loadConfig()
    {
        this.getConfig().options().copyDefaults();
        saveDefaultConfig();
    }

    private void connectToDatabase()
    {
        host = this.getConfig().getString("host");
        port = this.getConfig().getInt("port");
        database = this.getConfig().getString("database");
        username = this.getConfig().getString("username");
        password = this.getConfig().getString("password");

        hikari = new HikariDataSource();
        hikari.setDataSourceClassName("com.mysql.jdbc.jdbc2.optional.MysqlDataSource");
        hikari.addDataSourceProperty("serverName", host);
        hikari.addDataSourceProperty("port", port);
        hikari.addDataSourceProperty("databaseName", database);
        hikari.addDataSourceProperty("user", username);
        hikari.addDataSourceProperty("password", password);
        try
        {
            BaseFields.connection = hikari.getConnection();
        }
        catch (SQLException e)
        {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[HBRgs] Unable to set basefields connection to Hikari connection!");
            e.printStackTrace();
        }
    }

    // Initializes the default values for all the classes' items
    private void loadClassItems()
    {
        CommonItems.loadItems();

        Archer.loadItems();
        Assassin.loadItems();
        Demo.loadItems();
        Mage.loadItems();
        Paladin.loadItems();
        Priest.loadItems();
        Scout.loadItems();
        Sorcerer.loadItems();
        Wizard.loadItems();
    }

    private void registerListeners()
    {
        Bukkit.getPluginManager().registerEvents(new AsyncPlayerDataLoader(), this);
        Bukkit.getPluginManager().registerEvents(new CustomChatFormatListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerLeaveListener(), this);
        Bukkit.getPluginManager().registerEvents(new SmokeScreenThrowListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerDamagePlayerListener(), this);
        Bukkit.getPluginManager().registerEvents(new CloakActivationListener(), this);
        Bukkit.getPluginManager().registerEvents(new BlindGrenadeThrowListener(), this);
        Bukkit.getPluginManager().registerEvents(new ChemicalGrenadeThrowListener(), this);
        Bukkit.getPluginManager().registerEvents(new ProtectionSpiritActivationListener(), this);
        Bukkit.getPluginManager().registerEvents(new NotchsWisdomActivationListener(), this);
        Bukkit.getPluginManager().registerEvents(new DreamweaverBandageActivationListener(), this);
        Bukkit.getPluginManager().registerEvents(new TotemListeners(), this);
        Bukkit.getPluginManager().registerEvents(new SummonWooflessListener(), this);
    }

    private void vanishPacketListener()
    {
        protocolManager.addPacketListener(new PacketAdapter(this, PacketType.Play.Client.ARM_ANIMATION)
        {
            @Override
            public void onPacketReceiving(PacketEvent armSwingEvent)
            {
                final int ATTACK_REACH = 4;

                Player observer = armSwingEvent.getPlayer();
                PlayerData observerPd = playerDataMap.get(observer.getUniqueId());

                // Hitbox of the observer player, with their attack reach included.
                Location observerPos = observer.getEyeLocation();
                Vector3D observerDir = new Vector3D(observerPos.getDirection());
                Vector3D observerStart = new Vector3D(observerPos);
                Vector3D observerEnd = observerStart.add(observerDir.multiply(ATTACK_REACH));

                Player hit = null;

                // Get nearby entities
                for (Player target : protocolManager.getEntityTrackers(observer))
                {
                    PlayerData targetPd = playerDataMap.get(target.getUniqueId());

                    // Checks if a vanished player (target) is in the observer's hitbox.
                    if (targetPd.isVanished())
                    {
                        // Hitbox of the target player
                        Vector3D targetPos = new Vector3D(target.getLocation());
                        Vector3D minimum = targetPos.add(-0.5, 0, -0.5);
                        Vector3D maximum = targetPos.add(0.5, 1.67, 0.5);

                        /*
                         * If the target player's hitbox falls within the observer player's hitbox,
                         * mark the target player as hit by the observer.
                         */
                        if (hasIntersection(observerStart, observerEnd, minimum, maximum))
                        {
                            // If the hit player was already hit recently, don't hit them again.
                            // Prevents machine-gun-like, no-delay hitspam
                            Player finalHit = target;
                            AtomicBoolean wasAlreadyHit = new AtomicBoolean(false);
                            Bukkit.getScheduler().runTask(Main.getInstance(), () ->
                            {
                                if (finalHit.getNoDamageTicks() != 0)
                                {
                                    // Block the observer from hitting the block behind the vanished player here
                                    wasAlreadyHit.set(true);
                                }
                            });
                            if (wasAlreadyHit.get())
                            {
                                armSwingEvent.setCancelled(true);
                            }
                            else
                            {
                                hit = target;
                            }
                        }
                    }
                }

                // Simulates a hit against the closest vanished player by the observer
                if (hit != null)
                {
                    // Fires VanishedPlayerHitByPlayerEvent on the main thread (since this is an async thread)
                    VanishedPlayerHitByPlayerEvent vHitEvent = new VanishedPlayerHitByPlayerEvent(hit, observer);
                    AtomicBoolean cancelled = new AtomicBoolean(false);
                    Bukkit.getScheduler().runTask(Main.getInstance(), () ->
                    {
                        Bukkit.getPluginManager().callEvent(vHitEvent);
                        if (vHitEvent.isCancelled())
                        {
                            cancelled.set(true);
                        }
                    });

                    if (!cancelled.get())
                    {
                        // This packet makes the observer player attack the vanished "hit" player
                        PacketContainer useEntity = protocolManager.createPacket(PacketType.Play.Client.USE_ENTITY);
                        useEntity.getIntegers().write(0, hit.getEntityId());
                        useEntity.getEntityUseActions().write(0, EnumWrappers.EntityUseAction.ATTACK);
                        try
                        {
                            protocolManager.recieveClientPacket(observer, useEntity);
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }

                        // Sets the damage delay for hit player to avoid hitspam from the observer.
                        Player finalHit = hit;
                        Bukkit.getScheduler().runTask(Main.getInstance(), () ->
                        {
                            finalHit.setNoDamageTicks(finalHit.getMaximumNoDamageTicks());
                        });

                        /*
                         * WARNING: The game must be in ADVENTURE MODE to prevent the observer from breaking
                         *  the block behind the vanished player, which would the observer to hit the vanished
                         *  player without any hit delay. (or else the vanished player would get hit like a machine gun)
                         */
                    }
                }
            }
        });
    }

    // Checks if two given 3D Vectors have intersecting points (AABB Collision Detection)
    //    Source: [url]http://www.gamedev.net/topic/338987-aabb---line-segment-intersection-test/[/url]
    private boolean hasIntersection(Vector3D p1, Vector3D p2, Vector3D min, Vector3D max)
    {
        final double epsilon = 0.0001f;

        Vector3D d = p2.subtract(p1).multiply(0.5);
        Vector3D e = max.subtract(min).multiply(0.5);
        Vector3D c = p1.add(d).subtract(min.add(max).multiply(0.5));
        Vector3D ad = d.abs();

        if (Math.abs(c.x) > e.x + ad.x)
            return false;
        if (Math.abs(c.y) > e.y + ad.y)
            return false;
        if (Math.abs(c.z) > e.z + ad.z)
            return false;

        if (Math.abs(d.y * c.z - d.z * c.y) > e.y * ad.z + e.z * ad.y + epsilon)
            return false;
        if (Math.abs(d.z * c.x - d.x * c.z) > e.z * ad.x + e.x * ad.z + epsilon)
            return false;
        if (Math.abs(d.x * c.y - d.y * c.x) > e.x * ad.y + e.y * ad.x + epsilon)
            return false;

        return true;
    }

    // Toggles vanish for a given player
    public void toggleVisibilityNative(Player target)
    {
        PlayerData targetPd = playerDataMap.get(target.getUniqueId());

        // Checks if the target player is already vanished.
        if (targetPd.isVanished())
        {
            // Fires PlayerUnvanishEvent
            PlayerUnvanishEvent puvEvent = new PlayerUnvanishEvent(target);
            Bukkit.getPluginManager().callEvent(puvEvent);

            // Unvanishes the target player if the PlayerUnvanishEvent isn't cancelled
            if (!puvEvent.isCancelled())
            {
                targetPd.setVanished(false);
                Bukkit.getOnlinePlayers().forEach( (onlinePlayer) ->
                {
                    onlinePlayer.showPlayer(this, target);
                });
            }
        }
        else
        {
            // Fires PlayerVanishEvent
            PlayerVanishEvent pvEvent = new PlayerVanishEvent(target);
            Bukkit.getPluginManager().callEvent(pvEvent);

            // Vanishes the target player if the PlayerVanishEvent isn't cancelled
            if (!pvEvent.isCancelled())
            {
                targetPd.setVanished(true);
                Bukkit.getOnlinePlayers().forEach( (onlinePlayer) ->
                {
                    onlinePlayer.hidePlayer(this, target);
                });
            }
        }
    }

    @Override
    public void onDisable()
    {
        if (!hikari.equals(null))
        {
            hikari.close();
        }
    }

    public static Main getInstance()
    {
        return instance;
    }

    public HikariDataSource getHikari()
    {
        return hikari;
    }

    public ProtocolManager getProtocolManager()
    {
        return protocolManager;
    }
}
