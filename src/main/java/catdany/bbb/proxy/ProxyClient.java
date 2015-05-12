package catdany.bbb.proxy;

import cpw.mods.fml.relauncher.Side;

public class ProxyClient implements IProxy
{
	@Override
	public void preInit() {}
	
	@Override
	public void init() {}
	
	@Override
	public void postInit() {}
	
	@Override
	public Side getSide()
	{
		return Side.CLIENT;
	}
}