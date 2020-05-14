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
            if (heldItem.getType() == Material.BLAZE_POWDER)
            {
                NotchsWisdom notchsWisdom = null;

                // If the player is looking at a block, activate the notch's wisdom there.
                // Otherwise, activate it wherever the player is.
                if (action == Action.RIGHT_CLICK_BLOCK)
                {
                    notchsWisdom = new NotchsWisdom(player, getCrosshairTargetLocation(player));
                }
                else if (action == Action.RIGHT_CLICK_AIR)
                {
                    notchsWisdom = new NotchsWisdom(player, player.getLocation().subtract(0.0, 0.5, 0.0));
                }
                notchsWisdom.activate();

                // Removing activated Notch's Wisdom from player's inventory
                player.getInventory().getItemInMainHand().setAmount(
                        player.getInventory().getItemInMainHand().getAmount() - 1);
            }
        }
    }

    private static Location getCrosshairTargetLocation(Player player)
    {
        return player.getTargetBlockExact(5).getLocation();
    }
}
