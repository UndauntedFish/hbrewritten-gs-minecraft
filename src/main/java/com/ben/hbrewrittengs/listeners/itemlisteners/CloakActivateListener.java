package com.ben.hbrewrittengs.listeners.itemlisteners;

import com.ben.hbrewrittengs.Main;
import com.ben.hbrewrittengs.PlayerData;
import com.ben.hbrewrittengs.bossbarcooldown.BossBarCooldown;
import com.ben.hbrewrittengs.customitems.assassin.Cloak;
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

public class CloakActivateListener implements Listener
{
    @EventHandler
    public void onCloakActivate(PlayerInteractEvent e)
    {
        Player player = e.getPlayer();
        PlayerData pd = Main.getInstance().playerDataMap.get(player.getUniqueId());

        Action action = e.getAction();
        ItemStack thrownItem = player.getInventory().getItemInMainHand();
        int thrownItemSlot = player.getInventory().getHeldItemSlot();
        String cooldownBossbarTitle = ChatColor.WHITE + "Cloak Time Remaining";

        if (e.getHand() == EquipmentSlot.HAND)
        {
            if (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK)
            {
                if (thrownItem.getType() == Material.GLOWSTONE_DUST)
                {
                    // Checks if smoke screen has any active cooldowns
                    if (!pd.activeCooldowns.isEmpty())
                    {
                        for (Iterator<BossBarCooldown> itr = pd.activeCooldowns.iterator(); itr.hasNext(); )
                        {
                            BossBarCooldown cooldown = itr.next();

                            if (cooldown.getPlayerUUID().equals(pd.getUUID()))
                            {
                                if (cooldown.isDone() && cooldown.getCooldownTitle().equals(cooldownBossbarTitle))
                                {
                                    itr.remove();
                                    break;
                                }
                                else
                                {
                                    player.sendMessage(ChatColor.RED + "You are already cloaked!");
                                    e.setCancelled(true);
                                    return;
                                }
                            }
                        }

                    }

                    // Cloaking the player
                    if (pd.isCloaked())
                    {
                        player.sendMessage(ChatColor.RED + "You are already cloaked!");
                        e.setCancelled(true);
                        return;
                    }

                    Cloak.activate(pd);
                    pd.setCloaked(true);

                    // Starting the cooldown
                    BossBarCooldown cooldown = new BossBarCooldown(pd, Main.getInstance().getConfig().getDouble("cloakduration"), cooldownBossbarTitle, BarColor.WHITE);
                    cooldown.start();
                    pd.activeCooldowns.add(cooldown);
                }
            }
        }
    }
}
