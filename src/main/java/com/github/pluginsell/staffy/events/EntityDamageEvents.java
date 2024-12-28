package com.github.pluginsell.staffy.events;

import com.github.pluginsell.staffy.utils.HashMaps;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class EntityDamageEvents implements Listener {
    @EventHandler
    public void staffModeDamageEvent(EntityDamageEvent event) {
        if(event.getEntity() instanceof Player) {
            if(HashMaps.staffMode.containsKey((Player) event.getEntity())) {
                event.setCancelled(true);
            }
        }
    }
}
