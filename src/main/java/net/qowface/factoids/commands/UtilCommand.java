/* 
 * Factoids - A documentation and help plugin for Bukkit.
 * Copyright (C) 2013 Qowface (Robert Marquess)
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package net.qowface.factoids.commands;

import net.qowface.factoids.Factoids;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;

/**
 * Handles the factoids (utility) command.
 * 
 * @author Qowface
 */
public class UtilCommand extends AbstractCommand {

    public UtilCommand(Factoids plugin) {
        super(plugin);
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 1) {
            return false;
        }

        if (args[0].equalsIgnoreCase("about") || args[0].equalsIgnoreCase("-a")) {
            about(sender);
            return true;
        } else if (args[0].equalsIgnoreCase("reload") || args[0].equalsIgnoreCase("-r")) {
            reload(sender);
            return true;
        } else {
            return false;
        }
    }
    
    private void about(CommandSender sender) {
        if (!sender.hasPermission("factoids.util") && sender instanceof Player) {
            sender.sendMessage(ChatColor.RED + "You do not have permission to do that!");
            return;
        }
        
        String version = plugin.getDescription().getVersion();
        
        sender.sendMessage(ChatColor.AQUA + "" + ChatColor.BOLD + "Factoids v" + version + ChatColor.RESET + ChatColor.GOLD + " by Qowface");
        sender.sendMessage(ChatColor.GRAY + "Documentation and help plugin for Bukkit. Provide predefined");
        sender.sendMessage(ChatColor.GRAY + "facts to your players and stop repeating yourself.");
        sender.sendMessage(ChatColor.GREEN + "BukkitDev: " + ChatColor.LIGHT_PURPLE + "http://dev.bukkit.org/bukkit-plugins/factoids/");
        sender.sendMessage(ChatColor.GREEN + "GitHub: " + ChatColor.LIGHT_PURPLE + "https://github.com/Qowface/Factoids/");
    }
    
    private void reload(CommandSender sender) {
        if (!sender.hasPermission("factoids.util") && sender instanceof Player) {
            sender.sendMessage(ChatColor.RED + "You do not have permission to do that!");
            return;
        }
        
        PluginManager manager = plugin.getServer().getPluginManager();
        
        sender.sendMessage(ChatColor.GREEN + "Reloading Factoids...");
        
        plugin.reloadConfig();
        manager.disablePlugin(plugin);
        manager.enablePlugin(plugin);
        
        sender.sendMessage(ChatColor.GREEN + "Factoids Reloaded!");
    }
    
}
