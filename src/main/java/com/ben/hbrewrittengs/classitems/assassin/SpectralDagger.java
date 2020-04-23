package com.ben.hbrewrittengs.classitems.assassin;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class SpectralDagger
{
    private ItemStack spectralDagger;

    public SpectralDagger()
    {
        /* LORE */
        List<String> spectralDaggerLore = new ArrayList<>();
        spectralDaggerLore.add("Charged with Herobrine's hate,");
        spectralDaggerLore.add("this blade can pierce his ghostly resistance!");

        /* ITEM */
        spectralDagger = new ItemStack(Material.GOLDEN_SWORD);
        ItemMeta spectralDaggerMeta = spectralDagger.getItemMeta();
        spectralDaggerMeta.setDisplayName(ChatColor.GOLD + "Spectral Dagger");
        spectralDaggerMeta.setLore(spectralDaggerLore);
        spectralDaggerMeta.setUnbreakable(true);
        spectralDagger.setItemMeta(spectralDaggerMeta);
    }

    public ItemStack getItem()
    {
        return spectralDagger;
    }
}
