package com.ben.hbrewrittengs.listeners;

import com.ben.hbrewrittengs.Config;
import com.ben.hbrewrittengs.Main;
import com.ben.hbrewrittengs.PlayerData;
import com.ben.hbrewrittengs.enums.GameState;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.enchantment.PrepareItemEnchantEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class ShardCaptureListener implements Listener
{
    @EventHandler
    public void onShardCapture(PlayerInteractEvent e)
    {
        Player player = e.getPlayer();
        PlayerData pd = Main.getInstance().playerDataMap.get(player.getUniqueId());

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
                        // Send async query to database updating their points/tokens
                        PlayerData shardHolderPD = Main.getInstance().playerDataMap.get(pd);
                        pd.addPoints(Config.getShardCapPoints());

                        Main.arena.captureActiveShard();

                        // Removing the captured shard from player's inventory
                        player.getInventory().getItemInMainHand().setAmount(
                                player.getInventory().getItemInMainHand().getAmount() - 1);
                    }
                }
            }
        }
    }

    /*
     * If the player sneaks while capturing the shard, they are still able to open the enchantment table GUI (for some strange reason)
     * So, this eventhandler listens for whenever the player puts an item in the ench table to be enchanted, and cancels their ability to enchant it.
     *
     * Clunky solution, but it works.
     */
    @EventHandler
    public void onEnchant(PrepareItemEnchantEvent e)
    {
        e.setCancelled(true);
    }
}
