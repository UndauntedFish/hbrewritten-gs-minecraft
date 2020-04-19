package com.ben.hbrewrittengs.database;

import java.util.UUID;

import com.ben.hbrewrittengs.Main;
import com.ben.hbrewrittengs.enums.Rank;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

import com.ben.hbrewrittengs.PlayerData;
import org.bukkit.event.player.PlayerJoinEvent;

public class AsyncPlayerDataLoader implements Listener
{

	// Fires after the player clicks join, but before they spawn in on the server.
	@EventHandler
	public void onPlayerLogin(AsyncPlayerPreLoginEvent e)
	{
		UUID uuid = e.getUniqueId();

		PlayerData pd = new PlayerData(uuid);
		Queries.loadPointsIntoPlayerData(pd);

		if (Main.getInstance().playerDataMap.containsKey(uuid))
		{
			Main.getInstance().playerDataMap.replace(uuid, pd);
		}
		else
		{
			Main.getInstance().playerDataMap.put(uuid, pd);
		}
		Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "[HBRgs] Player data successfully loaded.");
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e)
	{
		Player player = e.getPlayer();
		UUID uuid = player.getUniqueId();
		PlayerData pd = Main.getInstance().playerDataMap.get(uuid);

		// Displays the player their stats 0.5s after they join.
		Bukkit.getScheduler().runTaskLater(Main.getInstance(), new Runnable()
		{
			public void run()
			{
				pd.setDataLoaded(true);
				pd.setRank(Rank.setRankFromPoints(pd.getPoints()));
			}
		}, 100L); // L = ticks. 100 ticks.
	}
}
