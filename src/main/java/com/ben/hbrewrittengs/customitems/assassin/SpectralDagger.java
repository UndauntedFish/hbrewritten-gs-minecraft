package com.ben.hbrewrittengs.customitems.assassin;

import com.ben.hbrewrittengs.Main;
import com.ben.hbrewrittengs.PlayerData;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
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
    public static void spectralStab(Player stabber, Player stabbed)
    {
        PlayerData pdStabber = Main.getInstance().playerDataMap.get(stabber.getUniqueId());
        PlayerData pdStabbed = Main.getInstance().playerDataMap.get(stabbed.getUniqueId());

        // Play HB hurt sound
        stabbed.getWorld().playSound(stabbed.getLocation(), Sound.ENTITY_BLAZE_HURT, 1.5F, 1.0F);

        // Play Spectral Stab sounds
        stabbed.playSound(stabbed.getLocation(), Sound.ENTITY_GHAST_HURT, 0.8F, 1.0F);
        stabber.playSound(stabbed.getLocation(), Sound.ITEM_TRIDENT_THUNDER, 1.0F, 0.5F);
    }
}
