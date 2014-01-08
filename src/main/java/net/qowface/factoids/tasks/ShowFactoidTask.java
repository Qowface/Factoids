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
package net.qowface.factoids.tasks;

import net.qowface.factoids.Factoids;
import net.qowface.factoids.util.Factoid;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Task to display a fact to a player.
 * 
 * @author Qowface
 */
public class ShowFactoidTask extends BukkitRunnable {

    private Factoids plugin;
    private CommandSender target;
    private String factoidID;
    
    public ShowFactoidTask(Factoids plugin, CommandSender target, String factoidID) {
        this.plugin = plugin;
        this.target = target;
        this.factoidID = factoidID;
    }
    
    public void run() {
        plugin.log.debug("Running ShowFactoidTask (Target = " + target.getName() + ", Factoid = " + factoidID + ") [" + getTaskId() + "]");
        
        Factoid fact = plugin.getLoadedFactoids().get(factoidID);
        
        String factoidFormat = plugin.getConfig().getString("Config.Format.Factoid");
        String titleFormat = plugin.getConfig().getString("Config.Format.Title");
        String borderFormat = plugin.getConfig().getString("Config.Format.Border");
        boolean showTitle = plugin.getConfig().getBoolean("Config.Settings.ShowTitle");
        boolean showFrame = plugin.getConfig().getBoolean("Config.Settings.ShowFrame");
        
        if (fact.getShowTitle() != null) {
            showTitle = fact.getShowTitle();
        }
        if (fact.getShowFrame() != null) {
            showFrame = fact.getShowFrame();
        }
        
        String title = fact.getTitle();
        if (title == null) {
            title = fact.getId();
        }
        
        String border = "-----------------------------------------------------"; //53
        
        if (showFrame) {
            target.sendMessage(ChatColor.translateAlternateColorCodes('&', borderFormat + border));
        }
        if (showTitle) {
            target.sendMessage(ChatColor.translateAlternateColorCodes('&', titleFormat + title));
            if (showFrame) {
                target.sendMessage(ChatColor.translateAlternateColorCodes('&', borderFormat + border));
            }
        }
        
        for (String message : fact.getMessageLines()) {
            target.sendMessage(ChatColor.translateAlternateColorCodes('&', factoidFormat + message));
        }
        
        if (showFrame) {
            target.sendMessage(ChatColor.translateAlternateColorCodes('&', borderFormat + border));
        }
        
        plugin.log.debug("ShowFactoidTask Completed (Target = " + target.getName() + ", Factoid = " + factoidID + ") [" + getTaskId() + "]");
    }
    
}
