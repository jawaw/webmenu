package net.montoyo.mcef.network;


import java.util.Deque;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Queues;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import net.montoyo.mcef.example.BrowserScreen;
import net.montoyo.mcef.example.ExampleMod;
import net.montoyo.mcef.network.Simple.Packet;

public class PacketHandler implements NetworkInterface{

    public final static Deque<Packet> packetsQueue = Queues.<Packet>newArrayDeque();
    private final Minecraft mc = Minecraft.getMinecraft();
	public final SimpleNetworkWrapper SIMPLE_NETWORK_WRAPPER;
    
    public PacketHandler() {
    	SIMPLE_NETWORK_WRAPPER = NetworkRegistry.INSTANCE.newSimpleChannel(NetworkInterface.MCEF_CHANNEL);
   	 	SIMPLE_NETWORK_WRAPPER.registerMessage(Simple.class, Simple.Packet.class, NetworkInterface.MCEF_DISCRIMINATOR, Side.CLIENT);
    }
     	
    public void handler() { 
        if (!this.packetsQueue.isEmpty()) 
        {
			try {
				Packet packet = packetsQueue.removeFirst();
				int id = packet.jsonObject.getIntValue(PACKET_ID);
				JSONObject content = packet.jsonObject.getJSONObject(CONTENT); 

				switch (id) {
				case PACKET_EXECUTE_COMMAND: break;
				case PACKT_SHOW_SCREEN: {
					mc.addScheduledTask(() -> {
						String url = content.getString(KEY_URL);
						if(!Minecraft.getMinecraft().inGameHasFocus)  {
							packetsQueue.add(packet);
						}
						else if (!(mc.currentScreen instanceof BrowserScreen) && url != null) {
							ExampleMod.INSTANCE.showScreen(url);
						}
					});
					break;
				}
				case PACKT_SHOW_TOAST: {
					mc.addScheduledTask(() -> {
						ToastMessage toastMessage = new ToastMessage();
						toastMessage.show(content.getString(KEY_TITLE), content.getString(KEY_DESCRIPTION));
					});
					break;
				}
				default: break;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
        }    	
    }
	
}
