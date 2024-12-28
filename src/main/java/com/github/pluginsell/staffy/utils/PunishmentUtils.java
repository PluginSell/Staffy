package com.github.pluginsell.staffy.utils;

import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Calendar;
import java.util.Date;

public class PunishmentUtils {
    public void banishTarget(Player target) {
        if(new PluginUtils().staffyPunishments()) {

        } else {
            Calendar calendar = Calendar.getInstance();
            BanList banList = Bukkit.getBanList(BanList.Type.NAME);
            calendar.add(Calendar.DAY_OF_MONTH, 30);
            Date date = calendar.getTime();
            banList.addBan(target.getName(), new MessageUtils().color("Leaving While Frozen"), date, "Staffy");

        }
    }

    public String getRemainder(Date now, Date expire) {
        long diff = expire.getTime() - now.getTime();

        long diffSeconds = diff / 1000 % 60;
        long diffMinutes = diff / (60 * 1000) % 60;
        long diffHours = diff / (60 * 60 * 1000) % 24;
        long diffDays = diff / (24 * 60 * 60 * 1000);


        String remaining = null;
        if (diffDays > 0) {
            remaining = new MessageUtils().color("&e" + diffDays + "&6 day(s)");
        }
        if (diffHours > 0) {
            if (remaining != null) {
                remaining = remaining + new MessageUtils().color(" &e" + diffHours + "&6 hour(s)");
            } else {
                remaining = new MessageUtils().color("&e" + diffHours + "&6 hour(s)");
            }
        }
        if (diffMinutes > 0) {
            if (remaining != null) {
                remaining = remaining + new MessageUtils().color(" &e" + diffMinutes + "&6 minute(s)");
            } else {
                remaining = new MessageUtils().color("&e" + diffMinutes + "&6 minute(s)");
            }
        }
        if (diffSeconds > 0) {
            if (remaining != null) {
                remaining = remaining + new MessageUtils().color(" &e" + diffSeconds + "&6 second(s)");
            } else {
                remaining = new MessageUtils().color("&e" + diffSeconds + "&6 second(s)");
            }
        }

        return remaining;
    }
}
