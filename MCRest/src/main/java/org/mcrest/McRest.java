package org.mcrest;

import java.util.Arrays;
import java.util.HashSet;
import java.util.logging.Level;

import org.bukkit.plugin.java.JavaPlugin;
import org.mcrest.application.resources.RestJaxRsApplication;
import org.mcrest.bukkit.BukkitPorxy;
import org.restlet.Component;
import org.restlet.data.Protocol;
import org.restlet.service.CorsService;

/**
 * Created by frank on 2015/3/3.
 */
public class McRest extends JavaPlugin {

    private static BukkitPorxy bukkitPorxy;

    @Override
    public void onEnable(){

        bukkitPorxy = new BukkitPorxy(this);
        
        this.getLogger().info("Enable");
        // Set Bukkit Server.
        ConfigHandler.getInstance().setConfigByPlugin(this);
        try{
            startServices();
            this.getLogger().info("McRest start success.");
            this.getLogger().info("Listen on 0.0.0.0:"+ConfigHandler.getInstance().getPort()+"/"+ConfigHandler.getInstance().getPrefix());
        }catch (Exception e){
            this.getLogger().warning(e.toString());
        }
    }
    @Override
    public void onDisable(){
        this.getLogger().info("McRest disabled");
        ConfigHandler.getInstance().saveConfig();
    }

    public void startServices() throws Exception {
        // Create a new Component
        Component component = new Component();
        // Add a new HTTP server listening on port which from config file.
        // If first init the port is 8281.
        component.getServers().add(Protocol.HTTP, ConfigHandler.getInstance().getPort());
        // clear the log messgae
        component.getLogger().setLevel(Level.SEVERE);
        // solve ajax cross domain
        CorsService corsService = new CorsService();
        corsService.setAllowedOrigins(new HashSet<>(Arrays.asList("*")));
        corsService.setAllowedCredentials(true);
        component.getServices().add(corsService);
        // Attach the sample application.
        component.getDefaultHost().attach("/"+ConfigHandler.getInstance().getPrefix(), new RestJaxRsApplication(null));
        // Start the component.
        component.start();
    }
}
