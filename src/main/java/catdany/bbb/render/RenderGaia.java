package catdany.bbb.render;

import net.minecraft.client.renderer.entity.RenderEntity;
import net.minecraft.entity.Entity;
import vazkii.botania.client.core.handler.BossBarHandler;
import catdany.bbb.entity.EntityGaia;

public class RenderGaia extends RenderEntity
{
	@Override
	public void doRender(Entity entity, double x, double y, double z, float par5, float par6)
	{
		EntityGaia dopple = (EntityGaia)entity;
		BossBarHandler.setCurrentBoss(dopple);
	}
}
