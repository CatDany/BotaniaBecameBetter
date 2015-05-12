package catdany.bbb.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotBagCurrent extends Slot
{
	public SlotBagCurrent(IInventory inventory, int slotNumber, int xDisplayPosition, int yDisplayPosition)
	{
		super(inventory, slotNumber, xDisplayPosition, yDisplayPosition);
	}
	
	@Override
	public boolean canTakeStack(EntityPlayer player)
	{
		return false;
	}
	
	@Override
	public boolean isItemValid(ItemStack stack)
	{
		return false;
	}
}
