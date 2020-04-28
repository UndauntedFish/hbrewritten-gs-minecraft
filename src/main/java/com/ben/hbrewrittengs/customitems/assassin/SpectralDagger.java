package com.ben.hbrewrittengs.customitems.assassin;

import com.ben.hbrewrittengs.Main;
import com.ben.hbrewrittengs.PlayerData;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
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

    // Activates the spectral stab and deals double damage to herobrine. Listens to
    public void spectralStab(Player stabber, Player stabbed)
    {
        PlayerData pdStabber = Main.getInstance().playerDataMap.get(stabber.getUniqueId());
        PlayerData pdStabbed = Main.getInstance().playerDataMap.get(stabbed.getUniqueId());

        if (!pdStabbed.isHerobrine())
        {
            return;
        }
    }
}
