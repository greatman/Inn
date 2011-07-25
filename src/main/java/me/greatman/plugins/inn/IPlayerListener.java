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

    public IPlayerListener(Inn instance) {
        plugin = instance;
    }
    public void onPlayerEnterBed(PlayerBedEnterEvent event){
    	
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
        if (plugin.getPlayerData().get(playerName).isSelecting() && player.getItemInHand().getType() == Material.AIR) {
            int x, y, z;
            Location loc = event.getClickedBlock().getLocation();
            x = loc.getBlockX();
            y = loc.getBlockY();
            z = loc.getBlockZ();
            
            PlayerData pData = plugin.getPlayerData().get(playerName);
            
            if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
                int[] xyz = { x, y, z };
                pData.setPositionA(xyz);
                if(pData.checkSize()) {
                    player.sendMessage(ChatColor.DARK_AQUA + "First Position " + ChatColor.LIGHT_PURPLE + x + " " + y + " " + z + ChatColor.DARK_AQUA + " size " + ChatColor.LIGHT_PURPLE + plugin.getPlayerData().get(playerName).getSizeString());
                } else {
                    player.sendMessage(ChatColor.DARK_AQUA + "First Position " + ChatColor.LIGHT_PURPLE + x + " " + y + " " + z);
                }
                
                if(pData.getPositionA() != null && pData.getPositionB() == null) {
                    player.sendMessage(ChatColor.DARK_AQUA + "Now, right click to select the far upper corner for the inn.");
                } else if(pData.getPositionA() != null && pData.getPositionB() != null) {
                    player.sendMessage(ChatColor.DARK_AQUA + "Type " + ChatColor.WHITE + "/inn create [Inn Name]" + ChatColor.DARK_AQUA + ", if you're happy with your selection, otherwise keep selecting!");
                }
            } else if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                int[] xyz = { x, y, z };
                pData.setPositionB(xyz);
                if(pData.checkSize()) {
                    player.sendMessage(ChatColor.DARK_AQUA + "Second Position " + ChatColor.LIGHT_PURPLE + x + " " + y + " " + z + ChatColor.DARK_AQUA + " size " + ChatColor.LIGHT_PURPLE + plugin.getPlayerData().get(playerName).getSizeString());
                } else {
                    player.sendMessage(ChatColor.DARK_AQUA + "Second Position " + ChatColor.LIGHT_PURPLE + x + " " + y + " " + z);
                }
                
                if(pData.getPositionB() != null && pData.getPositionA() == null) {
                    player.sendMessage(ChatColor.DARK_AQUA + "Now, left click to select the bottom corner for a shop.");
                } else if(pData.getPositionA() != null && pData.getPositionB() != null) {
                    player.sendMessage(ChatColor.DARK_AQUA + "Type " + ChatColor.WHITE + "/inn create [Inn Name]" + ChatColor.DARK_AQUA + ", if you're happy with your selection, otherwise keep selecting!");
                }
            }
        }

    }
}
