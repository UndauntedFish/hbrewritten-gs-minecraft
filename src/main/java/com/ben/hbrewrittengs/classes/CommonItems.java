package com.ben.hbrewrittengs.classes;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;


public class CommonItems
{
	private static ItemStack notchsWisdom, objLocator, bladeHeroism;
	private static ItemMeta notchsWisdomMeta, objLocatorMeta, bladeHeroismMeta;
	private static List<String> notchsWisdomLore = new ArrayList<>(),
								objLocatorLore = new ArrayList<>(),
								bladeHeroismLore = new ArrayList<>();
	
	public static ItemStack getNotchsWisdom(int amount)
	{
		/* LORE */
		notchsWisdomLore.add("Creates a field of healing");
		notchsWisdomLore.add("around your location.");

		/* ITEM */
		notchsWisdom = new ItemStack(Material.BLAZE_POWDER, amount);
		notchsWisdomMeta = notchsWisdom.getItemMeta();
		notchsWisdomMeta.setDisplayName(ChatColor.YELLOW + "Notch's Wisdom");
		notchsWisdomMeta.setLore(notchsWisdomLore);
		notchsWisdom.setItemMeta(notchsWisdomMeta);

		return notchsWisdom;
	}

	public static ItemStack getObjectiveLocator()
	{
		/* LORE */
		objLocatorLore.add("Whenever a shard spawns,");
		objLocatorLore.add("this compass will point to it!");

		/* ITEM */
		objLocator = new ItemStack(Material.COMPASS);
		objLocatorMeta = objLocator.getItemMeta();
		objLocatorMeta.setDisplayName(ChatColor.GRAY + "Objective Locator");
		objLocatorMeta.setLore(objLocatorLore);
		objLocator.setItemMeta(objLocatorMeta);

		return objLocator;
	}

	public static ItemStack getBladeOfHeroism()
	{
		/* LORE */
		bladeHeroismLore.add("Only true heros hold the");
		bladeHeroismLore.add("strength to use this sword.");

		/* ITEM */
		bladeHeroism = new ItemStack(Material.STONE_SWORD);
		bladeHeroismMeta = bladeHeroism.getItemMeta();
		bladeHeroismMeta.setDisplayName(ChatColor.YELLOW + "Blade of Heroism");
		bladeHeroismMeta.setLore(bladeHeroismLore);
		bladeHeroismMeta.setUnbreakable(true);
		bladeHeroism.setItemMeta(bladeHeroismMeta);

		return bladeHeroism;
	}
}
