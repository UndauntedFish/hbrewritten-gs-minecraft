package com.ben.hbrewrittengs;

import java.util.UUID;

import com.ben.hbrewrittengs.database.Queries;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.ben.hbrewrittengs.enums.Rank;

public class PlayerData
{
	private UUID uuid;
	
	private int points;
	private Rank rank;
	private boolean allowChat; // There is a .5s chat delay. This will be true .5s after the player joins.
	
	public PlayerData(UUID uuid)
	{
		this.uuid = uuid;
		this.rank = Rank.setRankFromPoints(points);
		this.allowChat = false;
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

	public void setAllowedChat(boolean allowChat)
	{
		this.allowChat = allowChat;
	}

	public boolean isAllowedChat()
	{
		return allowChat;
	}
}
