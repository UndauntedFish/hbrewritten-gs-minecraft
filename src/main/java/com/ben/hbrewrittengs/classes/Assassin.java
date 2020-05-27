package com.ben.hbrewrittengs.classes;

import com.ben.hbrewrittengs.enums.ClassData;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.ArrayList;
import java.util.List;

public class Assassin
{
    // Armor
    private static ItemStack helmet, chestplate, leggings, boots;
    private static LeatherArmorMeta helmetMeta, chestplateMeta, leggingsMeta, bootsMeta;

    // Weapons and Extras
    private static ItemStack spectralDagger, cloak, smokeScreen;
    private static ItemMeta spectralDaggerMeta, cloakMeta, smokeScreenMeta;
    private static List<String> spectralDaggerLore = new ArrayList<>(),
                                cloakLore = new ArrayList<>(),
                                smokeScreenLore = new ArrayList<>();

    public static void loadItems()
    {
        /* LORES */
        spectralDaggerLore.add("Charged with Herobrine's hate,");
        spectralDaggerLore.add("this blade can pierce his ghostly resistance!");

        cloakLore.add("Harness Herobrine's dark energy");
        cloakLore.add("and give him a taste of his own meds!");
        cloakLore.add("Right click to vanish.");

        smokeScreenLore.add("Casts a cloud of smoke to mask your movements!");
        smokeScreenLore.add("Right click to throw, left click to detonate.");

        /* ITEMS */

        // Armor
        helmet = new ItemStack(Material.LEATHER_HELMET);
        helmetMeta = (LeatherArmorMeta) helmet.getItemMeta();
        helmetMeta.setColor(ClassData.ASSASSIN.getHelmetColor());
        helmet.setItemMeta(helmetMeta);

        chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
        chestplateMeta = (LeatherArmorMeta) chestplate.getItemMeta();
        chestplateMeta.setColor(ClassData.ASSASSIN.getHelmetColor());
        chestplate.setItemMeta(chestplateMeta);

        leggings = new ItemStack(Material.LEATHER_LEGGINGS);
        leggingsMeta = (LeatherArmorMeta) leggings.getItemMeta();
        leggingsMeta.setColor(ClassData.ASSASSIN.getHelmetColor());
        leggings.setItemMeta(leggingsMeta);

        boots = new ItemStack(Material.LEATHER_BOOTS);
        bootsMeta = (LeatherArmorMeta) boots.getItemMeta();
        bootsMeta.setColor(ClassData.ASSASSIN.getHelmetColor());
        boots.setItemMeta(bootsMeta);

        // Weapons and Extras
        spectralDagger = new ItemStack(Material.GOLDEN_SWORD);
        spectralDaggerMeta = spectralDagger.getItemMeta();
        spectralDaggerMeta.setDisplayName(ChatColor.GOLD + "Spectral Dagger");
        spectralDaggerMeta.setLore(spectralDaggerLore);
        spectralDaggerMeta.setUnbreakable(true);
        spectralDagger.setItemMeta(spectralDaggerMeta);

        cloak = new ItemStack(Material.GLOWSTONE_DUST, 3);
        cloakMeta = cloak.getItemMeta();
        cloakMeta.setDisplayName(ChatColor.WHITE.toString() + ChatColor.BOLD + "Phantasmal " +
                ChatColor.RESET.toString() + ChatColor.WHITE + "Cloak");
        cloakMeta.setLore(cloakLore);
        cloak.setItemMeta(cloakMeta);

        smokeScreen = new ItemStack(Material.IRON_NUGGET, 3);
        smokeScreenMeta = smokeScreen.getItemMeta();
        smokeScreenMeta.setDisplayName(ChatColor.GRAY + "Smoke Screen");
        smokeScreenMeta.setLore(smokeScreenLore);
        smokeScreen.setItemMeta(smokeScreenMeta);


    }

    // Gives the player the items of a class.
    public static void giveClass(Player player)
    {
        // Setting items to the player's inventory slots
        player.getInventory().clear();
        player.getInventory().setHelmet(helmet);
        player.getInventory().setChestplate(chestplate);
        player.getInventory().setLeggings(leggings);
        player.getInventory().setBoots(boots);
        player.getInventory().setItem(0, spectralDagger);
        player.getInventory().setItem(1, cloak);
        player.getInventory().setItem(2, smokeScreen);
        player.getInventory().setItem(3, CommonItems.getBlindingGrenade(3, ChatColor.YELLOW + "Charge of " + ChatColor.BOLD + "Blinding!"));
        player.getInventory().setItem(8, CommonItems.getObjectiveLocator());

        // Set the player's health to 8 hearts
        AttributeInstance healthAttribute = player.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        healthAttribute.setBaseValue(16.0);
    }
}
