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
		EntityRenderers.register(Index.SEAT_ENTITY_TYPE.get(), new SeatRenderFactory());
		
		//RenderType transl = RenderType.translucent();
		RenderType cutout = RenderType.cutoutMipped();       
		
		ItemBlockRenderTypes.setRenderLayer(Index.HEDGE_OAK.get(), cutout);
		ItemBlockRenderTypes.setRenderLayer(Index.HEDGE_BIRCH.get(), cutout);
		ItemBlockRenderTypes.setRenderLayer(Index.HEDGE_SPRUCE.get(), cutout);
		ItemBlockRenderTypes.setRenderLayer(Index.HEDGE_DARK_OAK.get(), cutout);
		ItemBlockRenderTypes.setRenderLayer(Index.HEDGE_JUNGLE.get(), cutout);
		ItemBlockRenderTypes.setRenderLayer(Index.HEDGE_ACACIA.get(), cutout);
		ItemBlockRenderTypes.setRenderLayer(Index.HEDGE_MANGROVE.get(), cutout);
        
		ItemBlockRenderTypes.setRenderLayer(Index.CROSSRAIL.get(), cutout);
        
		ItemBlockRenderTypes.setRenderLayer(Index.VERTICAL_REPEATER.get(), cutout);
		ItemBlockRenderTypes.setRenderLayer(Index.VERTICAL_COMPARATOR.get(), cutout);
	}
}
