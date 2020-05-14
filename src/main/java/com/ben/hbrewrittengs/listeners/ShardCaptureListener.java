package com.ben.hbrewrittengs.listeners;

import com.ben.hbrewrittengs.Main;
import com.ben.hbrewrittengs.enums.GameState;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class ShardCaptureListener implements Listener
{
    @EventHandler
    public void onShardCapture(PlayerInteractEvent e)
    {
        Player player = e.getPlayer();

        Action action = e.getAction();
        ItemStack heldItem = player.getInventory().getItemInMainHand();

        if (e.getHand() == EquipmentSlot.HAND)
        {
            if (action == Action.RIGHT_CLICK_BLOCK)
            {
                if (e.getClickedBlock().getType() == Material.ENCHANTING_TABLE)
                {
                    // Prevents players from seeing the enchantment table GUI
                    e.setCancelled(true);

                    if (heldItem.getType() == Material.NETHER_STAR &&
                            Main.arena.getGameState() == GameState.SHARD_PICKEDUP)
                    {
                        Main.arena.captureActiveShard();

                        // Removing the captured shard from player's inventory
                        player.getInventory().getItemInMainHand().setAmount(
                                player.getInventory().getItemInMainHand().getAmount() - 1);
                    }
                }
            }
        }
    }
}
