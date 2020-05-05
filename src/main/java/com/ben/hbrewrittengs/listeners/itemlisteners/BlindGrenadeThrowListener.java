package com.ben.hbrewrittengs.listeners.itemlisteners;

import com.ben.hbrewrittengs.Main;
import com.ben.hbrewrittengs.PlayerData;
import com.ben.hbrewrittengs.bossbarcooldown.BossBarCooldown;
import com.ben.hbrewrittengs.customitems.assassin.Cloak;
import com.ben.hbrewrittengs.customitems.assassin.SmokeScreen;
import com.ben.hbrewrittengs.customitems.common.BlindGrenade;
import com.ben.hbrewrittengs.enums.Format;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.boss.BarColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.Iterator;

public class BlindGrenadeThrowListener implements Listener
{
    @EventHandler
    public void onBlindGrenadeThrow(PlayerInteractEvent e)
    {
        Player player = e.getPlayer();
        PlayerData pd = Main.getInstance().playerDataMap.get(player.getUniqueId());

        Action action = e.getAction();
        ItemStack heldItem = player.getInventory().getItemInMainHand();

        if (e.getHand() == EquipmentSlot.HAND)
        {
            if (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK)
            {
                if (heldItem.getType() == Material.GOLD_NUGGET)
                {
                    // Throwing the blind grenade
                    BlindGrenade.unpinAndThrow(player);
                }
            }
        }
    }
}
