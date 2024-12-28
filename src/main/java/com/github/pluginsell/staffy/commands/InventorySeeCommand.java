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

public class InventorySeeCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            if (HashMaps.staffMode.containsKey(sender)) {
                if(args.length >= 1) {
                    if(Bukkit.getPlayer(args[0]) != null) {
                        if(!HashMaps.staffMode.containsKey(Bukkit.getPlayer(args[0]))) {
                            if (args.length >= 2 && args[1].equalsIgnoreCase("true")) {
                                new StaffUtils().inventorySeeTarget(Bukkit.getPlayer(args[0]), (Player) sender, true);
                            } else {
                                new StaffUtils().inventorySeeTarget(Bukkit.getPlayer(args[0]), (Player) sender, false);
                            }
                        } else {
                            sender.sendMessage(prefix + new MessageUtils().color("&cYou cannot view the inventories of staff members."));
                        }
                    } else {
                        sender.sendMessage(Main.prefix + new MessageUtils().color("&e" + args[0] + "&c is not online."));
                    }
                } else {
                    new StaffUtils().inventorySeeTarget((Player) sender, (Player) sender, false);
                }
            } else {
                sender.sendMessage(prefix + new MessageUtils().color("&cYou do not have permission to access that command."));
            }
        }
        return true;
    }
}
