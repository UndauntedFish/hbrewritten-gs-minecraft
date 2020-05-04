package com.ben.hbrewrittengs.classes;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;


public class CommonItems
{
	private static ItemStack notchsWisdom, objLocator, bladeHeroism, dwBandage,
			woofless, blindGrenade;
	private static ItemMeta notchsWisdomMeta, objLocatorMeta, bladeHeroismMeta, dwBandageMeta,
			wooflessMeta, blindGrenadeMeta;
	private static List<String> notchsWisdomLore = new ArrayList<>(),
								objLocatorLore = new ArrayList<>(),
								bladeHeroismLore = new ArrayList<>(),
								dwBandageLore = new ArrayList<>(),
								wooflessLore = new ArrayList<>(),
								blindGrenadeLore = new ArrayList<>();

	public static void loadItems()
	{
		/* Notch's Wisdom LORE */
		notchsWisdomLore.add("Creates a field of healing");
		notchsWisdomLore.add("around your location.");

		/* Notch's Wisdom ITEM */
		notchsWisdom = new ItemStack(Material.BLAZE_POWDER);
		notchsWisdomMeta = notchsWisdom.getItemMeta();
		notchsWisdomMeta.setDisplayName(ChatColor.YELLOW + "Notch's Wisdom");
		notchsWisdomMeta.setLore(notchsWisdomLore);
		notchsWisdom.setItemMeta(notchsWisdomMeta);

		/* Objective Locator LORE */
		objLocatorLore.add("Whenever a shard spawns,");
		objLocatorLore.add("this compass will point to it!");

		/* Objective Locator ITEM */
		objLocator = new ItemStack(Material.COMPASS);
		objLocatorMeta = objLocator.getItemMeta();
		objLocatorMeta.setDisplayName(ChatColor.GRAY + "Objective Locator");
		objLocatorMeta.setLore(objLocatorLore);
		objLocator.setItemMeta(objLocatorMeta);

		/* Blade of Heroism LORE */
		bladeHeroismLore.add("Only true heros hold the");
		bladeHeroismLore.add("strength to use this sword.");

		/* Blade of Heroism ITEM */
		bladeHeroism = new ItemStack(Material.STONE_SWORD);
		bladeHeroismMeta = bladeHeroism.getItemMeta();
		bladeHeroismMeta.setDisplayName(ChatColor.YELLOW + "Blade of Heroism");
		bladeHeroismMeta.setLore(bladeHeroismLore);
		bladeHeroismMeta.setUnbreakable(true);
		bladeHeroism.setItemMeta(bladeHeroismMeta);

		/* Dreamweaver Bandage LORE */
		dwBandageLore.add("A quick heal, perfect for");
		dwBandageLore.add("sticky situations.");

		/* Dreamweaver Bandage ITEM */
		dwBandage = new ItemStack(Material.MAGMA_CREAM);
		dwBandageMeta = dwBandage.getItemMeta();
		dwBandageMeta.setDisplayName(ChatColor.YELLOW + "Dreamweaver Bandage");
		dwBandageMeta.setLore(dwBandageLore);
		dwBandage.setItemMeta(dwBandageMeta);

		/* Summon Wolfless LORE */
		wooflessLore.add("A furry friend. It gets");
		wooflessLore.add("lonely out there!");

		/* Summon Wolfless ITEM */
		woofless = new ItemStack(Material.BONE);
		wooflessMeta = woofless.getItemMeta();
		wooflessMeta.setDisplayName(ChatColor.WHITE + "Summon Woofless");
		wooflessMeta.setLore(wooflessLore);
		woofless.setItemMeta(wooflessMeta);

		/* Blinding Grenade LORE */
		blindGrenadeLore.add("Blinds those it hits for 3 seconds.");

		/* Blinding Grenade ITEM */
		blindGrenade = new ItemStack(Material.GOLD_NUGGET);
		blindGrenadeMeta = blindGrenade.getItemMeta();
		blindGrenadeMeta.setLore(blindGrenadeLore);
		blindGrenade.setItemMeta(blindGrenadeMeta);

	}

	public static ItemStack getNotchsWisdom(int amount)
	{
		notchsWisdom.setAmount(amount);
		return notchsWisdom;
	}

	public static ItemStack getObjectiveLocator()
	{
		return objLocator;
	}

	public static ItemStack getBladeOfHeroism()
	{
		return bladeHeroism;
	}

	public static ItemStack getDreamweaverBandage(int amount)
	{
		dwBandage.setAmount(amount);
		return dwBandage;
	}

	public static ItemStack getSummonWoofless()
	{
		return woofless;
	}

	public static ItemStack getBlindingGrenade(int amount, String name)
	{
		blindGrenadeMeta = blindGrenade.getItemMeta();
		blindGrenadeMeta.setDisplayName(name);
		blindGrenade.setItemMeta(blindGrenadeMeta);
		blindGrenade.setAmount(amount);

		return blindGrenade;
	}
}
