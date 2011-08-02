package me.greatman.plugins.inn;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import me.greatman.plugins.inn.commands.InnCmd;
import me.greatman.plugins.inn.extras.CommandManager;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.Event.Priority;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.config.Configuration;

import com.alta189.sqlLibrary.SQLite.sqlCore;
import com.nijikokun.register.payment.Method;

public class Inn extends JavaPlugin {
	public static String name,version;
	public Configuration config;
	public IConfig IConfig;
	private Map<String, PlayerData> playerData;
	private final CommandManager commandManager = new CommandManager(this);
	private final IPlayerListener playerListener = new IPlayerListener(this);
	private final IServerListener serverListener = new IServerListener(this);
	private final IBlockListener blockListener = new IBlockListener(this);
	public Method Method;
	public static double cost;
	public File pFolder = new File("plugins" + File.separator + "Inn");
	public static sqlCore manageSQLite; // SQLite handler
	public Logger log = Logger.getLogger("Minecraft");
	public String logPrefix = "[" + Inn.name + " " + Inn.version + "] ";
    public void onDisable() {
        // TODO: Place any custom disable code here.
    	manageSQLite.close();
        System.out.println(this + " is now disabled!");
    }

    public void onEnable() {
    	setPlayerData(Collections.synchronizedMap(new HashMap<String, PlayerData>()));
    	name = this.getDescription().getName();
		version = this.getDescription().getVersion();
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvent(Event.Type.PLAYER_BED_ENTER, playerListener, Priority.Lowest, this);
		pm.registerEvent(Event.Type.PLAYER_INTERACT, playerListener, Priority.Monitor,this);
		pm.registerEvent(Event.Type.PLAYER_MOVE,playerListener,Priority.Monitor,this);
		pm.registerEvent(Event.Type.PLUGIN_DISABLE, serverListener,Priority.Monitor,this);
		pm.registerEvent(Event.Type.PLUGIN_ENABLE, serverListener,Priority.Monitor,this);
		pm.registerEvent(Event.Type.BLOCK_BREAK, blockListener, Priority.Monitor, this);
		ILogger.initialize(Logger.getLogger("Minecraft"));
		IConfig IConfig = new IConfig(this);
		IConfig.configCheck();
		IPermissions.initialize(this);
		InnCmd InnCmd = new InnCmd(this);
		addCommand("inn",InnCmd);
	    
	    manageSQLite = new sqlCore(this.log, this.logPrefix, "Inn", pFolder.getPath());
	    manageSQLite.initialize();
	    String tableQuery;
	    if (!manageSQLite.checkTable("doors")) {
	    	ILogger.info("Creating Doors table");
        	 tableQuery = "CREATE TABLE doors ("
						+"id  INTEGER,"
						+"x  INTEGER,"
						+"y  INTEGER,"
						+"z  INTEGER,"
						+"owner  STRING,"
						+"price  INTEGER,"
						+"PRIMARY KEY (id ASC)"
						+");";
        	 if(manageSQLite.createTable(tableQuery))
        		 ILogger.info("Table doors created!");
        	 else
        		 ILogger.warning("Error while creating doors table");
	    }
	    ILogger.info("Initialized!");
	    
    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        return commandManager.dispatch(sender, cmd, label, args);
    }

    /*
     * Adds the specified command to the command manager and server.
     * 
     * @param command   The label of the command.
     * @param executor  The command class that excecutes the command.
     */
    private void addCommand(String command, CommandExecutor executor) {
        getCommand(command).setExecutor(executor);
        commandManager.addCommand(command, executor);
    }
    public void setPlayerData(Map<String, PlayerData> playerData) {
        this.playerData = playerData;
    }

    public Map<String, PlayerData> getPlayerData() {
        return playerData;
    }
    public Player getPlayer(CommandSender sender) {
        Player player = null;
        if (isPlayer(sender)) {
            player = (Player) sender;
        }
        return player;
    }
    public boolean isPlayer(CommandSender sender) {
        return sender != null && sender instanceof Player;
    }
    public static boolean doorAlreadyExists(int x, int y, int z){
    	String query = "SELECT id FROM doors WHERE x="+ x + " AND y=" + y + " AND z=" + z;
    	ResultSet result = Inn.manageSQLite.sqlQuery(query);
    	int id = 0;
    	try {
			if (result.next()){
				id = result.getInt("id");
				if (id != 0)
					return true;
				else
					return false;
			}else
				return false;
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	return false;
    }
    public static int getDoorPrice(int x, int y, int z){
    	if (doorAlreadyExists(x,y,z)){
    		String query = "SELECT price FROM doors WHERE x="+ x + " AND y=" + y + " AND z=" + z;
    		ResultSet result = Inn.manageSQLite.sqlQuery(query);
    		try {
				if (result.next()){
					return result.getInt("price");
				}else
					
					return -1;
			} catch (SQLException e) {
				e.printStackTrace();
			}
    	}else
    		return -1;
    return -1;
    }
    public static String getOwner(int x, int y, int z){
    	if (doorAlreadyExists(x,y,z)){
    		String query = "SELECT owner FROM doors WHERE x="+ x + " AND y=" + y + " AND z=" + z;
    		ResultSet result = Inn.manageSQLite.sqlQuery(query);
    		try {
				if (result.next()){
					return result.getString("owner");
				}else
					return "";
			} catch (SQLException e) {
				e.printStackTrace();
			}
    	}else
    		return "";
    return "";
    }
}
