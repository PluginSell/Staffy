package com.github.pluginsell.staffy.utils;


import com.github.pluginsell.staffy.Main;
import com.github.pluginsell.staffy.commands.*;
import com.github.pluginsell.staffy.events.*;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Invite;
import net.dv8tion.jda.api.entities.channel.Channel;
import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.awt.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import static com.github.pluginsell.staffy.Main.loadConfigManager;
import static com.github.pluginsell.staffy.Main.prefix;

public class Init {
    JavaPlugin plugin = Main.getPlugin(Main.class);

    List<Listener> successfulListeners = new ArrayList<>();
    List<Listener> failedListeners = new ArrayList<>();
    List<String> successfulCommands = new ArrayList<>();
    List<String> failedCommands = new ArrayList<>();

    public static void init() {
        new MessageUtils().messageLog("&aStarting Up &bPluginSell&a's &3Staffy&a Plugin.");
        new Init().checkStaffyPunishments();
        new Init().loadConfig();
        new Init().registerEvents();
        new Init().registerCommands();
        new Init().connectJDA();
        new Init().getGuild();
        new Init().managementChannel();
        new Init().adminChannel();
        new Init().staffChannel();
        new Init().staffChatChannel();
        new Init().commandChannel();
        new Init().generateInviteLink();
        Timers.second();
    }
    public void checkStaffyPunishments() {
        new MessageUtils().messageLog("&6Checking Plugins for StaffyPunishments...");
        if(new PluginUtils().staffyPunishments()) {
            new MessageUtils().messageLog("&aConnected Staffy to StaffyPunishments.");
        } else {
            new MessageUtils().messageLog("&cCould not connect Staffy to StaffyPunishments. StaffyPunishments is not enabled.");
        }
    }

    public void loadConfig() {
        new MessageUtils().messageLog("&6Loading Config...");
        loadConfigManager();
        new MessageUtils().messageLog("&aLoaded Config.");
    }

    public void connectJDA() {
        new MessageUtils().messageLog("&6Connecting Staffy to Discord...");
        JDA jda = null;
        try {
            jda = JDABuilder.createDefault(new ConfigUtils().getString("discord.bot-token")).build().awaitReady();
        } catch (InterruptedException | IllegalArgumentException e) {
        }
        if (jda == null) {
            new MessageUtils().messageLog("&cCould not connect Staffy to Discord. An invalid Token was used.");
        } else {
            new MessageUtils().messageLog("&aConnected Staffy to Discord.");
            PluginUtils.jda = jda;
        }
    }

    public void getGuild() {
        JDA jda = PluginUtils.jda;
        Guild guild = null;
        if (jda != null) {
            new MessageUtils().messageLog("&6Getting Discord Server...");
            try {
                guild = jda.getGuildById(new ConfigUtils().getString("discord.guild-id"));
            } catch (IllegalArgumentException | NullPointerException e) {
            }
            if (guild != null) {
                PluginUtils.guild = guild;
                new MessageUtils().messageLog("&aConnected to Discord Server.");
            } else {
                new MessageUtils().messageLog("&cCould not find Discord Server. An invalid Guild ID was used.");
            }
        }
    }

    public void managementChannel() {
        JDA jda = PluginUtils.jda;
        Guild guild = PluginUtils.guild;
        String channelID;
        TextChannel channel = null;
        if (guild != null) {
            new MessageUtils().messageLog("&6Getting Management Channel...");
            try {
                channelID = new ConfigUtils().getString("discord.management.channel-id");
                if (jda.getGuildChannelById(channelID) != null) {
                    if (jda.getGuildChannelById(channelID).getType().equals(ChannelType.TEXT)) {
                        channel = guild.getChannelById(TextChannel.class, channelID);
                        PluginUtils.management = channel;
                    } else {
                        new MessageUtils().messageLog("&cCould not connect to Management Channel. That Channel is not a TEXT Channel.");
                        return;
                    }
                }
            } catch (IllegalArgumentException | NullPointerException e) {
            }
            if (channel != null) {
                try {
                    if (Bukkit.getIp().isEmpty()) {
                        new MessageUtils().send(channel, new MessageUtils().embed("Connected Staffy to " + InetAddress.getLocalHost().getHostAddress(), Color.GREEN));
                    } else {
                        new MessageUtils().send(channel, new MessageUtils().embed("Connected Staffy to " + Bukkit.getIp(), Color.GREEN));
                    }
                } catch (UnknownHostException e) {
                    new MessageUtils().send(channel, new MessageUtils().embed("Unable to fetch IP Address", Color.red));
                    new MessageUtils().send(channel, new MessageUtils().embed("Connected Staffy to Server", Color.GREEN));
                }
                new MessageUtils().messageLog("&aConnected Staffy to Management Channel, &e" + channel.getName() + "&a.");
            } else {
                new MessageUtils().messageLog("&cCould not find Management Channel. An invalid Channel ID was used.");
            }
        }
    }

    public void adminChannel() {
        JDA jda = PluginUtils.jda;
        Guild guild = PluginUtils.guild;
        String channelID;
        TextChannel channel = null;
        if (guild != null) {
            new MessageUtils().messageLog("&6Getting Admin Channel...");
            try {
                channelID = new ConfigUtils().getString("discord.admin.channel-id");
                if (jda.getGuildChannelById(channelID) != null) {
                    if (jda.getGuildChannelById(channelID).getType().equals(ChannelType.TEXT)) {
                        channel = guild.getChannelById(TextChannel.class, channelID);
                        PluginUtils.admin = channel;
                    } else {
                        new MessageUtils().messageLog("&cCould not connect to Admin Channel. That Channel is not a TEXT Channel.");
                        return;
                    }
                }
            } catch (IllegalArgumentException | NullPointerException e) {
            }
            if (channel != null) {
                new MessageUtils().send(channel, new MessageUtils().embed("Admin-Alerts will be logged here.", Color.GREEN));
                new MessageUtils().messageLog("&aConnected Staffy to Admin Channel, &e" + channel.getName() + "&a.");
            } else {
                new MessageUtils().messageLog("&cCould not find Admin Channel. An invalid Channel ID was used.");
            }
        }
    }

    public void staffChannel() {
        JDA jda = PluginUtils.jda;
        Guild guild = PluginUtils.guild;
        String channelID;
        TextChannel channel = null;
        if (guild != null) {
            new MessageUtils().messageLog("&6Getting Staff Channel...");
            try {
                channelID = new ConfigUtils().getString("discord.staff.channel-id");
                if (jda.getGuildChannelById(channelID) != null) {
                    if (jda.getGuildChannelById(channelID).getType().equals(ChannelType.TEXT)) {
                        channel = guild.getChannelById(TextChannel.class, channelID);
                        PluginUtils.staff = channel;
                    } else {
                        new MessageUtils().messageLog("&cCould not connect to Staff Channel. That Channel is not a TEXT Channel.");
                        return;
                    }
                }
            } catch (IllegalArgumentException | NullPointerException e) {
            }
            if (channel != null) {
                new MessageUtils().send(channel, new MessageUtils().embed("Staff-Alerts will be logged here.", Color.GREEN));
                new MessageUtils().messageLog("&aConnected Staffy to Staff Channel, &e" + channel.getName() + "&a.");
            } else {
                new MessageUtils().messageLog("&cCould not find Staff Channel. An invalid Channel ID was used.");
            }
        }
    }


    public void staffChatChannel() {
        JDA jda = PluginUtils.jda;
        Guild guild = PluginUtils.guild;
        String channelID;
        TextChannel channel = null;
        if (guild != null) {
            new MessageUtils().messageLog("&6Getting Staff-Chat Channel...");
            try {
                channelID = new ConfigUtils().getString("discord.staff-chat.channel-id");
                if (jda.getGuildChannelById(channelID) != null) {
                    if (jda.getGuildChannelById(channelID).getType().equals(ChannelType.TEXT)) {
                        channel = guild.getChannelById(TextChannel.class, channelID);
                        PluginUtils.staffChat = channel;
                    } else {
                        new MessageUtils().messageLog("&cCould not connect to Staff-Chat Channel. That Channel is not a TEXT Channel.");
                        return;
                    }
                }
            } catch (IllegalArgumentException | NullPointerException e) {
            }
            if (channel != null) {
                new MessageUtils().send(channel, new MessageUtils().embed("Staff-Chat will be logged here.", Color.GREEN));
                new MessageUtils().messageLog("&aConnected Staffy to Staff-Chat Channel, &e" + channel.getName() + "&a.");
            } else {
                new MessageUtils().messageLog("&cCould not find Staff-Chat Channel. An invalid Channel ID was used.");
            }
        }
    }

    public void commandChannel() {
        JDA jda = PluginUtils.jda;
        Guild guild = PluginUtils.guild;
        String channelID;
        TextChannel channel = null;
        if (guild != null) {
            new MessageUtils().messageLog("&6Getting Command-Log Channel...");
            try {
                channelID = new ConfigUtils().getString("discord.command-log.channel-id");
                if (jda.getGuildChannelById(channelID) != null) {
                    if (jda.getGuildChannelById(channelID).getType().equals(ChannelType.TEXT)) {
                        channel = guild.getChannelById(TextChannel.class, channelID);
                        PluginUtils.commandLog = channel;
                    } else {
                        new MessageUtils().messageLog("&cCould not connect to Command-Log Channel. That Channel is not a TEXT Channel.");
                        return;
                    }
                }
            } catch (IllegalArgumentException | NullPointerException e) {
            }
            if (channel != null) {
                new MessageUtils().send(channel, new MessageUtils().embed("Commands will be logged here.", Color.GREEN));
                new MessageUtils().messageLog("&aConnected Staffy to Command-Log Channel, &e" + channel.getName() + "&a.");
            } else {
                new MessageUtils().messageLog("&cCould not find Command-Log Channel. An invalid Channel ID was used.");
            }
        }
    }

    public Invite getInvite() {
        Guild guild = PluginUtils.guild;
        Invite invite = null;
        if(guild != null) {
            new MessageUtils().messageLog("&6Getting Invite Links...");
            invite = new PluginUtils().checkInvite();
        }
        if(invite != null) {
            new MessageUtils().messageLog("&aInvite Link found.");
            PluginUtils.invite = invite;
        }
        return invite;
    }

    public void generateInviteLink() {
        Guild guild = PluginUtils.guild;
        Invite invite = getInvite();
        if(guild != null) {
            if(invite == null) {
                new MessageUtils().messageLog("&cCould not find Invite Link.");
                new MessageUtils().messageLog("&6Generating Invite Link...");
                for (Channel channel : guild.getChannels()) {
                    if(channel.getType().equals(ChannelType.TEXT)) {
                        TextChannel textChannel = (TextChannel) channel;
                        textChannel.createInvite().queue();
                        getInvite();
                        return;
                    }
                }
            }


        }
    }

    public void registerEvents() {
        new MessageUtils().messageLog("&6Registering Events...");

        enableListener(new AsyncPlayerChatEvents());
        enableListener(new BlockBreakEvents());
        enableListener(new BlockPlaceEvents());
        enableListener(new EntityDamageByEntityEvents());
        enableListener(new EntityDamageEvents());
        enableListener(new EntityTargetLivingEntityEvents());
        enableListener(new InventoryClickEvents());
        enableListener(new PlayerCommandReprocessEvents());
        enableListener(new PlayerDropItemEvents());
        enableListener(new PlayerInteractEntityEvents());
        enableListener(new PlayerInteractEvents());
        enableListener(new PlayerJoinEvents());
        enableListener(new PlayerLoginEvents());
        enableListener(new PlayerMoveEvents());
        enableListener(new PlayerQuitEvents());

        int count = successfulListeners.size();
        if(!failedListeners.isEmpty()) {
            count = failedListeners.size() + successfulListeners.size();
            Bukkit.getConsoleSender().sendMessage(prefix + new MessageUtils().color("&e&l" + failedListeners.size() + "&c&l/&e&l" + count + "&c&l events failed to register. Unable to enable plugin. Stopping server..."));
        } else {
            Bukkit.getConsoleSender().sendMessage(prefix + new MessageUtils().color("&e&l" + count + "&a&l/&e&l" + count + "&a&l events registered."));
        }

    }
    public void enableListener(Listener listener) {
        PluginManager pm = Bukkit.getServer().getPluginManager();
        try {
            pm.registerEvents(listener, plugin);
            String event = listener.getClass().getName().replaceAll("com.github.pluginsell.staffy.events.", "");
            Bukkit.getConsoleSender().sendMessage(prefix + new MessageUtils().color("&a&lRegistered &e&l" + event));
            successfulListeners.add(listener);
        } catch(NullPointerException err) {
            Bukkit.getConsoleSender().sendMessage(prefix + new MessageUtils().color("&c&lFailed to register &e&l" + listener));
            failedListeners.add(listener);
        }
    }

    public void registerCommands() {
        Bukkit.getConsoleSender().sendMessage(prefix + new MessageUtils().color("&a&lRegistering Commands..."));

        enableCommand("freeze", new FreezeCommand());
        enableCommand("inventorysee", new InventorySeeCommand());
        enableCommand("randomteleport", new RandomTeleportCommand());
        enableCommand("staffchat", new StaffChatCommand());
        enableCommand("staffmode", new StaffCommand());
        enableCommand("unfreeze", new UnfreezeCommand());
        enableCommand("vanish", new VanishCommand());

        int count = successfulCommands.size();
        if(!failedCommands.isEmpty()) {
            count = failedCommands.size() + successfulCommands.size();
            Bukkit.getConsoleSender().sendMessage(prefix + new MessageUtils().color("&e&l" + failedCommands.size() + "&c&l/&e&l" + count + "&c&l commands failed to register. Unable to enable plugin. Stopping server..."));
        } else {
            Bukkit.getConsoleSender().sendMessage(prefix + new MessageUtils().color("&e&l" + count + "&a&l/&e&l" + count + "&a&l commands registered."));
        }
    }
    public void enableCommand(String command, CommandExecutor commandExecutor) {
        try {
            Bukkit.getPluginCommand(command).setExecutor(commandExecutor);
            if(plugin.getCommand(command) == null) {
                Bukkit.getConsoleSender().sendMessage(prefix + new MessageUtils().color("&c&lFailed to register &e&l" + command + " &c&lCommand."));
                failedCommands.add(command);
            } else {
                Bukkit.getConsoleSender().sendMessage(prefix + new MessageUtils().color("&a&lRegistered &e&l" + command + " &a&lCommand."));
                successfulCommands.add(command);
            }
        } catch(NullPointerException err) {
            Bukkit.getConsoleSender().sendMessage(prefix + new MessageUtils().color("&c&lFailed to register &e&l" + command + " &c&lCommand."));
            failedCommands.add(command);
        }
    }
}
