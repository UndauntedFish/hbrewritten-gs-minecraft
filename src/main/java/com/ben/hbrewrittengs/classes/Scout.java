package com.ben.hbrewrittengs.classes;

import com.ben.hbrewrittengs.enums.ClassData;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public class Scout
{
    // Armor
    private static ItemStack helmet, chestplate, leggings, boots;
    private static LeatherArmorMeta helmetMeta;

    // Weapons and Extras
    private static ItemStack handcraftedBow, owlArrows;
    private static ItemMeta handcraftedBowMeta, owlArrowsMeta;
    private static List<String> handcraftedBowLore = new ArrayList<>(),
                                eagleArrowsLore = new ArrayList<>();

    public static void loadItems()
    {
        /* LORES */
        handcraftedBowLore.add("It's not a work of art,");
        handcraftedBowLore.add("but it works.");

        eagleArrowsLore.add("An arrow made for speed.");
        eagleArrowsLore.add("Woooosh.");


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
        handcraftedBow = new ItemStack(Material.BOW);
        handcraftedBowMeta = handcraftedBow.getItemMeta();
        handcraftedBowMeta.setDisplayName(ChatColor.WHITE + "Handcrafted Recurve");
        handcraftedBowMeta.setLore(handcraftedBowLore);
        handcraftedBowMeta.setUnbreakable(true);
        handcraftedBow.setItemMeta(handcraftedBowMeta);

        owlArrows = new ItemStack(Material.ARROW, 32);
        owlArrowsMeta = owlArrows.getItemMeta();
        owlArrowsMeta.setDisplayName(ChatColor.YELLOW + "Eagle Quill Feather");
        owlArrowsMeta.setLore(eagleArrowsLore);
        owlArrows.setItemMeta(owlArrowsMeta);
    }

    public static void giveClass(Player player)
    {
        // Setting items to the player's inventory slots
        player.getInventory().clear();
        player.getInventory().setHelmet(helmet);
        player.getInventory().setChestplate(chestplate);
        player.getInventory().setLeggings(leggings);
        player.getInventory().setBoots(boots);
        player.getInventory().setItem(0, CommonItems.getBladeOfHeroism());
        player.getInventory().setItem(1, handcraftedBow);
        player.getInventory().setItem(2, owlArrows);
        player.getInventory().setItem(8, CommonItems.getObjectiveLocator());

        // Giving the player Speed I
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 0, false, false));
    }
}
