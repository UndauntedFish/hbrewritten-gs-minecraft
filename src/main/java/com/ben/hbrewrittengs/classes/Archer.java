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

public class Archer implements BaseClass
{
    // Armor
    private static ItemStack helmet, chestplate, boots;
    private static LeatherArmorMeta helmetMeta;

    // Weapons and Extras
    private static ItemStack phoenixBow, eagleArrows, hatchetOfWar;
    private static ItemMeta phoenixBowMeta, eagleArrowsMeta, hatchetOfWarMeta;
    private static List<String> phoenixBowLore = new ArrayList<>(),
                                eagleArrowsLore = new ArrayList<>(),
                                hatchetOfWarLore = new ArrayList<>();

    public static void giveClass(Player player)
    {
        /* LORES */
        phoenixBowLore.add("A bow of mighty power");
        phoenixBowLore.add("used by only the best.");

        eagleArrowsLore.add("Flights through the wind");
        eagleArrowsLore.add("like a work of art.");

        hatchetOfWarLore.add("There are many war scars");
        hatchetOfWarLore.add("on this finely crafted");
        hatchetOfWarLore.add("metal axe.");

        /* ITEMS */
        // Armor
        helmet = new ItemStack(Material.LEATHER_HELMET);
        helmetMeta = (LeatherArmorMeta) helmet.getItemMeta();
        helmetMeta.setColor(ClassData.ARCHER.getHelmetColor());
        helmet.setItemMeta(helmetMeta);

        chestplate = new ItemStack(Material.CHAINMAIL_CHESTPLATE);
        boots = new ItemStack(Material.CHAINMAIL_BOOTS);

        // Weapons and Extras
        phoenixBow = new ItemStack(Material.BOW);
        phoenixBowMeta = phoenixBow.getItemMeta();
        phoenixBowMeta.setDisplayName(ChatColor.LIGHT_PURPLE + "Phoenix Bow");
        phoenixBowMeta.setLore(phoenixBowLore);
        phoenixBowMeta.setUnbreakable(true);
        phoenixBow.setItemMeta(phoenixBowMeta);

        eagleArrows = new ItemStack(Material.ARROW, 64);
        eagleArrowsMeta = eagleArrows.getItemMeta();
        eagleArrowsMeta.setDisplayName(ChatColor.YELLOW + "Eagle Quill Feather");
        eagleArrowsMeta.setLore(eagleArrowsLore);
        eagleArrows.setItemMeta(eagleArrowsMeta);

        hatchetOfWar = new ItemStack(Material.IRON_AXE);
        hatchetOfWarMeta = hatchetOfWar.getItemMeta();
        hatchetOfWarMeta.setDisplayName(ChatColor.LIGHT_PURPLE + "Hatchet of War");
        hatchetOfWarMeta.setLore(hatchetOfWarLore);
        hatchetOfWarMeta.setUnbreakable(true);
        hatchetOfWar.setItemMeta(hatchetOfWarMeta);

        // Setting items to the player's inventory slots
        player.getInventory().setHelmet(helmet);
        player.getInventory().setChestplate(chestplate);
        player.getInventory().setBoots(boots);
        player.getInventory().setItem(0, phoenixBow);
        player.getInventory().setItem(1, eagleArrows);
        player.getInventory().setItem(2, hatchetOfWar);
        player.getInventory().setItem(8, CommonItems.getObjectiveLocator());
    }
}
