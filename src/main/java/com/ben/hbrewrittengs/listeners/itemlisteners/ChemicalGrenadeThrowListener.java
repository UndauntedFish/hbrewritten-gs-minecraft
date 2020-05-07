package com.ben.hbrewrittengs.listeners.itemlisteners;

import com.ben.hbrewrittengs.Main;
import com.ben.hbrewrittengs.PlayerData;
import com.ben.hbrewrittengs.bossbarcooldown.ImplicitCooldown;
import com.ben.hbrewrittengs.customitems.demo.ChemicalGrenade;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.Iterator;

public class ChemicalGrenadeThrowListener implements Listener
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
                if (heldItem.getType() == Material.GHAST_TEAR)
                {
                    // Checks if smoke screen has any active cooldowns
                    if (!pd.activeImplicitCooldowns.isEmpty())
                    {
                        for (Iterator<ImplicitCooldown> itr = pd.activeImplicitCooldowns.iterator(); itr.hasNext(); )
                        {

                            ImplicitCooldown cooldown = itr.next();
                            if (cooldown.getCooldownItem().isSimilar(heldItem) &&
                                    cooldown.getPlayerUUID().equals(pd.getUUID()))
                            {
                                if (cooldown.isDone())
                                {
                                    itr.remove();
                                    break;
                                }
                                else
                                {
                                    e.setCancelled(true);
                                    return;
                                }
                            }
                        }

                    }

                    // Throwing the blind grenade
                    ChemicalGrenade.unpinAndThrow(player);
                }
            }
        }
    }
}
