package com.github.pluginsell.staffy.events;

import com.github.pluginsell.staffy.utils.HashMaps;
import com.github.pluginsell.staffy.utils.StaffUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMoveEvents implements Listener {
    @EventHandler
    public void frozenPlayerMoveEvent(PlayerMoveEvent event) {
        if(HashMaps.frozenPlayers.containsKey(event.getPlayer())) {
            event.getPlayer().teleport(HashMaps.frozenPlayers.get(event.getPlayer()));
            new StaffUtils().freezeTarget(event.getPlayer(), null);
        }
    }
}
