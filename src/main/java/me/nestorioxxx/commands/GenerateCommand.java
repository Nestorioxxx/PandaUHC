package me.nestorioxxx.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import me.nestorioxxx.world.WorldManager;

public class GenerateCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            if (label.equalsIgnoreCase("generate")) {
                if (sender.hasPermission("uhc.generate")) {
                    if (WorldManager.getInstance().uhcWorld != null) {
                        sender.sendMessage(ChatColor.RED + "A world has already been generated or is being generated!");
                    } else {
                        WorldManager.getInstance().generate();
                    }
                } else {
                    sender.sendMessage(ChatColor.RED + "You don't have permission to use that command!");
                }
            }
        } else {
            sender.sendMessage(ChatColor.RED + "You must be a player to use that command!");
        }
        return false;
    }
}
