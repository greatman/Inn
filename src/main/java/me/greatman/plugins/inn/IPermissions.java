package me.greatman.plugins.inn;

import com.nijikokun.bukkit.Permissions.Permissions;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

/**
 * @description Handles all plugin permissions
 * @author Tagette
 */
public class IPermissions {

    private enum PermissionHandler {

        PERMISSIONS, GROUP_MANAGER, NONE
    }
    private static PermissionHandler handler;
    public static Plugin PermissionPlugin;
    private static Inn plugin;

    public static void initialize(Inn instance) {
        IPermissions.plugin = instance;
        Plugin Permissions = plugin.getServer().getPluginManager().getPlugin("Permissions");
        handler = PermissionHandler.NONE;

        if (Permissions != null) {
            PermissionPlugin = Permissions;
            handler = PermissionHandler.PERMISSIONS;
            String version = PermissionPlugin.getDescription().getVersion();
                ILogger.info("Permissions version " + version + " loaded.");
        } else{
        	ILogger.info("No permissions system found!");
        }
    }

    public static void onEnable(Plugin plugin) {
        if (PermissionPlugin == null) {
            String pluginName = plugin.getDescription().getName();
            handler = PermissionHandler.NONE;

            if (pluginName.equals("Permissions")) {
                PermissionPlugin = plugin;
                handler = PermissionHandler.PERMISSIONS;
                String version = plugin.getDescription().getVersion();
                    ILogger.info("Permissions version " + version + " loaded.");
            } else if (pluginName.equals("GroupManager")) {
                PermissionPlugin = plugin;
                handler = PermissionHandler.GROUP_MANAGER;
                String version = plugin.getDescription().getVersion();
                    ILogger.info("GroupManager version " + version + " loaded.");
            }
        }
    }

    public static boolean permission(Player player, String permission, boolean defaultPerm) {
        switch (handler) {
            case PERMISSIONS:
                return ((Permissions) PermissionPlugin).getHandler().has(player, permission);
            case NONE:
                return defaultPerm;
            default:
                return defaultPerm;
        }
    }

    public static boolean isAdmin(Player player) {
        return permission(player, "inn.admin", player.isOp());
    }
}
