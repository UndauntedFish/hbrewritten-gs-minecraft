package com.ben.hbrewrittengs.databaseutils;

import com.ben.hbrewrittengs.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class AsyncQuery extends BaseFields
{
    private PreparedStatement preparedStatement;
    private String sql;

    private CompletableFuture<ResultSet> result;

    public AsyncQuery(String sql) throws IllegalArgumentException
    {
        if (connection.equals(null))
        {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[HBRgs] ERROR: Null datasource input for AsyncQuery!");
            throw new IllegalArgumentException();
        }

        if (sql.equals(null))
        {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[HBRgs] ERROR: Null sql input for AsyncQuery!");
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
        this.result = new CompletableFuture<>();
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
    public ResultSet execute()
    {
        Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    result.complete(preparedStatement.executeQuery());
                }
                catch (SQLException e)
                {
                    e.printStackTrace();
                }
            }
        });

        ResultSet queryResult = null;
        try
        {
            queryResult = result.get();
        }
        catch (InterruptedException | ExecutionException e)
        {
            e.printStackTrace();
        }
        return queryResult;
    }
}
