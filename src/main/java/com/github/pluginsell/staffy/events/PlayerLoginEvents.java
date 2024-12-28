package com.github.pluginsell.staffy.events;

import com.github.pluginsell.staffy.utils.MessageUtils;
import com.github.pluginsell.staffy.utils.PunishmentUtils;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import java.util.Calendar;

public class PlayerLoginEvents implements Listener {
    @EventHandler
    public void bannedPlayerJoinEvent(PlayerLoginEvent event) {
        if (event.getResult().equals(PlayerLoginEvent.Result.KICK_BANNED)) {
            BanList banList = Bukkit.getBanList(BanList.Type.NAME);
            if (banList.isBanned(event.getPlayer().getName())) {
                if (banList.getBanEntry(event.getPlayer().getName()).getSource().equals("Staffy")) {
                    event.setKickMessage(new MessageUtils().color("&c&lYou have been banned by: &e" + banList.getBanEntry(event.getPlayer().getName()).getSource() + "&c&l.\n Reason: &e" + banList.getBanEntry(event.getPlayer().getName()).getReason() + "&c&l. \nExpires: &e" + new PunishmentUtils().getRemainder(Calendar.getInstance().getTime(), banList.getBanEntry(event.getPlayer().getName()).getExpiration()) + "&c&l."));
                }
            }
        }
    }
}
