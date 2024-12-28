package com.github.pluginsell.staffy.events;

import com.github.pluginsell.staffy.utils.HashMaps;
import com.github.pluginsell.staffy.utils.StaffUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockPlaceEvents implements Listener {
    @EventHandler
    public void staffModeBlockPlaceEvent(BlockPlaceEvent event) {
        if(HashMaps.staffMode.containsKey(event.getPlayer())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void frozenPlayerBlockPlaceEvent(BlockPlaceEvent event) {
        if(HashMaps.frozenPlayers.containsKey(event.getPlayer())) {
            event.setCancelled(true);
            new StaffUtils().freezeTarget(event.getPlayer(), null);
        }
    }
}
