package com.ben.hbrewrittengs;

import com.ben.hbrewrittengs.classes.Spectator;
import com.ben.hbrewrittengs.classes.SpectatorItems;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class SpectatorManager extends SpectatorItems
{
    private ArrayList<Spectator> spectatorList;
    private boolean everyoneIsSpectator;

    public SpectatorManager()
    {
        spectatorList = new ArrayList<>();
        everyoneIsSpectator = false;
    }

    // Adds a player to the spectator list and makes them invisible and able to fly
    public void addPlayer(Player player)
    {
        Spectator spectator = new Spectator(player);
        spectatorList.add(spectator);

        // Turns the player invisible, and gives them the spectator class.
        giveClass(player);

        // Vanishes the player, hiding their sprint particles and whatever else
        Main.getInstance().toggleVisibilityNative(player);

        // Decreases the total survivor count by 1, since this one just died and is now spectating.
        Main.arena.setSurvivorsLeft(Main.arena.getSurvivorsLeft() - 1);
    }

    // Returns true if the targeted player is a spectator, false otherwise.
    public boolean isSpectator(Player player)
    {
        for (Spectator s : spectatorList)
        {
            if (s.getUUID().equals(player.getUniqueId()))
            {
                return true;
            }
        }
        return false;
    }

    // Gets the total number of survivors left in the game. This does not include Herobrine of course.
    public int getSpectatorCount()
    {
        return spectatorList.size();
    }
}
