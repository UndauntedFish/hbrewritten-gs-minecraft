package com.ben.hbrewrittengs.listeners.itemlisteners;

import com.ben.hbrewrittengs.Main;
import com.ben.hbrewrittengs.PlayerData;
import com.ben.hbrewrittengs.customitems.paladin.ProtectionSpirit;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;

public class ProtectionSpiritActivationListener implements Listener
{
    @EventHandler
    public void onProtectionSpiritActivate(ProjectileLaunchEvent e)
    {
        if (e.getEntity().getShooter() instanceof Player && e.getEntity() instanceof EnderPearl)
        {
            Player player = (Player) e.getEntity().getShooter();
            PlayerData pd = Main.getInstance().playerDataMap.get(player.getUniqueId());

            EnderPearl thrownPearl = (EnderPearl) e.getEntity();
            thrownPearl.remove();
            player.playSound(player.getLocation(), Sound.ENTITY_ENDER_PEARL_THROW, 1.0F, 0.5F);

            // Activates protSpirit and deletes thrownPearl from the world after 5 ticks
            Bukkit.getScheduler().runTaskLater(Main.getInstance(), new Runnable()
            {
                @Override
                public void run()
                {
                    ProtectionSpirit protSpirit = new ProtectionSpirit(player);
                    protSpirit.activate();
                }
            }, 10L);
        }
    }
}
