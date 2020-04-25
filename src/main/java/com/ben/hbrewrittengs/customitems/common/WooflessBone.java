package com.ben.hbrewrittengs.customitems.common;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class WooflessBone
{
    private ItemStack woofless;

    public WooflessBone()
    {
        /* LORE */
        List<String> wooflessLore = new ArrayList<>();
        wooflessLore.add("A furry friend. It gets");
        wooflessLore.add("lonely out there!");

        /* ITEM */
        woofless = new ItemStack(Material.BONE);
        ItemMeta wooflessMeta = woofless.getItemMeta();
        wooflessMeta.setDisplayName(ChatColor.WHITE + "Summon Woofless");
        wooflessMeta.setLore(wooflessLore);
        woofless.setItemMeta(wooflessMeta);
    }

    public ItemStack getItem()
    {
        return woofless;
    }
}
