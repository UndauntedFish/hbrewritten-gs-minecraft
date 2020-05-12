package com.ben.hbrewrittengs.listeners.itemlisteners;

import com.ben.hbrewrittengs.Main;
import com.ben.hbrewrittengs.PlayerData;
import com.ben.hbrewrittengs.customitems.common.Woofless;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class SummonWooflessListener implements Listener
{
    @EventHandler
    public void onSummonWoofless(PlayerInteractEvent e)
    {
        Player player = e.getPlayer();
        PlayerData pd = Main.getInstance().playerDataMap.get(player.getUniqueId());

        Action action = e.getAction();
        ItemStack heldItem = player.getInventory().getItemInMainHand();

        if (e.getHand() == EquipmentSlot.HAND)
        {
            if (action == Action.RIGHT_CLICK_BLOCK)
            {
                if (heldItem.getType() == Material.BONE)
                {
                    // Summons the Woofless at the player's crosshair location
                    Woofless.spawn(player, e.getClickedBlock().getLocation().add(0.0, 1.0, 0.0));

                    // Removing used Summon Woofless bone from player's inventory
                    player.getInventory().getItemInMainHand().setAmount(
                            player.getInventory().getItemInMainHand().getAmount() - 1);
                }
            }
        }
    }

    /*
     * Must manually remove dead wolves, since "Wolf was slain by <player using <item>"
     * broadcasts in chat if a tamed wolf dies normally. Implement this using NMS.
     */
    public void wolfDeathMessagePacketListener()
    {

    }
}
