package catdany.bbb;

import net.minecraft.creativetab.CreativeTabs;

import org.apache.logging.log4j.Logger;

import catdany.bbb.blocks.BlockRepo;
import catdany.bbb.command.CommandSlimeChunk;
import catdany.bbb.event.WorldEvents;
import catdany.bbb.gui.GuiHandler;
import catdany.bbb.items.ItemRepo;
import catdany.bbb.lexicon.LexiconHooks;
import catdany.bbb.libs.EventBusHelper;
import catdany.bbb.misc.CreativeTabBBB;
import catdany.bbb.proxy.IProxy;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkRegistry;

@Mod
(
	modid = Refs.MODID,
	name = Refs.NAME,
	version = Refs.VERSION,
	dependencies = Refs.DEPENDENCIES,
	acceptedMinecraftVersions = Refs.MCVERSION
)
public class BBB
{
	@Mod.Instance
	public static BBB instance;
	@SidedProxy(modId = Refs.MODID, clientSide = Refs.PROXY_CLIENT, serverSide = Refs.PROXY_SERVER)
	public static IProxy proxy;
	static Logger log;
	
	public static CreativeTabs modTab;
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent e)
	{
		log = e.getModLog();
		Log.debug("Logger is initialized successfully.");
		BBBCfg.initConfiguration(e);
		BBBCfg.updateConfig();
	}
	
	@Mod.EventHandler
	public void init(FMLInitializationEvent e)
	{
		modTab = new CreativeTabBBB();
		BlockRepo.init();
		ItemRepo.init();
		CraftingRecipes.init();
		LexiconHooks.init();
		
		NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandler());
		EventBusHelper.checkBusAndRegister(new WorldEvents());
	}
	
	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent e)
	{
		//
	}
	
	@Mod.EventHandler
	public void serverInit(FMLServerStartingEvent e)
	{
		e.registerServerCommand(new CommandSlimeChunk());
	}
}