package com.ben.hbrewrittengs.classes;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;


public class CommonItems
{
	private static ItemStack notchsWisdom;
	private static ItemMeta notchsWisdomMeta;
	
	public static ItemStack getNotchsWisdom(int amount)
	{
		notchsWisdom = new ItemStack(Material.BLAZE_POWDER, amount);
		notchsWisdomMeta = notchsWisdom.getItemMeta();
		notchsWisdomMeta.setDisplayName(ChatColor.GOLD + "Notch's Wisdom");
		notchsWisdom.setItemMeta(notchsWisdomMeta);
		return notchsWisdom;
	}
}
