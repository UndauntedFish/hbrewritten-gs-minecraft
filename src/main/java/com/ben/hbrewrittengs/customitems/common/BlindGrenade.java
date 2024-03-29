package com.ben.hbrewrittengs.customitems.common;

import com.ben.hbrewrittengs.Main;
import com.ben.hbrewrittengs.cooldowns.ImplicitCooldown;
import com.ben.hbrewrittengs.customitems.ThrowableItem;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.LinkedList;

public class BlindGrenade extends ThrowableItem
{
    private static long unpinDuration = Main.getInstance().getConfig().getLong("grenade_unpinduration");
    private static long fuseDuration = Main.getInstance().getConfig().getLong("grenade_fuseduration");
    private static double grenadeUsageDelay = Main.getInstance().getConfig().getDouble("grenadeusagedelay");
    private static LinkedList<Entity> thrownEntities = new LinkedList<>();

    public static void unpinAndThrow(Player thrower)
    {
        // Remove thrown blind grenade from player's inventory
        int blindGrenadeCount = thrower.getInventory().getItemInMainHand().getAmount();
        thrower.getInventory().getItemInMainHand().setAmount(blindGrenadeCount - 1);

        // Do cooldown if player has more grenades in their inventory
        if (blindGrenadeCount - 1 != 0)
        {
            // Start implicit cooldown (the player won't be notified of this cooldown's effect, it'll just happen in the background)
            ImplicitCooldown cooldown = new ImplicitCooldown(thrower, thrower.getInventory().getItemInMainHand(), grenadeUsageDelay);
            Main.getInstance().playerDataMap.get(thrower.getUniqueId()).activeImplicitCooldowns.add(cooldown);
            cooldown.start();
        }

        // Play grenade fting sound
        thrower.playSound(thrower.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.55F, 2.0F);
        thrower.playSound(thrower.getLocation(), Sound.ENTITY_CREEPER_PRIMED, 1.0F, 1.7F);

        // Throws the blind grenade after playing the unpinning animation
        Bukkit.getScheduler().runTaskLater(Main.getInstance(), new Runnable()
        {
            @Override
            public void run()
            {
                // Throw blind grenade and play grenade whoosh sound
                thrower.playSound(thrower.getLocation(), Sound.ENTITY_SNOWBALL_THROW, 1.0F, 0.5F);
                Entity thrownEntity = throwItem(Material.GOLD_NUGGET, thrower);
                thrownEntities.add(thrownEntity);

                // Send packet that swings the player's arm (all players should be able to see this animation)
                PacketContainer swingArm = Main.getInstance().getProtocolManager().createPacket(PacketType.Play.Server.ANIMATION);
                swingArm.getIntegers().write(0, thrower.getEntityId());
                swingArm.getIntegers().write(1, 0);
                try
                {
                    Main.getInstance().getProtocolManager().sendServerPacket(thrower, swingArm);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

                // Detonates the grenade after x ticks (fuseDuration)
                while (!thrownEntities.isEmpty())
                {
                    detonateAfterDelay(thrownEntities.removeLast());
                }
            }
        }, unpinDuration);
    }

    private static void detonateAfterDelay(Entity thrownEntity)
    {
        Bukkit.getScheduler().runTaskLater(Main.getInstance(), new Runnable()
        {
            @Override
            public void run()
            {
                // Blinds anyone near the blind grenade
                thrownEntity.getNearbyEntities(10.0, 10.0, 10.0).forEach( (entity) ->
                {
                    if (entity instanceof Player)
                    {
                        Player target = (Player) entity;
                        target.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 5 * 20, 0, false, false));
                    }
                });
                thrownEntity.getWorld().playSound(thrownEntity.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 4.0F, 1.0F);
                thrownEntity.getWorld().spawnParticle(Particle.EXPLOSION_LARGE, thrownEntity.getLocation(), 1);
                spawnTorchForOneTick(thrownEntity.getLocation());
                thrownEntity.remove();
            }
        }, fuseDuration);
    }

    // Creates a flash effect by spawning and despawning a torch v e r y quickly
    private static void spawnTorchForOneTick(Location location)
    {
        Material temp = location.getBlock().getType();
        location.getBlock().setType(Material.TORCH);
        Bukkit.getScheduler().runTaskLater(Main.getInstance(), new Runnable()
        {
            @Override
            public void run()
            {
                location.getBlock().setType(temp);
            }
        }, 1);
    }
}
