package catdany.bbb.event;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraftforge.event.world.BlockEvent;
import catdany.bbb.Log;
import catdany.bbb.blocks.BlockRepo;
import catdany.bbb.libs.WorldUtils;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class WorldEvents
{
	@SubscribeEvent
	public void spawnerDrops(BlockEvent.BreakEvent e)
	{
		if (e.block == Blocks.mob_spawner)
		{
			ItemStack stack = new ItemStack(BlockRepo.manaSpawner);
			TileEntityMobSpawner tile = (TileEntityMobSpawner)e.world.getTileEntity(e.x, e.y, e.z);
			stack.setTagCompound(new NBTTagCompound());
			NBTTagCompound spawnerData = new NBTTagCompound();
			tile.writeToNBT(spawnerData);
			spawnerData.removeTag("x");
			spawnerData.removeTag("y");
			spawnerData.removeTag("z");
			stack.getTagCompound().setTag("SpawnerData", spawnerData);
			EntityItem entityItem = new EntityItem(e.world, e.x + 0.5, e.y + 0.5, e.z + 0.5, stack);
			e.world.spawnEntityInWorld(entityItem);
			Log.debug("Dropped Unactivated Mana Spawner (Natural) to block drops at coords {%s; %s; %s} in dimension {%s} of type {%s}", e.x, e.y, e.z, WorldUtils.getDimensionId(e.world), spawnerData.getString("EntityId"));
		}
	}
}