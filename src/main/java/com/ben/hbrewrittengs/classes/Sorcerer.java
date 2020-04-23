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

public class Sorcerer implements BaseClass
{
    // Armor
    private static ItemStack helmet, chestplate, leggings, boots;
    private static LeatherArmorMeta helmetMeta;

    // Weapons and Extras
    private static ItemStack axeOfDeath, healingTotem, harmingTotem;
    private static ItemMeta axeOfDeathMeta, healingTotemMeta, harmingTotemMeta;
    private static List<String> axeOfDeathLore = new ArrayList<>(),
                                healingTotemLore = new ArrayList<>(),
                                harmingTotemLore = new ArrayList<>();

    public static void giveClass(Player player)
    {
        /* LORES */
        axeOfDeathLore.add("The axe of many lost souls.");
        healingTotemLore.add("Heals users nearby for 1 heart per second.");
        harmingTotemLore.add("Harms Herobrine every second when he is nearby.");

        /* ITEMS */

        // Armor
        helmet = new ItemStack(Material.LEATHER_HELMET);
        helmetMeta = (LeatherArmorMeta) helmet.getItemMeta();
        helmetMeta.setColor(ClassData.SORCERER.getHelmetColor());
        helmet.setItemMeta(helmetMeta);
        chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
        leggings = new ItemStack(Material.LEATHER_LEGGINGS);
        boots = new ItemStack(Material.LEATHER_BOOTS);

        // Weapons and Extras
        axeOfDeath = new ItemStack(Material.IRON_AXE);
        axeOfDeathMeta = axeOfDeath.getItemMeta();
        axeOfDeathMeta.setDisplayName(ChatColor.YELLOW + "Axe of Death");
        axeOfDeathMeta.setLore(axeOfDeathLore);
        axeOfDeathMeta.setUnbreakable(true);
        axeOfDeath.setItemMeta(axeOfDeathMeta);

        healingTotem = new ItemStack(Material.OAK_FENCE);
        healingTotemMeta = healingTotem.getItemMeta();
        healingTotemMeta.setDisplayName(ChatColor.YELLOW.toString() + ChatColor.BOLD + "Totem: " +
                ChatColor.RESET.toString() + ChatColor.YELLOW + "Healing");
        healingTotemMeta.setLore(healingTotemLore);
        healingTotem.setItemMeta(healingTotemMeta);

        harmingTotem = new ItemStack(Material.NETHER_BRICK_FENCE);
        harmingTotemMeta = harmingTotem.getItemMeta();
        harmingTotemMeta.setDisplayName(ChatColor.YELLOW.toString() + ChatColor.BOLD + "Totem: " +
                ChatColor.RESET.toString() + ChatColor.YELLOW + "Pain");
        harmingTotemMeta.setLore(harmingTotemLore);
        harmingTotem.setItemMeta(harmingTotemMeta);


        // Setting items to the player's inventory slots
        player.getInventory().setHelmet(helmet);
        player.getInventory().setChestplate(chestplate);
        player.getInventory().setLeggings(leggings);
        player.getInventory().setBoots(boots);
        player.getInventory().setItem(0, axeOfDeath);
        player.getInventory().setItem(1, CommonItems.getSummonWoofless());
        player.getInventory().setItem(2, healingTotem);
        player.getInventory().setItem(3, harmingTotem);
        player.getInventory().setItem(8, CommonItems.getObjectiveLocator());
    }
}