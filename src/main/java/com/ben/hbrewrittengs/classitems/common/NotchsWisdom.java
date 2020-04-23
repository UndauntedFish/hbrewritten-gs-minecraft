package com.ben.hbrewrittengs.classitems.common;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class NotchsWisdom
{
    private ItemStack notchsWisdom;

    public NotchsWisdom()
    {
        /* LORE */
        List<String> notchsWisdomLore = new ArrayList<>();
        notchsWisdomLore.add("Creates a field of healing");
        notchsWisdomLore.add("around your location.");

        /* ITEM */
        notchsWisdom = new ItemStack(Material.BLAZE_POWDER);
        ItemMeta notchsWisdomMeta = notchsWisdom.getItemMeta();
        notchsWisdomMeta.setDisplayName(ChatColor.YELLOW + "Notch's Wisdom");
        notchsWisdomMeta.setLore(notchsWisdomLore);
        notchsWisdom.setItemMeta(notchsWisdomMeta);
    }

    public ItemStack getItem(int amount)
    {
        notchsWisdom.setAmount(amount);
        return notchsWisdom;
    }
}
