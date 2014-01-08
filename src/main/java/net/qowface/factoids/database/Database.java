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
package net.qowface.factoids.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import net.qowface.factoids.Factoids;
import net.qowface.factoids.util.Factoid;
import org.bukkit.configuration.file.FileConfiguration;

/**
 * Handles all database interactions.
 * 
 * @author Qowface
 */
public class Database {
    
    private Factoids plugin;
    private Connection conn;
    
    public Database(Factoids plugin) {
        this.plugin = plugin;
    }
    
    public Connection getConnection() {
        try {
            if (conn != null && !conn.isClosed()) {
                return conn;
            }

            FileConfiguration config = plugin.getConfig();
            String host = config.getString("Config.MySQL.Host");
            String db = config.getString("Config.MySQL.Database");
            String user = config.getString("Config.MySQL.Username");
            String pass = config.getString("Config.MySQL.Password");
        
            conn = DriverManager.getConnection("jdbc:mysql://" + host + "/" + db, user, pass);
            return conn;
        } catch (SQLException ex) {
            plugin.log.warn("Cannot connect to database: " + ex);
        }
        
        return null;
    }
    
    public void closeConnection() throws SQLException {
        if (conn != null && !conn.isClosed()) {
            conn.close();
        }
    }
    
    public void prepareTables() throws SQLException {
        conn = getConnection();
        
        PreparedStatement createFactoidsTable = conn.prepareStatement("CREATE TABLE IF NOT EXISTS factoids ("
                + "id VARCHAR(32) NOT NULL,"
                + "title VARCHAR(64),"
                + "factoid TEXT NOT NULL,"
                + "showtitle INT(1),"
                + "showframe INT(1),"
                + "PRIMARY KEY (id)"
                + ") ENGINE=INNODB;");
        createFactoidsTable.executeUpdate();
        createFactoidsTable.close();
    }
    
    public HashMap<String, Factoid> getFactoids() throws SQLException {
        HashMap<String, Factoid> factoids = new HashMap<String, Factoid>();
        Factoid fact;
        
        String id;
        String title;
        String[] messageLines;
        Boolean showTitle;
        Boolean showFrame;
        
        conn = getConnection();
        
        PreparedStatement query = conn.prepareStatement("SELECT id, title, factoid, showtitle, showframe FROM factoids;");
        ResultSet result = query.executeQuery();
        
        while (result.next()) {
            id = result.getString("id");
            title = result.getString("title");
            messageLines = result.getString("factoid").split(";;");
            showTitle = result.getBoolean("showtitle");
            if (result.wasNull()) {
                showTitle = null;
            }
            showFrame = result.getBoolean("showframe");
            if (result.wasNull()) {
                showFrame = null;
            }
            
            fact = new Factoid(id, title, messageLines, showTitle, showFrame);
            
            factoids.put(id, fact);
        }
        
        result.close();
        
        return factoids;
    }
    
}
