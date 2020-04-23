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

public class Paladin implements BaseClass
{
	// Armor
	private static ItemStack helmet, chestplate, boots;
	private static LeatherArmorMeta helmetMeta;

	// Weapons and Extras
	private static ItemStack paladinsMight, protSpirit, notchsWisdom;
	private static ItemMeta paladinsMightMeta, protSpiritMeta;
	private static List<String> paladinsMightLore = new ArrayList<>(), protSpiritLore = new ArrayList<>();
	
	public static void giveClass(Player player)
	{
		/* LORES */
		paladinsMightLore.add("The might of the paladins!");
		protSpiritLore.add("The spirit of protection empowers you!");

		/* ITEMS */

		// Armor
		helmet = new ItemStack(Material.LEATHER_HELMET);
		helmetMeta = (LeatherArmorMeta) helmet.getItemMeta();
		helmetMeta.setColor(ClassData.PALADIN.getHelmetColor());
		helmet.setItemMeta(helmetMeta);
		chestplate = new ItemStack(Material.CHAINMAIL_CHESTPLATE);
		boots = new ItemStack(Material.CHAINMAIL_BOOTS);

		// Weapons and Extras
		paladinsMight = new ItemStack(Material.IRON_SWORD);
		paladinsMightMeta = paladinsMight.getItemMeta();
		paladinsMightMeta.setDisplayName(ChatColor.YELLOW + "Paladin's Might");
		paladinsMightMeta.setLore(paladinsMightLore);
		paladinsMightMeta.setUnbreakable(true);
		paladinsMight.setItemMeta(paladinsMightMeta);

		protSpirit = new ItemStack(Material.ENDER_PEARL, 3);
		protSpiritMeta = protSpirit.getItemMeta();
		protSpiritMeta.setDisplayName(ChatColor.WHITE + "Protection " + ChatColor.BOLD + "Spirit");
		protSpiritMeta.setLore(protSpiritLore);
		protSpirit.setItemMeta(protSpiritMeta);

		notchsWisdom = CommonItems.getNotchsWisdom(3);

		// Setting items to the player's inventory slots
		player.getInventory().setHelmet(helmet);
		player.getInventory().setChestplate(chestplate);
		player.getInventory().setBoots(boots);
		player.getInventory().setItem(0, paladinsMight);
		player.getInventory().setItem(1, protSpirit);
		player.getInventory().setItem(2, notchsWisdom);
		player.getInventory().setItem(8, CommonItems.getObjectiveLocator());
	}
}
