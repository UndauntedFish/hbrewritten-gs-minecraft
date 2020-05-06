package com.ben.hbrewrittengs.customitems;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class ThrownGrenadeItem
{
    private volatile boolean wasThrown;
    private long unpinTime;
    private Material item;
    private Player thrower;

    private volatile Entity thrownEntity;

    public ThrownGrenadeItem(Material item, Player thrower)
    {
        this.item = item;
        this.thrower = thrower;
        this.unpinTime = 2L * (4L + 1L);
        this.wasThrown = false;
    }

    // Throws the selected item with a grenade unpinning delay. Used for chemical grenades.
    public void throwItem()
    {
        // Play grenade pin and fuse sound to player
        thrower.playSound();

        // Throw the item
        Location throwOrigin = thrower.getLocation().add(0.0, 1.0, 0.0);
        thrownEntity = thrower.getWorld().dropItemNaturally(throwOrigin, new ItemStack(item));
        thrownEntity.setVelocity(throwOrigin.getDirection().add(new Vector(0.0, 0.1, 0.0)).multiply(1.3));
        wasThrown = true;

        // Removing thrown blind grenade from player's inventory
        thrower.getInventory().getItemInMainHand().setAmount(
                thrower.getInventory().getItemInMainHand().getAmount() - 1);
    }

    // If the unpinning animation is still playing (implying the grenade hasn't been thrown yet), return true.
    public boolean wasThrown()
    {
        return wasThrown;
    }

    public Entity getThrownEntity()
    {
        // Return the thrown entity after it was unpinned and thrown.
        return thrownEntity;
    }

    public void removeThrownEntity()
    {
        thrownEntity.remove();
    }
}
