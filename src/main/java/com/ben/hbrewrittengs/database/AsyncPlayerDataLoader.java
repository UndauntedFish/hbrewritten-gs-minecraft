package com.ben.hbrewrittengs.database;

import com.ben.hbrewrittengs.Main;
import com.ben.hbrewrittengs.PlayerData;
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

		int points = Queries.getPoints(uuid);
		pd.setPoints(points);

		boolean isHerobrine = Queries.isHerobrine(uuid);
		pd.setHerobrine(isHerobrine);

		String activeClass = Queries.getActiveClass(uuid);
		pd.setActiveClass(activeClass);


		// Adding the PlayerData object to the main class's hashmap.
		if (Main.getInstance().playerDataMap.containsKey(uuid))
		{
			Main.getInstance().playerDataMap.replace(uuid, pd);
		}
		else
		{
			Main.getInstance().playerDataMap.put(uuid, pd);
		}
		Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "[HBRls] Player data successfully loaded.");
	}
}
