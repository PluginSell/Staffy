package com.github.pluginsell.staffy.events;

import com.github.pluginsell.staffy.utils.MessageUtils;
import com.github.pluginsell.staffy.utils.StaffUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.awt.*;

import static com.github.pluginsell.staffy.utils.PluginUtils.*;

public class PlayerJoinEvents implements Listener {
    @EventHandler
    public void staffJoinEvent(PlayerJoinEvent event) {
        if(event.getPlayer().hasPermission("staffy.staffmode") || event.getPlayer().isOp()) {
            if(staff != null) {
                new MessageUtils().send(staff, new MessageUtils().embed(event.getPlayer().getName() + " has joined the server.", Color.GREEN));
            }
        }
        if(event.getPlayer().hasPermission("staffy.staffmode.onjoin") || event.getPlayer().isOp()) {
            new StaffUtils().enableStaffMode(event.getPlayer());
        }
    }
}
