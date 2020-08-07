package com.ben.hbrewrittengs.database;

import com.ben.hbrewrittengs.databaseutils.AsyncQuery;
import com.ben.hbrewrittengs.databaseutils.AsyncUpdate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class Queries
{
    private static String getPoints = "SELECT points " +
                                      "FROM hbstats " +
                                      "WHERE uuid = ?";

    private static String addPoints = "UPDATE hbstats " +
                                      "SET points = points + ? " +
                                      "WHERE uuid = ?";

    private static String getActiveClass = "SELECT active_class " +
                                           "FROM hbstats " +
                                           "WHERE uuid = ?";

    private static String isHerobrine = "SELECT is_herobrine " +
                                        "FROM hbstats " +
                                        "WHERE uuid = ?";

    private static String setHerobrine = "UPDATE hbstats " +
                                         "SET is_herobrine = ? " +
                                         "WHERE uuid = ?";

    // Gets the player's current amount of points from the database
    public static int getPoints(UUID uuid)
    {
        AsyncQuery query = new AsyncQuery(getPoints);
        query.setString(1, uuid.toString());
        ResultSet rs = query.execute();
        try
        {
            while (rs.next())
            {
                return rs.getInt("points");
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return -42; // This means the player doesn't exist in the db. THIS SHOULD NEVER HAPPEN.
    }

    // Increments a player's current amount of points in the database by a specific amount
    public static void addPoints(int pointsToAdd, UUID uuid)
    {
        AsyncUpdate query = new AsyncUpdate(addPoints);
        query.setString(1, Integer.toString(pointsToAdd));
        query.setString(2, uuid.toString());
        query.execute();
    }

    // Gets the player's current active class from the database.
    // Output be formatted in all caps and with no color codes like "PALADIN"
    public static String getActiveClass(UUID uuid)
    {
        AsyncQuery query = new AsyncQuery(getActiveClass);
        query.setString(1, uuid.toString());
        ResultSet rs = query.execute();
        try
        {
            while (rs.next())
            {
                return rs.getString("active_class");
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    // Returns the player's "is_herobrine" field as is in the database.
    // If it is true, that means the user used a herobrine pass.
    public static boolean isHerobrine(UUID uuid)
    {
        AsyncQuery query = new AsyncQuery(isHerobrine);
        query.setString(1, uuid.toString());
        ResultSet rs = query.execute();
        try
        {
            while (rs.next())
            {
                int result =  rs.getInt("is_herobrine");
                if (result == 1)
                {
                    return true;
                }
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return false;
    }

    // Sets the player's "is_herobrine" field in the database to the inputted boolean value
    public static void setHerobrine(UUID uuid, boolean value)
    {
        AsyncUpdate query = new AsyncUpdate(setHerobrine);

        // puts 1 in the query if value is true, 0 otherwise
        if (value)
        {
            query.setString(1, Integer.toString(1));
        }
        else
        {
            query.setString(1, Integer.toString(0));
        }
        query.setString(2, uuid.toString());
        query.execute();
    }
}
