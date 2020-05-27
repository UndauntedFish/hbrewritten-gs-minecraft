package com.ben.hbrewrittengs;

import com.ben.hbrewrittengs.enums.GameState;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ShardSpawnCommand implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        boolean senderIsPlayer = false;
        Player player = null;
        if (sender instanceof Player)
        {
            senderIsPlayer = true;
            player = (Player) sender;
        }

        if (args.length < 1)
        {
            if (senderIsPlayer)
            {
                player.sendMessage("/shard <spawn/destroy>");
            }
            else
            {
                Bukkit.getConsoleSender().sendMessage("/shard <spawn/destroy>");
            }
        }

        if (args[0].equalsIgnoreCase("spawn"))
        {
            if (Main.arena.getGameState() == GameState.IDLE)
            {
                Main.arena.spawnShard();
            }
        }
        else if (args[0].equalsIgnoreCase("destroy"))
        {
            if (Main.arena.getGameState() == GameState.SHARD_SPAWNED)
            {
                Main.arena.getActiveShard().despawn();
            }
        }
        else
        {
            if (senderIsPlayer)
            {
                player.sendMessage("/shard <spawn/destroy>");
            }
            else
            {
                Bukkit.getConsoleSender().sendMessage("/shard <spawn/destroy>");
            }
        }
        return false;
    }
}
