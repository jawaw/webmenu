package work.craftpowered.mcefp;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

import fr.xephi.authme.api.v3.AuthMeApi;
import lombok.Getter;
import work.craftpowered.mcefp.network.BukkitNetwork;
import work.craftpowered.mcefp.network.NetworkInterface;

@Getter
public class Mcefp extends JavaPlugin implements Listener, NetworkInterface {

    private static Mcefp instance;
    private FileConfiguration fileConfiguration;
    private BukkitNetwork bukkitNetwork;
    private AuthMeApi authMeApi;

    String BASE_URL;
    String URL_LOGIN;
    String URL_REGISTER;
    String URL_MENU;

    public static Mcefp getInstance() { return instance; }

    @Override
    public void onEnable() {
        saveDefaultConfig();
        instance = this;
        authMeApi = AuthMeApi.getInstance();
        fileConfiguration = getConfig();
        bukkitNetwork = new BukkitNetwork(this);
        getServer().getPluginManager().registerEvents(this, this);
        loadConfiguration();
    }

    @Override
    public void onDisable() {
    }

    private void loadConfiguration() {
        BASE_URL = fileConfiguration.getString("BASE_URL");
        URL_LOGIN = BASE_URL + fileConfiguration.getString("URL_LOGIN");
        URL_REGISTER = BASE_URL + fileConfiguration.getString("URL_REGISTER");
        URL_MENU = BASE_URL + fileConfiguration.getString("URL_MENU");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player) || args.length == 0) return false;
        if (args[0].equals("menu")) {
            bukkitNetwork.sendPacktTo(((Player) sender).getUniqueId(),
                    generateJSONHeader(PACKT_SHOW_SCREEN, generateJSONContent(KEY_URL, URL_MENU)));
        } else if (args[0].equals("url") && args[1]!= null) {
            bukkitNetwork.sendPacktTo(((Player) sender).getUniqueId(),
                    generateJSONHeader(PACKT_SHOW_SCREEN, generateJSONContent(KEY_URL, args[1])));
        }
        return false;
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerLoginIn(PlayerJoinEvent ev) {
        bukkitNetwork.sendPacktTo(ev.getPlayer().getUniqueId(),
                generateJSONHeader(PACKT_SHOW_SCREEN, generateJSONContent(KEY_URL,
                        authMeApi.isRegistered(ev.getPlayer().getName().toLowerCase()) ? URL_LOGIN : URL_REGISTER)));
    }

    public void sendMessage(Player player, String title, String description) {
        HashMap<String, String> toast = Maps.newHashMap();
        toast.put(KEY_TITLE, title);
        toast.put(KEY_DESCRIPTION, description);
        bukkitNetwork.sendPacktTo(player.getUniqueId(),
                generateJSONHeader(PACKT_SHOW_TOAST, generateJSONContents(toast)));
    }

    private JSONObject generateJSONHeader(int id, JSONObject content) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(PACKET_ID, id);
        jsonObject.put(CONTENT, content);

        return jsonObject;
    }

    private JSONObject generateJSONContent(String key, String value) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(key, value);

        return jsonObject;
    }

    private JSONObject generateJSONContents(HashMap<String, String> contents) {
        JSONObject jsonObject = new JSONObject();
        contents.forEach((k, v) -> jsonObject.put(k, v));

        return jsonObject;
    }

}
