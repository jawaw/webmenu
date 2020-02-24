package work.craftpowered.mcefp.network;

public interface NetworkInterface {

    String MCEF_CHANNEL = "network:simple";
    int MCEF_DISCRIMINATOR = 127;

    String CONTENT = "content";
    String PACKET_ID = "packetId";
    
    String KEY_URL = "url";
    String KEY_COMMAND = "command";
    String KEY_TITLE = "title";
    String KEY_DESCRIPTION = "description";
    
    int PACKET_EXECUTE_COMMAND = 100;
    int PACKT_SHOW_SCREEN = 101;
    int PACKT_SHOW_TOAST = 102;
    
}
