package com.github.pluginsell.staffy.utils;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.awt.*;

import static com.github.pluginsell.staffy.Main.prefix;

public class MessageUtils {
    public String color(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public void messageLog(String string) {
        Bukkit.getServer().getConsoleSender().sendMessage(prefix + color(string));
    }

    public EmbedBuilder embed(String string, Color color) {
        return new EmbedBuilder().setDescription(string).setColor(color);
    }

    public EmbedBuilder embed(String title, String description, Color color) {
        return new EmbedBuilder().setTitle(title).setDescription(description).setColor(color);
    }

    public void send(TextChannel channel, String string) {
        channel.sendMessage(string).queue();
    }

    public void send(TextChannel channel, EmbedBuilder embed) {
        channel.sendMessageEmbeds(embed.build()).queue();
    }
}
