package com.ben.hbrewrittengs.bossbarcooldown;

import com.ben.hbrewrittengs.Main;
import com.ben.hbrewrittengs.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;

public class ImplicitCooldown
{
    private Player player;
    private PlayerData pd;
    private ItemStack cooldownItem;
    private double lengthInSeconds, timeRemaining;
    private boolean isDone, wasStarted;
    private int taskID;

    public ImplicitCooldown(PlayerData pd, ItemStack cooldownItem, double lengthInSeconds)
    {
        this.pd = pd;
        this.player = Bukkit.getPlayer(pd.getUUID());
        this.cooldownItem = cooldownItem;
        this.lengthInSeconds = lengthInSeconds;
        this.isDone = false;
        this.wasStarted = false;
    }

    public ImplicitCooldown(Player player, ItemStack cooldownItem, double lengthInSeconds)
    {
        this.pd = Main.getInstance().playerDataMap.get(player);
        this.player = player;
        this.cooldownItem = cooldownItem;
        this.lengthInSeconds = lengthInSeconds;
        this.isDone = false;
        this.wasStarted = false;
    }

    public void start()
    {
        this.wasStarted = true;
        this.timeRemaining = lengthInSeconds;

        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Runnable()
        {
            @Override
            public void run()
            {
                if (timeRemaining <= 0.0)
                {
                    // end cooldown
                    isDone = true;
                    Bukkit.getScheduler().cancelTask(taskID);
                }
                else
                {
                    // continue cooldown
                    timeRemaining = timeRemaining - (1.0F / 20.0F);
                }
            }
        }, 0L, 1L);
    }

    public boolean wasStarted()
    {
        return wasStarted;
    }

    public boolean isDone()
    {
        return isDone;
    }

    public int getTimeRemaining()
    {
        return (int) (timeRemaining + 0.5);
    }

    public double getExactTimeRemaining()
    {
        return round(timeRemaining, 2);
    }

    private static double round(double value, int places)
    {
        if (places < 0)
        {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[ItemNameCooldown] CRITICAL ERROR! Places is < 0 in INC.round");
            throw new IllegalArgumentException();
        }

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public UUID getPlayerUUID()
    {
        return player.getUniqueId();
    }

    public ItemStack getCooldownItem()
    {
        return cooldownItem;
    }
}
