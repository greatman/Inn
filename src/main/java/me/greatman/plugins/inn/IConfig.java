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

import java.io.File;
import java.util.List;

import org.bukkit.util.config.Configuration;
/**
 * @description Contains all the functions for configuration file handling
 * @author greatman
 *
 */
public class IConfig {
    public IConfig(Inn instance) {
    }
    public String directory = "plugins" + File.separator + "Inn";
    File file = new File(directory + File.separator + "config.yml");
    public void configCheck(){
        
        new File(directory).mkdir();
        if(!file.exists()){
            try {
                file.createNewFile();
                addDefaults();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {

            loadkeys();
        }
    }
    public void write(String root, Object x){
        Configuration config = load();
        config.setProperty(root, x);
        config.save();
    }
    public Boolean readBoolean(String root){
        Configuration config = load();
        return config.getBoolean(root, false);
    }

    public Double readDouble(String root){
        Configuration config = load();
        return config.getDouble(root, 0);
    }
    public List<String> readStringList(String root){
        Configuration config = load();
        return config.getKeys(root);
    }
    public String readString(String root){
        Configuration config = load();
        return config.getString(root);
    }
    public int readInteger(String root,int def){
    	Configuration config = load();
    	return config.getInt(root, def);
    }
    private Configuration load(){

        try {
            Configuration config = new Configuration(file);
            config.load();
            return config;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    private void addDefaults(){
        ILogger.info("Generating Config File...");
        write("Inn.cost", 100.0);
        write("Inn.timeout", 10);
        write("Inn.autoclose",10);
        loadkeys();
    }
    private void loadkeys(){
        ILogger.info("Loading Config File...");
        Inn.cost = readDouble("Inn.cost");
        Inn.autoclose = readInteger("Inn.autoclose",10);
        Inn.timeout = readInteger("Inn.timeout", 10);
        }
}