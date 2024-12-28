package com.github.pluginsell.staffy;

import com.github.pluginsell.staffy.utils.ConfigManager;
import com.github.pluginsell.staffy.utils.Init;
import com.github.pluginsell.staffy.utils.MessageUtils;
import com.github.pluginsell.staffy.utils.StaffUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.awt.*;

import static com.github.pluginsell.staffy.utils.PluginUtils.jda;
import static com.github.pluginsell.staffy.utils.PluginUtils.management;
import static com.github.pluginsell.staffy.utils.HashMaps.frozenPlayers;
import static com.github.pluginsell.staffy.utils.HashMaps.staffMode;

public final class Main extends JavaPlugin {

    public static String prefix = new MessageUtils().color("&3Staffy &B> ");
    public static ConfigManager data;

    @Override
    public void onEnable() {
        Init.init();
        this.getServer().getConsoleSender().sendMessage(prefix + new MessageUtils().color("&aenabled"));
    }

    @Override
    public void onDisable() {
        loadConfigManager();
        for (Player player: staffMode.keySet()) {
            new StaffUtils().disableStaffMode(player);
        }
        for (Player player: frozenPlayers.keySet()) {
            new StaffUtils().unfreezeTarget(player, null);
        }
        if(management != null) {
            new MessageUtils().send(management, new MessageUtils().embed("Disconnected from Staffy.", Color.red));
        }
        jda.shutdownNow();
    }

    public static void loadConfigManager() {
        data = new ConfigManager(Main.getPlugin(Main.class));
        Bukkit.getPluginManager().getPlugin(Main.getPlugin(Main.class).getName()).getConfig().options().copyDefaults();
        Bukkit.getPluginManager().getPlugin(Main.getPlugin(Main.class).getName()).saveDefaultConfig();
    }
}
