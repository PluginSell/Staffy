package com.github.pluginsell.staffy.inventoryutils;

import com.github.pluginsell.staffy.utils.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class ArmorSeeInventory {
    public Inventory inventory(Player target) {
        Inventory inventory = Bukkit.createInventory(null, 9, new MessageUtils().color("&6" + target.getName() + "&a's Armor Contents"));
        inventory.setItem(0, new StaffModeInventory().glassPane());
        inventory.setItem(1, target.getInventory().getHelmet());
        inventory.setItem(2, new StaffModeInventory().glassPane());
        inventory.setItem(3, target.getInventory().getChestplate());
        inventory.setItem(4, new StaffModeInventory().glassPane());
        inventory.setItem(5, target.getInventory().getLeggings());
        inventory.setItem(6, new StaffModeInventory().glassPane());
        inventory.setItem(7, target.getInventory().getBoots());
        inventory.setItem(8, new StaffModeInventory().glassPane());
        return inventory;
    }
}
