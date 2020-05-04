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

public class Demo
{
    // Armor
    private static ItemStack helmet, chestplate, boots;
    private static LeatherArmorMeta helmetMeta;

    // Weapons and Extras
    private static ItemStack heGrenade;
    private static ItemMeta heGrenadeMeta;
    private static List<String> heGrenadeLore = new ArrayList<>();

    public static void loadItems()
    {
        /* LORES */
        heGrenadeLore.add("Goes KABOOM 4 seconds after it's thrown");

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
    }

    public static void giveClass(Player player)
    {
        // Setting items to the player's inventory slots
        player.getInventory().clear();
        player.getInventory().setHelmet(helmet);
        player.getInventory().setChestplate(chestplate);
        player.getInventory().setBoots(boots);
        player.getInventory().setItem(0, CommonItems.getBladeOfHeroism());
        player.getInventory().setItem(1, heGrenade);
        player.getInventory().setItem(2, CommonItems.getBlindingGrenade(5, ChatColor.GRAY + "Blinding Grenade"));
        player.getInventory().setItem(8, CommonItems.getObjectiveLocator());
    }
}
