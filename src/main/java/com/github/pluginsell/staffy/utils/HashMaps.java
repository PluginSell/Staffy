package com.github.pluginsell.staffy.utils;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class HashMaps {
    public static HashMap<Player, Integer> invisibleStaff = new HashMap<>();
    public static HashMap<Player, ItemStack[]> staffMode = new HashMap<>();
    public static HashMap<Player, Location> staffModeLocation = new HashMap<>();
    public static HashMap<Player, String> staffChat = new HashMap<>();
    public static HashMap<Player, Location> frozenPlayers = new HashMap<>();
    public static HashMap<Player, Integer> staffHelp = new HashMap<>();
    public static HashMap<Player, Player> inventorySee = new HashMap<>();
}
