package com.ben.hbrewrittengs.customitems.demo;

import com.ben.hbrewrittengs.Main;
import com.ben.hbrewrittengs.bossbarcooldown.ImplicitCooldown;
import com.ben.hbrewrittengs.customitems.ThrowableItem;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.LinkedList;
import java.util.Random;

public class ChemicalGrenade extends ThrowableItem
{
    private static Random rand = new Random();
    private static float[] explosionValues = new float[] {1.55F, 1.7F, 1.9F};
    private static long unpinDuration = Main.getInstance().getConfig().getLong("grenade_unpinduration");
    private static long fuseDuration = Main.getInstance().getConfig().getLong("grenade_fuseduration");
    private static double grenadeUsageDelay = Main.getInstance().getConfig().getDouble("grenadeusagedelay");
    private static double damage = Main.getInstance().getConfig().getDouble("chemicalgrenade_damage");
    private static double dmgDiameter = Main.getInstance().getConfig().getDouble("chemicalgrenade_damageradius");
    private static LinkedList<Entity> thrownEntities = new LinkedList<>();

    public static void unpinAndThrow(Player thrower)
    {
        // Remove thrown chem grenade from player's inventory
        int chemGrenadeCount = thrower.getInventory().getItemInMainHand().getAmount();
        thrower.getInventory().getItemInMainHand().setAmount(chemGrenadeCount - 1);

        // Do cooldown if player has more grenades in their inventory
        if (chemGrenadeCount - 1 != 0)
        {
            // Start implicit cooldown (the player won't be notified of this cooldown's effect, it'll just happen in the background)
            ImplicitCooldown cooldown = new ImplicitCooldown(thrower, thrower.getInventory().getItemInMainHand(), grenadeUsageDelay);
            Main.getInstance().playerDataMap.get(thrower.getUniqueId()).activeImplicitCooldowns.add(cooldown);
            cooldown.start();
        }

        // Play grenade fting sound
        thrower.playSound(thrower.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.55F, 2.0F);
        thrower.playSound(thrower.getLocation(), Sound.ENTITY_CREEPER_PRIMED, 1.0F, 1.7F);

        // Throws the chem grenade after playing the unpinning animation
        Bukkit.getScheduler().runTaskLater(Main.getInstance(), new Runnable()
        {
            @Override
            public void run()
            {
                // Throw chem grenade and play grenade whoosh sound
                thrower.playSound(thrower.getLocation(), Sound.ENTITY_SNOWBALL_THROW, 1.0F, 0.5F);
                Entity thrownEntity = throwItem(Material.GHAST_TEAR, thrower);
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
                thrownEntity.getNearbyEntities(dmgDiameter, dmgDiameter, dmgDiameter).forEach( (entity) ->
                {
                    if (entity instanceof LivingEntity)
                    {
                        LivingEntity target = (LivingEntity) entity;
                        target.damage(damage);
                    }
                });
                thrownEntity.getWorld().playSound(thrownEntity.getLocation(), Sound.ITEM_TOTEM_USE, 3.0F, randPitch());
                thrownEntity.getWorld().spawnParticle(Particle.EXPLOSION_LARGE, thrownEntity.getLocation(), 1);
                thrownEntity.getWorld().spawnParticle(Particle.EXPLOSION_NORMAL, thrownEntity.getLocation(), 15);
                spawnTorchForOneTick(thrownEntity.getLocation());
                thrownEntity.remove();
            }
        }, fuseDuration);
    }

    private static float randPitch()
    {
        int randomIndex = new Random().nextInt(explosionValues.length);
        return explosionValues[randomIndex];
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
