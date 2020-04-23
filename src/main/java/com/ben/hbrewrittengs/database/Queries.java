package com.ben.hbrewrittengs.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class Queries
{
    private static String getPoints = "SELECT points " +
                                      "FROM hbstats " +
                                      "WHERE uuid = ?";

    private static String getActiveClass = "SELECT active_class " +
                                           "FROM hbstats " +
                                           "WHERE uuid = ?";

    private static String isHerobrine = "SELECT is_herobrine " +
                                        "FROM hbstats " +
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

    // Returns the player's "is_herobrine" entry as is in the database.
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
}
