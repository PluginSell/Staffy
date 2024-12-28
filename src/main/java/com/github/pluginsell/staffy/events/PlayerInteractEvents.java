package com.github.pluginsell.staffy.events;

import com.github.pluginsell.staffy.Main;
import com.github.pluginsell.staffy.inventoryutils.StaffModeInventory;
import com.github.pluginsell.staffy.utils.HashMaps;
import com.github.pluginsell.staffy.utils.MessageUtils;
import com.github.pluginsell.staffy.utils.StaffUtils;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteractEvents implements Listener {
    @EventHandler
    public void staffRandomTeleportEvent(PlayerInteractEvent event) {
        if(HashMaps.staffMode.containsKey(event.getPlayer())) {
            if(event.getPlayer().getItemInHand().equals(new StaffModeInventory().randomTeleportItem())) {
                new StaffUtils().randomTeleportStaff(event.getPlayer());
            }
        }
    }

    @EventHandler
    public void throughTopEvent(PlayerInteractEvent event) {
        if(HashMaps.staffMode.containsKey(event.getPlayer())) {
            if(event.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
                if(!event.getClickedBlock().getType().equals(Material.AIR)) {
                    if(event.getPlayer().getItemInHand().equals(new StaffModeInventory().throughItem())) {
                        new StaffUtils().throughTop(event.getPlayer(), event.getClickedBlock().getLocation());
                    }
                }
            }
        }
    }

    @EventHandler
    public void throughWallEvent(PlayerInteractEvent event) {
        if(HashMaps.staffMode.containsKey(event.getPlayer())) {
            if(event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                if(!event.getClickedBlock().getType().equals(Material.AIR)) {
                    if(event.getPlayer().getItemInHand().equals(new StaffModeInventory().throughItem())) {
                        new StaffUtils().throughWall(event.getPlayer(), event.getClickedBlock().getLocation());
                    }
                }
            }
        }
    }

    @EventHandler
    public void quickPunishmentsEvent(PlayerInteractEvent event) {
        if(HashMaps.staffMode.containsKey(event.getPlayer())) {
            if(event.getPlayer().getItemInHand().equals(new StaffModeInventory().quickPunishmentItem())) {
                event.getPlayer().sendMessage(Main.prefix + new MessageUtils().color("&cThis item is in-development, and is coming soon."));
            }
        }
    }
}
