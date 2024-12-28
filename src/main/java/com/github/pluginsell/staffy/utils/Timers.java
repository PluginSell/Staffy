package com.github.pluginsell.staffy.utils;

import com.github.pluginsell.staffy.Main;
import org.bukkit.scheduler.BukkitRunnable;

public class Timers {
    public static void second() {
        new BukkitRunnable() {
            @Override
            public void run() {

                new StaffUtils().staffHelpCooldown();

            }
        }.runTaskTimer(Main.getPlugin(Main.class), 20L, 20L);
    }
}
