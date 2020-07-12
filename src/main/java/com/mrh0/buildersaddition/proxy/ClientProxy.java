package com.mrh0.buildersaddition.proxy;

import com.mrh0.buildersaddition.Index;
import com.mrh0.buildersaddition.client.render.entity.SeatRenderFactory;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class ClientProxy extends CommonProxy {
	@Override
	public void init(final FMLCommonSetupEvent evt) {
		RenderingRegistry.registerEntityRenderingHandler(Index.SEAT_ENTITY_TYPE, new SeatRenderFactory());
		
		RenderType cutout = RenderType.getCutoutMipped();        
        RenderTypeLookup.setRenderLayer(Index.HEDGE_OAK, cutout);
        RenderTypeLookup.setRenderLayer(Index.HEDGE_BIRCH, cutout);
        RenderTypeLookup.setRenderLayer(Index.HEDGE_SPRUCE, cutout);
        RenderTypeLookup.setRenderLayer(Index.HEDGE_DARK_OAK, cutout);
        RenderTypeLookup.setRenderLayer(Index.HEDGE_JUNGLE, cutout);
        RenderTypeLookup.setRenderLayer(Index.HEDGE_ACACIA, cutout);
	}
}
