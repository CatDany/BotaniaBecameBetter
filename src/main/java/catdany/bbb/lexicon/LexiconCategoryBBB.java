package catdany.bbb.lexicon;

import catdany.bbb.Refs;
import net.minecraft.util.ResourceLocation;
import vazkii.botania.api.lexicon.LexiconCategory;

public class LexiconCategoryBBB extends LexiconCategory
{
	public static final ResourceLocation icon = new ResourceLocation(Refs.RESLOC + ":" + "textures/gui/CategoryBBB.png");
	
	public LexiconCategoryBBB()
	{
		super("botania.category.bbb");
	}
	
	@Override
	public ResourceLocation getIcon()
	{
		return icon;
	}
}