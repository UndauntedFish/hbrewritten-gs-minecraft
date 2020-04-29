package com.ben.hbrewrittengs;

import com.ben.hbrewrittengs.bossbarcooldown.BossBarCooldown;
import com.ben.hbrewrittengs.enums.ClassData;
import com.ben.hbrewrittengs.enums.Rank;
import org.bukkit.entity.Entity;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.UUID;

public class PlayerData
{
	private UUID uuid;

	private int points, tokens;
	private Rank rank;
	private boolean isHerobrine, showXPCooldown, isCloaked;
	private ClassData activeClass;
	public ArrayList<BossBarCooldown> activeCooldowns = new ArrayList<>();
	public LinkedList<Entity> thrownSmokeScreens = new LinkedList<>(),
							  thrownChemGrenades = new LinkedList<>(),
							  thrownBlindGrenades = new LinkedList<>();

	public PlayerData(UUID uuid)
	{
		this.uuid = uuid;
		this.rank = Rank.setRankFromPoints(points);
		this.isHerobrine = false;
		this.showXPCooldown = true;
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

	public boolean isCloaked()
	{
		return isCloaked;
	}

	public void setCloaked(boolean cloaked)
	{
		isCloaked = cloaked;
	}
}
