package catdany.bbb.lexicon;

import java.lang.reflect.Field;

import sun.reflect.Reflection;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.lexicon.LexiconEntry;
import catdany.bbb.BBBCfg;
import catdany.bbb.Log;

public class LexiconHooks
{
	public static LexiconCategoryBBB categoryBBB;
	
	public static LexiconManaSpawner entryManaSpawner;
	public static LexiconFlowerHydrafatus entryFlowerHydrafatus;
	public static LexiconFlowerRedahlia entryFlowerRedahlia;
	public static LexiconElvenTablet entryElvenTablet;
	public static LexiconBag entryBag;
	public static LexiconNovasteel entryNovasteel;
	
	public static void init()
	{
		categoryBBB = new LexiconCategoryBBB();
		BotaniaAPI.addCategory(categoryBBB);
		entryManaSpawner = new LexiconManaSpawner();
		addEntry(entryManaSpawner, "enableManaSpawners");
		entryFlowerHydrafatus = new LexiconFlowerHydrafatus();
		addEntry(entryFlowerHydrafatus, null);
		entryFlowerRedahlia = new LexiconFlowerRedahlia();
		addEntry(entryFlowerRedahlia, null);
		entryElvenTablet = new LexiconElvenTablet();
		addEntry(entryElvenTablet, "enableElvenTablet");
		entryBag = new LexiconBag();
		addEntry(entryBag, null);
		entryNovasteel = new LexiconNovasteel();
		addEntry(entryNovasteel, null);
	}
	
	private static void addEntry(LexiconEntry entry, String configOption)
	{
		boolean enabled = true;
		if (configOption != null)
		{
			try
			{
				Class<BBBCfg> classCfg = BBBCfg.class;
				Field f = classCfg.getField(configOption);
				enabled = (Boolean)f.get(null);
			}
			catch (ReflectiveOperationException t)
			{
				Log.printStackTrace(t, false);
			}
		}
		if (enabled)
		{
			BotaniaAPI.addEntry(entry, categoryBBB);
		}
	}
}
