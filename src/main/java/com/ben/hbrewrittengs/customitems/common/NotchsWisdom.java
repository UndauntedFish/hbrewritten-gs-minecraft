package com.ben.hbrewrittengs.customitems.common;

import com.ben.hbrewrittengs.Main;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class NotchsWisdom
{
    private Player player;
    private AttributeInstance maxHealth;
    private Location activationLocation, soundLocation;
    private boolean particlesShown;

    // Loads Notch's Wisdom variables from config.yml
    private double notchsWisdomDuration = Main.getInstance().getConfig().getDouble("notchswisdom_duration");
    private static double healthIncreasePerSecond =  Main.getInstance().getConfig().getDouble("notchswisdom_healthpersecond");

    private int taskID, particlesTaskID;

    public NotchsWisdom(Player player, Location activationLocation)
    {
        this.player = player;

        this.activationLocation = activationLocation;

        this.soundLocation = activationLocation;
        soundLocation.setY(activationLocation.getY() + 1.0);
    }

    public void activate()
    {
        activationLocation.getWorld().playSound(activationLocation, Sound.ENTITY_ENDERMAN_TELEPORT, 0.5F, 1.0F);

        // Heals the player 0.5-2 hearts per second for however long protSpiritDuration is. (Default is 12s)
        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Runnable()
        {
            @Override
            public void run()
            {
                // Starts the particles runnable if it was not already started.
                if (particlesTaskID == 0)
                {
                    spawnNotchsWisdomParticles();
                }

                if (notchsWisdomDuration <= 0.0)
                {
                    // end cooldown, cancel particles runnable and this healing runnable.
                    Bukkit.getScheduler().cancelTask(particlesTaskID);
                    Bukkit.getScheduler().cancelTask(taskID);
                }
                else
                {
                    // continue cooldown
                    healNearbyPlayers();

                    // Half a second has passed, so decrease protSpiritDuration by half a second.
                    notchsWisdomDuration = notchsWisdomDuration - 1;
                }
            }
        }, 0L, 20L);
    }

    // Contains the routine to heal nearby player for every second that the Notch's Wisdom is active.
    private void healNearbyPlayers()
    {
        // Playing the sounds
        activationLocation.getWorld().playSound(soundLocation, Sound.BLOCK_LAVA_POP, 0.7F, 1.0F);
        activationLocation.getWorld().playSound(soundLocation, Sound.ENTITY_CAT_PURREOW, 3.0F, 1.0F);
        activationLocation.getWorld().playSound(soundLocation, Sound.ENTITY_CAT_PURREOW, 3.0F, 1.0F);


        /*
         * Increases the health of each living entity within three blocks of the activation Location:
         *
         * If adding to the player's current health will make their new health greater that their max health,
         * set their new health to the max health. Otherwise, add the healthIncrease to their current health.
         */
        activationLocation.getWorld().getNearbyEntities(activationLocation, 3.0, 3.0, 3.0).forEach( (nearbyEntity) ->
        {
            if (nearbyEntity instanceof LivingEntity)
            {
                LivingEntity nearbyLivingEntity = (LivingEntity) nearbyEntity;
                maxHealth = nearbyLivingEntity.getAttribute(Attribute.GENERIC_MAX_HEALTH);

                if (nearbyLivingEntity.getHealth() + healthIncreasePerSecond >= maxHealth.getBaseValue())
                {
                    nearbyLivingEntity.setHealth(maxHealth.getBaseValue());
                }
                else
                {
                    nearbyLivingEntity.setHealth(nearbyLivingEntity.getHealth() + healthIncreasePerSecond);
                }
            }
        });

    }

    // Plays Notch's Wisdom particles at player's crosshair target
    private void spawnNotchsWisdomParticles()
    {
        // Spawns notch's wisdom particles every tick
        particlesTaskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Runnable()
        {
            @Override
            public void run()
            {
                player.getWorld().playEffect(activationLocation, Effect.ENDER_SIGNAL, 1);
            }
        }, 0L, 1L);
    }

    public Location getActivationLocation()
    {
        return activationLocation;
    }

    public void setActivationLocation(Location activationLocation)
    {
        this.activationLocation = activationLocation;
    }
}
