package net.montoyo.mcef.network.packets;

import com.alibaba.fastjson.JSONObject;

public interface PacketOut {
	
    public abstract JSONObject toJSON();

}
