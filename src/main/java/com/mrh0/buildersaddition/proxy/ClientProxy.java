package com.mrh0.buildersaddition.proxy;

import com.mrh0.buildersaddition.Index;
import com.mrh0.buildersaddition.client.render.SeatRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class ClientProxy extends CommonProxy {
	@Override
	public void init(final FMLCommonSetupEvent evt){
		RenderingRegistry.registerEntityRenderingHandler(Index.SEAT_ENTITY_TYPE, new SeatRenderFactory());
	}
}
