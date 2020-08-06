package com.ben.hbrewrittengs.listeners;

import com.ben.hbrewrittengs.Main;
import com.ben.hbrewrittengs.PlayerData;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class PlayerLeaveListener implements Listener
{
	@EventHandler
	public void onKick(PlayerKickEvent e)
	{
		playerLeaveRoutine(e.getPlayer());
		e.setLeaveMessage(null);
	}
	
	@EventHandler
	public void onDisconnect(PlayerQuitEvent e)
	{
		playerLeaveRoutine(e.getPlayer());
		e.setQuitMessage(null);
	}

	private void playerLeaveRoutine(Player player)
	{
		UUID uuid = player.getUniqueId();
		PlayerData pd = Main.getInstance().playerDataMap.get(uuid);

		// Resets health to 20.0
		AttributeInstance healthAttribute = player.getAttribute(Attribute.GENERIC_MAX_HEALTH);
		healthAttribute.setBaseValue(20.0);

		// If the player was invulnerable, set that to false
		if (player.isInvulnerable())
		{
			player.setInvulnerable(false);
		}

		// Disables flying if the player is allowed to fly
		if(player.getAllowFlight())
		{
			player.setAllowFlight(false);
		}


		// set herobrine to false in db here if player is hb
		Main.getInstance().playerDataMap.remove(pd);
	}
}
