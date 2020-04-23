package com.ben.hbrewrittengs.listeners;

import com.ben.hbrewrittengs.Main;
import com.ben.hbrewrittengs.PlayerData;
import com.ben.hbrewrittengs.enums.Rank;
import com.ben.hbrewrittengs.inventoryitems.ItemManager;
import org.bukkit.Bukkit;
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

		ItemManager.addItemsToPlayerInventory(player);
		addPlayerToTimer(player);
	}
	
	// Safely adds player to timer. If there is not enough players, it won't add the player until the minplayers threshold is met, etc.
	private void addPlayerToTimer(Player player)
	{
		int playersOnline = Bukkit.getOnlinePlayers().size();
		int minPlayersNeededToStart = Main.getInstance().getConfig().getInt("minplayers");
		
		if ( (!Main.getInstance().lobbyTimer.wasStarted()) && (playersOnline >= minPlayersNeededToStart) ) 
		{
			/*
			 *  Code will reach here if the timer wasn't already started AND 
			 *  there's enough players to start the timer.
			 *  
			 *  So we'll start the timer and add the new player and all players who were waiting prior.
			 */
			Main.getInstance().lobbyTimer.startTimer();
			
			// Adds the player who just joined, and the players already waiting to the timer.
			for (Player p : Bukkit.getOnlinePlayers())
			{
				Main.getInstance().lobbyTimer.getTimer().addPlayer(p);
			}
		}
		else if (Main.getInstance().lobbyTimer.wasStarted())
		{
			// Code will reach here if the timer was started already. So we just add the new player to the timer.
			Main.getInstance().lobbyTimer.getTimer().addPlayer(player);
		}
		else
		{
			// Code will reach here if the timer wasn't started because there's not enough players waiting.
			Main.getInstance().lobbyTimer.getTimer().addPlayer(player);

			int currentPlayersNeededToStart = (minPlayersNeededToStart - playersOnline);
			
			if (currentPlayersNeededToStart == 1)
			{
				Main.getInstance().lobbyTimer.getTimer().setTitle("1 more player needed to start the game!");
			}
			else
			{
				Main.getInstance().lobbyTimer.getTimer().setTitle(currentPlayersNeededToStart + " more players needed to start the game!");
			}
		}
	}
}
