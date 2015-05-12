package catdany.bbb.tile;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.AxisAlignedBB;
import vazkii.botania.api.mana.IManaPool;
import vazkii.botania.api.mana.spark.ISparkAttachable;
import vazkii.botania.api.mana.spark.ISparkEntity;
import vazkii.botania.api.mana.spark.SparkHelper;
import catdany.bbb.Log;

public class TileManaSpawner extends TileEntity implements ISparkAttachable
{
	private static final int POOL_MAX = 1000000;
	public static final int MAX_MANA = POOL_MAX / 2;
	
	public int manaStored = 0;
	public NBTTagCompound spawnerData;
	public boolean consumeDrops = false;
	
	@Override
	public void updateEntity()
	{
		ISparkEntity spark = getAttachedSpark();
		if (spark != null)
		{
			List<ISparkEntity> sparkEntities = SparkHelper.getSparksAround(worldObj, xCoord + 0.5, yCoord + 0.5, zCoord + 0.5);
			for (ISparkEntity otherSpark : sparkEntities)
			{
				if (spark == otherSpark)
					continue;
		
				if (otherSpark.getAttachedTile() != null && otherSpark.getAttachedTile() instanceof IManaPool)
					otherSpark.registerTransfer(spark);
			}
		}
		if (isFull())
		{
			if (spark != null)
			{
				worldObj.removeEntity((Entity)spark);
			}
			consumeDrops = true;
			worldObj.setBlock(xCoord, yCoord, zCoord, Blocks.mob_spawner);
			TileEntityMobSpawner tile = (TileEntityMobSpawner)worldObj.getTileEntity(xCoord, yCoord, zCoord);
			spawnerData.setInteger("x", xCoord);
			spawnerData.setInteger("y", yCoord);
			spawnerData.setInteger("z", zCoord);
			tile.readFromNBT(spawnerData);
		}
	}
	
	@Override
	public boolean canUpdate()
	{
		return true;
	}
	
	public void writeCustomNBT(NBTTagCompound tag)
	{
		tag.setInteger("ManaStored", manaStored);
		tag.setTag("SpawnerData", spawnerData);
	}
	
	public void readCustomNBT(NBTTagCompound tag)
	{
		manaStored = tag.getInteger("ManaStored");
		spawnerData = tag.getCompoundTag("SpawnerData");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound tag)
	{
		super.writeToNBT(tag);
		writeCustomNBT(tag);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tag)
	{
		super.readFromNBT(tag);
		readCustomNBT(tag);
	}
	
	@Override
	public void attachSpark(ISparkEntity entity) {}
	
	@Override
	public boolean areIncomingTranfersDone()
	{
		return isFull();
	}
	
	@Override
	public boolean canAttachSpark(ItemStack stack)
	{
		return true;
	}
	
	@Override
	public ISparkEntity getAttachedSpark()
	{
		List<ISparkEntity> sparks = worldObj.getEntitiesWithinAABB(ISparkEntity.class, AxisAlignedBB.getBoundingBox(xCoord, yCoord + 1, zCoord, xCoord + 1, yCoord + 2, zCoord + 1));
		if(sparks.size() == 1)
		{
			Entity e = (Entity)sparks.get(0);
			return (ISparkEntity)e;
		}
		return null;
	}
	
	@Override
	public boolean canRecieveManaFromBursts()
	{
		return true;
	}
	
	@Override
	public int getAvailableSpaceForMana()
	{
		return Math.max(0, MAX_MANA - getCurrentMana());
	}
	
	@Override
	public int getCurrentMana()
	{
		return manaStored;
	}
	
	@Override
	public boolean isFull()
	{
		return manaStored >= MAX_MANA;
	}
	
	@Override
	public void recieveMana(int amount)
	{
		manaStored = Math.min(MAX_MANA, manaStored + amount);
	}
	
	@Override
	public Packet getDescriptionPacket()
	{
		NBTTagCompound tag = new NBTTagCompound();
		writeCustomNBT(tag);
		S35PacketUpdateTileEntity packet = new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 0, tag);
		return packet;
	}
	
	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt)
	{
		readCustomNBT(pkt.func_148857_g());
	}
}
