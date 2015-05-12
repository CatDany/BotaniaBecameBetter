package catdany.bbb;

import net.minecraftforge.common.config.Configuration;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

/**
 * Created by Dany @ 2015 May, 03
 * @author Dany
 *
 */
public class BBBCfg
{
	public static Configuration config;
	
	public static boolean debugOutput = false;
	public static boolean dropUnactivatedManaSpawners = true;
	public static boolean justifyTextInLexicon = false;
	
	public static void updateConfig()
	{
		config.load();
		debugOutput = config.getBoolean("debugOutput", "misc", debugOutput, "Allow debug output for mod?");
		dropUnactivatedManaSpawners = config.getBoolean("dropUnactivatedManaMobSpawners", "gameplay", dropUnactivatedManaSpawners, "Drop Unactivated Mana Mob Spawners from regular spawners? Set to false to disable this feature. Do note that it's the only way to get them naturally.");
		justifyTextInLexicon = config.getBoolean("justifyTextInLexicon", "botania", justifyTextInLexicon, "Set this to true to enable justified text in the Lexica Botania's text pages.");
		// Look, GitHub! That's how it needs to be done. Don't save it forcefully, my Notepad++ doesn't like it at all.
		if (config.hasChanged())
		{
			config.save();
		}
		Log.info("Debug Logging is " + (debugOutput ? "enabled" : "disabled") + ".");
	}
	
	static void initConfiguration(FMLPreInitializationEvent e)
	{
		config = new Configuration(e.getSuggestedConfigurationFile());
	}
}