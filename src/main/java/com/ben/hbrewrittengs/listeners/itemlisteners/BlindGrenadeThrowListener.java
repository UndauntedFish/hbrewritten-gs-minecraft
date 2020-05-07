package com.ben.hbrewrittengs.listeners.itemlisteners;

import com.ben.hbrewrittengs.Main;
import com.ben.hbrewrittengs.PlayerData;
import com.ben.hbrewrittengs.customitems.common.BlindGrenade;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class BlindGrenadeThrowListener implements Listener
{
    @EventHandler
    public void onBlindGrenadeThrow(PlayerInteractEvent e)
    {
        Player player = e.getPlayer();
        PlayerData pd = Main.getInstance().playerDataMap.get(player.getUniqueId());

        Action action = e.getAction();
        ItemStack heldItem = player.getInventory().getItemInMainHand();

        if (e.getHand() == EquipmentSlot.HAND)
        {
            if (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK)
            {
                if (heldItem.getType() == Material.GOLD_NUGGET)
                {
                    // Throwing the blind grenade
                    BlindGrenade.unpinAndThrow(player);
                }
            }
        }
    }
}
