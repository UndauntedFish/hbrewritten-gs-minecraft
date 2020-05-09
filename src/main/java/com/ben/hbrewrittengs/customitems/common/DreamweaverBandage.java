package com.ben.hbrewrittengs.customitems.common;

import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;

public class DreamweaverBandage
{
    private Player player;
    private double maxHealth;

    public DreamweaverBandage(Player player)
    {
        this.player = player;
        this.maxHealth = player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
    }

    public void activate()
    {
        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 1.0F);
        player.setHealth(maxHealth);
    }
}
