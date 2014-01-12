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
package net.qowface.factoids;

import java.sql.SQLException;
import java.util.HashMap;
import net.qowface.factoids.commands.FactCommand;
import net.qowface.factoids.commands.UtilCommand;
import net.qowface.factoids.database.Database;
import net.qowface.factoids.util.Factoid;
import net.qowface.factoids.util.Loggy;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Main class.
 * 
 * @author Qowface
 */
public class Factoids extends JavaPlugin {
    
    public Loggy log;
    private Database db;
    
    private HashMap<String, Factoid> loadedFactoids = new HashMap<String, Factoid>();
    
    @Override
    public void onEnable() {
        // Setup config
        if (getConfig().options().header() == null) {
            getConfig().options().copyHeader();
            getConfig().options().copyDefaults(true);
            saveConfig();
        }
        
        // Initialize logger
        log = new Loggy(this);
        
        // Initialize database
        db = new Database(this);
        try {
            db.prepareTables();
        } catch (SQLException e) {
            log.warn("Could not prepare tables: " + e.toString());
        }
        
        // Load factoids from database
        try {
            loadedFactoids = db.getFactoids();
        } catch (SQLException e) {
            log.warn("Could not load factoids from database: " + e.toString());
        }
        
        // Register commands
        this.getCommand("fact").setExecutor(new FactCommand(this));
        this.getCommand("factoids").setExecutor(new UtilCommand(this));
        
        log.info("Enabled");
    }
    
    @Override
    public void onDisable() {
        try {
            db.closeConnection();
        } catch (SQLException e) {
            log.warn("Could not close connection: " + e.toString());
        }
        loadedFactoids.clear();
        
        log.info("Disabled");
    }
    
    public HashMap<String, Factoid> getLoadedFactoids() {
        return loadedFactoids;
    }
    
    public Database getDB() {
        return db;
    }
    
}
