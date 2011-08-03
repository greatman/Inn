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
package me.greatman.plugins.inn.extras;

import java.util.Hashtable;
import java.util.Map;
import me.greatman.plugins.inn.Inn;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 *
 * @author brenda
 */
public class CommandManager {

    private Map<String, CommandExecutor> commands = new Hashtable<String, CommandExecutor>();

    public CommandManager(Inn instance) {
    }

    public void addCommand(String label, CommandExecutor executor) {
        commands.put(label, executor);
    }

    public boolean dispatch(CommandSender sender, Command command, String label, String[] args) {
        if (!commands.containsKey(label)) {
            return false;
        }

        boolean handled = true;

        CommandExecutor ce = commands.get(label);
        handled = ce.onCommand(sender, command, label, args);

        return handled;
    }
}