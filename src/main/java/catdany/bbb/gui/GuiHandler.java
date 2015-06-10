package catdany.bbb.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import catdany.bbb.BBB;
import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler
{
	public static final int GUI_BAG = 0;
	
	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z)
	{
		switch (id)
		{
		case GUI_BAG:
			return new GuiBag(player, player.inventory.getCurrentItem());
		}
		return null;
	}
	
	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z)
	{
		switch (id)
		{
		case GUI_BAG:
			return new ContainerBag(player, player.inventory.getCurrentItem());
		}
		return null;
	}
	
	public static void showGUI(EntityPlayer player, int id, int x, int y, int z)
	{
		player.openGui(BBB.instance, id, player.worldObj, x, y, z);
	}
	
	public static void showGUI(EntityPlayer player, int id)
	{
		showGUI(player, id, -1, -1, -1);
	}
}
