package com.ben.hbrewrittengs.customevents;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerVanishEvent extends Event implements Cancellable
{
    private Player vanished;
    private static final HandlerList HANDLERS = new HandlerList();
    private boolean cancelled;

    public PlayerVanishEvent(Player vanished)
    {
        this.vanished = vanished;
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
        return vanished;
    }
}
