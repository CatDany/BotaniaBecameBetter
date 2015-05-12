package catdany.bbb.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import catdany.bbb.Refs;
import catdany.bbb.misc.InventoryBag;

//GuiChest
public class GuiBag extends GuiContainer
{
    private static final ResourceLocation texture = new ResourceLocation(Refs.RESLOC + ":" + "textures/gui/GuiBag.png");
    
	public final ItemStack bag;
	public final InventoryBag inv;
	public final EntityPlayer player;
	
	public GuiBag(EntityPlayer player, ItemStack bag)
	{
		super(new ContainerBag(player, bag));
		this.bag = bag;
		this.inv = new InventoryBag(player);
		this.player = player;
		
		xSize = 176;
		ySize = 168;
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int x, int y)
	{
		String s = this.inv.hasCustomInventoryName() ? this.inv.getInventoryName() : I18n.format(this.inv.getInventoryName(), new Object[0]);
        this.fontRendererObj.drawString(s, this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2, 6, 4210752);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int x, int y)
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(texture);
        
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        
        drawTexturedModalRect(k, l, 0, 0, xSize, ySize);
	}
}
