package com.ben.hbrewrittengs.customitems;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class ThrowableItem
{
    // Throws the selected item
    public static Entity throwItem(Material item, Player thrower)
    {
        Location throwOrigin = thrower.getLocation().add(0.0, 0.5, 0.0);

        Entity thrownItem = thrower.getWorld().dropItemNaturally(throwOrigin, new ItemStack(item));
        thrownItem.setVelocity(throwOrigin.getDirection().add(new Vector(0.0, 0.15, 0.0)).multiply(1.2));
        thrower.playSound(throwOrigin, Sound.ENTITY_SNOWBALL_THROW, 1.0F, 0.5F);

        return thrownItem;
    }
}
