package com.ben.hbrewrittengs.listeners.itemlisteners;

import com.ben.hbrewrittengs.Main;
import com.ben.hbrewrittengs.PlayerData;
import com.ben.hbrewrittengs.customitems.common.NotchsWisdom;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class NotchsWisdomActivationListener implements Listener
{
    @EventHandler
    public void onNotchsWisdomActivate(PlayerInteractEvent e)
    {
        Player player = e.getPlayer();
        PlayerData pd = Main.getInstance().playerDataMap.get(player.getUniqueId());

        Action action = e.getAction();
        ItemStack heldItem = player.getInventory().getItemInMainHand();

        if (e.getHand() == EquipmentSlot.HAND)
        {
            if (action == Action.RIGHT_CLICK_BLOCK)
            {
                if (heldItem.getType() == Material.BLAZE_POWDER)
                {
                    // Checks if the player is looking at a solid block.
                    // Notch's Wisdom only activates on solid blocks.
                    // Activating the Notch's Wisdom
                    NotchsWisdom notchsWisdom = new NotchsWisdom(player, getCrosshairTargetLocation(player));
                    notchsWisdom.activate();

                    // Removing activated Notch's Wisdom from player's inventory
                    player.getInventory().getItemInMainHand().setAmount(
                            player.getInventory().getItemInMainHand().getAmount() - 1);
                }
            }
        }
    }

    private static Location getCrosshairTargetLocation(Player player)
    {
        return player.getTargetBlockExact(5).getLocation();
    }
}
