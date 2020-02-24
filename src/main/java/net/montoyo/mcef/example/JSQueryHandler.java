package net.montoyo.mcef.example;

import com.alibaba.fastjson.JSONObject;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.montoyo.mcef.api.IBrowser;
import net.montoyo.mcef.api.IJSQueryCallback;
import net.montoyo.mcef.utilities.Log;

public class JSQueryHandler {

	private static Minecraft mc = Minecraft.getMinecraft();
	private static String token;

	public static boolean jsQueryHandler(IBrowser b, long queryId, String query, boolean persistent,
			IJSQueryCallback cb) {

		try {
			JSONObject queryJSONObject = JSONObject.parseObject(query);
			String queryType = queryJSONObject.getString("action");
			String queryBody = queryJSONObject.getString("body");

			switch (queryType) {
			case "close": {
				ExampleMod.INSTANCE.setBackup((BrowserScreen) mc.currentScreen);
				mc.displayGuiScreen(null);
				cb.success("");
				return true;
			}
			case "username": {
				mc.addScheduledTask(() -> {
					String name = mc.getSession().getUsername();
					cb.success(name);
				});				
				return true;
			}			
			case "playsound": {
				mc.addScheduledTask(() -> {
					SoundEvent sound = new SoundEvent(new ResourceLocation(queryBody));
					mc.getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(sound, 1.0f));
					cb.success("");
				});
				return true;
			}
			case "getToken": {
				cb.success(token);
				return true;
			}
			case "setToken": {
				token = queryBody;
				cb.success("");
				return true;
			}
			default:
				break;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (query.equalsIgnoreCase("username")) {
			if (b.getURL().startsWith("mod://") || b.getURL().startsWith("http://")) {
				// Only allow MCEF URLs to get the player's username to keep his identity secret

				mc.addScheduledTask(() -> {
					// Add this to a scheduled task because this is NOT called from the main
					// Minecraft thread...

					try {
						String name = mc.getSession().getUsername();
						cb.success(name);
					} catch (Throwable t) {
						cb.failure(500, "Internal error.");
						Log.warning("Could not get username from JavaScript:");
						t.printStackTrace();
					}
				});
			} else
				cb.failure(403, "Can't access username from external page");

			return true;
		}

		// new method....

		return false;
	}

}
