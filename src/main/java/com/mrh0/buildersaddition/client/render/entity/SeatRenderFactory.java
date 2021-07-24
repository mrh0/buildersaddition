package com.mrh0.buildersaddition.client.render.entity;

import com.mrh0.buildersaddition.entity.SeatEntity;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class SeatRenderFactory implements EntityRendererProvider<SeatEntity>{
	@Override
	public EntityRenderer<SeatEntity> create(Context c) {
		return new SeatRender(c);
	}
}
