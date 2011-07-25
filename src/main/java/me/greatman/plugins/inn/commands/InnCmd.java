package me.greatman.plugins.inn.commands;

import me.greatman.plugins.inn.IPermissions;
import me.greatman.plugins.inn.Inn;
import me.greatman.plugins.inn.PlayerData;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class InnCmd implements CommandExecutor {
	private final Inn plugin;
    public InnCmd(Inn instance) {
        plugin = instance;
    }
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    	boolean handled = false;
    	if (is(label, "inn")) {
    		if (args == null || args.length == 0){
    			handled = true;
    		}
    		if (is(args[0], "select")){
    			if (!(sender instanceof Player)){
    				sendMessage(sender,colorizeText("Only players can use this command.",ChatColor.RED));
    				return handled;
    			}
    			if (isPlayer(sender) && IPermissions.permission(plugin.getPlayer(sender), "inn.select", plugin.getPlayer(sender).isOp())){
    				Player player = (Player) sender;
    				String playerName = player.getName();
    				if (!plugin.getPlayerData().containsKey(playerName)) {
    	                plugin.getPlayerData().put(playerName, new PlayerData(plugin, playerName));
    	            }
    	            plugin.getPlayerData().get(playerName).setSelecting(!plugin.getPlayerData().get(playerName).isSelecting());

    	            if (plugin.getPlayerData().get(playerName).isSelecting()) {
    	                sender.sendMessage(ChatColor.WHITE + "Inn selection enabled." + ChatColor.DARK_AQUA + " Use " + ChatColor.WHITE + "bare hands " + ChatColor.DARK_AQUA + "to select!");
    	                sender.sendMessage(ChatColor.DARK_AQUA + "Left click to select the bottom corner for a shop");
    	                sender.sendMessage(ChatColor.DARK_AQUA + "Right click to select the far upper corner for the shop");
    	            } else {
    	                sender.sendMessage(ChatColor.DARK_AQUA + "Selection disabled");
    	                plugin.getPlayerData().put(playerName, new PlayerData(plugin, playerName));
    	            }
    			}else
    				sendMessage(sender,colorizeText("Permission denied.",ChatColor.RED));
    		}
    	}
    	return handled;
    }
 // Simplifies and shortens the if statements for commands.
    private boolean is(String entered, String label) {
        return entered.equalsIgnoreCase(label);
    }

    // Checks if the current user is actually a player.
    private boolean isPlayer(CommandSender sender) {
        return sender != null && sender instanceof Player;
    }

    // Checks if the current user is actually a player and sends a message to that player.
    private boolean sendMessage(CommandSender sender, String message) {
        boolean sent = false;
        if (isPlayer(sender)) {
            Player player = (Player) sender;
            player.sendMessage(message);
            sent = true;
        }
        return sent;
    }
    public String colorizeText(String text, ChatColor color) {
        return color + text + ChatColor.WHITE;
    }
}
