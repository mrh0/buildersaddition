package com.mrh0.buildersaddition.proxy;

import com.mrh0.buildersaddition.Index;
import com.mrh0.buildersaddition.client.render.SeatRenderFactory;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class ClientProxy extends CommonProxy {
	@Override
	public void init(final FMLCommonSetupEvent evt){
		RenderingRegistry.registerEntityRenderingHandler(Index.SEAT_ENTITY_TYPE, new SeatRenderFactory());
		
		RenderType translucent = RenderType.getTranslucent();        
        RenderTypeLookup.setRenderLayer(Index.HEDGE_OAK, translucent);
        RenderTypeLookup.setRenderLayer(Index.HEDGE_BIRCH, translucent);
        RenderTypeLookup.setRenderLayer(Index.HEDGE_SPRUCE, translucent);
        RenderTypeLookup.setRenderLayer(Index.HEDGE_DARK_OAK, translucent);
        RenderTypeLookup.setRenderLayer(Index.HEDGE_JUNGLE, translucent);
        RenderTypeLookup.setRenderLayer(Index.HEDGE_ACACIA, translucent);
	}
}
