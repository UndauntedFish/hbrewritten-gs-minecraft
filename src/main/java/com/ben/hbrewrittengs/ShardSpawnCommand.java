package com.ben.hbrewrittengs;

import com.ben.hbrewrittengs.enums.GameState;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ShardSpawnCommand implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        if (Main.arena.getGameState() == GameState.IDLE)
        {
            Main.arena.spawnShard();
        }
        return false;
    }
}
