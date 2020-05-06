package com.ben.hbrewrittengs.customitems.common;

import com.ben.hbrewrittengs.Main;
import com.ben.hbrewrittengs.customitems.ThrownGrenadeItem;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Random;

public class BlindGrenade
{
    private static Random rand = new Random();
    private static float[] explosionValues = new float[] {1.55F, 1.7F, 1.9F};
    private static long unpinDuration = Main.getInstance().getConfig().getLong("grenade_unpinduration");
    private static long fuseDuration = Main.getInstance().getConfig().getLong("blindgrenade_fuseduration");

    public static void unpinAndThrow(Player thrower)
    {
        ThrownGrenadeItem thrownGrenade = new ThrownGrenadeItem(Material.GOLD_NUGGET, thrower);

        // Play grenade fting sound

        // Throws the blind grenade after playing the unpinning animation
        Bukkit.getScheduler().runTaskLater(Main.getInstance(), new Runnable()
        {
            @Override
            public void run()
            {
                // Play grenade whoo sound
                thrower.playSound(thrower.getLocation(), Sound.ENTITY_SNOWBALL_THROW, 1.0F, 0.5F);
                thrownGrenade.throwItem();
            }
        }, unpinDuration);

        // Send packet that swings the player's arm (all players should be able to see this animation)
        PacketContainer swingArm = Main.getInstance().getProtocolManager().createPacket(PacketType.Play.Client.ARM_ANIMATION);
        swingArm.getHands().write(0, EnumWrappers.Hand.MAIN_HAND);
        try
        {
            Main.getInstance().getProtocolManager().recieveClientPacket(thrower, swingArm);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        Bukkit.getScheduler().runTaskLater(Main.getInstance(), new Runnable()
        {
            @Override
            public void run()
            {
                // Blinds anyone near the blind grenade
                thrownGrenade.getThrownEntity().getNearbyEntities(5.0, 5.0, 5.0).forEach( (entity) ->
                {
                    if (entity instanceof Player)
                    {
                        Player target = (Player) entity;
                        target.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 5, 0, false, false));
                    }
                });
            }
        }, fuseDuration);

        // Randomly selects one of three explosion sounds to play
        thrower.getWorld().playSound(thrownGrenade.getThrownEntity().getLocation(), Sound.ENTITY_VILLAGER_YES, 1.0F, randPitch());

    }

    private static float randPitch()
    {
        int randomIndex = new Random().nextInt(explosionValues.length);
        return explosionValues[randomIndex];
    }
}
