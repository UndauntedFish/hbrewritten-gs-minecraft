package com.ben.hbrewrittengs.customevents;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerUnvanishEvent extends Event implements Cancellable
{
    private Player unvanished;
    private static final HandlerList HANDLERS = new HandlerList();
    private boolean cancelled;

    public PlayerUnvanishEvent(Player unvanished)
    {
        this.unvanished = unvanished;
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

    @Override
    public boolean isCancelled()
    {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel)
    {
        this.cancelled = cancel;
    }

    public Player getPlayer()
    {
        return unvanished;
    }
}
