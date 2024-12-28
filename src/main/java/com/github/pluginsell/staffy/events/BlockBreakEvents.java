package com.github.pluginsell.staffy.events;

import com.github.pluginsell.staffy.utils.HashMaps;
import com.github.pluginsell.staffy.utils.StaffUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreakEvents implements Listener {
    @EventHandler
    public void staffModeBlockBreakEvent(BlockBreakEvent event) {
        if(HashMaps.staffMode.containsKey(event.getPlayer())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void frozenPlayerBlockBreakEvent(BlockBreakEvent event) {
        if(HashMaps.frozenPlayers.containsKey(event.getPlayer())) {
            event.setCancelled(true);
            new StaffUtils().freezeTarget(event.getPlayer(), null);
        }
    }
}
