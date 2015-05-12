package catdany.bbb.proxy;

import cpw.mods.fml.relauncher.Side;

public interface IProxy
{
	public void preInit();
	
	public void init();
	
	public void postInit();
	
	public Side getSide();
}
