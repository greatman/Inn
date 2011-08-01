package me.greatman.plugins.inn;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;


import org.bukkit.entity.Player;

public class PlayerData {
    // Objects
    private Inn plugin = null;

    // Attributes
    public List<UUID> shopList = Collections.synchronizedList(new ArrayList<UUID>());
    public String playerName = null;
    private boolean isSelecting = false;
    private int xyzA[] = null;
    private int xyzB[] = null;
    protected String size = "";
    
    // Logging
    private static final Logger log = Logger.getLogger("Minecraft");    

    // Constructor
    public PlayerData(Inn plugin, String playerName) {
        this.plugin = plugin;
        this.playerName = playerName;
    }

    public int[] getPositionA() {
        return xyzA;
    }

    public int[] getPositionB() {
        return xyzB;
    }

    public void setPositionA(int[] xyz) {
        xyzA = xyz.clone();
    }

    public void setPositionB(int[] xyz) {
        xyzB = xyz.clone();
    }

    public String getSizeString() {
        return size;
    }
    /*public boolean addPlayerToShop(Shop shop) {
        String playerWorld = plugin.getServer().getPlayer(playerName).getWorld().getName();

        if (!playerIsInShop(shop) && shop.getWorld().equalsIgnoreCase(playerWorld)) {
            shopList.add(shop.getUuid());
            return true;
        } else {
            return false;
        }
    }

    public boolean playerIsInShop(Shop shop) {
        String playerWorld = plugin.getServer().getPlayer(playerName).getWorld().getName();

        if (shopList.contains(shop.getUuid())) {
            if (shop.getWorld().equalsIgnoreCase(playerWorld)) {
                return true;
            }
        }
        return false;
    }

    public void removePlayerFromShop(Player player, UUID uuid) {
        shopList.remove(uuid);
    }

    public List<UUID> playerShopsList(String playerName) {
        return shopList;
    }


    public double getBalance(String playerName) {
        EconomyResponse balanceResp = plugin.getEconManager().getBalance(playerName);
        return balanceResp.amount;
    }
    
    public UUID getCurrentShop() {
        if(shopList.size() == 1) {
            return shopList.get(0);
        } else {
            return null;
        }
    }*/

    public void setSelecting(boolean isSelecting) {
        this.isSelecting = isSelecting;
    }

    public boolean isSelecting() {
        return isSelecting;
    }

}
