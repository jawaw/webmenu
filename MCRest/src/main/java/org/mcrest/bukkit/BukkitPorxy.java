package org.mcrest.bukkit;

import org.bukkit.plugin.java.JavaPlugin;

import fr.xephi.authme.api.v3.AuthMeApi;

public class BukkitPorxy {
	
	private static JavaPlugin plugin;
	private static AuthMeApi authMeApi;

	public BukkitPorxy(JavaPlugin plugin) { this.plugin = plugin; }
	
	public void init() { 
		authMeApi = AuthMeApi.getInstance();
	}
	
	public static AuthMeApi getAuthMeApi() {
		return authMeApi;
	}

	
	
	
	

}
