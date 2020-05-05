package com.ben.hbrewrittengs.customitems;

import com.ben.hbrewrittengs.Main;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class ThrowableItem
{
    // Throws the selected item
    public static Entity throwItem(Material item, Player thrower)
    {
        Location throwOrigin = thrower.getLocation().add(0.0, 1.0, 0.0);

        Entity thrownItem = thrower.getWorld().dropItemNaturally(throwOrigin, new ItemStack(item));
        thrownItem.setVelocity(throwOrigin.getDirection().add(new Vector(0.0, 0.1, 0.0)).multiply(1.3));
        thrower.playSound(throwOrigin, Sound.ENTITY_SNOWBALL_THROW, 1.0F, 0.5F);

        return thrownItem;
    }

    // Throws the selected item with a grenade unpinning delay. Used for chemical grenades.
    public static Entity throwItemWithDelay(Material item, Player thrower)
    {
        // Play grenade pin and fuse sound to player


        // Throw the item after a specified amount of game ticks.
        CompletableFuture<Entity> thrownItem = new CompletableFuture<>();
        Bukkit.getScheduler().runTaskLater(Main.getInstance(), new Runnable()
        {
            @Override
            public void run()
            {
                Location throwOrigin = thrower.getLocation().add(0.0, 1.0, 0.0);
                Entity thrownEntity = thrower.getWorld().dropItemNaturally(throwOrigin, new ItemStack(item));
                thrownEntity.setVelocity(throwOrigin.getDirection().add(new Vector(0.0, 0.1, 0.0)).multiply(1.3));
                thrownItem.complete(thrownEntity);
                thrower.playSound(throwOrigin, Sound.ENTITY_SNOWBALL_THROW, 1.0F, 0.5F);
            }
        }, 2L * (4L + 1L)); // 1 redstone tick = 2 game ticks, 4 redstone ticks = 2*4 game ticks.

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

        // Return the thrown entity (so it can be accessed, modified, etc. in other methods)
        Entity thrownEntity = null;
        try
        {
            thrownEntity = thrownItem.get();
        }
        catch (InterruptedException | ExecutionException e)
        {
            e.printStackTrace();
        }
        return thrownEntity;
    }
}
