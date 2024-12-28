package com.github.pluginsell.staffy.events;

import com.github.pluginsell.staffy.Main;
import com.github.pluginsell.staffy.inventoryutils.StaffModeInventory;
import com.github.pluginsell.staffy.utils.HashMaps;
import com.github.pluginsell.staffy.utils.MessageUtils;
import com.github.pluginsell.staffy.utils.StaffUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class PlayerInteractEntityEvents implements Listener {
    @EventHandler
    public void staffFreezePlayerEvent(PlayerInteractEntityEvent event) {
        if(event.getRightClicked() instanceof Player) {
            if(HashMaps.staffMode.containsKey(event.getPlayer())) {
                if(event.getPlayer().getItemInHand().equals(new StaffModeInventory().freezeItem())) {
                    event.setCancelled(true);
                    if(HashMaps.frozenPlayers.containsKey((Player) event.getRightClicked())) {
                        new StaffUtils().unfreezeTarget((Player) event.getRightClicked(), event.getPlayer());
                    } else {
                        event.getPlayer().sendMessage(Main.prefix + new MessageUtils().color("&e" + event.getRightClicked().getName() + "&c is not frozen."));
                    }
                }
            }
        }
    }

    @EventHandler
    public void staffInventorySeeEvent(PlayerInteractEntityEvent event) {
        if(event.getRightClicked() instanceof Player) {
            if(HashMaps.staffMode.containsKey(event.getPlayer())) {
                if(!HashMaps.staffMode.containsKey((Player) event.getRightClicked())) {
                    new StaffUtils().inventorySeeTarget((Player) event.getRightClicked(), event.getPlayer(), false);
                }
            }
        }
    }
}
