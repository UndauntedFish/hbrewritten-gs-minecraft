package com.ben.hbrewrittengs.database;

import com.ben.hbrewrittengs.Main;
import com.ben.hbrewrittengs.PlayerData;
import org.bukkit.Bukkit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Queries
{
    // Retrieve's the player's points from the database and stores it in PlayerData. Returns true if the data was loaded.
    public static void loadPointsIntoPlayerData(PlayerData pd)
    {
        Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), new Runnable()
        {
            @Override
            public void run()
            {
                String sql = "SELECT points ";
                sql += "FROM hbstats ";
                sql += "WHERE uuid = ?";

                try
                {
                    Connection connection = Main.getInstance().getHikari().getConnection();

                    PreparedStatement ps = connection.prepareStatement(sql);
                    ps.setString(1, pd.getUUID().toString());
                    ResultSet rs = ps.executeQuery();
                    if (rs.next())
                    {
                        pd.setPoints(rs.getInt("points"));
                    }
                    rs.close();
                }
                catch (SQLException e)
                {
                    e.printStackTrace();
                }
            }
        });
    }
}
