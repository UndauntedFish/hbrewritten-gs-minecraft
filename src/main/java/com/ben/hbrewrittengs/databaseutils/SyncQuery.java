package com.ben.hbrewrittengs.databaseutils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SyncQuery extends BaseFields
{
    private PreparedStatement preparedStatement;
    private String sql;

    private ResultSet result;

    public SyncQuery(String sql) throws IllegalArgumentException
    {
        if (connection.equals(null))
        {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[HBRgs] ERROR: Null datasource for BaseFields!");
            throw new IllegalArgumentException();
        }

        if (sql.equals(null))
        {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[HBRgs] ERROR: Null sql input for SyncUpdate!");
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
        this.result = null;
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

    // Executes the update.
    public ResultSet execute()
    {
        try
        {
            result = preparedStatement.executeQuery();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return result;
    }
}
