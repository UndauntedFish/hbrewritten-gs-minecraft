package com.ben.hbrewrittengs;

import com.ben.hbrewrittengs.enums.Rank;

import java.util.UUID;

public class PlayerData
{
	private UUID uuid;

	private int points, tokens;
	private Rank rank;
	private boolean isHerobrine;
	private String activeClass;
	private Object class;

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

	public String getActiveClass()
	{
		return activeClass;
	}

	public void setActiveClass(String myClass)
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

}
