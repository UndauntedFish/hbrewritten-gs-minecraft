package com.ben.hbrewrittengs.database;

import com.ben.hbrewrittengs.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AsyncUpdate extends BaseFields
{
    private PreparedStatement preparedStatement;
    private String sql;

    public AsyncUpdate(String sql) throws IllegalArgumentException
    {
        if (connection.equals(null))
        {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[HBRgs] ERROR: Null datasource input for AsyncUpdate!");
            throw new IllegalArgumentException();
        }

        if (sql.equals(null))
        {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[HBRgs] ERROR: Null sql input for AsyncUpdate!");
            throw new IllegalArgumentException();
        }

        this.sql = sql;
        try
        {
            this.preparedStatement = connection.prepareStatement(sql);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public void setString(int index, String input)
    {
        try
        {
            preparedStatement.setString(index, input);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    // Executes the query. Will pause the class it got called in until the async task is done.
    public void execute()
    {
        Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    preparedStatement.executeUpdate();
                }
                catch (SQLException e)
                {
                    e.printStackTrace();
                }
            }
        });
    }
}
