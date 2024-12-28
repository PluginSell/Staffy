package com.github.pluginsell.staffy.commands;

import com.github.pluginsell.staffy.Main;
import com.github.pluginsell.staffy.utils.HashMaps;
import com.github.pluginsell.staffy.utils.MessageUtils;
import com.github.pluginsell.staffy.utils.StaffUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FreezeCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            if(HashMaps.staffMode.containsKey((Player) sender)) {
                if(args.length >= 1) {
                    if(Bukkit.getPlayer(args[0]) != null) {
                        if(!HashMaps.staffMode.containsKey(Bukkit.getPlayer(args[0]))) {
                            new StaffUtils().freezeTarget(Bukkit.getPlayer(args[0]), (Player) sender);
                        } else {
                            sender.sendMessage(Main.prefix + new MessageUtils().color("&cYou cannot freeze a staff member."));
                        }
                    } else {
                        sender.sendMessage(Main.prefix + new MessageUtils().color("&e" + args[0] + "&c is not online."));
                    }
                } else {
                    sender.sendMessage(Main.prefix + new MessageUtils().color("&cYou must specify a player."));
                }
            } else {
                sender.sendMessage(Main.prefix + new MessageUtils().color("&cYou do not have permission to access that command."));
            }
        }
        return true;
    }
}
