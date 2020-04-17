package com.ben.hbrewrittengs.classes;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class Paladin implements BaseClass
{
	// Items
	ItemStack paladinsMight, protectionSpirit; 
	
	// Armor
	ItemStack helmet = new ItemStack(Material.LEATHER_HELMET);
	LeatherArmorMeta helmetMeta = (LeatherArmorMeta) helmet.getItemMeta();
	
	
	public void setItemsInInventory(Player player)
	{
	}

	public void setPlayer(Player player)
	{
		
	}

}
