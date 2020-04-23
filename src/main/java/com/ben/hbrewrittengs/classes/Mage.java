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

public class Mage implements BaseClass
{
    // Armor
    private static ItemStack helmet, chestplate, leggings, boots;
    private static LeatherArmorMeta helmetMeta;

    // Weapons and Extras
    private static ItemStack elderSword, waterBow, manaArrows, manaCharge;
    private static ItemMeta elderSwordMeta, waterBowMeta, manaArrowsMeta, manaChargeMeta;
    private static List<String> elderSwordLore = new ArrayList<>(),
                                waterBowLore = new ArrayList<>(),
                                manaArrowsLore = new ArrayList<>(),
                                manaChargeLore = new ArrayList<>();

    public static void giveClass(Player player)
    {
        /* LORES */
        elderSwordLore.add("The sword that has passed");
        elderSwordLore.add("through generations.");

        waterBowLore.add("Fires one arrow every second");
        waterBowLore.add("with the healing power of h2o!");

        manaArrowsLore.add("Fire these magical arrows");
        manaArrowsLore.add("at players to heal them!");

        manaChargeLore.add("Gives everyone a huge speed");
        manaChargeLore.add("boost for 5 seconds!");


        /* ITEMS */

        // Armor
        helmet = new ItemStack(Material.LEATHER_HELMET);
        helmetMeta = (LeatherArmorMeta) helmet.getItemMeta();
        helmetMeta.setColor(ClassData.SCOUT.getHelmetColor());
        helmet.setItemMeta(helmetMeta);
        chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
        leggings = new ItemStack(Material.LEATHER_LEGGINGS);
        boots = new ItemStack(Material.LEATHER_BOOTS);

        // Weapons and Extras
        elderSword = new ItemStack(Material.WOODEN_SWORD);
        elderSwordMeta = elderSword.getItemMeta();
        elderSwordMeta.setDisplayName(ChatColor.GOLD + "Elder's Sword");
        elderSwordMeta.setLore(elderSwordLore);
        elderSwordMeta.setUnbreakable(true);
        elderSword.setItemMeta(elderSwordMeta);

        waterBow = new ItemStack(Material.BOW);
        waterBowMeta = waterBow.getItemMeta();
        waterBowMeta.setDisplayName(ChatColor.YELLOW + "Water of " + ChatColor.BOLD + "Healing");
        waterBowMeta.setLore(waterBowLore);
        waterBowMeta.setUnbreakable(true);
        waterBow.setItemMeta(waterBowMeta);

        manaArrows = new ItemStack(Material.ARROW, 32);
        manaArrowsMeta = manaArrows.getItemMeta();
        manaArrowsMeta.setDisplayName(ChatColor.WHITE + "Mana " + ChatColor.BOLD + "Arrow");
        manaArrowsMeta.setLore(manaArrowsLore);
        manaArrows.setItemMeta(manaArrowsMeta);

        manaCharge = new ItemStack(Material.BLAZE_ROD);
        manaChargeMeta = manaCharge.getItemMeta();
        manaChargeMeta.setDisplayName(ChatColor.GOLD + "Mana " + ChatColor.BOLD + "Charge");
        manaChargeMeta.setLore(manaChargeLore);
        manaCharge.setItemMeta(manaChargeMeta);


        // Setting items to the player's inventory slots
        player.getInventory().setHelmet(helmet);
        player.getInventory().setChestplate(chestplate);
        player.getInventory().setLeggings(leggings);
        player.getInventory().setBoots(boots);
        player.getInventory().setItem(0, elderSword);
        player.getInventory().setItem(1, waterBow);
        player.getInventory().setItem(2, manaArrows);
        player.getInventory().setItem(3, manaCharge);
        player.getInventory().setItem(8, CommonItems.getObjectiveLocator());
    }
}
