package com.github.pluginsell.staffy.utils;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Invite;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import org.bukkit.Bukkit;

public class PluginUtils {
    public static JDA jda = null;
    public static Guild guild = null;
    public static TextChannel management = null;
    public static TextChannel admin = null;
    public static TextChannel staff = null;
    public static TextChannel staffChat = null;
    public static TextChannel commandLog = null;
    public static Invite invite = null;

    public Invite checkInvite() {
        Invite checkInvite = null;
        if (guild != null) {
            for (Invite invites : guild.retrieveInvites().complete()) {
                if (invites.getInviter().getId().equals(jda.getSelfUser().getId())) {
                    checkInvite = invites;
                    break;
                }
            }
        }
        invite = checkInvite;
        return checkInvite;
    }

    public boolean staffyPunishments() {
        return Bukkit.getPluginManager().getPlugin("StaffyPunishments") != null && Bukkit.getPluginManager().isPluginEnabled("StaffyPunishments");
    }
}
