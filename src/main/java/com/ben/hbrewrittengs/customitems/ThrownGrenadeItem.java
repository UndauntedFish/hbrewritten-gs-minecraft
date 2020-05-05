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

public class ThrownGrenadeItem
{
    public boolean isUnpinning;
    private Material item;
    private Player thrower;

    private CompletableFuture<Entity> thrownItem;
    private Entity thrownEntity;

    public ThrownGrenadeItem(Material item, Player thrower)
    {
        this.item = item;
        this.thrower = thrower;
    }

    // Throws the selected item with a grenade unpinning delay. Used for chemical grenades.
    public void throwItem()
    {
        isUnpinning = true;

        // Play grenade pin and fuse sound to player


        // Throw the item after a specified amount of game ticks.
        thrownItem = new CompletableFuture<>();
        Bukkit.getScheduler().runTaskLater(Main.getInstance(), new Runnable()
        {
            @Override
            public void run()
            {
                isUnpinning = false;

                Location throwOrigin = thrower.getLocation().add(0.0, 1.0, 0.0);
                Entity thrownEntity = thrower.getWorld().dropItemNaturally(throwOrigin, new ItemStack(item));
                thrownEntity.setVelocity(throwOrigin.getDirection().add(new Vector(0.0, 0.1, 0.0)).multiply(1.3));
                thrownItem.complete(thrownEntity);
                thrower.playSound(throwOrigin, Sound.ENTITY_SNOWBALL_THROW, 1.0F, 0.5F);

                // Removing thrown blind grenade from player's inventory
                thrower.getInventory().getItemInMainHand().setAmount(
                        thrower.getInventory().getItemInMainHand().getAmount() - 1);
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
    }

    // If the unpinning animation is still playing (implying the grenade hasn't been thrown yet), return true.
    public boolean isUnpinning()
    {
        return isUnpinning;
    }

    public Entity getThrownEntity()
    {
        // Return the thrown entity (so it can be accessed, modified, etc. in other methods)
        if (thrownEntity.equals(null))
        {
            try
            {
                thrownEntity = thrownItem.get();
                return thrownEntity;
            }
            catch (InterruptedException | ExecutionException e)
            {
                e.printStackTrace();
            }
        }
        else
        {
            return thrownEntity;
        }
    }
}
