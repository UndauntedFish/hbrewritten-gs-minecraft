package com.ben.hbrewrittengs.database;

import java.util.UUID;

import com.ben.hbrewrittengs.enums.Rank;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

import com.ben.hbrewrittengs.PlayerData;

public class AsyncPlayerDataLoader implements Listener
{
	@EventHandler
	public void onPlayerLogin(AsyncPlayerPreLoginEvent e)
	{
		UUID uuid = e.getUniqueId();

		PlayerData pd = new PlayerData(uuid);
		Queries.loadPointsIntoPlayerData(pd);
		pd.setRank(Rank.setRankFromPoints(pd.getPoints()));

	}
}
