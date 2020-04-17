package com.ben.hbrewrittengs;

import java.util.HashMap;
import java.util.UUID;

import com.zaxxer.hikari.HikariDataSource;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import com.ben.hbrewrittengs.database.AsyncPlayerDataLoader;

import javax.sql.DataSource;

public class Main extends JavaPlugin
{
    private static Main instance;

    public HashMap<UUID, PlayerData> playerDataMap = new HashMap<>();

    // Database setup class used to send queries. Used throughout all classes.
    private HikariDataSource hikari;

    @Override
    public void onEnable()
    {
        loadConfig();
        instance = this;

        // Hikari Database Connection Setup
        hikari = new HikariDataSource();
        hikari.setDataSourceClassName("org.mariadb.jdbc.MySQLDataSource");
        hikari.addDataSourceProperty("serverName", this.getConfig().getString("host"));
        hikari.addDataSourceProperty("port", this.getConfig().getInt("port"));
        hikari.addDataSourceProperty("databaseName", this.getConfig().getString("database"));
        hikari.addDataSourceProperty("user", this.getConfig().getString("username"));
        if (!this.getConfig().getString("password").equals(null))
        {
            hikari.addDataSourceProperty("password", this.getConfig().getString("password"));
        }
        else
        {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[HBRgs] You forgot to set the password!");
            this.getPluginLoader().disablePlugin(this);
        }

        // Eventhandler registration
        Bukkit.getPluginManager().registerEvents(new AsyncPlayerDataLoader(), this);
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
