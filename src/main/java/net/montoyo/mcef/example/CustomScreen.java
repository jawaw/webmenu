package net.montoyo.mcef.example;

import org.lwjgl.input.Keyboard;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.montoyo.mcef.MCEF;
import net.montoyo.mcef.api.API;
import net.montoyo.mcef.api.IBrowser;
import net.montoyo.mcef.api.MCEFApi;

public class CustomScreen extends GuiScreen {
	
    IBrowser browser = null;
    private String urlToLoad = null;

    public CustomScreen(String url) {
        urlToLoad = (url == null) ? MCEF.HOME_PAGE : url;
    }
    
    @Override
    public void initGui() {
        ExampleMod.INSTANCE.hudBrowser = null;

        if(browser == null) {
            //Grab the API and make sure it isn't null.
            API api = MCEFApi.getAPI();
            if(api == null)
                return;
            
            //Create a browser and resize it to fit the screen
            browser = api.createBrowser((urlToLoad == null) ? MCEF.HOME_PAGE : urlToLoad, false);
            urlToLoad = null;
        }
        
        //Resize the browser if window size changed
        if(browser != null)
            browser.resize(mc.displayWidth, mc.displayHeight);    	
        
        Keyboard.enableRepeatEvents(true);
    }
    
    public int scaleY(int y) {
        double sy = ((double) y) / ((double) height) * ((double) mc.displayHeight);
        return (int) sy;
    }
    
    public void loadURL(String url) {
        if(browser == null)
            urlToLoad = url;
        else
            browser.loadURL(url);
    }

    @Override
    public void updateScreen() {
        if(urlToLoad != null && browser != null) {
            browser.loadURL(urlToLoad);
            urlToLoad = null;
        }
    }

    @Override
    public void drawScreen(int i1, int i2, float f) {

        if(browser != null) {
            GlStateManager.disableDepth();
            GlStateManager.enableTexture2D();
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            browser.draw(.0d, height, width, .0d);
            GlStateManager.enableDepth();
        }
        super.drawScreen(i1, i2, f);
    }
    
    @Override
    public void onGuiClosed() {
        //Make sure to close the browser when you don't need it anymore.
        if(!ExampleMod.INSTANCE.hasBackup() && browser != null)
            browser.close();
        
        Keyboard.enableRepeatEvents(false);
    }
    
    @Override
    public void handleInput() {

    }
		
}
