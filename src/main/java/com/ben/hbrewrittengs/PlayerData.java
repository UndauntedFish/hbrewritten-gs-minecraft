package com.ben.hbrewrittengs;

import com.ben.hbrewrittengs.enums.Rank;

import java.util.UUID;

public class PlayerData
{
	private UUID uuid;
	
	private int points;
	private Rank rank;
	private boolean hasDataLoaded; // There is a .5s chat delay. This will be true .5s after the player joins.
	
	public PlayerData(UUID uuid)
	{
		this.uuid = uuid;
		this.rank = Rank.setRankFromPoints(points);
		this.hasDataLoaded = false;
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

	public void setDataLoaded(boolean hasDataLoaded)
	{
		this.hasDataLoaded = hasDataLoaded;
	}

	public boolean hasDataLoaded()
	{
		return hasDataLoaded;
	}
}
