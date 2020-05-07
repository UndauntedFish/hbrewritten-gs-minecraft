package com.ben.hbrewrittengs.bossbarcooldown;

import com.ben.hbrewrittengs.Main;
import com.ben.hbrewrittengs.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
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
    private BossBar bossbar;
    private double lengthInSeconds, timeRemaining, bossbarIncrement;
    private boolean isDone, wasStarted;
    private int taskID;
    private String cooldownEndMessage;

    public ImplicitCooldown(PlayerData pd, ItemStack cooldownItem, double lengthInSeconds, String cooldownTitle, BarColor barColor)
    {
        this.pd = pd;
        this.player = Bukkit.getPlayer(pd.getUUID());
        this.cooldownItem = cooldownItem;
        this.bossbar = Bukkit.createBossBar(cooldownTitle, barColor, BarStyle.SOLID);
        this.lengthInSeconds = lengthInSeconds;
        this.isDone = false;
        this.wasStarted = false;
        bossbarIncrement = 1.0 / (lengthInSeconds * 20.0);
    }

    public void start()
    {
        this.wasStarted = true;
        this.timeRemaining = lengthInSeconds;
        bossbar.setProgress(1.0);
        bossbar.addPlayer(player);

        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Runnable()
        {
            @Override
            public void run()
            {
                if (timeRemaining <= 0.0)
                {
                    // end cooldown
                    pd.activeBossBarCooldowns.remove(player.getUniqueId());
                    isDone = true;
                    bossbar.setProgress(0.0);
                    bossbar.removeAll();
                    if (!cooldownEndMessage.equals(null))
                    {
                        player.sendMessage(cooldownEndMessage);
                    }
                    Bukkit.getScheduler().cancelTask(taskID);
                }
                else
                {
                    // continue cooldown

                    // This makes sure that the bossbar progress is never set above 1.0F
                    if (bossbar.getProgress() - bossbarIncrement < 0.0)
                    {
                        bossbar.setProgress(0.0);
                    }
                    else
                    {
                        bossbar.setProgress(bossbar.getProgress() - bossbarIncrement);
                    }

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

    public String getCooldownEndMessage()
    {
        return cooldownEndMessage;
    }

    public void setCooldownEndMessage(String cooldownEndMessage)
    {
        this.cooldownEndMessage = cooldownEndMessage;
    }
}
