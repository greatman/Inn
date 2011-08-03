/**
 * 
 * Copyright 2011 Greatman (https://github.com/greatman)
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 * 
 */

package me.greatman.plugins.inn;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @description Handles the logging of the plugin
 * @author Tagette
 */
public class ILogger {

    private static Logger log;
    private static String prefix;

    public static void initialize(Logger newLog) {
        ILogger.log = newLog;
        prefix = "[" + Inn.name + " " + Inn.version + "] ";
    }

    public static Logger getLog() {
        return log;
    }

    public static String getPrefix() {
        return prefix;
    }

    public static void setPrefix(String prefix) {
        ILogger.prefix = prefix;
    }

    public static void info(String message) {
        log.info(prefix + message);
    }

    public static void error(String message) {
        log.severe(prefix + message);
    }
    public static void severe(String message) {
        log.severe(prefix + message);
    }

    public static void warning(String message) {
        log.warning(prefix + message);
    }

    public static void config(String message) {
        log.config(prefix + message);
    }

    public static void log(Level level, String message) {
        log.log(level, prefix + message);
    }
}
