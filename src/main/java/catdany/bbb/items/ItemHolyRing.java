package catdany.bbb.items;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import vazkii.botania.api.mana.IManaUsingItem;
import vazkii.botania.api.mana.ManaItemHandler;
import baubles.api.BaubleType;
import baubles.api.IBauble;
import catdany.bbb.BBB;
import catdany.bbb.Refs;
import catdany.bbb.libs.IconRegHelper;
import cpw.mods.fml.common.registry.GameRegistry;

public class ItemHolyRing extends Item implements IBauble, IManaUsingItem
{
	public static final String NAME = "HolyRing";
	public static final int MANA_PER_TICK = 3; // Note: Mana used per 1 tick of Wither effect removed, it doesn't use Mana passively
	
	public ItemHolyRing()
	{
		super();
		setUnlocalizedName(Refs.MODID + ":" + NAME);
		setCreativeTab(BBB.modTab);
		
		GameRegistry.registerItem(this, NAME);
	}
	
	@Override
	public void onWornTick(ItemStack stack, EntityLivingBase living)
	{
		if (living instanceof EntityPlayer && living.isPotionActive(Potion.wither))
		{
			PotionEffect potion = living.getActivePotionEffect(Potion.wither);
			int mana = MANA_PER_TICK * potion.getDuration() * potion.getAmplifier();
			boolean f = ManaItemHandler.requestManaExact(stack, (EntityPlayer)living, mana, true);
			if (f)
			{
				living.removePotionEffect(potion.getPotionID());
			}
		}
	}
	
	@Override
	public void registerIcons(IIconRegister reg)
	{
		itemIcon = IconRegHelper.regItem(this, reg);
	}
	
	@Override
	public boolean canEquip(ItemStack stack, EntityLivingBase living)
	{
		return true;
	}
	
	@Override
	public boolean canUnequip(ItemStack stack, EntityLivingBase living)
	{
		return true;
	}
	
	@Override
	public BaubleType getBaubleType(ItemStack stack)
	{
		return BaubleType.RING;
	}
	
	@Override
	public void onEquipped(ItemStack stack, EntityLivingBase living) {}
	
	@Override
	public void onUnequipped(ItemStack stack, EntityLivingBase living) {}
	
	@Override
	public boolean usesMana(ItemStack stack)
	{
		return true;
	}
}
