package com.ben.hbrewrittengs.listeners;

import com.ben.hbrewrittengs.Main;
import com.ben.hbrewrittengs.PlayerData;
import com.ben.hbrewrittengs.bossbarcooldown.BossBarCooldown;
import com.ben.hbrewrittengs.customitems.assassin.SmokeScreen;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.boss.BarColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.Iterator;

public class SmokeScreenThrowListener implements Listener
{
    @EventHandler
    public void onSmokeScreenThrow(PlayerInteractEvent e)
    {
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Eventhandler Started.");
        Player player = e.getPlayer();
        PlayerData pd = Main.getInstance().playerDataMap.get(player.getUniqueId());

        Action action = e.getAction();
        ItemStack thrownItem = player.getInventory().getItemInMainHand();
        int thrownItemSlot = player.getInventory().getHeldItemSlot();

        if (e.getHand() == EquipmentSlot.HAND)
        {
            Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "itemslot is hand.");
            if (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK)
            {
                Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Action is rightclick.");
                if (thrownItem.getType() == Material.IRON_NUGGET)
                {
                    Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Material is ironnugget.");
                    // Checks if smoke screen has any active cooldowns
                    if (!pd.activeCooldowns.isEmpty())
                    {
                        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "pd.activecooldiwns is NOT empty.");
                        for (Iterator<BossBarCooldown> itr = pd.activeCooldowns.iterator(); itr.hasNext(); )
                        {
                            BossBarCooldown cooldown = itr.next();
                            if (cooldown.getPlayerUUID().equals(pd.getUUID()))
                            {
                                if (cooldown.isDone())
                                {
                                    Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Removed expired cooldown.");
                                    itr.remove();
                                    break;
                                }
                                else
                                {
                                    Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Cancelled event due to active cooldown.");
                                    player.sendMessage(ChatColor.RED + "Cannot use your smoke screen on cooldown!");
                                    e.setCancelled(true);
                                    return;
                                }
                            }
                        }

                    }
                    Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Initiating throw sequence.");

                    // Item action here, there are no active cooldowns
                    Entity thrownSmokeScreenEntity = SmokeScreen.throwItem(Material.IRON_NUGGET, player);
                    pd.thrownSmokeScreens.add(thrownSmokeScreenEntity);

                    // Starts cooldown
                    BossBarCooldown cooldown = new BossBarCooldown(pd, 25.0, ChatColor.GRAY + "Smoke Screen Cooldown", BarColor.WHITE);
                    cooldown.start();
                    pd.activeCooldowns.add(cooldown);

                    // Removes thrown smoke screen from player's inventory
                    player.getInventory().getItemInMainHand().setAmount(
                            player.getInventory().getItemInMainHand().getAmount() - 1);

                    Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Threw smokescreen.");
                }
            }
            else if ((e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK))
            {
                /* Code reaches here if player left clicked */

                // If the thrown item is a smoke screen, activate it
                if (!pd.thrownSmokeScreens.isEmpty())
                {
                    Entity thrownSmokeScreenEntity = pd.thrownSmokeScreens.removeLast();
                    SmokeScreen.activate(thrownSmokeScreenEntity, player);
                }
            }
        }
    }
}
