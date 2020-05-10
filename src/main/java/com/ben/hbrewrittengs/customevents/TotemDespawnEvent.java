package com.ben.hbrewrittengs.customevents;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

public class TotemDespawnEvent extends Event
{
    private static final HandlerList HANDLERS = new HandlerList();

    private Player player;
    private ItemStack totemItem;

    public TotemDespawnEvent(Player player, ItemStack totemItem)
    {
        this.player = player;
        this.totemItem = totemItem;
    }

    @Override
    public HandlerList getHandlers()
    {
        return HANDLERS;
    }

    public static HandlerList getHandlerList()
    {
        return HANDLERS;
    }

    public Player getPlayer()
    {
        return player;
    }

    public void setPlayer(Player player)
    {
        this.player = player;
    }

    public ItemStack getTotemItem()
    {
        return totemItem;
    }
}
