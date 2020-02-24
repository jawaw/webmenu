package org.mcrest.application;


import com.google.common.collect.Sets;

import work.craftpowered.mcefp.resources.Resources;

import org.bukkit.Bukkit;
import org.mcrest.application.resources.Test;
import javax.ws.rs.core.Application;
import java.util.Set;

/**
 * Created by frank on 2015/3/3.
 */
public class RestApplication extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = Sets.newHashSet();
        resources.add(Test.class);	
        if(Bukkit.getPluginManager().getPlugin("Mcefp") != null)
        	resources.addAll(Resources.getClasses());
        return resources;
    }

}
