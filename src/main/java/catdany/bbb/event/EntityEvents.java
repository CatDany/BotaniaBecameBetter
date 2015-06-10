package catdany.bbb.event;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.ISpecialArmor;
import net.minecraftforge.common.ISpecialArmor.ArmorProperties;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import catdany.bbb.Log;
import catdany.bbb.Refs;
import catdany.bbb.entity.EntityGaia;
import catdany.bbb.items.ItemNovasteelArmor;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class EntityEvents
{
	@SubscribeEvent
	public void livingDamage(LivingHurtEvent e)
	{
		if (ItemNovasteelArmor.isFullNovasteel(e.entityLiving)
				&& e.source != DamageSource.lava
				&& !(e.source.getEntity() instanceof EntityGaia))
		{
			Log.debug("wot > %s", e.source.damageType);
			damageArmor(e);
			e.ammount = 0F;
		}
		// The Never Lucky One gets punished by The Almighty One
		if (e.entityLiving instanceof EntityPlayer && ((EntityPlayer)e.entityLiving).getGameProfile().getId().toString().equals(Refs.NEVER_LUCKY_UUID) && e.source.getEntity() instanceof EntityPlayer && ((EntityPlayer)e.source.getEntity()).inventory.getCurrentItem() == null && ((EntityPlayer)e.source.getEntity()).getGameProfile().getId().toString().equals(Refs.T3H_31337_UUID))
		{
			e.ammount = 50F;
		}
	}
	
	private void damageArmor(LivingHurtEvent e)
	{
		EntityLivingBase living = e.entityLiving;
		DamageSource source = e.source;
		float amount = e.ammount;
		for (int i = 1; i <= 4; i++)
		{
			ItemStack stack = living.getEquipmentInSlot(i);
			if (stack != null)
			{
				ArmorProperties prop = ((ISpecialArmor)stack.getItem()).getProperties(living, stack, source, amount, i);
				double absorb = amount * prop.AbsorbRatio;
                if (absorb > 0)
                {
                    int itemDamage = (int)(absorb / 25D < 1 ? 1 : absorb / 25D);
                    if (stack.getItem() instanceof ISpecialArmor)
                    {
                        ((ISpecialArmor)stack.getItem()).damageArmor(living, stack, source, itemDamage, prop.Slot);
                    }
                }
			}
		}
	}
}