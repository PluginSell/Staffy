package com.github.pluginsell.staffy.utils;

import com.github.pluginsell.staffy.inventoryutils.ArmorSeeInventory;
import com.github.pluginsell.staffy.inventoryutils.StaffModeInventory;
import org.bukkit.*;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

import static com.github.pluginsell.staffy.Main.prefix;
import static com.github.pluginsell.staffy.utils.PluginUtils.admin;
import static com.github.pluginsell.staffy.utils.HashMaps.*;

public class StaffUtils {
    public void enableStaffMode(Player target) {
        target.setAllowFlight(true);
        vanishPlayer(target);
        enableStaffChat(target);
        staffMode.put(target, target.getInventory().getContents());
        staffModeLocation.put(target, target.getLocation());
        target.getInventory().setContents(new StaffModeInventory().inventory().getContents());
        if (admin != null) {
            new MessageUtils().send(admin, new MessageUtils().embed(target.getName() + " has went into Staff-Mode.", Color.PINK));
        }

    }

    public void disableStaffMode(Player target) {
        if (!target.getGameMode().equals(GameMode.CREATIVE) || !target.getGameMode().equals(GameMode.SPECTATOR)) {
            target.setAllowFlight(false);
        }
        unvanishPlayer(target);
        disableStaffChat(target);
        target.getInventory().clear();
        target.getInventory().setContents(staffMode.get(target));
        target.setFallDistance(0f);
        target.teleport(staffModeLocation.get(target));
        staffMode.remove(target);
        if (admin != null) {
            new MessageUtils().send(admin, new MessageUtils().embed(target.getName() + " has left Staff-Mode.", Color.PINK));
        }
    }

    public void enableStaffChat(Player target) {
        staffChat.put(target, null);
        target.sendMessage(new MessageUtils().color(prefix + "&aYou have enabled staff-chat."));
        if (admin != null) {
            new MessageUtils().send(admin, new MessageUtils().embed(target.getName() + " has enabled Staff-Chat.", Color.PINK));
        }
    }

    public void disableStaffChat(Player target) {
        staffChat.remove(target, null);
        target.sendMessage(new MessageUtils().color(prefix + "&cYou have disabled staff-chat."));
        if (admin != null) {
            new MessageUtils().send(admin, new MessageUtils().embed(target.getName() + " has disabled Staff-Chat.", Color.PINK));
        }
    }

    public void vanishPlayer(Player target) {
        invisibleStaff.put(target, checkPerm(target));
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (invisibleStaff.containsKey(player)) {
                if (checkPerm(target) > checkPerm(player)) {
                    player.hidePlayer(target);
                    target.showPlayer(player);
                }
            } else {
                player.hidePlayer(target);
            }
        }
        target.sendMessage(new MessageUtils().color(prefix + "&aYou are now invisible."));
        if (admin != null) {
            new MessageUtils().send(admin, new MessageUtils().embed(target.getName() + " has enabled Vanish.", Color.PINK));
        }
    }

    public void unvanishPlayer(Player target) {
        invisibleStaff.remove(target);
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.showPlayer(target);
            if (invisibleStaff.containsKey(player)) {
                target.hidePlayer(player);
            }
        }
        target.sendMessage(new MessageUtils().color(prefix + "&cYou are no longer invisible."));
        if (admin != null) {
            new MessageUtils().send(admin, new MessageUtils().embed(target.getName() + " has disabled Vanish.", Color.PINK));
        }
    }

    public int checkPerm(Player target) {
        int perm = 0;
        if (target.hasPermission("staffy.staffmode.management")) {
            perm = 3;
        } else if (target.hasPermission("staffy.staffmode.admin")) {
            perm = 2;
        } else if (target.hasPermission("staffy.staffmode")) {
            perm = 1;
        }
        return perm;
    }

    public void staffChat(Player player, String message, boolean frozen) {
        if (PluginUtils.staffChat != null) {
            if (frozen) {
                new MessageUtils().send(PluginUtils.staffChat, new MessageUtils().embed("[FROZEN] " + player.getName() + " > " + message, Color.BLUE));
            } else {
                new MessageUtils().send(PluginUtils.staffChat, new MessageUtils().embed("[3] " + player.getName() + " > " + message, Color.BLUE));
            }
        }
        for (Player target : Bukkit.getOnlinePlayers()) {
            if (target.hasPermission("staffy.staffmode")) {
                if (frozen) {
                    target.sendMessage(new MessageUtils().color(" &3&lFROZEN &b" + player.getName() + " &3> &a" + message));
                } else {
                    target.sendMessage(new MessageUtils().color(" &3&lSTAFFCHAT &b" + player.getName() + " &3> &a" + message));
                }
            }
        }
        if (frozen) {
            player.sendMessage(new MessageUtils().color(" &3&lFROZEN &b" + player.getName() + " &3> &a" + message));
        }
    }

    public void staffChat(CommandSender sender, String message, boolean frozen) {
        if (PluginUtils.staffChat != null) {
            if (frozen) {
                new MessageUtils().send(PluginUtils.staffChat, new MessageUtils().embed("[FROZEN] " + sender.getName() + " > " + message, Color.BLUE));
            } else {
                new MessageUtils().send(PluginUtils.staffChat, new MessageUtils().embed("[3] " + sender.getName() + " > " + message, Color.BLUE));
            }
        }
        for (Player target : Bukkit.getOnlinePlayers()) {
            if (target.hasPermission("staffy.staffmode")) {
                if (frozen) {
                    target.sendMessage(new MessageUtils().color(" &3&lFROZEN &b" + sender.getName() + " &3> &a" + message));
                } else {
                    target.sendMessage(new MessageUtils().color(" &3&lSTAFFCHAT &b" + sender.getName() + " &3> &a" + message));
                }
            }
        }
        if (frozen) {
            sender.sendMessage(new MessageUtils().color(" &3&lFROZEN &b" + sender.getName() + " &3> &a" + message));
        }
    }

    public void staffHelpCooldown() {
        for (Player player : staffHelp.keySet()) {
            if (staffHelp.get(player).equals(0)) {
                staffHelp.remove(player);
            } else {
                staffHelp.put(player, staffHelp.get(player) - 1);
            }
        }
    }

    public void freezeTarget(Player target, Player staff) {
        if (PluginUtils.admin != null) {
            if (staff != null) {
                new MessageUtils().send(PluginUtils.admin, new MessageUtils().embed(staff.getName() + " has frozen " + target.getName(), Color.PINK));
            }
        }
        if (new PluginUtils().checkInvite() == null) {
            new Init().generateInviteLink();
        }
        frozenPlayers.put(target, target.getLocation());
        target.playSound(target.getLocation(), Sound.ANVIL_LAND, 1, 1);
        target.sendMessage(new MessageUtils().color("&3&l&n_____________________________________________"));
        target.sendMessage(" ");
        if (staff != null) {
            new MessageUtils().send(PluginUtils.staff, new MessageUtils().embed(target.getName() + " has been frozen by " + staff.getName(), Color.RED));
            staff.sendMessage(prefix + new MessageUtils().color("&aYou have frozen &e" + target.getName()));
            target.sendMessage(new MessageUtils().color("          &c&lYOU HAVE BEEN &b&lFROZEN &c&lBY &e&l" + staff.getName()));
        } else {
            target.sendMessage(new MessageUtils().color("                      &c&lYOU HAVE BEEN &b&lFROZEN"));
        }
        target.sendMessage(new MessageUtils().color("&3&l&n_____________________________________________"));
        target.sendMessage(" ");
        target.sendMessage(new MessageUtils().color("      &4&lLOGGING OUT OR REFUSING TO COOPERATE WILL "));
        target.sendMessage(new MessageUtils().color("      &4&lRESULT IN A 30 DAY BAN. "));
        target.sendMessage(" ");
        target.sendMessage(new MessageUtils().color("      &c&lJoin a support room, and wait to be moved "));
        target.sendMessage(new MessageUtils().color("      &c&lby staff: &9&l" + PluginUtils.invite.getUrl()));
        target.sendMessage(new MessageUtils().color("&3&l&n_____________________________________________"));
    }

    public void unfreezeTarget(Player target, Player staff) {
        if (PluginUtils.admin != null) {
            if (staff != null) {
                new MessageUtils().send(PluginUtils.admin, new MessageUtils().embed(staff.getName() + " has unfrozen " + target.getName(), Color.PINK));
            }
        }
        frozenPlayers.remove(target);
        target.playSound(target.getLocation(), Sound.ANVIL_BREAK, 1, 1);
        target.sendMessage(new MessageUtils().color("&3&l&n_____________________________________________"));
        target.sendMessage(" ");
        if (staff != null) {
            new MessageUtils().send(PluginUtils.staff, new MessageUtils().embed(target.getName() + " has been unfrozen by " + staff.getName(), Color.GREEN));
            staff.sendMessage(prefix + new MessageUtils().color("&aYou have unfrozen &e" + target.getName()));
            target.sendMessage(new MessageUtils().color("         &a&lYOU HAVE BEEN &b&lUNFROZEN &a&lBY &e&l" + staff.getName()));
        } else {
            new MessageUtils().send(PluginUtils.staff, new MessageUtils().embed(target.getName() + " has been unfrozen", Color.GREEN));
            target.sendMessage(new MessageUtils().color("         &a&lYOU HAVE BEEN &b&lUNFROZEN"));
        }
        target.sendMessage(new MessageUtils().color("&3&l&n_____________________________________________"));
    }

    public void randomTeleportStaff(Player staff) {
        ArrayList<Player> onlinePlayers = new ArrayList<>();
        if (staffMode.size() < Bukkit.getOnlinePlayers().size() && Bukkit.getOnlinePlayers().size() - 1 > 0) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (!staffMode.containsKey(player)) {
                    onlinePlayers.add(player);
                }
            }
            Player target = onlinePlayers.get(new Random().nextInt(onlinePlayers.size()));
            staff.teleport(target);
            if (admin != null) {
                new MessageUtils().send(PluginUtils.admin, new MessageUtils().embed(staff.getName() + " has randomly teleported to " + target.getName(), Color.PINK));
            }
        } else {
            staff.sendMessage(prefix + new MessageUtils().color("&cThere is no one online to teleport to."));
        }
    }

    public void inventorySeeTarget(Player target, Player staff, boolean armorContents) {
        if (admin != null) {
            if (armorContents) {
                new MessageUtils().send(PluginUtils.admin, new MessageUtils().embed(staff.getName() + " has opened " + target.getName() + "'s Armor Contents", Color.PINK));
            } else {
                new MessageUtils().send(PluginUtils.admin, new MessageUtils().embed(staff.getName() + " has opened " + target.getName() + "'s Inventory", Color.PINK));
            }
        }
        inventorySee.put(staff, target);
        if (armorContents) {
            staff.openInventory(new ArmorSeeInventory().inventory(target));
        } else {
            staff.openInventory(target.getInventory());
        }
    }

    public void throughWall(Player staff, Location clickLocation) {
        String direction;
        clickLocation.setY(staff.getLocation().getBlockY());
        clickLocation.setYaw(staff.getLocation().getYaw());
        clickLocation.setPitch(staff.getLocation().getPitch());
        int x = clickLocation.getBlockX();
        int y = clickLocation.getBlockY();
        int z = clickLocation.getBlockZ();
        double yaw = (staff.getLocation().getYaw() - 90) % 360;
        if (yaw < 0) {
            yaw += 360.0;
        }
        if (44.9 < yaw && yaw < 135.1) {
            direction = "N";
        } else if (134.9 < yaw && yaw < 225.1) {
            direction = "E";
        } else if (224.9 < yaw && yaw < 315.1) {
            direction = "S";
        } else {
            direction = "W";
        }
        for (int i = 0; i < 100; i++) {
            clickLocation.setX(x);
            clickLocation.setY(y);
            clickLocation.setZ(z);
            if (direction.equals("N")) {
                clickLocation.setZ(z - i);
                if (clickLocation.getBlock().getType().equals(Material.AIR)) {
                    clickLocation.setY(clickLocation.getBlockY() + 1);
                    if (clickLocation.getBlock().getType().equals(Material.AIR)) {
                        clickLocation.setY(clickLocation.getBlockY() - 1);
                        staff.teleport(clickLocation);
                        break;
                    }
                }
            } else if (direction.equals("E")) {
                clickLocation.setX(x + i);
                if (clickLocation.getBlock().getType().equals(Material.AIR)) {
                    clickLocation.setY(clickLocation.getBlockY() + 1);
                    if (clickLocation.getBlock().getType().equals(Material.AIR)) {
                        clickLocation.setY(clickLocation.getBlockY() - 1);
                        staff.teleport(clickLocation);
                        break;
                    }
                }
            } else if (direction.equals("S")) {
                clickLocation.setZ(z + i);
                if (clickLocation.getBlock().getType().equals(Material.AIR)) {
                    clickLocation.setY(clickLocation.getBlockY() + 1);
                    if (clickLocation.getBlock().getType().equals(Material.AIR)) {
                        clickLocation.setY(clickLocation.getBlockY() - 1);
                        staff.teleport(clickLocation);
                        break;
                    }
                }
            } else if (direction.equals("W")) {
                clickLocation.setX(x - i);
                if (clickLocation.getBlock().getType().equals(Material.AIR)) {
                    clickLocation.setY(clickLocation.getBlockY() + 1);
                    if (clickLocation.getBlock().getType().equals(Material.AIR)) {
                        clickLocation.setY(clickLocation.getBlockY() - 1);
                        staff.teleport(clickLocation);
                        break;
                    }
                }
            }
        }
    }

    public void throughTop(Player staff, Location clickLocation) {
        clickLocation.setYaw(staff.getLocation().getYaw());
        clickLocation.setPitch(staff.getLocation().getPitch());
        int y = clickLocation.getBlockY();
        for (int i = 0; i < 256; i++) {
            if (!staff.isSneaking()) {
                clickLocation.setY(y + i);
                if (clickLocation.getBlock().getType().equals(Material.AIR)) {
                    clickLocation.setY(clickLocation.getBlockY() + 1);
                    if (clickLocation.getBlock().getType().equals(Material.AIR)) {
                        clickLocation.setY(clickLocation.getBlockY() - 1);
                        staff.teleport(clickLocation);
                        break;
                    }
                }
            } else {
                clickLocation.setY(y - i);
                if (clickLocation.getBlock().getType().equals(Material.AIR)) {
                    clickLocation.setY(clickLocation.getBlockY() - 1);
                    if (clickLocation.getBlock().getType().equals(Material.AIR)) {
                        if (clickLocation.getBlockY() > 2) {
                            staff.teleport(clickLocation);
                        }
                        break;
                    }
                }
            }
        }
    }
}
