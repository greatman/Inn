package me.greatman.plugins.inn;

import java.io.File;
import java.util.Arrays;
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
     loadkeys();
    }
    private void loadkeys(){
        ILogger.info("Loading Config File...");
        Inn.cost = readDouble("Inn.cost");
        for(int i=0;i<=200;i++){
        	if (readBoolean("door." + i + ".active")){
        		Inn.doorsx[i] = readInteger("door." + i + ".x",0);
            	Inn.doorsy[i] = readInteger("door." + i + ".y",0);
            	Inn.doorsz[i] = readInteger("door." + i + ".z",0);
            	Inn.owner[i] = readString("door." + i + ".owner");
            	Inn.price[i] = readInteger("door." + i + ".price",0);
            	ILogger.info(Arrays.toString(Inn.doorsx));
        	}else
        		break;
        	
        }
        
        }
}