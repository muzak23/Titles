package com.muzak23.titles;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.regex.Pattern;

public final class Titles extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        System.out.println(ChatColor.GREEN + "Titles enabled!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        System.out.println(ChatColor.RED + "Titles disabled!");
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // String debugPrefix = "[" + ChatColor.BLUE + "D" + ChatColor.RESET + "] ";  // For debug messages so they stand out
        if (command.getName().equals("title")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (sender.hasPermission("titles.base")) {
                    switch (args.length) {
                        case 0:
                            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "lp user " + player.getName() + " meta removeprefix 1000");
                            player.sendMessage(ChatColor.GREEN + "Title removed.");
                            break;
                        case 1:
                            //player.sendMessage(debugPrefix + "Case 1");
                            if (args[0].length() <= 16) {
                                //player.sendMessage(debugPrefix + "less than 16 char");
                                if (!player.hasPermission("titles.admin")) {
                                    //player.sendMessage(debugPrefix + "Not admin");
                                    if (Pattern.matches("(&4A(dmin)?.*?)", args[0])) {
                                        player.sendMessage("The admin prefix is not allowed!");
                                    } else {
                                        if (!player.hasPermission("titles.mod")) {
                                            //player.sendMessage(debugPrefix + "Not mod");
                                            if (Pattern.matches("(.*&6M(od)?.*)", args[0])) {
                                                player.sendMessage("The moderator prefix is not allowed!");
                                            } else {
                                                if (!player.hasPermission("titles.rgb")) {
                                                    //player.sendMessage(debugPrefix + "No RGB");
                                                    if (Pattern.matches("(.*#[0-9a-fA-F*]{6}.*)", args[0])) {
                                                        player.sendMessage("Hex codes are not allowed in titles. Sorry!");
                                                    } else {  //  not a hex
                                                        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "lp user " + player.getName() + " meta removeprefix 1000");
                                                        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "lp user " + player.getName() + " meta addprefix 1000 &f[" + args[0] + "&f]");
                                                    }
                                                } else {  //  has titles.rgb
                                                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "lp user " + player.getName() + " meta removeprefix 1000");
                                                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "lp user " + player.getName() + " meta addprefix 1000 &f[" + args[0] + "&f]");
                                                }
                                            }
                                        } else {  //  has titles.mod
                                            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "lp user " + player.getName() + " meta removeprefix 1000");
                                            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "lp user " + player.getName() + " meta addprefix 1000 &f[" + args[0] + "&f]");
                                        }
                                    }
                                } else {  //  has titles.admin
                                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "lp user " + player.getName() + " meta removeprefix 1000");
                                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "lp user " + player.getName() + " meta addprefix 1000 &f[" + args[0] + "&f]");
                                }
                            } else {  //  Args[1] over 16 characters
                                player.sendMessage(ChatColor.RED + "Titles must be under 16 characters!");
                            }
                                break;
                        default:
                            player.sendMessage(ChatColor.RED + "Titles cannot have spaces.");
                            break;
                    }
                } else {  //  does not have titles.base
                    player.sendMessage("Donate $15 or more to change your chat title.");
                    }
            } else {  //  sender not a player
                System.out.println("You must be a player to use this command!");
            }
        }  // end of if command is /title
        return true;
    }  //  end of onCommand
}