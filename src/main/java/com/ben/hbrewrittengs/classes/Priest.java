package com.ben.hbrewrittengs.classes;

import com.ben.hbrewrittengs.enums.ClassData;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class Priest
{
    // Armor
    private static ItemStack helmet, chestplate, leggings, boots;
    private static LeatherArmorMeta helmetMeta;

    public static void giveClass(Player player)
    {
        /* ITEMS */

        // Armor
        helmet = new ItemStack(Material.LEATHER_HELMET);
        helmetMeta = (LeatherArmorMeta) helmet.getItemMeta();
        helmetMeta.setColor(ClassData.PRIEST.getHelmetColor());
        helmet.setItemMeta(helmetMeta);
        chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
        leggings = new ItemStack(Material.LEATHER_LEGGINGS);
        boots = new ItemStack(Material.LEATHER_BOOTS);

        // Setting items to the player's inventory slots
        player.getInventory().setHelmet(helmet);
        player.getInventory().setChestplate(chestplate);
        player.getInventory().setLeggings(leggings);
        player.getInventory().setBoots(boots);
        player.getInventory().setItem(0, CommonItems.getBladeOfHeroism());
        player.getInventory().setItem(1, CommonItems.getDreamweaverBandage(2));
        player.getInventory().setItem(2, CommonItems.getNotchsWisdom(3));
        player.getInventory().setItem(3, CommonItems.getSummonWoofless());
        player.getInventory().setItem(8, CommonItems.getObjectiveLocator());
    }
}
