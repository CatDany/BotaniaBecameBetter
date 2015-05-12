package catdany.bbb.lexicon;

import vazkii.botania.api.BotaniaAPI;

public class LexiconHooks
{
	public static LexiconCategoryBBB categoryBBB;
	
	public static LexiconManaSpawner entryManaSpawner;
	public static LexiconFlowerHydrafatus entryFlowerHydrafatus;
	public static LexiconFlowerRedahlia entryFlowerRedahlia;
	public static LexiconElvenTablet entryElvenTablet;
	public static LexiconBag entryBag;
	
	public static void init()
	{
		categoryBBB = new LexiconCategoryBBB();
		BotaniaAPI.addCategory(categoryBBB);
		entryManaSpawner = new LexiconManaSpawner();
		BotaniaAPI.addEntry(entryManaSpawner, categoryBBB);
		entryFlowerHydrafatus = new LexiconFlowerHydrafatus();
		BotaniaAPI.addEntry(entryFlowerHydrafatus, categoryBBB);
		entryFlowerRedahlia = new LexiconFlowerRedahlia();
		BotaniaAPI.addEntry(entryFlowerRedahlia, categoryBBB);
		entryElvenTablet = new LexiconElvenTablet();
		BotaniaAPI.addEntry(entryElvenTablet, categoryBBB);
		entryBag = new LexiconBag();
		BotaniaAPI.addEntry(entryBag, categoryBBB);
	}
}
