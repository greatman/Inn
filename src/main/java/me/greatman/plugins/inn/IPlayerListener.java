package me.greatman.plugins.inn;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerListener;

import com.nijikokun.register.payment.Method.MethodAccount;

public class IPlayerListener extends PlayerListener {
	private final Inn plugin;
    public IPlayerListener(Inn instance) {
        plugin = instance;
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
            	
            	
            	if (Inn.doorAlreadyExists(x,y,z)){
            		player.sendMessage(ChatColor.RED + "This door is already registered!");
            		return;
            	}
                int[] xyz = { x, y, z };
                pData.setPositionA(xyz);
                player.sendMessage(ChatColor.DARK_AQUA + "Door selected");
                
	            if(pData.getPositionA() != null) {
	                   player.sendMessage(ChatColor.DARK_AQUA + "Type " + ChatColor.WHITE + "/inn create [Price]" + ChatColor.DARK_AQUA + ", if you're happy with your selection, otherwise keep selecting!");
	            }
                
        
            }
        //Are we trying to open a door?
        }else if (event.getClickedBlock().getType() == Material.WOODEN_DOOR){
        	if (IPermissions.permission(player, "inn.bypass", player.isOp()))
        			return;
        	int x, y, z;
        	ILogger.info("Where");
            Location loc = event.getClickedBlock().getLocation();
            x = loc.getBlockX();
            y = loc.getBlockY();
            z = loc.getBlockZ();
            ILogger.info("Where1 X:" + x + " Y:" + y + " Z:" + z);
            if (Inn.doorAlreadyExists(x,y,z)){
            	String owner = Inn.getOwner(x,y,z);
            	ILogger.info("Where2" + owner + playerName);
            	if (owner == playerName)
            		return;
            	ILogger.info("Where3");
            	int price = Inn.getDoorPrice(x,y,z);
            	
            	ILogger.info("Where4");
            	MethodAccount playerAccount = plugin.Method.getAccount(playerName);
            	if (playerAccount.hasEnough(price)){
            		ILogger.info("Where5");
            		playerAccount.subtract(price);
            		MethodAccount playerAccount2 = plugin.Method.getAccount(owner);
            		playerAccount2.add(price);
            		ILogger.info("Where6");
            		player.sendMessage(ChatColor.DARK_AQUA + "You are entering " + owner + " inn room");
            	}else
            		event.setCancelled(true);
            }
        }
    }
}
