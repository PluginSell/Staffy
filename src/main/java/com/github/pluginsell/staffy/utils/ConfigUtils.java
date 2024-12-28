package com.github.pluginsell.staffy.utils;

import com.github.pluginsell.staffy.Main;

public class ConfigUtils {
    public String getString(String path) {
        return new MessageUtils().color(Main.data.getConfig().getString(path));
    }
    public boolean getBoolean(String path) {
        return Main.data.getConfig().getBoolean(path);
    }
}
