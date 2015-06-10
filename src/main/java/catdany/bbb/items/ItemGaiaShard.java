package catdany.bbb.items;

import java.awt.Color;
import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import vazkii.botania.common.Botania;
import catdany.bbb.BBB;
import catdany.bbb.Refs;
import catdany.bbb.libs.IconRegHelper;
import catdany.bbb.libs.KeyBoardHelper;
import catdany.bbb.libs.Paragraph;
import cpw.mods.fml.common.registry.GameRegistry;

public class ItemGaiaShard extends Item
{
	public static final String NAME = "GaiaShard";
	
	public ItemGaiaShard()
	{
		super();
		setUnlocalizedName(Refs.MODID + ":" + NAME);
		setCreativeTab(BBB.modTab);
		
		GameRegistry.registerItem(this, NAME);
	}
	
	@Override
	public void registerIcons(IIconRegister reg)
	{
		itemIcon = IconRegHelper.regItem(this, reg);
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4)
	{
		if (KeyBoardHelper.isShiftDown())
		{
			list.add(StatCollector.translateToLocal("bbb.gaiaShard.0"));
			list.add(StatCollector.translateToLocal("bbb.gaiaShard.1"));
		}
		else
		{
			list.add(StatCollector.translateToLocal("botaniamisc.shiftinfo").replace("&", Paragraph.sign));
		}
	}
	
	public int getColorFromItemStack(ItemStack stack, int pass)
	{
		return Color.HSBtoRGB(Botania.proxy.getWorldElapsedTicks() * 2 % 360 / 360F, 0.25F, 1F);
	}
}
