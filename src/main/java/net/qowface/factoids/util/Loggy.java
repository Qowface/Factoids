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
package net.qowface.factoids.util;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.plugin.Plugin;

public class Loggy {
    
    private Plugin plugin;
    private Logger log;
    private boolean debug;
    
    public Loggy(Plugin plugin) {
        this.plugin = plugin;
        log = plugin.getLogger();
        debug = plugin.getConfig().getBoolean("Config.Debug");
    }
    
    public void info(String msg) {
        log.log(Level.INFO, "{0}", msg);
    }
    
    public void warn(String msg) {
        log.log(Level.WARNING, "{0}", msg);
    }
    
    public void debug(String msg) {
        if (debug) {
            log.log(Level.INFO, "[Debug] {0}", msg);
        }
    }
    
}
