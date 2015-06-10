package catdany.bbb.proxy;

import catdany.bbb.entity.EntityGaia;
import catdany.bbb.event.RenderEvents;
import catdany.bbb.items.ItemNovasteelArmor;
import catdany.bbb.libs.EventBusHelper;
import catdany.bbb.render.RenderGaia;
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
		RenderingRegistry.registerEntityRenderingHandler(EntityGaia.class, new RenderGaia());
	}
	
	@Override
	public void initEventHandlers()
	{
		EventBusHelper.checkBusAndRegister(new RenderEvents());
	}
	
	@Override
	public void postInit() {}
	
	@Override
	public Side getSide()
	{
		return Side.CLIENT;
	}
}