package com.ben.hbrewrittengs.classitems.assassin;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class Cloak
{
    private ItemStack cloak;

    public Cloak()
    {
        /* LORE */
        List<String> cloakLore = new ArrayList<>();
        cloakLore.add("Harness Herobrine's dark energy");
        cloakLore.add("and give him a taste of his own meds!");
        cloakLore.add("Right click to vanish.");

        /* ITEM */
        cloak = new ItemStack(Material.GLOWSTONE_DUST);
        ItemMeta cloakMeta = cloak.getItemMeta();
        cloakMeta.setDisplayName(ChatColor.WHITE.toString() + ChatColor.BOLD + "Phantasmal " +
                ChatColor.RESET.toString() + ChatColor.WHITE + "Cloak");
        cloakMeta.setLore(cloakLore);
        cloak.setItemMeta(cloakMeta);
    }

    public ItemStack getItem(int amount)
    {
        cloak.setAmount(amount);
        return cloak;
    }
}
