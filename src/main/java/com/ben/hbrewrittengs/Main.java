package com.ben.hbrewrittengs;

import com.ben.hbrewrittengs.database.AsyncPlayerDataLoader;
import com.ben.hbrewrittengs.listeners.CustomChatFormatListener;
import com.ben.hbrewrittengs.listeners.PlayerJoinListener;
import com.ben.hbrewrittengs.listeners.PlayerLeaveListener;
import com.zaxxer.hikari.HikariDataSource;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;

public class Main extends JavaPlugin
{
    private static Main instance;

    public HashMap<UUID, PlayerData> playerDataMap = new HashMap<>();

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

        // Eventhandler registration
        Bukkit.getPluginManager().registerEvents(new AsyncPlayerDataLoader(), this);
        Bukkit.getPluginManager().registerEvents(new CustomChatFormatListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerLeaveListener(), this);

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
