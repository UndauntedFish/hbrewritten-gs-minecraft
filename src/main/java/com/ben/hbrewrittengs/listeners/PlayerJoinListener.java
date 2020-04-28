package com.ben.hbrewrittengs.listeners;

import com.ben.hbrewrittengs.Main;
import com.ben.hbrewrittengs.PlayerData;
import com.ben.hbrewrittengs.enums.ClassData;
import com.ben.hbrewrittengs.enums.Rank;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener
{
	@EventHandler
	public void onJoin(PlayerJoinEvent e)
	{		
		Player player = e.getPlayer();
		PlayerData pd = Main.getInstance().playerDataMap.get(player.getUniqueId());

		e.setJoinMessage(null);
		pd.setRank(Rank.setRankFromPoints(pd.getPoints()));

		if (pd.getActiveClass() != ClassData.ASSASSIN)
		{
			player.setHealth(20.0);
		}
	}
}
