/**
 * 
 * Copyright 2011 MilkBowl (https://github.com/MilkBowl)
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
 * Modified by Greatman (https://github.com/greatman)
 */

package me.greatman.plugins.inn;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;


public class PlayerData {
    // Objects

    // Attributes
    public List<UUID> shopList = Collections.synchronizedList(new ArrayList<UUID>());
    public String playerName = null;
    private boolean isSelecting = false;
    private int xyzA[] = null;
    private int xyzB[] = null;
    protected String size = "";
    
    // Logging 

    // Constructor
    public PlayerData(Inn plugin, String playerName) {
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
