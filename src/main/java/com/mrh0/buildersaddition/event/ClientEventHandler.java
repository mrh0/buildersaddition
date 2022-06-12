package com.mrh0.buildersaddition.event;

import com.mrh0.buildersaddition.BuildersAddition;
import com.mrh0.buildersaddition.Index;
import com.mrh0.buildersaddition.client.render.tileentity.ShelfRenderer;
import com.mrh0.buildersaddition.client.render.tileentity.ShopSignRenderer;
import com.mrh0.buildersaddition.config.Config;
import com.mrh0.buildersaddition.gui.ArcadeGui;
import com.mrh0.buildersaddition.gui.BookshelfGui;
import com.mrh0.buildersaddition.gui.ShelfGui;
import com.mrh0.buildersaddition.gui.SpeakerGui;
import com.mrh0.buildersaddition.midi.MidiHandler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.FoliageColor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = BuildersAddition.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEventHandler {
	@SubscribeEvent
	public static void registerColor(ColorHandlerEvent.Block evt){
		BlockColor bc = (a, b, c, d) -> {
			return b != null && c != null ? BiomeColors.getAverageFoliageColor(b, c) : FoliageColor.getDefaultColor();
		};
		
		evt.getBlockColors().register(bc, Index.HEDGE_OAK.get(), Index.HEDGE_BIRCH.get(), Index.HEDGE_SPRUCE.get(), Index.HEDGE_DARK_OAK.get(), Index.HEDGE_JUNGLE.get(), Index.HEDGE_ACACIA.get(),  Index.HEDGE_MANGROVE.get());
	}
	
	@SubscribeEvent
	public static void registerColor(ColorHandlerEvent.Item evt){
		ItemColor ic = (a, b) -> {
			BlockState blockstate = ((BlockItem)a.getItem()).getBlock().defaultBlockState();
			return Minecraft.getInstance().getBlockColors().getColor(blockstate, null, (BlockPos)null, b);
		};
		
		evt.getItemColors().register(ic, Index.HEDGE_OAK.get(), Index.HEDGE_BIRCH.get(), Index.HEDGE_SPRUCE.get(), Index.HEDGE_DARK_OAK.get(), Index.HEDGE_JUNGLE.get(), Index.HEDGE_ACACIA.get(),  Index.HEDGE_MANGROVE.get());
	}
	
	public static void clientRegistry() {
		MenuScreens.register(Index.BOOKSHELF_CONTAINER.get(), BookshelfGui::new);
		MenuScreens.register(Index.SHELF_CONTAINER.get(), ShelfGui::new);
		MenuScreens.register(Index.SPEAKER_CONTAINER.get(), SpeakerGui::new);
		MenuScreens.register(Index.ARCADE_CONTAINER.get(), ArcadeGui::new);
		
		// TODO FIX!
    	//ClientRegistry.bindTileEntityRenderer(Index.SHOP_SIGN_TILE_ENTITY_TYPE, ShopSignRenderer::new);
    	//ClientRegistry.bindTileEntityRenderer(Index.SHELF_TILE_ENTITY_TYPE, ShelfRenderer::new);
		BlockEntityRenderers.register(Index.SHOP_SIGN_TILE_ENTITY_TYPE.get(), c -> new ShopSignRenderer(c.getBlockEntityRenderDispatcher()));
		BlockEntityRenderers.register(Index.SHELF_TILE_ENTITY_TYPE.get(), c -> new ShelfRenderer(c.getBlockEntityRenderDispatcher()));
    	
    	//Start MIDI controller.
    	if(Config.MIDI_ENABLED.get() && Config.MIDI_INPUT_ENABLED.get())
    		BuildersAddition.midi = new MidiHandler(null);
    }
}
