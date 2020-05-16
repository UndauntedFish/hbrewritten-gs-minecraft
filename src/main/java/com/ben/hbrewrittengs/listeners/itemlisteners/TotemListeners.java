package com.ben.hbrewrittengs.listeners.itemlisteners;

import com.ben.hbrewrittengs.Main;
import com.ben.hbrewrittengs.PlayerData;
import com.ben.hbrewrittengs.classes.Sorcerer;
import com.ben.hbrewrittengs.customevents.TotemDespawnEvent;
import com.ben.hbrewrittengs.customitems.sorcerer.TotemPain;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.UUID;

public class TotemListeners implements Listener
{
    // Contains activated pain totems.
    private static HashMap<UUID, TotemPain> activePainTotems = new HashMap<>();

    // Contains pain totems that are activated, but have been retrieved and sitting in the player's inventory.
    private static HashMap<UUID, TotemPain> dormantPainTotems = new HashMap<>();

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e)
    {
        Player player = e.getPlayer();
        UUID uuid = player.getUniqueId();
        PlayerData pd = Main.getInstance().playerDataMap.get(player.getUniqueId());

        Block placedBlock = e.getBlockPlaced();

        // Checks if the player placed a pain totem, and activates it if so.
        if (placedBlock.getType() == Material.NETHER_BRICK_FENCE)
        {
            if (dormantPainTotems.containsKey(uuid))
            {
                // Player already has active pain totem, reactivate it.
                TotemPain existingTotemPain = dormantPainTotems.remove(uuid);
                existingTotemPain.setLocation(placedBlock.getLocation());
                existingTotemPain.activate();
                activePainTotems.put(uuid, existingTotemPain);
            }
            else
            {
                // Player didn't place a pain totem before, give them one and activate it.
                TotemPain totemPain = new TotemPain(player, placedBlock.getLocation());
                totemPain.activate();
                activePainTotems.put(uuid, totemPain);
            }
            return;
        }

        /* Checks if the player placed a healing totem, and activates it if so.
        if (placedBlock.getType() == Material.OAK_FENCE)
        {
            TotemHealing totemHealing = new TotemHealing(player, placedBlock.getLocation());
            totemHealing.activate();
            pd.activeTotems.add(totemHealing);
            return;
        }
         */

        // Disallows player from placing the block if it wasn't a healing or pain totem.
        e.setCancelled(true);
    }

    @EventHandler
    public void onTotemDoubleClick(PlayerInteractEvent e)
    {
        Player player = e.getPlayer();
        UUID uuid = player.getUniqueId();
        PlayerData pd = Main.getInstance().playerDataMap.get(player.getUniqueId());

        Action action = e.getAction();

        // If a Sorcerer right clicks their placed totem, they can collect it, pause the timer, and use it again.
        if (e.getHand() == EquipmentSlot.HAND)
        {
            if (activePainTotems.containsKey(uuid))
            {
                if (action == Action.RIGHT_CLICK_BLOCK)
                {
                    Block clickedBlock = e.getClickedBlock();

                    if (clickedBlock.getType() == Material.NETHER_BRICK_FENCE) /*||
                         (clickedBlock.getType() == Material.OAK_FENCE && pd.hasTotemHealing())*/
                    {
                        player.getInventory().addItem(Sorcerer.getPainTotemItemStack());
                        TotemPain activeTotem = activePainTotems.remove(uuid);
                        activeTotem.deactivate();
                        dormantPainTotems.put(player.getUniqueId(), activeTotem);
                        clickedBlock.setType(Material.AIR);
                    }
                }
            }
        }
    }

    // Fires whenever the player's healing or pain totem's duration is up, and removes it from their inventory.
    @EventHandler
    public void onTotemDespawn(TotemDespawnEvent e)
    {
        Player player = e.getPlayer();
        UUID uuid = player.getUniqueId();
        ItemStack despawnedTotem = e.getTotemItem();

        if (player.getInventory().contains(despawnedTotem))
        {
            player.getInventory().remove(despawnedTotem);

            if (dormantPainTotems.containsKey(uuid))
            {
                dormantPainTotems.remove(uuid);
            }
        }
    }
}
