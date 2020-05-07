package com.ben.hbrewrittengs;

import com.ben.hbrewrittengs.bossbarcooldown.BossBarCooldown;
import com.ben.hbrewrittengs.bossbarcooldown.ImplicitCooldown;
import com.ben.hbrewrittengs.enums.ClassData;
import com.ben.hbrewrittengs.enums.Rank;
import org.bukkit.entity.Entity;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.UUID;

public class PlayerData
{
	/*
	 * This class is a wrapper for the Player class. It stores game-relevant information for each player.
	 * Each player's PlayerData wrapper is stored in a hashmap in the Main class, with their uuid as the key.
	 */


	private UUID uuid;

	/*
	 * These variables' values are fetched from the database upon the player joining the game
	 *  (see AsyncPlayerDataLoader to see how that works).
	 */
	private int points, tokens;
	private Rank rank;
	private ClassData activeClass;
	private boolean isHerobrine;

	// If a player is vanished (ex. an Assassin using their cloak) this will be true.
	// If the player is Herobrine, this will always be true until the third shard is capped and they turn human
	private boolean isVanished;

	// These are all active cooldowns that the player CAN see. A bossbar will show players how much time is remaining.
	public ArrayList<BossBarCooldown> activeBossBarCooldowns = new ArrayList<>();

	// These are all active cooldowns that the player cannot see. They just prohibit them from using an item for x seconds.
	public ArrayList<ImplicitCooldown> activeImplicitCooldowns = new ArrayList<>();

	// Any entity a player throws will be stored here for future removal.
	public LinkedList<Entity> thrownSmokeScreens = new LinkedList<>(),
							  thrownChemGrenades = new LinkedList<>(),
							  thrownBlindGrenades = new LinkedList<>();

	public PlayerData(UUID uuid)
	{
		this.uuid = uuid;
		this.rank = Rank.setRankFromPoints(points);
		this.isHerobrine = false;
	}

	public UUID getUUID()
	{
		return uuid;
	}

	public int getPoints()
	{
		return points;
	}

	public void setPoints(int points)
	{
		this.points = points;
	}

	public void incrementPointsBy(int addedPoints)
	{
		this.points += points;
	}

	public Rank getRank()
	{
		return rank;
	}

	public void setRank(Rank rank)
	{
		this.rank = rank;
	}

	public ClassData getActiveClass()
	{
		return activeClass;
	}

	public void setActiveClass(ClassData myClass)
	{
		this.activeClass = myClass;
	}

	public int getTokens()
	{
		return tokens;
	}

	public void setTokens(int tokens)
	{
		this.tokens = tokens;
	}

	public boolean isHerobrine()
	{
		return isHerobrine;
	}

	public void setHerobrine(boolean isHerobrine)
	{
		this.isHerobrine = isHerobrine;
	}

	public boolean isVanished()
	{
		return isVanished;
	}

	public void setVanished(boolean vanished)
	{
		isVanished = vanished;
	}
}
