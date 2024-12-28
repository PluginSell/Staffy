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

import static com.github.pluginsell.staffy.Main.prefix;

public class UnfreezeCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            if (HashMaps.staffMode.containsKey((Player) sender)) {
                if(args.length >= 1) {
                    if(Bukkit.getPlayer(args[0]) != null) {
                        if(HashMaps.frozenPlayers.containsKey(Bukkit.getPlayer(args[0]))) {
                            new StaffUtils().unfreezeTarget(Bukkit.getPlayer(args[0]), (Player) sender);
                        } else {
                            sender.sendMessage(Main.prefix + new MessageUtils().color("&e" + Bukkit.getPlayer(args[0]).getName() + "&c is not frozen."));
                        }
                    } else {
                        sender.sendMessage(Main.prefix + new MessageUtils().color("&e" + args[0] + "&c is not online."));
                    }
                } else {
                    sender.sendMessage(Main.prefix + new MessageUtils().color("&cYou must specify a player."));
                }
            } else {
                sender.sendMessage(prefix + new MessageUtils().color("&cYou do not have permission to access that command."));
            }
        } else {
            if(args.length >= 1) {
                if(Bukkit.getPlayer(args[0]) != null) {
                    if(HashMaps.frozenPlayers.containsKey(Bukkit.getPlayer(args[0]))) {
                        new StaffUtils().unfreezeTarget(Bukkit.getPlayer(args[0]), null);
                    } else {
                        sender.sendMessage(Main.prefix + new MessageUtils().color("&e" + Bukkit.getPlayer(args[0]).getName() + "&c is not frozen."));
                    }
                } else {
                    sender.sendMessage(Main.prefix + new MessageUtils().color("&e" + args[0] + "&c is not online."));
                }
            } else {
                sender.sendMessage(Main.prefix + new MessageUtils().color("&cYou must specify a player."));
            }
        }
        return true;
    }
}
