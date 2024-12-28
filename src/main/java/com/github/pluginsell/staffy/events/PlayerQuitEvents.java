package com.github.pluginsell.staffy.events;

import com.github.pluginsell.staffy.utils.HashMaps;
import com.github.pluginsell.staffy.utils.MessageUtils;
import com.github.pluginsell.staffy.utils.PunishmentUtils;
import com.github.pluginsell.staffy.utils.StaffUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.awt.*;

import static com.github.pluginsell.staffy.utils.PluginUtils.staff;

public class PlayerQuitEvents implements Listener {
    @EventHandler
    public void staffQuitEvent(PlayerQuitEvent event) {
        if (event.getPlayer().hasPermission("staffy.staffmode") || event.getPlayer().isOp()) {
            new StaffUtils().disableStaffMode(event.getPlayer());
            if (staff != null) {
                new MessageUtils().send(staff, new MessageUtils().embed(event.getPlayer().getName() + " has left the server.", Color.RED));
            }
        }
    }

    @EventHandler
    public void onFrozenPlayerQuitEvent(PlayerQuitEvent event) {
        if(HashMaps.frozenPlayers.containsKey(event.getPlayer())) {
            new StaffUtils().unfreezeTarget(event.getPlayer(), null);
            new PunishmentUtils().banishTarget(event.getPlayer());
        }
    }
}
