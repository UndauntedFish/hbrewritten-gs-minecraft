package com.ben.hbrewrittengs.classes;

import com.ben.hbrewrittengs.enums.ClassData;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.ArrayList;
import java.util.List;

public class Priest implements BaseClass
{
    // Armor
    private static ItemStack helmet, chestplate, leggings, boots;
    private static LeatherArmorMeta helmetMeta;

    // Weapons and Extras
    private static ItemStack bladeHeroism, eagleArrows, hatchetOfWar;
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
        helmetMeta.setColor(ClassData.PRIEST.getHelmetColor());
        helmet.setItemMeta(helmetMeta);

        chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
        leggings = new ItemStack(Material.LEATHER_LEGGINGS);
        boots = new ItemStack(Material.LEATHER_BOOTS);

        // Weapons and Extras


        // Setting items to the player's inventory slots
        player.getInventory().setHelmet(helmet);
        player.getInventory().setChestplate(chestplate);
        player.getInventory().setLeggings(leggings);
        player.getInventory().setBoots(boots);
        player.getInventory().setItem(0, phoenixBow);
        player.getInventory().setItem(1, eagleArrows);
        player.getInventory().setItem(2, hatchetOfWar);
        player.getInventory().setItem(8, CommonItems.getObjectiveLocator());
    }
}
