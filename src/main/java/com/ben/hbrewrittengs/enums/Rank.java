package com.ben.hbrewrittengs.enums;

import com.ben.hbrewrittengs.utils.ColorUtils;
import org.bukkit.ChatColor;

public enum Rank
{
	SPIRIT       (0, ColorUtils.fromRGB(128, 128, 128) + "Spirit"),
	DEFENDER     (100, ColorUtils.fromRGB(0, 179, 179) + "Defender"),
	CASPER       (500, ColorUtils.fromRGB(0, 255, 255) + "Casper"),
	BANSHEE      (1500, ColorUtils.fromRGB(255, 0, 255) + "Banshee"),
	SOULSEEKER   (2500, ColorUtils.fromRGB(255, 128, 0) + "Soulseeker"),
	HAUNTER      (4000, ColorUtils.fromRGB(255, 255, 0) + "Haunter"),
	POSSESSED    (5000, ColorUtils.fromRGB(255, 77, 77) + "Possessed"),
	WRAITH       (7500, ColorUtils.fromRGB(0, 255, 255) + "Wraith"),
	PHANTOM      (10000, ColorUtils.fromRGB(0, 255, 255) + "Phantom"),
	SKINWALKER   (15000, ColorUtils.fromRGB(255, 255, 0) + "Skinwalker"),
	MYSTERIA     (25000, ColorUtils.fromRGB(255, 0, 255) + "Mysteria"),
	WIDOWER      (35000, ColorUtils.fromRGB(0, 179, 179) + "Widower"),
	PARANORMAL   (45000, ColorUtils.fromRGB(0, 0, 255) + "Paranormal"),
	SPECTRAL     (60000, ColorUtils.fromRGB(0, 179, 0) + "Spectral"),
	MYTHICAL     (80000, ColorUtils.fromRGB(255, 128, 0) + "Mythical"),
	GHOSTBUSTER  (100000, ColorUtils.fromRGB(191, 191, 191) + "Ghostbuster"),
	SPOOKY       (150000, ColorUtils.fromRGB(179, 0, 179) + "" + ChatColor.BOLD + "Spooky"),
	ETHEREAL     (200000, ColorUtils.fromRGB(255, 77, 77) + "" + ChatColor.BOLD + "Ethereal"),
	DEMONHUNTER  (250000, ColorUtils.fromRGB(191, 191, 191) + "" + ChatColor.BOLD + "DemonHunter"),
	DIVINE       (300000, ColorUtils.fromRGB(255, 0, 255) + "" + ChatColor.BOLD + "Divine"),
	DEATHBRINGER (-1, ColorUtils.fromRGB(255, 77, 77) + "" + ChatColor.BOLD + "Death Bringer");
	
	private final int POINTSREQUIRED;
	private final String DISPLAYNAME;
	
	private Rank(int pointsRequired, String displayName)
	{
		this.POINTSREQUIRED = pointsRequired;
		this.DISPLAYNAME = displayName;
	}
	
	public int getPointsRequired()
	{
		return POINTSREQUIRED;
	}
	
	public String getDisplayName()
	{
		return DISPLAYNAME + ChatColor.RESET;
	}
	
	public static Rank setRankFromPoints(int points)
	{
		Rank[] ranks = Rank.values();
		
		for (int i = 0; i < ranks.length; i++)
		{
			if (points == ranks[i].getPointsRequired())
			{
				return ranks[i];
			}
			
			// If ranks has next element
			if (!ranks[i + 1].equals(null))
			{
				if (points > ranks[i].getPointsRequired() && points < ranks[i + 1].getPointsRequired())
				{
					return ranks[i];
				}
				else
				{
					return Rank.DIVINE;
				}
			}
			else
			{
				return Rank.DEATHBRINGER;
			}
		}
		
		return null;
	}
	
}
