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
        // Spawns particles around the totem
        double x = 0.0;
        double z = 0.0;
        for (double angle = 0.0; angle < 360.0; angle += (360.0 / totemParticleCount))
        {
            x = (totemRadius * Math.sin(Math.toRadians(angle)));
            z = (totemRadius * Math.cos(Math.toRadians(angle)));

            player.getWorld().spawnParticle(Particle.VILLAGER_ANGRY,
                    totemLoc.getX() + x, totemLoc.getY(), totemLoc.getZ() + z, 1);
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
