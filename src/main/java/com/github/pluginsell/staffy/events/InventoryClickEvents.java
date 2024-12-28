package com.github.pluginsell.staffy.events;

import com.github.pluginsell.staffy.inventoryutils.ArmorSeeInventory;
import com.github.pluginsell.staffy.inventoryutils.StaffModeInventory;
import com.github.pluginsell.staffy.utils.HashMaps;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

public class InventoryClickEvents implements Listener {
    @EventHandler
    public void staffModeInventoryClickEvent(InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player) {
            if (HashMaps.staffMode.containsKey((Player) event.getWhoClicked())) {
                if(event.getClick() == ClickType.NUMBER_KEY) event.setCancelled(true);
                if (event.getCurrentItem().equals(new StaffModeInventory().glassPane())) {
                    event.setCancelled(true);
                } else if (event.getCurrentItem().equals(new StaffModeInventory().freezeItem())) {
                    event.setCancelled(true);
                } else if (event.getCurrentItem().equals(new StaffModeInventory().inventorySeeItem())) {
                    event.setCancelled(true);
                } else if (event.getCurrentItem().equals(new StaffModeInventory().randomTeleportItem())) {
                    event.setCancelled(true);
                } else if (event.getCurrentItem().equals(new StaffModeInventory().quickPunishmentItem())) {
                    event.setCancelled(true);
                } else if (event.getCurrentItem().equals(new StaffModeInventory().throughItem())) {
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void staffArmorSeeInventoryClickEvent(InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player) {
            if (HashMaps.staffMode.containsKey((Player) event.getWhoClicked())) {
                if (HashMaps.inventorySee.containsKey((Player) event.getWhoClicked())) {
                    if (event.getClickedInventory().getTitle().equals(new ArmorSeeInventory().inventory(HashMaps.inventorySee.get((Player) event.getWhoClicked())).getTitle())) {
                        if(event.getClick() == ClickType.NUMBER_KEY) event.setCancelled(true);
                        if (event.getCurrentItem().equals(new StaffModeInventory().glassPane())) {
                            event.setCancelled(true);
                        } else {
                            if (!event.getClickedInventory().getType().equals(InventoryType.PLAYER)) {
                                Player target = HashMaps.inventorySee.get((Player) event.getWhoClicked());
                                ItemStack itemStack = new ItemStack(Material.AIR);
                                if (event.getCursor() != null) {
                                    itemStack = event.getCursor();
                                }
                                if(event.getSlot() == 1) {
                                    target.getInventory().setHelmet(itemStack);
                                } else if(event.getSlot() == 3) {
                                    target.getInventory().setChestplate(itemStack);
                                } else if(event.getSlot() == 5) {
                                    target.getInventory().setLeggings(itemStack);
                                } else if(event.getSlot() == 7) {
                                    target.getInventory().setBoots(itemStack);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}