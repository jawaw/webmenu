package work.craftpowered.mcefp.resources;

import com.google.common.collect.Sets;

import java.util.Set;

public class Resources {

    public static Set<Class<?>> getClasses() {
        Set<Class<?>> resources = Sets.newHashSet();
        resources.add(AuthmeController.class);
        resources.add(CommandContoller.class);
        return resources;
    }



}
