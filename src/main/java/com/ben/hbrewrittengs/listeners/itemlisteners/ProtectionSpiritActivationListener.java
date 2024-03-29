package com.ben.hbrewrittengs.listeners.itemlisteners;

import com.ben.hbrewrittengs.customitems.paladin.ProtectionSpirit;
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
            EnderPearl thrownPearl = (EnderPearl) e.getEntity();

            // Removing activated Protection Spirit from player's inventory
            player.getInventory().getItemInMainHand().setAmount(
                    player.getInventory().getItemInMainHand().getAmount() - 1);

            // Deletes thrownPearl from the world and plays enderpearl throw sound.
            thrownPearl.remove();
            player.playSound(player.getLocation(), Sound.ENTITY_ENDER_PEARL_THROW, 1.0F, 0.5F);

            // Activates protSpirit
            ProtectionSpirit protSpirit = new ProtectionSpirit(player);
            protSpirit.activate();
        }
    }
}
