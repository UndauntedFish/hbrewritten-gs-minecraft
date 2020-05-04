package com.ben.hbrewrittengs.customevents;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class VanishedPlayerHitByPlayerEvent extends Event implements Cancellable
{
    private Player damaged, damager;
    private static final HandlerList HANDLERS = new HandlerList();
    private boolean cancelled;

    public VanishedPlayerHitByPlayerEvent(Player damaged, Player damager)
    {
        this.damaged = damaged;
        this.damager = damager;
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

    public Player getDamaged()
    {
        return damaged;
    }

    public Player getDamager()
    {
        return damager;
    }
}
