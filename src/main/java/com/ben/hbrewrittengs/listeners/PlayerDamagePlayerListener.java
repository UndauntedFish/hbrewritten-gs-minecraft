package com.ben.hbrewrittengs.listeners;

import com.ben.hbrewrittengs.Main;
import com.ben.hbrewrittengs.PlayerData;
import com.ben.hbrewrittengs.bossbarcooldown.BossBarCooldown;
import com.ben.hbrewrittengs.customitems.assassin.SpectralDagger;
import com.ben.hbrewrittengs.enums.ClassData;
import com.ben.hbrewrittengs.enums.Format;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.boss.BarColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Iterator;

public class PlayerDamagePlayerListener implements Listener
{
    @EventHandler
    public void onPlayerDamagePlayer(EntityDamageByEntityEvent e)
    {
        if (e.getEntity() instanceof Player && e.getDamager() instanceof  Player)
        {
            Player damaged = (Player) e.getEntity();
            Player damager = (Player) e.getDamager();

            PlayerData pdDamaged = Main.getInstance().playerDataMap.get(damaged.getUniqueId());
            PlayerData pdDamager = Main.getInstance().playerDataMap.get(damager.getUniqueId());

            ItemStack damagersHeldItem = damager.getInventory().getItemInMainHand();

            // Disallow Assassin from damaging hb while cloaked
            if (pdDamager.isVanished() && pdDamaged.isHerobrine())
            {
                damager.sendMessage(Format.PREFIX_INGAME + ChatColor.RED.toString() + "Cannot hit Herobrine while cloaked!");
                e.setCancelled(true);
            }

            // Disallow Survivors from hitting themselves
            if ( !(pdDamaged.isHerobrine() && pdDamaged.isHerobrine()) )
            {
                e.setCancelled(true);
            }

            // Assassin 's Spectral Stab listener: If attack is a Spectral Stab, do it. Else, attack hb normally.
            if (pdDamager.getActiveClass() == ClassData.ASSASSIN && pdDamaged.isHerobrine())
            {
                if (damagersHeldItem.getType() == Material.GOLDEN_SWORD)
                {
                    double spectralStabDmg = Main.getInstance().getConfig().getDouble("spectralstab_damage");
                    double spectralStabCooldown = Main.getInstance().getConfig().getDouble("spectralstab_cooldown");


                    // Checks if smoke screen has any active cooldowns
                    if (!pdDamager.activeBossBarCooldowns.isEmpty())
                    {
                        for (Iterator<BossBarCooldown> itr = pdDamager.activeBossBarCooldowns.iterator(); itr.hasNext(); )
                        {

                            BossBarCooldown cooldown = itr.next();
                            if (cooldown.getCooldownItem().isSimilar(damagersHeldItem) &&
                                    cooldown.getPlayerUUID().equals(pdDamager.getUUID()))
                            {
                                if (cooldown.isDone())
                                {
                                    itr.remove();
                                    break;
                                }
                                else
                                {
                                    return;
                                }
                            }
                        }

                    }

                    // Checks for critical hit (source: https://www.spigotmc.org/threads/check-if-a-player-is-about-to-do-a-critical-hit.348659/)
                    if (damager.getFallDistance() > 0 && !damager.isOnGround())
                    {
                        SpectralDagger.spectralStab(damager, damaged);

                        // Damage herobrine (fixed damage value set in config)
                        e.setDamage(0);
                        damaged.damage(spectralStabDmg);

                        // Set cooldown for player's spectral stab
                        BossBarCooldown cooldown = new BossBarCooldown(pdDamager, damagersHeldItem, spectralStabCooldown, ChatColor.GOLD + "Spectral Stab Cooldown", BarColor.YELLOW);
                        cooldown.setCooldownEndMessage(Format.PREFIX_INGAME + ChatColor.GOLD.toString() + "Spectral Stab cooldown over!");
                        cooldown.start();
                        pdDamager.activeBossBarCooldowns.add(cooldown);
                    }
                }
            }
        }
    }
}
