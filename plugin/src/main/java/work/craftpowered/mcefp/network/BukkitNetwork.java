package work.craftpowered.mcefp.network;

import com.alibaba.fastjson.JSONObject;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.util.UUID;

public class BukkitNetwork implements PluginMessageListener, NetworkInterface{
	
    private final JavaPlugin plugin;

	public BukkitNetwork(JavaPlugin pl) {
		plugin = pl;
        plugin.getServer().getMessenger().registerIncomingPluginChannel( plugin, MCEF_CHANNEL, this );
        plugin.getServer().getMessenger().registerOutgoingPluginChannel( plugin, MCEF_CHANNEL);
	}

	@Override
	public void onPluginMessageReceived(String arg0, Player arg1, byte[] arg2) {
	}
	
    public void sendPacktTo( UUID uuid, JSONObject jsonObject ) {
        // forge and its damm registries... we need to add a leading 0x0 byte so that forge can figure out the right packet class. Thanks md_5!
        byte[] temp = jsonObject.toString().getBytes();
        byte[] data = new byte[temp.length + 1];
        System.arraycopy( temp, 0, data, 1, temp.length );
        data[0] = (byte)MCEF_DISCRIMINATOR;
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () ->
                    org.bukkit.Bukkit.getPlayer( uuid ).sendPluginMessage( plugin, MCEF_CHANNEL, data )
//                        org.bukkit.Bukkit.getPlayer( uuid ).sendPluginMessage( plugin, MCEF_CHANNEL, temp )
        );
    }

}
