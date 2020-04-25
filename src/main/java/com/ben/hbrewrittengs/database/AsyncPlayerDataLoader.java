package com.ben.hbrewrittengs.database;

import com.ben.hbrewrittengs.Main;
import com.ben.hbrewrittengs.PlayerData;
import com.ben.hbrewrittengs.enums.ClassData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

import java.util.UUID;

public class AsyncPlayerDataLoader implements Listener
{
	// Fires after the player clicks join, but before they spawn in on the server.
	@EventHandler
	public void onPlayerLogin(AsyncPlayerPreLoginEvent e)
	{
		UUID uuid = e.getUniqueId();

		// Building up the player's PlayerData object with data fetched from the database
		PlayerData pd = new PlayerData(uuid);

		pd.setPoints(Queries.getPoints(uuid));
		Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD + "Points: " + pd.getPoints());

		pd.setHerobrine(Queries.isHerobrine(uuid));
		Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD + "IsHerobrine: " + pd.isHerobrine());

		pd.setActiveClass(ClassData.valueOf(Queries.getActiveClass(uuid)));
		Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD + "Active Class: " + pd.getActiveClass().getName());


		// Adding the PlayerData object to the main class's hashmap.
		if (Main.getInstance().playerDataMap.containsKey(uuid))
		{
			Main.getInstance().playerDataMap.replace(uuid, pd);
			Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "[HBRls] Player data successfully overwritten.");
		}
		else
		{
			Main.getInstance().playerDataMap.put(uuid, pd);
			Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "[HBRls] Player data successfully loaded.");
		}
	}
}
