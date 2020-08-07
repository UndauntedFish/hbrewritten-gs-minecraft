package com.ben.hbrewrittengs.listeners;

import com.ben.hbrewrittengs.Main;
import com.ben.hbrewrittengs.PlayerData;
import com.ben.hbrewrittengs.classes.*;
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

		// Resets the player's health, potion effects, invulnerability, inventory.
		Main.arena.removeAllEffectsAndItems(player);

		if (!pd.isHerobrine())
		{
			assignClass(player, pd.getActiveClass());
		}
		else
		{
			assignHerobrine(player);
		}

	}

	// Gives the player the items for their chosen class
	private static void assignClass(Player player, ClassData chosenClass)
	{
		if (chosenClass.equals(ClassData.PALADIN))
		{
			Paladin.giveClass(player);
		}
		else if (chosenClass.equals(ClassData.ASSASSIN))
		{
			Assassin.giveClass(player);
		}
		else
		{
			Archer.giveClass(player);
			player.sendMessage("fuck you");
		}

		/*
		switch(chosenClass)
		{
			case ARCHER:
				Archer.giveClass(player);
			case ASSASSIN:
				Assassin.giveClass(player);
			case DEMO:
				Demo.giveClass(player);
			case MAGE:
				Mage.giveClass(player);
			case PALADIN:
				Paladin.giveClass(player);
			case PRIEST:
				Priest.giveClass(player);
			case SCOUT:
				Scout.giveClass(player);
			case SORCERER:
				Sorcerer.giveClass(player);
			case WIZARD:
				Wizard.giveClass(player);
		}
		*/
	}

	// Gives the player the items for HEROBRINE (no shards capped)
	private static void assignHerobrine(Player player)
	{
		Main.arena.setHerobrine(player);
	}
}
