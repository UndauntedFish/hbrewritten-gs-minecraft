package com.ben.hbrewrittengs.customitems.common;

import com.ben.hbrewrittengs.customitems.ThrowableItem;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Random;

public class BlindGrenade extends ThrowableItem
{
    private static Random rand = new Random();
    private static float[] explosionValues = new float[] {1.55F, 1.7F, 1.9F};

    public static void unpinAndThrow(Player thrower)
    {
        // Throws the blind grenade after playing the unpinning animation
        Entity thrownGrenade = throwItemWithDelay(Material.GOLD_NUGGET, thrower);

        // Blinds anyone near the blind grenade and removes the thrownGrenade entity from the world
        thrownGrenade.getNearbyEntities(5.0, 5.0, 5.0).forEach( (entity) ->
        {
            if (entity instanceof Player)
            {
                Player target = (Player) entity;
                target.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 5, 0, false, false));
            }
        });

        // Randomly selects one of three explosion sounds to play
        thrower.getWorld().playSound(thrownGrenade.getLocation(), Sound.ENTITY_VILLAGER_YES, 1.0F, randPitch());
    }

    private static float randPitch()
    {
        int randomIndex = new Random().nextInt(explosionValues.length);
        return explosionValues[randomIndex];
    }
}
