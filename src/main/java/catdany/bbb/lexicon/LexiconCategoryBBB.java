package catdany.bbb.lexicon;

import net.minecraft.util.ResourceLocation;
import vazkii.botania.api.lexicon.LexiconCategory;
import catdany.bbb.Refs;

public class LexiconCategoryBBB extends LexiconCategory
{
	public static final ResourceLocation icon = new ResourceLocation(Refs.RESLOC + ":" + "textures/gui/CategoryBBB.png");
	
	public LexiconCategoryBBB()
	{
		super("botania.category.bbb");
		//TODO: Fix localization issue when sharing entries (botania.entry.botania.entry.%s)
	}
	
	@Override
	public ResourceLocation getIcon()
	{
		return icon;
	}
}