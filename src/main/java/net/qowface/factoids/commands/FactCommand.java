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
import net.qowface.factoids.tasks.ShowFactoidTask;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

public class FactCommand extends AbstractCommand {

    public FactCommand(Factoids plugin) {
        super(plugin);
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0 && !config.getString("Config.Presets.Default").isEmpty()) {
            return viewFactoid(sender, config.getString("Config.Presets.Default"));
        } else if (args.length == 1) {
            return viewFactoid(sender, args[0]);
        } else if (args.length == 2) {
            if (args[0].equals("*")) {
                return broadcastFactoid(sender, args[1]);
            } else {
                return sendFactoid(sender, args[0], args[1]);
            }
        } else {
            return false;
        }
    }
    
    public boolean viewFactoid(CommandSender sender, String factoidID) {
        if (!sender.hasPermission("factoids.view") && sender instanceof Player) {
            sender.sendMessage(ChatColor.RED + "You do not have permission to view factoids!");
            return true;
        }
        
        if (!plugin.getLoadedFactoids().containsKey(factoidID)) {
            sender.sendMessage(ChatColor.RED + "No factoid found!");
            return true;
        }
        
        showFactoidTo(sender, factoidID);
        
        return true;
    }
    
    public boolean sendFactoid(CommandSender sender, String target, String factoidID) {
        if (!sender.hasPermission("factoids.send") && sender instanceof Player) {
            sender.sendMessage(ChatColor.RED + "You do not have permission to send factoids!");
            return true;
        }
        
        Player player = plugin.getServer().getPlayer(target);
        
        if (player == null) {
            sender.sendMessage(ChatColor.RED + "Player " + target + " not found!");
            return true;
        }
        
        if (!plugin.getLoadedFactoids().containsKey(factoidID)) {
            sender.sendMessage(ChatColor.RED + "No factoid found!");
            return true;
        }
        
        showFactoidTo(player, factoidID);
        sender.sendMessage(ChatColor.GREEN + "Factoid " + factoidID + " sent to " + player.getName() + "!");
        
        return true;
    }
    
    public boolean broadcastFactoid(CommandSender sender, String factoidID) {
        if (!sender.hasPermission("factoids.broadcast") && sender instanceof Player) {
            sender.sendMessage(ChatColor.RED + "You do not have permission to broadcast factoids!");
            return true;
        }
        
        if (!plugin.getLoadedFactoids().containsKey(factoidID)) {
            sender.sendMessage(ChatColor.RED + "No factoid found!");
            return true;
        }
        
        int targets = showFactoidAll(factoidID);
        sender.sendMessage(ChatColor.GREEN + "Factoid " + factoidID + " sent to " + targets + " players!");
        
        return true;
    }
    
    public void showFactoidTo(CommandSender target, String factoidID) {
        BukkitTask task = new ShowFactoidTask(plugin, target, factoidID).runTask(plugin);
    }
    
    public int showFactoidAll(String factoidID) {
        Player[] players = plugin.getServer().getOnlinePlayers();
        
        for (Player player : players) {
            showFactoidTo(player, factoidID);
        }
        
        return players.length;
    }
    
}
