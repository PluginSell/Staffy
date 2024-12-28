package com.github.pluginsell.staffy.events;

import com.github.pluginsell.staffy.utils.HashMaps;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;

public class EntityTargetLivingEntityEvents implements Listener {
    @EventHandler
    public void entityTargetStaffModeEvent(EntityTargetLivingEntityEvent event) {
        if(!(event.getEntity() instanceof Player)) {
            if(event.getTarget() instanceof Player) {
                if(HashMaps.invisibleStaff.containsKey((Player) event.getTarget())) {
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void entityTargetFrozenPlayerEvent(EntityTargetLivingEntityEvent event) {
        if(!(event.getEntity() instanceof Player)) {
            if(event.getTarget() instanceof Player) {
                if(HashMaps.frozenPlayers.containsKey((Player) event.getTarget())) {
                    event.setCancelled(true);
                }
            }
        }
    }
}
