package com.ben.hbrewrittengs.customitems.sorcerer;

import com.ben.hbrewrittengs.Main;
import com.ben.hbrewrittengs.classes.Sorcerer;
import com.ben.hbrewrittengs.cooldowns.BossBarCooldown;
import com.ben.hbrewrittengs.customevents.TotemDespawnEvent;
import com.ben.hbrewrittengs.enums.Format;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.boss.BarColor;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class TotemPain
{
    private Player player;
    private Location totemLoc;
    private boolean isActivated, isDone;
    private BossBarCooldown durationBossbar;

    private int taskID;

    // Loads Totem variables from config.yml
    private double totemDuration = Main.getInstance().getConfig().getDouble("totem_duration");
    private static double totemRadius = Main.getInstance().getConfig().getDouble("totem_radius");
    private static double totemParticleCount = Main.getInstance().getConfig().getInt("totem_particlecount");
    private static double totemDmgPerSecond = Main.getInstance().getConfig().getDouble("totemharming_dmgpersecond");

    public TotemPain(Player player, Location totemLoc)
    {
        this.player = player;
        this.totemLoc = totemLoc;
        this.durationBossbar = new BossBarCooldown(player, null, totemDuration, ChatColor.RED + "Totem: Pain", BarColor.RED);
        this.durationBossbar.setCooldownEndMessage(Format.PREFIX_INGAME + ChatColor.WHITE.toString() + "Your pain totem has perished.");
    }

    public void activate()
    {
        isActivated = true;

        // If it is the first time activating this totem, display the bossbar.
        if (totemDuration == Main.getInstance().getConfig().getDouble("totem_duration"))
        {
            durationBossbar.start();

            taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Runnable()
            {
                @Override
                public void run()
                {
                    if (totemDuration <= 0.0)
                    {
                        isDone = true;
                        isActivated = false;
                        totemLoc.getBlock().setType(Material.AIR);

                        TotemDespawnEvent event = new TotemDespawnEvent(player, Sorcerer.getPainTotemItemStack());
                        Bukkit.getPluginManager().callEvent(event);

                        Bukkit.getScheduler().cancelTask(taskID);
                    }
                    else
                    {
                        if (isActivated)
                        {
                            spawnParticles();
                            harmEntitiesInRadius();
                        }

                        totemDuration--;
                    }
                }
            }, 0L, 20L);
        }
    }

    private void spawnParticles()
    {
        Vector origin = totemLoc.toVector();
        Vector circumferencePtr = origin.add(new Vector(totemRadius, 0.0, 0.0));

        // Spawns particles around the origin vector
        for (double i = 0.0; i < 360.0; i += (360.0 / totemParticleCount))
        {
            circumferencePtr = circumferencePtr.rotateAroundAxis(origin, i);

            player.getWorld().spawnParticle(
                    Particle.VILLAGER_ANGRY,
                    circumferencePtr.toLocation(player.getWorld()),
                    1
            );
        }
    }

    private void harmEntitiesInRadius()
    {
        totemLoc.getWorld().getNearbyEntities(totemLoc, totemRadius, 4.0, totemRadius).forEach( (entity) ->
        {
            if (entity instanceof Player)
            {
                Player target = (Player) entity;
                double maxHealth = target.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();

                // Damages the LivingEntitiy (or kills them if their health is lower than dps)
                if (target.getHealth() - totemDmgPerSecond <= 0)
                {
                    target.setHealth(0.0);
                }
                else
                {
                    target.setHealth(target.getHealth() - totemDmgPerSecond);
                }

                // Plays totem harm sound to harmed players
                target.playSound(target.getLocation(), Sound.ENTITY_BAT_HURT, 1.0F, 1.0F);
            }
        });
    }

    public void setLocation(Location totemLoc)
    {
        this.totemLoc = totemLoc;
    }

    public void deactivate()
    {
        isActivated = false;
    }

    public boolean isDone()
    {
        return isDone;
    }
}
