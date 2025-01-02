package com.github.pluginsell.staffy.commands;

import com.github.pluginsell.staffy.Main;
import com.github.pluginsell.staffy.utils.HashMaps;
import com.github.pluginsell.staffy.utils.MessageUtils;
import com.github.pluginsell.staffy.utils.StaffUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LockDownCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            if(HashMaps.staffMode.containsKey((Player) sender)) {
                new StaffUtils().lockDown((Player) sender);
            }
        }
        return true;
    }
}
