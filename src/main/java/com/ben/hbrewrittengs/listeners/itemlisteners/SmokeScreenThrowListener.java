package com.ben.hbrewrittengs.listeners.itemlisteners;

import com.ben.hbrewrittengs.Main;
import com.ben.hbrewrittengs.PlayerData;
import com.ben.hbrewrittengs.bossbarcooldown.BossBarCooldown;
import com.ben.hbrewrittengs.customitems.assassin.SmokeScreen;
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
        Player player = e.getPlayer();
        PlayerData pd = Main.getInstance().playerDataMap.get(player.getUniqueId());

        Action action = e.getAction();
        ItemStack thrownItem = player.getInventory().getItemInMainHand();
        int thrownItemSlot = player.getInventory().getHeldItemSlot();
        String cooldownBossbarTitle = ChatColor.GRAY + "Smoke Screen Cooldown";


        if (e.getHand() == EquipmentSlot.HAND)
        {
            if (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK)
            {
                if (thrownItem.getType() == Material.IRON_NUGGET)
                {
                    // Checks if smoke screen has any active cooldowns
                    if (!pd.activeCooldowns.isEmpty())
                    {
                        for (Iterator<BossBarCooldown> itr = pd.activeCooldowns.iterator(); itr.hasNext();)
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
                                    player.sendMessage(ChatColor.RED + "Cannot use your smoke screen on cooldown!");
                                    e.setCancelled(true);
                                    return;
                                }
                            }
                        }

                    }

                    // Throwing the smokescreen
                    if (!pd.thrownSmokeScreens.isEmpty())
                    {
                        player.sendMessage(ChatColor.RED + "Cannot throw two smoke screens at once!");
                        e.setCancelled(true);
                        return;
                    }
                    Entity thrownSmokeScreenEntity = SmokeScreen.throwItem(Material.IRON_NUGGET, player);
                    pd.thrownSmokeScreens.add(thrownSmokeScreenEntity);

                    // Removing thrown smoke screen from player's inventory
                    player.getInventory().getItemInMainHand().setAmount(
                            player.getInventory().getItemInMainHand().getAmount() - 1);
                }
            }
            else if ((e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK))
            {
                /* Code reaches here if player left clicked */

                // If the thrown item is a smoke screen, activate it.
                if (!pd.thrownSmokeScreens.isEmpty())
                {
                    Entity thrownSmokeScreenEntity = pd.thrownSmokeScreens.removeLast();
                    SmokeScreen.activate(thrownSmokeScreenEntity, player);

                    // Starting the cooldown
                    BossBarCooldown cooldown = new BossBarCooldown(pd, Main.getInstance().getConfig().getDouble("smokescreen"), cooldownBossbarTitle, BarColor.WHITE);
                    cooldown.start();
                    pd.activeCooldowns.add(cooldown);
                }
            }
        }
    }
}
