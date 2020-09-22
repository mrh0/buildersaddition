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

import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.BlockItem;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.FoliageColors;
import net.minecraft.world.IBlockDisplayReader;
import net.minecraft.world.biome.BiomeColors;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = BuildersAddition.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEventHandler {
	@SubscribeEvent
	public static void registerColor(ColorHandlerEvent.Block evt){
		IBlockColor bc = (a, b, c, d) -> {
			return b != null && c != null ? BiomeColors.getFoliageColor(b, c) : FoliageColors.getDefault();
		};
		
		evt.getBlockColors().register(bc, Index.HEDGE_OAK, Index.HEDGE_BIRCH, Index.HEDGE_SPRUCE, Index.HEDGE_DARK_OAK, Index.HEDGE_JUNGLE, Index.HEDGE_ACACIA);
	}
	
	@SubscribeEvent
	public static void registerColor(ColorHandlerEvent.Item evt){
		IItemColor ic = (a, b) -> {
			BlockState blockstate = ((BlockItem)a.getItem()).getBlock().getDefaultState();
			return Minecraft.getInstance().getBlockColors().getColor(blockstate, (IBlockDisplayReader)null, (BlockPos)null, b);
		};
		
		evt.getItemColors().register(ic, Index.HEDGE_OAK, Index.HEDGE_BIRCH, Index.HEDGE_SPRUCE, Index.HEDGE_DARK_OAK, Index.HEDGE_JUNGLE, Index.HEDGE_ACACIA);
	}
	
	public static void clientRegistry() {
    	ScreenManager.registerFactory(Index.BOOKSHELF_CONTAINER, BookshelfGui::new);
    	ScreenManager.registerFactory(Index.SHELF_CONTAINER, ShelfGui::new);
    	ScreenManager.registerFactory(Index.SPEAKER_CONTAINER, SpeakerGui::new);
    	ScreenManager.registerFactory(Index.ARCADE_CONTAINER, ArcadeGui::new);
    	
    	ClientRegistry.bindTileEntityRenderer(Index.SHOP_SIGN_TILE_ENTITY_TYPE, ShopSignRenderer::new);
    	ClientRegistry.bindTileEntityRenderer(Index.SHELF_TILE_ENTITY_TYPE, ShelfRenderer::new);
    	
    	//Start MIDI controller.
    	if(Config.MIDI_ENABLED.get() && Config.MIDI_INPUT_ENABLED.get())
    		BuildersAddition.midi = new MidiHandler(null);
    }
}
