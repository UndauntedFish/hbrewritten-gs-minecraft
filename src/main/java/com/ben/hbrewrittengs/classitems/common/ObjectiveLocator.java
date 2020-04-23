package com.ben.hbrewrittengs.classitems.common;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ObjectiveLocator
{
    private ItemStack objLocator;

    public ObjectiveLocator()
    {
        /* LORE */
        List<String> objLocatorLore = new ArrayList<>();
        objLocatorLore.add("Whenever a shard spawns,");
        objLocatorLore.add("this compass will point to it!");

        /* ITEM */
        objLocator = new ItemStack(Material.COMPASS);
        ItemMeta objLocatorMeta = objLocator.getItemMeta();
        objLocatorMeta.setDisplayName(ChatColor.GRAY + "Objective Locator");
        objLocatorMeta.setLore(objLocatorLore);
        objLocator.setItemMeta(objLocatorMeta);
    }

    public ItemStack getItem()
    {
        return objLocator;
    }
}
