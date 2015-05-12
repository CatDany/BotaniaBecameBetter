package catdany.bbb.libs;

import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

public class WorldUtils
{
	public static int getDimensionId(World world)
	{
		return world.provider.dimensionId;
	}
	
	public static boolean isSlimeChunk(Chunk chunk)
	{
		return chunk.getRandomWithSeed(987234911L).nextInt(10) == 0;
	}
}