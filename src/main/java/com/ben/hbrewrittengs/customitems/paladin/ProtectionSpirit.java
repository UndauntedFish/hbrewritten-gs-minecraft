package com.ben.hbrewrittengs.customitems.paladin;

import com.ben.hbrewrittengs.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;

import java.util.Random;

public class ProtectionSpirit
{
    private static Random random = new Random();

    private Player player;
    private int taskID;
    private double protSpiritDuration;
    private Location feetLocation;
    private AttributeInstance maxHealth;

    public ProtectionSpirit(Player player)
    {
        this.player = player;

        this.maxHealth = player.getAttribute(Attribute.GENERIC_MAX_HEALTH);
    }

    // Activates the player's Protection Spirit
    public void activate()
    {
        protSpiritDuration = Main.getInstance().getConfig().getDouble("protectionspirit_duration");

        // Heals the player 0.5-2 hearts per second for however long protSpiritDuration is. (Default is 12s)
        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Runnable()
        {
            @Override
            public void run()
            {
                if (protSpiritDuration <= 0.0)
                {
                    // end cooldown
                    Bukkit.getScheduler().cancelTask(taskID);
                }
                else
                {
                    // continue cooldown
                    healPlayer();

                    // Half a second has passed, so decrease protSpiritDuration by half a second.
                    protSpiritDuration = protSpiritDuration - 0.5;
                }
            }
        }, 0L, 10L);
    }

    // Contains the routine to heal the player for one second of Protection Spirit.
    private void healPlayer()
    {
        // Playing the floof sound
        player.playSound(player.getLocation(), Sound.ENTITY_BAT_TAKEOFF, 1.0F, 0.5F);

        // Increasing the player's health
        double healthIncrease = getRandomNumber(1.0, 4.0);
        /*
         * If adding to the player's current health will make their new health greater that their max health,
         * set their new health to the max health. Otherwise, add the healthIncrease to their current health.
         */
        if (player.getHealth() + healthIncrease >= maxHealth.getBaseValue())
        {
            player.setHealth(maxHealth.getBaseValue());
        }
        else
        {
            player.setHealth(player.getHealth() + healthIncrease);
        }

        // Playing fireworks particles at player's feet
        feetLocation = player.getLocation();
        feetLocation.setY(feetLocation.getY() + 0.2);

        player.spawnParticle(Particle.FIREWORKS_SPARK, feetLocation, 15,0.2, 0.2, 0.2, 0.1);
        /*
         * The offset value sets where the particle is to go to, or in your case, to "fly".
         *
         * Use this method to set the offset and speed value,
         * where x,y,z offsets are where you want the particle to go relative to itself,
         * and the double extra value is the speed at which you want the particle to travel to its offset values.
         */
    }

    // Gets a random double value between <minimum> and <maximum>
    private static double getRandomNumber(double minimum, double maximum)
    {
        return random.nextDouble() * (maximum - minimum) + minimum;
    }
}
