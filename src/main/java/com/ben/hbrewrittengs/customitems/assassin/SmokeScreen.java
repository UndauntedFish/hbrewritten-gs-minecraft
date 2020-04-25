package com.ben.hbrewrittengs.customitems.assassin;

import com.ben.hbrewrittengs.customitems.ThrowableItem;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class SmokeScreen extends ThrowableItem
{
    // Activates the player's smokescreen and removes it from their inventory
    public static void activate(Entity thrownIronNuggetEntity, Player thrower)
    {
        thrownIronNuggetEntity.remove();
        thrower.getWorld().spawnParticle(Particle.CAMPFIRE_SIGNAL_SMOKE, thrownIronNuggetEntity.getLocation(), 6000, 2.5, 1.5, 2.5, 0.0);
        thrower.getWorld().playSound(thrownIronNuggetEntity.getLocation(), Sound.BLOCK_FIRE_EXTINGUISH, 10.0F, 0.1F);
    }
}
