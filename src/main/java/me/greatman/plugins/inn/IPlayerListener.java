package me.greatman.plugins.inn;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerListener;

public class IPlayerListener extends PlayerListener {
	private final Inn plugin;
	private IConfig IConfig;
    public IPlayerListener(Inn instance) {
        plugin = instance;
         IConfig = new IConfig(plugin);
    }
    @Override
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.isCancelled())
            return;

        Player player = event.getPlayer();
        String playerName = player.getName();
        if (!plugin.getPlayerData().containsKey(playerName)) {
            plugin.getPlayerData().put(playerName, new PlayerData(plugin, playerName));
        }

        // If our user is select & is not holding an item, selection time
        if (plugin.getPlayerData().get(playerName).isSelecting() && player.getItemInHand().getType() == Material.AIR && event.getClickedBlock().getType() == Material.WOODEN_DOOR) {
            int x, y, z;
            Location loc = event.getClickedBlock().getLocation();
            x = loc.getBlockX();
            y = loc.getBlockY();
            z = loc.getBlockZ();
            PlayerData pData = plugin.getPlayerData().get(playerName);
            
            if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
            	
            	for (int i=0;i < 2000;i++){
            		if (IConfig.readInteger("door."+ i + ".x", 0) == x && IConfig.readInteger("door."+ i + ".y", 0) == y && IConfig.readInteger("door."+ i + ".z", 0) == z){
            			player.sendMessage(ChatColor.RED + "This door is already registered!");
            			return;
            		}
            	}
                int[] xyz = { x, y, z };
                pData.setPositionA(xyz);
                player.sendMessage(ChatColor.DARK_AQUA + "Door selected");
                
	            if(pData.getPositionA() != null) {
	                   player.sendMessage(ChatColor.DARK_AQUA + "Type " + ChatColor.WHITE + "/inn create [Price]" + ChatColor.DARK_AQUA + ", if you're happy with your selection, otherwise keep selecting!");
	            }
                
        
            }
        }
    }
}
