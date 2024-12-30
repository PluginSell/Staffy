package com.github.pluginsell.staffy.inventoryutils;

import com.github.pluginsell.staffy.utils.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class StaffModeInventory {
    public Inventory inventory() {
        Inventory inventory = Bukkit.createInventory(null, InventoryType.PLAYER);
        inventory.setItem(0, freezeItem());
        inventory.setItem(1, glassPane());
        inventory.setItem(2, inventorySeeItem());
        inventory.setItem(3, glassPane());
        inventory.setItem(4, randomTeleportItem());
        inventory.setItem(5, glassPane());
        inventory.setItem(6, quickPunishmentItem());
        inventory.setItem(7, glassPane());
        inventory.setItem(8, throughItem());
        return inventory;
    }

    public ItemStack glassPane() {
        ItemStack itemStack = new ItemStack(Material.STAINED_GLASS_PANE,1, (short) 7);
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(" ");
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    public ItemStack freezeItem() {
        ItemStack itemStack = new ItemStack(Material.ICE);
        ItemMeta meta = itemStack.getItemMeta();
        List<String> lore = new ArrayList<>();
        meta.setDisplayName(new MessageUtils().color("&b&lFreeze"));
        lore.add(new MessageUtils().color("&7Left-Click a player to freeze them"));
        lore.add(new MessageUtils().color("&7Right-Click a player to unfreeze them"));
        meta.setLore(lore);
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    public ItemStack inventorySeeItem() {
        ItemStack itemStack = new ItemStack(Material.BOOK);
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(new MessageUtils().color("&6&lInventory Viewer"));
        List<String> lore = new ArrayList<>();
        lore.add(new MessageUtils().color("&7Left-Click a player to view their armor"));
        lore.add(new MessageUtils().color("&7Right-Click a player to view their inventory"));
        meta.setLore(lore);
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    public ItemStack randomTeleportItem() {
        ItemStack itemStack = new ItemStack(Material.COMPASS);
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(new MessageUtils().color("&a&lRandom Teleport"));
        List<String> lore = new ArrayList<>();
        lore.add(new MessageUtils().color("&7Right-Click to be randomly teleported to a player"));
        meta.setLore(lore);
        itemStack.setItemMeta(meta);
        return itemStack;
    }
    public ItemStack quickPunishmentItem() {
        ItemStack itemStack = new ItemStack(Material.BARRIER);
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(new MessageUtils().color("&c&lQuick Punish"));
        List<String> lore = new ArrayList<>();
        lore.add(new MessageUtils().color("&7Right-Click a player to punish them"));
        meta.setLore(lore);
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    public ItemStack throughItem() {
        ItemStack itemStack = new ItemStack(Material.SHEARS);
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(new MessageUtils().color("&3&lThrough"));
        List<String> lore = new ArrayList<>();
        lore.add(new MessageUtils().color("&7Left-Click a block to teleport above it."));
        lore.add(new MessageUtils().color("&7Shift-Left-Click a block to teleport below it."));
        lore.add(new MessageUtils().color("&7Right-Click a block to teleport through it."));
        meta.setLore(lore);
        itemStack.setItemMeta(meta);
        return itemStack;
    }

}
