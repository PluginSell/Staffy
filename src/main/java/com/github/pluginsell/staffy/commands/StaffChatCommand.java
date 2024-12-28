package com.github.pluginsell.staffy.commands;

import com.github.pluginsell.staffy.utils.MessageUtils;
import com.github.pluginsell.staffy.utils.StaffUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static com.github.pluginsell.staffy.Main.prefix;
import static com.github.pluginsell.staffy.utils.HashMaps.staffChat;

public class StaffChatCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length >= 1) {
            StringBuilder builder = new StringBuilder();
            for (String arg: args) {
                builder.append(arg);
            }
            new StaffUtils().staffChat(sender, String.valueOf(builder), false);
        } else {
            if(!(sender instanceof Player)) return true;
            if(sender.hasPermission("staffy.staffchat")) {
                if(staffChat.containsKey(sender)) {
                    new StaffUtils().disableStaffChat((Player) sender);
                } else {
                    new StaffUtils().enableStaffChat((Player) sender);
                }
            } else {
                sender.sendMessage(prefix + new MessageUtils().color("&cYou do not have permission to access that command."));
            }
        }
        return true;
    }
}
