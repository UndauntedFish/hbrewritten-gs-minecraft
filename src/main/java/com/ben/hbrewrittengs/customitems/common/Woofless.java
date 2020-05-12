package com.ben.hbrewrittengs.customitems.common;

import com.ben.hbrewrittengs.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;

public class Woofless
{
    // Spawns the Woofless at the specified location
    public static void spawn(Player owner, Location summonLocation)
    {
        // Spawns in the wolf and sets their owner to the Player
        Wolf wolf = (Wolf) summonLocation.getWorld().spawnEntity(summonLocation, EntityType.WOLF);
        wolf.setAdult();
        wolf.setTamed(true);
        wolf.setOwner(owner);

        // Makes the wolf look up
        wolf.setRotation(0.0F, -45.0F);

        // Makes the wolf howl
        wolf.getLocation().getWorld().playSound(wolf.getLocation(), Sound.ENTITY_WOLF_HOWL, 1.0F, 1.0F);

        // Makes the wolf look down after 2 seconds
        Bukkit.getScheduler().runTaskLater(Main.getInstance(), new Runnable()
        {
            @Override
            public void run()
            {
                wolf.setRotation(0.0F, 0.0F);
            }
        },20L * 2L);
    }
}
