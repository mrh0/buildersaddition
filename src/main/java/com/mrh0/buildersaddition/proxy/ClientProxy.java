package com.mrh0.buildersaddition.proxy;

import com.mrh0.buildersaddition.Index;
import com.mrh0.buildersaddition.client.render.entity.SeatRenderFactory;

import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class ClientProxy extends CommonProxy {
	@Override
	public void init(final FMLCommonSetupEvent evt) {
		//RenderingRegistry.registerEntityRenderingHandler(Index.SEAT_ENTITY_TYPE, new SeatRenderFactory());
		EntityRenderers.register(Index.SEAT_ENTITY_TYPE, new SeatRenderFactory());
		
		//RenderType transl = RenderType.translucent();
		RenderType cutout = RenderType.cutoutMipped();       
		
		ItemBlockRenderTypes.setRenderLayer(Index.HEDGE_OAK, cutout);
		ItemBlockRenderTypes.setRenderLayer(Index.HEDGE_BIRCH, cutout);
		ItemBlockRenderTypes.setRenderLayer(Index.HEDGE_SPRUCE, cutout);
		ItemBlockRenderTypes.setRenderLayer(Index.HEDGE_DARK_OAK, cutout);
		ItemBlockRenderTypes.setRenderLayer(Index.HEDGE_JUNGLE, cutout);
		ItemBlockRenderTypes.setRenderLayer(Index.HEDGE_ACACIA, cutout);
        
		ItemBlockRenderTypes.setRenderLayer(Index.CROSSRAIL, cutout);
        
		ItemBlockRenderTypes.setRenderLayer(Index.VERTICAL_REPEATER, cutout);
		ItemBlockRenderTypes.setRenderLayer(Index.VERTICAL_COMPARATOR, cutout);
	}
}
