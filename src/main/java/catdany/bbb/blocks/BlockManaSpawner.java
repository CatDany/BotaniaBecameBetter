package catdany.bbb.blocks;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import catdany.bbb.BBB;
import catdany.bbb.Log;
import catdany.bbb.Refs;
import catdany.bbb.items.ItemManaSpawner;
import catdany.bbb.libs.IconRegHelper;
import catdany.bbb.libs.WorldUtils;
import catdany.bbb.tile.TileManaSpawner;
import cpw.mods.fml.common.registry.GameRegistry;

public class BlockManaSpawner extends BlockContainer
{
	public static final String NAME = "ManaSpawner";
	
	public BlockManaSpawner()
	{
		super(Material.rock);
		setBlockName(Refs.MODID + ":" + NAME);
		setCreativeTab(BBB.modTab);
		setHardness(2.5F);
		setResistance(40.0F);
		
		GameRegistry.registerBlock(this, ItemManaSpawner.class, NAME);
	}
	
	@Override
	public void registerBlockIcons(IIconRegister reg)
	{
		blockIcon = IconRegHelper.regBlock(this, reg);
	}
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase living, ItemStack stack)
	{
		TileManaSpawner tile = (TileManaSpawner)world.getTileEntity(x, y, z);
		tile.spawnerData = stack.getTagCompound().getCompoundTag("SpawnerData");
	}
	
	@Override
	public void onBlockPreDestroy(World world, int x, int y, int z, int meta)
	{
		ItemStack stack = new ItemStack(BlockRepo.manaSpawner);
		TileManaSpawner tile = (TileManaSpawner)world.getTileEntity(x, y, z);
		if (!tile.consumeDrops)
		{
			stack.setTagCompound(new NBTTagCompound());
			stack.getTagCompound().setTag("SpawnerData", tile.spawnerData);
			EntityItem entityItem = new EntityItem(world, x + 0.5, y + 0.5, z + 0.5, stack);
			world.spawnEntityInWorld(entityItem);
			Log.debug("Dropped Unactivated Mana Spawner at coords {%s; %s; %s} in dimension {%s} of type {%s}", x, y, z, WorldUtils.getDimensionId(world), tile.spawnerData.getString("EntityId"));
		}
	}
	
	@Override
	public Item getItemDropped(int meta, Random random, int fortune)
	{
		return null;
	}
	
	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z, EntityPlayer player)
	{
		ItemStack stack = new ItemStack(BlockRepo.manaSpawner);
		stack.setTagCompound(new NBTTagCompound());
		stack.getTagCompound().setTag("SpawnerData", ((TileManaSpawner)world.getTileEntity(x, y, z)).spawnerData);
		return stack;
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int meta)
	{
		return new TileManaSpawner();
	}
	
	public boolean renderAsNormalBlock()
	{
		return false;
	}
	
	public boolean isOpaqueCube()
	{
		return false;
	}
}