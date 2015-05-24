package catdany.bbb.proxy;

import catdany.bbb.items.ItemNovasteelArmor;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.Side;

public class ProxyClient implements IProxy
{
	@Override
	public void preInit() {}
	
	@Override
	public void init()
	{
		ItemNovasteelArmor.RENDER_ID = RenderingRegistry.addNewArmourRendererPrefix("novasteel");
	}
	
	@Override
	public void postInit() {}
	
	@Override
	public Side getSide()
	{
		return Side.CLIENT;
	}
}