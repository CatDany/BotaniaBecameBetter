package catdany.bbb.libs;

import java.lang.reflect.Method;

import net.minecraftforge.common.MinecraftForge;
import catdany.bbb.BBB;
import catdany.bbb.Log;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;

public class EventBusHelper
{
	public static void register(Object eventHandler, EventBusType type)
	{
		Log.debug("Registering event handler %s of type %s on side %s", eventHandler.getClass().getName(), type, BBB.proxy.getSide());
		switch (type)
		{
		case FORGE:
			MinecraftForge.EVENT_BUS.register(eventHandler);
			break;
		case FML:
			FMLCommonHandler.instance().bus().register(eventHandler);
			break;
		case BOTH:
			register(eventHandler, EventBusType.FORGE);
			register(eventHandler, EventBusType.FML);
			break;
		}
	}
	
	public static EventBusType checkBus(Object eventHandler)
	{
		boolean forge = false;
		boolean fml = false;
		
		Class clazz = eventHandler.getClass();
		for (Method i : clazz.getMethods())
		{
			for (Class s : i.getParameterTypes())
			{
				if (s.getName().startsWith("cpw.mods.fml"))
					fml = true;
				else if (s.getName().startsWith("net.minecraftforge"))
					forge = true;
			}
		}
		
		if (forge && fml)
			return EventBusType.BOTH;
		else if (forge && !fml)
			return EventBusType.FORGE;
		else if (!forge && fml)
			return EventBusType.FML;
		else
			return null;
	}
	
	public static void checkBusAndRegister(Object eventHandler)
	{
		EventBusType type = checkBus(eventHandler);
		register(eventHandler, type);
	}
	
	public static void checkBusAndRegister(Object eventHandler, Side side)
	{
		if (FMLCommonHandler.instance().getSide().isClient() == side.isClient())
			return;
		checkBusAndRegister(eventHandler);
	}
	
	public enum EventBusType
	{
		FORGE,
		FML,
		BOTH
	}
}