package org.mcrest;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * This is mcrest config handler. All mcrest needed config para are store here.
 * Created by frank on 2015/3/5.
 */
public class ConfigHandler {
    private static ConfigHandler instance = null;
    private JavaPlugin plugin;
    private int port = 8888;
    private String prefix = "mcrest";

    protected ConfigHandler() {
    }

    public static ConfigHandler getInstance() {
        if (instance == null) {
            synchronized (ConfigHandler.class) {
                if (instance == null) {
                    instance = new ConfigHandler();
                }
            }
        }
        return instance;
    }

    public void setConfigByPlugin(JavaPlugin bukkitplugin) {
        this.plugin = bukkitplugin;
    }

    public int getPort() {
        return port;
    }

    public String getPrefix() {
        return prefix;
    }

    public void saveConfig() {
        this.plugin.saveConfig();
    }



}
