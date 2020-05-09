package com.ben.hbrewrittengs.listeners.itemlisteners;

import com.ben.hbrewrittengs.customitems.common.DreamweaverBandage;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class DreamweaverBandageActivationListener implements Listener
{
    @EventHandler
    public void onDreamweaverBandageActivate(PlayerInteractEvent e)
    {
        Player player = e.getPlayer();

        Action action = e.getAction();
        ItemStack heldItem = player.getInventory().getItemInMainHand();

        if (e.getHand() == EquipmentSlot.HAND)
        {
            if (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK)
            {
                if (heldItem.getType() == Material.MAGMA_CREAM)
                {
                    double maxHealth = player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();

                    if (player.getHealth() < maxHealth)
                    {
                        // Creating a new instance of the Dreamweaver Bandage for the player and activating it.
                        DreamweaverBandage dreamweaverBandage = new DreamweaverBandage(player);
                        dreamweaverBandage.activate();

                        // Removing activated Dreamweaver Bandage from player's inventory
                        player.getInventory().getItemInMainHand().setAmount(
                                player.getInventory().getItemInMainHand().getAmount() - 1);
                    }
                }
            }
        }
    }
}
