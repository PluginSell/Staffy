package com.github.pluginsell.staffy.events;

import com.github.pluginsell.staffy.Main;
import com.github.pluginsell.staffy.inventoryutils.StaffModeInventory;
import com.github.pluginsell.staffy.utils.HashMaps;
import com.github.pluginsell.staffy.utils.MessageUtils;
import com.github.pluginsell.staffy.utils.StaffUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class EntityDamageByEntityEvents implements Listener {
    @EventHandler
    public void playerAttackFrozenPlayerEvent(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player) {
            if (HashMaps.frozenPlayers.containsKey((Player) event.getEntity())) {
                event.setCancelled(true);
                if (event.getDamager() instanceof Player) {
                    if(!((Player) event.getDamager()).getItemInHand().equals(new StaffModeInventory().freezeItem()) || !((Player) event.getDamager()).getItemInHand().equals(new StaffModeInventory().quickPunishmentItem())) {
                        event.getDamager().sendMessage(Main.prefix + new MessageUtils().color("&cYou cannot attack a frozen player."));
                    }
                }
            }
        }
    }

    @EventHandler
    public void frozenPlayerAttackEntityEvent(EntityDamageByEntityEvent event) {
        if(event.getDamager() instanceof Player) {
            if(HashMaps.frozenPlayers.containsKey((Player) event.getDamager())) {
                event.setCancelled(true);
                if(event.getEntity() instanceof Player) {
                    event.getDamager().sendMessage(Main.prefix + new MessageUtils().color("&cYou cannot attack players while frozen."));
                } else {
                    event.getDamager().sendMessage(Main.prefix + new MessageUtils().color("&cYou cannot attack mobs while frozen."));
                }
            }
        }
    }

    @EventHandler
    public void staffFreezePlayerEvent(EntityDamageByEntityEvent event) {
        if(event.getDamager() instanceof Player) {
            if(HashMaps.staffMode.containsKey((Player) event.getDamager())) {
                if(((Player) event.getDamager()).getItemInHand().equals(new StaffModeInventory().freezeItem())) {
                    if(event.getEntity() instanceof Player) {
                        if(HashMaps.frozenPlayers.containsKey((Player) event.getEntity())) {
                            new StaffUtils().unfreezeTarget((Player) event.getEntity(), (Player) event.getDamager());
                        } else {
                            new StaffUtils().freezeTarget((Player) event.getEntity(), (Player) event.getDamager());
                        }
                    } else {
                        event.getDamager().sendMessage(Main.prefix + new MessageUtils().color("&cYou may only freeze players."));
                    }
                }
            }
        }
    }

    @EventHandler
    public void entityAttackStaffEvent(EntityDamageByEntityEvent event) {
        if(event.getEntity() instanceof Player) {
            if(HashMaps.staffMode.containsKey((Player) event.getEntity())) {
                event.setCancelled(true);
                if(!HashMaps.invisibleStaff.containsKey((Player) event.getEntity())) {
                    event.getDamager().sendMessage(Main.prefix + new MessageUtils().color("&cYou cannot attack a staff member in staff-mode."));
                }
            }
        }
    }

    @EventHandler
    public void staffArmorSeeEvent(EntityDamageByEntityEvent event) {
        if(event.getDamager() instanceof Player) {
            if(HashMaps.staffMode.containsKey((Player) event.getDamager())) {
                if(((Player) event.getDamager()).getInventory().getItemInHand().equals(new StaffModeInventory().inventorySeeItem())) {
                    if(event.getEntity() instanceof Player) {
                        if(!HashMaps.staffMode.containsKey((Player) event.getEntity())) {
                            new StaffUtils().inventorySeeTarget((Player) event.getEntity(), (Player) event.getDamager(), true);
                        }
                    }
                }
            }
        }
    }
}
