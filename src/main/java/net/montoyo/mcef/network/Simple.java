package net.montoyo.mcef.network;

import java.io.UnsupportedEncodingException;
import com.alibaba.fastjson.JSONObject;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class Simple implements IMessageHandler<Simple.Packet, IMessage>, NetworkInterface {

  
	@Override
	public IMessage onMessage(Packet packet, MessageContext ctx) {
		if(packet != null) PacketHandler.packetsQueue.add(packet);
      return null;
	}
	
	public void sendPacket(Packet packet) {
		try {
			Class<? extends Packet> packetClass = packet.getClass();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static class Packet implements IMessage {

		protected JSONObject  jsonObject;

		public Packet() {
		}

		public Packet(JSONObject  jsonObject) {
			this.jsonObject = jsonObject;
		}

		@Override
		public void fromBytes(ByteBuf buf) {
			byte[] req = new byte[buf.readableBytes()];
			buf.readBytes(req);
			try {
				jsonObject = JSONObject.parseObject(new String(req, "GBK"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void toBytes(ByteBuf buf) {
			buf.writeBytes(jsonObject.toString().getBytes());
		}
	}

}
