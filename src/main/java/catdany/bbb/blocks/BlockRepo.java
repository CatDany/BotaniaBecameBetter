package catdany.bbb.blocks;

import catdany.bbb.Refs;
import catdany.bbb.tile.TileFlowerHydrafatus;
import catdany.bbb.tile.TileFlowerRedahlia;
import catdany.bbb.tile.TileManaSpawner;
import cpw.mods.fml.common.registry.GameRegistry;

public class BlockRepo
{
	public static BlockManaSpawner manaSpawner;
	public static BlockFlowerHydrafatus flowerHydrafatus;
	public static BlockFlowerRedahlia flowerRedahlia;
	
	public static void init()
	{
		manaSpawner = new BlockManaSpawner();
		GameRegistry.registerTileEntity(TileManaSpawner.class, Refs.MODID + ":" + "TileManaSpawner");
		flowerHydrafatus = new BlockFlowerHydrafatus();
		GameRegistry.registerTileEntity(TileFlowerHydrafatus.class, Refs.MODID + ":" + "FlowerHydrafatus");
		flowerRedahlia = new BlockFlowerRedahlia();
		GameRegistry.registerTileEntity(TileFlowerRedahlia.class, Refs.MODID + ":" + "FlowerRedahlia");
	}
}