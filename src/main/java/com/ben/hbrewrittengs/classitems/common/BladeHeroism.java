package com.ben.hbrewrittengs.classitems.common;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class BladeHeroism
{
    private ItemStack bladeHeroism;

    public BladeHeroism()
    {
        /* LORE */
        List<String> bladeHeroismLore = new ArrayList<>();
        bladeHeroismLore.add("Only true heros hold the");
        bladeHeroismLore.add("strength to use this sword.");

        /* ITEM */
        bladeHeroism = new ItemStack(Material.STONE_SWORD);
        ItemMeta bladeHeroismMeta = bladeHeroism.getItemMeta();
        bladeHeroismMeta.setDisplayName(ChatColor.YELLOW + "Blade of Heroism");
        bladeHeroismMeta.setLore(bladeHeroismLore);
        bladeHeroismMeta.setUnbreakable(true);
        bladeHeroism.setItemMeta(bladeHeroismMeta);
    }

    public ItemStack getItem()
    {
        return bladeHeroism;
    }
}
