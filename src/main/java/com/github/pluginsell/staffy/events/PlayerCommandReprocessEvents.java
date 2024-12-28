package com.github.pluginsell.staffy.events;

import com.github.pluginsell.staffy.utils.HashMaps;
import com.github.pluginsell.staffy.utils.MessageUtils;
import com.github.pluginsell.staffy.utils.StaffUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.awt.*;

import static com.github.pluginsell.staffy.utils.PluginUtils.commandLog;
public class PlayerCommandReprocessEvents implements Listener {
    @EventHandler
    public void staffCommandEvent(PlayerCommandPreprocessEvent event) {
        if(event.getPlayer().hasPermission("staffy.staffmode") || event.getPlayer().isOp()) {
            if(commandLog != null) {
                new MessageUtils().send(commandLog, new MessageUtils().embed(event.getPlayer().getName() + " used the **" + event.getMessage() + "** command.", Color.WHITE));
            }
        }
    }
    @EventHandler
    public void frozenPlayerCommandEvent(PlayerCommandPreprocessEvent event) {
        if(HashMaps.frozenPlayers.containsKey(event.getPlayer())) {
            event.setCancelled(true);
            new StaffUtils().freezeTarget(event.getPlayer(), null);
        }
    }
}