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

		/* Building up the player's PlayerData object with data fetched from the database */

		PlayerData pd = new PlayerData(uuid);

		// Fetches the player's points from the database, puts it into PlayerData pd
		pd.setPoints(Queries.getPoints(uuid));
		Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD + "Points: " + pd.getPoints());

		// Fetches the player's is_herobrine value from the database, puts it into PlayerData pd
		pd.setHerobrine(Queries.isHerobrine(uuid));
		Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD + "IsHerobrine: " + pd.isHerobrine());
		// Resets is_herobrine to false in the database, if it was true.
		if (pd.isHerobrine())
		{
			Queries.setHerobrine(uuid, false);
		}

		// Fetches the player's chosen class from the database, puts it into PlayerData pd
		pd.setActiveClass(ClassData.valueOf(Queries.getActiveClass(uuid)));
		Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD + "Active Class: " + pd.getActiveClass().getName());


		// Adds PlayerData pd to the main class's PlayerData hashmap.
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
