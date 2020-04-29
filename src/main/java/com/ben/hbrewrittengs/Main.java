package com.ben.hbrewrittengs;

import com.ben.hbrewrittengs.database.AsyncPlayerDataLoader;
import com.ben.hbrewrittengs.database.BaseFields;
import com.ben.hbrewrittengs.listeners.CustomChatFormatListener;
import com.ben.hbrewrittengs.listeners.PlayerDamagePlayerListener;
import com.ben.hbrewrittengs.listeners.PlayerJoinListener;
import com.ben.hbrewrittengs.listeners.PlayerLeaveListener;
import com.ben.hbrewrittengs.listeners.itemlisteners.CloakActivateListener;
import com.ben.hbrewrittengs.listeners.itemlisteners.SmokeScreenThrowListener;
import com.zaxxer.hikari.HikariDataSource;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class Main extends JavaPlugin
{
    private static Main instance;

    public HashMap<UUID, PlayerData> playerDataMap = new HashMap<>();
    public List<Player> vanishedPlayers = new ArrayList<>();

    // Database setup class used to send queries. Used throughout all classes.
    private HikariDataSource hikari;
    private String host, database, username, password;
    private int port;

    @Override
    public void onEnable()
    {
        loadConfig();
        instance = this;

        // Hikari Database Connection Setup
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

        // Eventhandler registration
        Bukkit.getPluginManager().registerEvents(new AsyncPlayerDataLoader(), this);
        Bukkit.getPluginManager().registerEvents(new CustomChatFormatListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerLeaveListener(), this);
        Bukkit.getPluginManager().registerEvents(new SmokeScreenThrowListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerDamagePlayerListener(), this);
        Bukkit.getPluginManager().registerEvents(new CloakActivateListener(), this);


        // Command registration
        getCommand("class").setExecutor(new ClassCommand());
    }

    // Loads info from config.yml
    private void loadConfig()
    {
        this.getConfig().options().copyDefaults();
        saveDefaultConfig();
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


}
