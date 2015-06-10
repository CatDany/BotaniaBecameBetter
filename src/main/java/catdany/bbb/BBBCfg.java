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
	public static boolean deobfReflect = false;
	public static boolean justifyTextInLexicon = false;
	public static boolean enableManaSpawners = true;
	public static boolean enableElvenTablet = false;
	public static boolean enableDiceRebind = true;
	
	public static void updateConfig()
	{
		config.load();
		debugOutput = config.getBoolean("debugOutput", "misc", debugOutput, "Allow debug output for mod?");
		deobfReflect = config.getBoolean("deobfReflect", "misc", deobfReflect, "[Deprecated] Use deobfuscated names for reflection? Set to true only if you're in dev environment");
		justifyTextInLexicon = config.getBoolean("justifyTextInLexicon", "botania", justifyTextInLexicon, "Set this to true to enable justified text in the Lexica Botania's text pages.");
		enableManaSpawners = config.getBoolean("enableManaSpawners", "gameplay", enableManaSpawners, "Enable dropping Unactivated Mana Mob Spawners from regular spawners? Note: Botania already adds a way to move Mob Spawners, it's called Life Aggregator. It's an alternative, but it won't disable the original.");
		enableElvenTablet = config.getBoolean("enableElvenTablet", "gameplay", enableElvenTablet, "Enable Elven Mana Tablet? Note: Botania already adds a way to store 2 mana pools worth of Mana in a portable item, it's called Greater Band of Mana. It's an alternative, but it won't disable the original.");
		enableDiceRebind = config.getBoolean("enableDiceRebind", "gameplay", enableDiceRebind, "Enable Dice of Fate rebinding ritual? Learn more in Lexica Botania.");
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