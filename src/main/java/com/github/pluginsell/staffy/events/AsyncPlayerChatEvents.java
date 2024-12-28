package com.github.pluginsell.staffy.events;

import com.github.pluginsell.staffy.utils.HashMaps;
import com.github.pluginsell.staffy.utils.StaffUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import static com.github.pluginsell.staffy.utils.HashMaps.staffChat;

public class AsyncPlayerChatEvents implements Listener {
    @EventHandler
    public void staffChatEvent(AsyncPlayerChatEvent event) {
        if (staffChat.containsKey(event.getPlayer()) || (event.getPlayer().hasPermission("staffy.staffmode") && event.getMessage().startsWith("sc "))) {
            event.setCancelled(true);
            new StaffUtils().staffChat(event.getPlayer(), event.getMessage().replaceFirst("sc ", ""), false);
        }
    }

    @EventHandler
    public void frozenPlayerChatEvent(AsyncPlayerChatEvent event) {
        if(HashMaps.frozenPlayers.containsKey(event.getPlayer())) {
            event.setCancelled(true);
            new StaffUtils().staffChat(event.getPlayer(), event.getMessage(), true);
        }
    }
}
