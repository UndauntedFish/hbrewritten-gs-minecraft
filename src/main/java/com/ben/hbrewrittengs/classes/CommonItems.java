package com.ben.hbrewrittengs.classes;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;


public class CommonItems
{
	private static ItemStack notchsWisdom, objLocator;
	private static ItemMeta notchsWisdomMeta, objLocatorMeta;
	private static List<String> notchsWisdomLore = new ArrayList<>(), objLocatorLore = new ArrayList<>();
	
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
}
