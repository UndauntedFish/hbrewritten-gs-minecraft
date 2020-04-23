package com.ben.hbrewrittengs.classes;

import com.ben.hbrewrittengs.enums.ClassData;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.ArrayList;
import java.util.List;

public class Demo implements BaseClass
{
    // Armor
    private static ItemStack helmet, chestplate, boots;
    private static LeatherArmorMeta helmetMeta;

    // Weapons and Extras
    private static ItemStack heGrenade, blindGrenade;
    private static ItemMeta heGrenadeMeta, blindGrenadeMeta;
    private static List<String> heGrenadeLore = new ArrayList<>(),
                                blindGrenadeLore = new ArrayList<>(),

    public static void giveClass(Player player)
    {
        /* LORES */
        heGrenadeLore.add("Goes KABOOM 4 seconds after it's thrown");
        blindGrenadeLore.add("Blinds those it hits for 3 seconds.");

        /* ITEMS */

        // Armor
        helmet = new ItemStack(Material.LEATHER_HELMET);
        helmetMeta = (LeatherArmorMeta) helmet.getItemMeta();
        helmetMeta.setColor(ClassData.DEMO.getHelmetColor());
        helmet.setItemMeta(helmetMeta);
        chestplate = new ItemStack(Material.CHAINMAIL_CHESTPLATE);
        boots = new ItemStack(Material.CHAINMAIL_BOOTS);

        // Weapons and Extras
        heGrenade = new ItemStack(Material.GHAST_TEAR, 10);
        heGrenadeMeta = heGrenade.getItemMeta();
        heGrenadeMeta.setDisplayName(ChatColor.GRAY + "Chemical Grenade");
        heGrenadeMeta.setLore(heGrenadeLore);
        heGrenadeMeta.setUnbreakable(true);
        heGrenade.setItemMeta(heGrenadeMeta);

        blindGrenade = new ItemStack(Material.GOLD_NUGGET, 5);
        blindGrenadeMeta = blindGrenade.getItemMeta();
        blindGrenadeMeta.setDisplayName(ChatColor.GRAY + "Blinding Grenade");
        blindGrenadeMeta.setLore(blindGrenadeLore);
        blindGrenade.setItemMeta(blindGrenadeMeta);

        // Setting items to the player's inventory slots
        player.getInventory().setHelmet(helmet);
        player.getInventory().setChestplate(chestplate);
        player.getInventory().setBoots(boots);
        player.getInventory().setItem(0, CommonItems.getBladeOfHeroism());
        player.getInventory().setItem(1, heGrenade);
        player.getInventory().setItem(2, blindGrenade);
        player.getInventory().setItem(8, CommonItems.getObjectiveLocator());
    }
}
