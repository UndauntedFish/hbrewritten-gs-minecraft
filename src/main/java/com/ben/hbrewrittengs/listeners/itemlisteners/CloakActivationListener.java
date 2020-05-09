package com.ben.hbrewrittengs.listeners.itemlisteners;

import com.ben.hbrewrittengs.Main;
import com.ben.hbrewrittengs.PlayerData;
import com.ben.hbrewrittengs.cooldowns.BossBarCooldown;
import com.ben.hbrewrittengs.customitems.assassin.Cloak;
import com.ben.hbrewrittengs.enums.Format;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.boss.BarColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.Iterator;

public class CloakActivationListener implements Listener
{
    @EventHandler
    public void onCloakActivate(PlayerInteractEvent e)
    {
        Player player = e.getPlayer();
        PlayerData pd = Main.getInstance().playerDataMap.get(player.getUniqueId());

        Action action = e.getAction();
        ItemStack heldItem = player.getInventory().getItemInMainHand();

        if (e.getHand() == EquipmentSlot.HAND)
        {
            if (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK)
            {
                if (heldItem.getType() == Material.GLOWSTONE_DUST)
                {
                    // Checks if smoke screen has any active cooldowns
                    if (!pd.activeBossBarCooldowns.isEmpty())
                    {
                        for (Iterator<BossBarCooldown> itr = pd.activeBossBarCooldowns.iterator(); itr.hasNext(); )
                        {
                            BossBarCooldown cooldown = itr.next();

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
                                    player.sendMessage(ChatColor.RED + "Cannot cloak while already cloaked!");
                                    e.setCancelled(true);
                                    return;
                                }
                            }
                        }

                    }

                    // Activating the cloak
                    if (pd.isVanished())
                    {
                        player.sendMessage(ChatColor.RED + "Cannot cloak while already cloaked!");
                        e.setCancelled(true);
                        return;
                    }

                    Cloak.activate(pd);
                    player.sendMessage(Format.PREFIX_INGAME + ChatColor.WHITE.toString() + "POOF, you are VANished!");

                    // Starting the cooldown
                    BossBarCooldown cooldown = new BossBarCooldown(pd, heldItem, Main.getInstance().getConfig().getDouble("cloakduration"), ChatColor.WHITE + "Cloak Duration", BarColor.RED);
                    cooldown.setCooldownEndMessage(Format.PREFIX_INGAME + ChatColor.WHITE.toString() + "Your cloak wore off!");
                    cooldown.start();
                    pd.activeBossBarCooldowns.add(cooldown);

                    // Removing activated cloak from player's inventory
                    player.getInventory().getItemInMainHand().setAmount(
                            player.getInventory().getItemInMainHand().getAmount() - 1);
                }
            }
        }
    }
}
