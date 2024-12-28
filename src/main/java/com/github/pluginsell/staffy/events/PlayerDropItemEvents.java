package com.github.pluginsell.staffy.events;

import com.github.pluginsell.staffy.utils.HashMaps;
import com.github.pluginsell.staffy.utils.StaffUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class PlayerDropItemEvents implements Listener {
    @EventHandler
    public void frozenPlayerDropItemEvent(PlayerDropItemEvent event) {
        if(HashMaps.frozenPlayers.containsKey(event.getPlayer())) {
            event.setCancelled(true);
            new StaffUtils().freezeTarget(event.getPlayer(), null);
        }
    }

    @EventHandler
    public void staffDropItemEvent(PlayerDropItemEvent event) {
        if(HashMaps.staffMode.containsKey(event.getPlayer())) {
            event.setCancelled(true);
        }
    }
}
