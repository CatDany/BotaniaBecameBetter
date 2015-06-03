package catdany.bbb.event;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.InventoryEffectRenderer;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderPlayerEvent;
import vazkii.botania.common.Botania;
import catdany.bbb.Log;
import catdany.bbb.Refs;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class RenderEvents
{
	@SideOnly(Side.CLIENT)
	public int lastAngle;
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void renderLiving(RenderPlayerEvent.Post e)
	{
		if (!e.entityPlayer.getHideCape() && !(Minecraft.getMinecraft().currentScreen instanceof InventoryEffectRenderer) && e.entityPlayer.getGameProfile().getId().toString().equals(Refs.T3H_31337_UUID))
		{
			int t = (int)(e.entityPlayer.ticksExisted % 60);
			int a = (int)(t / 60D * 360);
			if (a != lastAngle)
			{
				lastAngle = a;
				boolean f = Minecraft.getMinecraft().thePlayer.getCommandSenderName().equals("CatDany");
				double pX = e.entityPlayer.posX + Math.sin(Math.toRadians(a)) * 0.85;
				double pY = e.entityPlayer.posY - 0.5 + (f ? 0 : 1.62);
				double pZ = e.entityPlayer.posZ + Math.cos(Math.toRadians(a)) * 0.85;
				World world = e.entityPlayer.worldObj;
				Botania.proxy.wispFX(world, pX, pY, pZ, 0.4F, 0.4F, 1.0F, 0.15F, 0.0F, 1.0F);
				//world.spawnParticle("happyVillager", pX, pY, pZ, 0, 0, 0);
			}
		}
	}
}
