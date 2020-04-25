package com.ben.hbrewrittengs.customitems.common;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class DreamweaverBandage
{
    private ItemStack dwBandage;

    public DreamweaverBandage(int amount)
    {
        /* LORE */
        List<String> dwBandageLore = new ArrayList<>();
        dwBandageLore.add("A quick heal, perfect for");
        dwBandageLore.add("sticky situations.");

        /* ITEM */
        dwBandage = new ItemStack(Material.MAGMA_CREAM, amount);
        ItemMeta dwBandageMeta = dwBandage.getItemMeta();
        dwBandageMeta.setDisplayName(ChatColor.YELLOW + "Dreamweaver Bandage");
        dwBandageMeta.setLore(dwBandageLore);
        dwBandage.setItemMeta(dwBandageMeta);
    }

    public ItemStack getItem()
    {
        return dwBandage;
    }
}
