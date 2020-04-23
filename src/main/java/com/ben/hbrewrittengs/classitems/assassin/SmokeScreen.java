package com.ben.hbrewrittengs.classitems.assassin;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class SmokeScreen
{
    private ItemStack smokeScreen;

    public SmokeScreen()
    {
        /* LORE */
        List<String> smokeScreenLore = new ArrayList<>();
        smokeScreenLore.add("Casts a cloud of smoke to mask your movements!");
        smokeScreenLore.add("Right click to throw,");
        smokeScreenLore.add("left click to detonate.");

        /* ITEM */
        smokeScreen = new ItemStack(Material.IRON_NUGGET, 3);
        ItemMeta smokeScreenMeta = smokeScreen.getItemMeta();
        smokeScreenMeta.setDisplayName(ChatColor.GRAY + "Smoke Screen");
        smokeScreenMeta.setLore(smokeScreenLore);
        smokeScreen.setItemMeta(smokeScreenMeta);
    }

    public ItemStack getItem(int amount)
    {
        smokeScreen.setAmount(amount);
        return smokeScreen;
    }
}
