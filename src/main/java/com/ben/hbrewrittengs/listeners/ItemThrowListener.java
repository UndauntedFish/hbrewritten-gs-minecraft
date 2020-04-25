package com.ben.hbrewrittengs.listeners;

import com.ben.hbrewrittengs.Main;
import com.ben.hbrewrittengs.PlayerData;
import com.ben.hbrewrittengs.customitems.assassin.SmokeScreen;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

public class ItemThrowListener implements Listener
{
    @EventHandler
    public void onItemThrow(PlayerInteractEvent e)
    {
        Player player = e.getPlayer();
        PlayerData pd = Main.getInstance().playerDataMap.get(player.getUniqueId());

        if (e.getHand() == EquipmentSlot.HAND)
        {
            if ((e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK))
            {
                /* Code reaches here if player right clicked */

                // Throw a smoke screen (iron nugget)
                if (player.getInventory().getItemInMainHand().getType() == Material.IRON_NUGGET)
                {
                    Entity thrownItem = SmokeScreen.throwItem(Material.IRON_NUGGET, player);
                    pd.thrownSmokeScreens.add(thrownItem);
                    // Removes the thrown item from the player's inventory.
                    player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
                }
            }
            else if ((e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK))
            {
                /* Code reaches here if player left clicked */

                // If the thrown item is a smoke screen, activate it
                if (!pd.thrownSmokeScreens.isEmpty())
                {
                    Entity thrownIronNuggetEntity = pd.thrownSmokeScreens.removeLast();
                    SmokeScreen.activate(thrownIronNuggetEntity, player);
                }
            }
        }
    }
}
