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

import java.math.BigDecimal;
import java.math.RoundingMode;
import org.bukkit.Material;

/**
 * @description Useful tools
 * @author Tagette
 */
public class ITools {
    
    public static double round(double value, int decimals) {
        BigDecimal bd = new BigDecimal(value).setScale(decimals, RoundingMode.HALF_EVEN);
        return bd.doubleValue();
    }

    public static boolean isInt(String i) {
        boolean is = false;
        try {
            Integer.parseInt(i);
            is = true;
        } catch (Exception e) {
        }
        return is;
    }

    public static boolean isFloat(String i) {
        boolean is = false;
        try {
            Float.parseFloat(i);
            is = true;
        } catch (Exception e) {
        }
        return is;
    }

    // Gets the Material from bukkit enum
    public static Material getMatByName(String name) {
        Material mat = null;
        if (isInt(name)) {
            mat = getMatById(Integer.parseInt(name));
        } else {
            mat = Material.getMaterial(getMatID(name));
        }
        return mat;
    }

    // Gets the Material from ID
    public static Material getMatById(int id) {
        return Material.getMaterial(id);
    }

    // Gets the id of a Material
    public static int getMatID(String name) {
        int matID = -1;
        Material[] mat = Material.values();
        int temp = 9999;
        Material tmp = null;
        for (Material m : mat) {
            if (m.name().toLowerCase().replaceAll("_", "").startsWith(name.toLowerCase().replaceAll("_", "").replaceAll(" ", ""))) {
                if (m.name().length() < temp) {
                    tmp = m;
                    temp = m.name().length();
                }
            }
        }
        if (tmp != null) {
            matID = tmp.getId();
        }
        return matID;
    }
    
    public static String join(String[] split, String delimiter) {
        String joined = "";
        for (String s : split) {
            joined += s + delimiter;
        }
        joined = joined.substring(0, joined.length() - (delimiter.length()));
        return joined;
    }
}
