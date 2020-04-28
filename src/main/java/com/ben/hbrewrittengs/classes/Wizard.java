package com.ben.hbrewrittengs.classes;

import com.ben.hbrewrittengs.enums.ClassData;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;

import java.util.ArrayList;
import java.util.List;

public class Wizard
{
    // Armor
    private static ItemStack helmet, chestplate, leggings, boots;
    private static LeatherArmorMeta helmetMeta;

    // Weapons and Extras
    private static ItemStack elixirSpeed, elixirStrength;
    private static PotionMeta elixirSpeedMeta, elixirStrengthMeta;
    private static List<String> elixirStrengthLore = new ArrayList<>(),
                                elixirSpeedLore = new ArrayList<>();

    public static void giveClass(Player player)
    {
        /* LORES */
        elixirSpeedLore.add("A friendly speed boost!");
        elixirStrengthLore.add("A friendly strength boost!");

        /* ITEMS */

        // Armor
        helmet = new ItemStack(Material.LEATHER_HELMET);
        helmetMeta = (LeatherArmorMeta) helmet.getItemMeta();
        helmetMeta.setColor(ClassData.WIZARD.getHelmetColor());
        helmet.setItemMeta(helmetMeta);
        chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
        leggings = new ItemStack(Material.LEATHER_LEGGINGS);
        boots = new ItemStack(Material.LEATHER_BOOTS);

        // Weapons and Extras
        elixirSpeed = new ItemStack(Material.SPLASH_POTION);
        elixirSpeedMeta = (PotionMeta) elixirSpeed.getItemMeta();
        elixirSpeedMeta.setDisplayName(ChatColor.YELLOW.toString() + ChatColor.BOLD + "Elixir: " + ChatColor.RESET.toString() + ChatColor.WHITE + "Speed");
        elixirSpeedMeta.setLore(elixirSpeedLore);
        elixirSpeedMeta.setBasePotionData(new PotionData(PotionType.SPEED, false, false));
        elixirSpeed.setItemMeta(elixirSpeedMeta);

        elixirStrength = new ItemStack(Material.SPLASH_POTION);
        elixirStrengthMeta = (PotionMeta) elixirStrength.getItemMeta();
        elixirStrengthMeta.setDisplayName(ChatColor.YELLOW.toString() + ChatColor.BOLD + "Elixir: " + ChatColor.RESET.toString() + ChatColor.GOLD + "Strength");
        elixirStrengthMeta.setLore(elixirStrengthLore);
        elixirStrengthMeta.setBasePotionData(new PotionData(PotionType.STRENGTH, false, false));
        elixirStrength.setItemMeta(elixirStrengthMeta);

        // Setting items to the player's inventory slots
        player.getInventory().clear();
        player.getInventory().setHelmet(helmet);
        player.getInventory().setChestplate(chestplate);
        player.getInventory().setLeggings(leggings);
        player.getInventory().setBoots(boots);
        player.getInventory().setItem(0, CommonItems.getBladeOfHeroism());
        player.getInventory().setItem(1, elixirSpeed);
        player.getInventory().setItem(2, elixirStrength);
        player.getInventory().setItem(3, CommonItems.getDreamweaverBandage(2));
        player.getInventory().setItem(8, CommonItems.getObjectiveLocator());
    }
}
