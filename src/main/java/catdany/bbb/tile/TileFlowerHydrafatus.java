package catdany.bbb.tile;

import java.util.ArrayList;

import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import vazkii.botania.api.item.IPetalApothecary;

public class TileFlowerHydrafatus extends TileEntity
{
	private static final int[][] apothecaryRelativeCoords =
		{
			{-1, 0, 0}, {0, -1, 0}, {0, 0, -1}, {1, 0, 0}, {0, 1, 0}, {0, 0, 1}
		};
	
	public int cooldown = 0;
	
	@Override
	public void updateEntity()
	{
		if (cooldown > 0)
		{
			cooldown--;
		}
		else
		{
			ArrayList<IPetalApothecary> apothecaries = new ArrayList<IPetalApothecary>();
			for (int[] i : apothecaryRelativeCoords)
			{
				TileEntity tile = worldObj.getTileEntity(xCoord + i[0], yCoord + i[1], zCoord + i[2]);
				if (tile instanceof IPetalApothecary && !((IPetalApothecary)tile).hasWater())
				{
					apothecaries.add((IPetalApothecary)tile);
				}
			}
			if (apothecaries.size() > 0 && drainWater())
			{
				IPetalApothecary k = apothecaries.get(worldObj.rand.nextInt(apothecaries.size()));
				k.setWater(true);
			}
			cooldown = 20;
		}
	}
	
	private boolean drainWater()
	{
		ArrayList<int[]> waterBlocks = new ArrayList<int[]>();
		for (int i = -1; i <= 1; i++)
		{
			for (int j = -1; j <= 1; j++)
			{
				if (worldObj.getBlock(xCoord + i, yCoord, zCoord + j) == Blocks.water)
				{
					waterBlocks.add(new int[] {xCoord + i, yCoord, zCoord + j});
				}
			}
		}
		if (waterBlocks.size() > 0)
		{
			int[] k = waterBlocks.get(worldObj.rand.nextInt(waterBlocks.size()));
			worldObj.setBlockToAir(k[0], k[1], k[2]);
			worldObj.scheduleBlockUpdate(k[0], k[1], k[2], Blocks.air, 5);
			return true;
		}
		return false;
	}
	
	@Override
	public boolean canUpdate()
	{
		return true;
	}
	
	public void readCustomNBT(NBTTagCompound tag)
	{
		cooldown = tag.getInteger("Cooldown");
	}
	
	public void writeCustomNBT(NBTTagCompound tag)
	{
		tag.setInteger("Cooldown", cooldown);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tag)
	{
		super.readFromNBT(tag);
		readCustomNBT(tag);
	}
	
	@Override
	public void writeToNBT(NBTTagCompound tag)
	{
		super.writeToNBT(tag);
		writeCustomNBT(tag);
	}
}