package com.github.pluginsell.staffy.commands;

import com.github.pluginsell.staffy.utils.*;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.awt.*;

import static com.github.pluginsell.staffy.Main.prefix;

public class StaffCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) return true;
        if(sender.hasPermission("staffy.staffmode") || sender.isOp()) {
            if(HashMaps.staffMode.containsKey(sender)) {
                new StaffUtils().disableStaffMode((Player) sender);
            } else {
                new StaffUtils().enableStaffMode((Player) sender);
            }
        } else {
            if(HashMaps.staffHelp.containsKey(sender)) {
                sender.sendMessage(prefix + new MessageUtils().color("&cYou are on cooldown for &e" + HashMaps.staffHelp.get(sender) + "&c seconds."));
            } else {
                HashMaps.staffHelp.put((Player) sender, 60);
                sender.sendMessage(prefix + new MessageUtils().color("&aYou have requested a staff member."));
                for (Player target: Bukkit.getOnlinePlayers()) {
                    if(target.hasPermission("staffy.staffmode")) {
                        target.sendMessage(new MessageUtils().color(" &3&lALERT &b> &e" + sender.getName() + "&b needs staff assistance."));
                    }
                }
                if(PluginUtils.staff != null) {
                    new MessageUtils().send(PluginUtils.staff, new MessageUtils().embed(sender.getName() + " needs staff assistance.", Color.MAGENTA));
                    if(new ConfigUtils().getBoolean("discord.staff.mention")) {
                        new MessageUtils().send(PluginUtils.staff, "@here");
                    }
                }
            }
        }
        return true;
    }
}
