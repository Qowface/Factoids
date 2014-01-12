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

/**
 * Represents a fact.
 * 
 * @author Qowface
 */
public class Factoid {
    
    private String id;
    private String title;
    private String[] messageLines;
    private Boolean showTitle;
    private Boolean showFrame;
    
    public Factoid(String id, String title, String[] messageLines, Boolean showTitle, Boolean showFrame) {
        this.id = id;
        this.title = title;
        this.messageLines = messageLines;
        this.showTitle = showTitle;
        this.showFrame = showFrame;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String[] getMessageLines() {
        return messageLines;
    }

    public void setMessageLines(String[] messageLines) {
        this.messageLines = messageLines;
    }

    public Boolean getShowTitle() {
        return showTitle;
    }

    public void setShowTitle(Boolean showTitle) {
        this.showTitle = showTitle;
    }

    public Boolean getShowFrame() {
        return showFrame;
    }

    public void setShowFrame(Boolean showFrame) {
        this.showFrame = showFrame;
    }
    
    public String getMessageString() {
        String message = "";
        boolean first = true;
        
        for (String string : getMessageLines()) {
            if (!first) {
                message += ";;";
            }
            message += string;
            first = false;
        }
        
        return message;
    }
    
}
