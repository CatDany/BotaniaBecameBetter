package catdany.bbb.entity;

import catdany.bbb.BBB;
import cpw.mods.fml.common.registry.EntityRegistry;

public class EntityRepo
{
	public static void initEntities()
	{
		int id = 0;
		
		EntityRegistry.registerModEntity(EntityGaia.class, "GaiaExtended", id++, BBB.instance, 128, 3, true);
	}
}
