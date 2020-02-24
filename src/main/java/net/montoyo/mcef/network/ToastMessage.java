package net.montoyo.mcef.network;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.toasts.GuiToast;
import net.minecraft.client.gui.toasts.IToast;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;

public class ToastMessage implements IToast {
	
    private ItemStack itemStack;
    private long firstDrawTime = 5000L;
    private String title;
    private String description;
    private boolean hasPlayedSound = false;
    
    public void show(String title, String description) { 
    	this.title = title;
    	this.description = description;
    	this.itemStack = new ItemStack(Items.EMERALD);
    	firstDrawTime = 5000L;
    	Minecraft.getMinecraft().getToastGui().add(this);
    }
    
	@Override
	public IToast.Visibility draw(GuiToast toastGui, long delta) {
        toastGui.getMinecraft().getTextureManager().bindTexture(TEXTURE_TOASTS);
        GlStateManager.color(1.0F, 1.0F, 1.0F);
        toastGui.drawTexturedModalRect(0, 0, 0, 32, 160, 32);
        toastGui.getMinecraft().fontRenderer.drawString(title, 30, 7, -11534256);
        toastGui.getMinecraft().fontRenderer.drawString(description, 30, 18, -16777216);
        if(!this.hasPlayedSound && delta > 0L) {
            this.hasPlayedSound = true;
            toastGui.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.getRecord(SoundEvents.UI_TOAST_CHALLENGE_COMPLETE, 1.0F, 1.0F));       	
        }
        RenderHelper.enableGUIStandardItemLighting();
        toastGui.getMinecraft().getRenderItem().renderItemAndEffectIntoGUI((EntityLivingBase)null, itemStack, 8, 8); 
        return delta - this.firstDrawTime >= 5000L ? IToast.Visibility.HIDE : IToast.Visibility.SHOW;
	}

}
