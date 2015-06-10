package catdany.bbb.event;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.InventoryEffectRenderer;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderPlayerEvent;
import vazkii.botania.common.Botania;
import catdany.bbb.Log;
import catdany.bbb.Refs;
import catdany.bbb.items.ItemNovasteelArmor;
import catdany.bbb.libs.PlayerUtils;
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
		boolean areWeCurrentlyRenderingSenpai = e.entityPlayer.getGameProfile().getId().toString().equals(Refs.T3H_31337_UUID);
		boolean f = PlayerUtils.isLocalPlayer(e.entityPlayer);
		World world = e.entityPlayer.worldObj;
		if (!(Minecraft.getMinecraft().currentScreen instanceof InventoryEffectRenderer) || !f)
		{
			if (!e.entityPlayer.getHideCape() && areWeCurrentlyRenderingSenpai)
			{
				int t = (int)(e.entityPlayer.ticksExisted % 60);
				int a = (int)(t / 60D * 360);
				if (a != lastAngle)
				{
					lastAngle = a;
					double pX = e.entityPlayer.posX + Math.sin(Math.toRadians(a)) * 0.85;
					double pY = e.entityPlayer.posY - 0.5 + (f ? 0 : 1.62);
					double pZ = e.entityPlayer.posZ + Math.cos(Math.toRadians(a)) * 0.85;
					Botania.proxy.wispFX(world, pX, pY, pZ, 0.4F, 0.4F, 1.0F, 0.15F, 0.0F, 1.0F);
					//world.spawnParticle("happyVillager", pX, pY, pZ, 0, 0, 0);
				}
			}
			else if (ItemNovasteelArmor.isFullNovasteel(e.entityPlayer))
			{
				float[] colors = {0.9764705882352941F, 1.0F, 0.3607843137254902F};
				if (areWeCurrentlyRenderingSenpai)
				{
					colors[0] = 0.4F;
					colors[1] = 0.4F;
					colors[2] = 1.0F;
				}
				if (world.rand.nextInt(7) == 0)
				{
					double pX = e.entityPlayer.posX;
					double pY = e.entityPlayer.posY - world.rand.nextDouble() * 0.6F + (f ? 0 : 1.62);
					double pZ = e.entityPlayer.posZ;
					float mX = (0.5F - world.rand.nextFloat()) / 12F;
					float mY = (0.5F - world.rand.nextFloat()) / 12F;
					float mZ = (0.5F - world.rand.nextFloat()) / 12F;
					float size = 0.08F + world.rand.nextFloat() * 0.1F;
					Botania.proxy.wispFX(world, pX, pY, pZ, colors[0], colors[1], colors[2], size, mX, mY, mZ, 2.5F);
				}
			}
		}
	}
}
