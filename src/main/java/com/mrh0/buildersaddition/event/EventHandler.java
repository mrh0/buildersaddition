package com.mrh0.buildersaddition.event;

import com.mrh0.buildersaddition.BuildersAddition;
import com.mrh0.buildersaddition.Index;
import com.mrh0.buildersaddition.entity.SeatEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.FoliageColors;
import net.minecraft.world.IBlockDisplayReader;
import net.minecraft.world.biome.BiomeColors;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber(modid = BuildersAddition.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EventHandler {
	@SubscribeEvent
	public static void registerBlock(Register<Block> evt){
		IForgeRegistry<Block> reg = evt.getRegistry();
		BlockRegistry.instance.initAll(reg);
	}
	
	@SubscribeEvent
	public static void registerItem(Register<Item> evt){
		IForgeRegistry<Item> reg = evt.getRegistry();
		ItemRegistry.instance.initAll(reg);
	}
	
	@SubscribeEvent
	public static void registerColor(ColorHandlerEvent.Block evt){
		IBlockColor bc = (a, b, c, d) -> {
			return b != null && c != null ? BiomeColors.getFoliageColor(b, c) : FoliageColors.getDefault();
		};
		
		evt.getBlockColors().register(bc, Index.HEDGE_OAK, Index.HEDGE_BIRCH, Index.HEDGE_SPRUCE, Index.HEDGE_DARK_OAK, Index.HEDGE_JUNGLE, Index.HEDGE_ACACIA);
	}
	
	@SubscribeEvent
	public static void registerColor(ColorHandlerEvent.Item evt){
		IItemColor ic = (p_210235_1_, p_210235_2_) -> {
			BlockState blockstate = ((BlockItem)p_210235_1_.getItem()).getBlock().getDefaultState();
			return Minecraft.getInstance().getBlockColors().getColor(blockstate, (IBlockDisplayReader)null, (BlockPos)null, p_210235_2_);
		};
		
		evt.getItemColors().register(ic, Index.HEDGE_OAK, Index.HEDGE_BIRCH, Index.HEDGE_SPRUCE, Index.HEDGE_DARK_OAK, Index.HEDGE_JUNGLE, Index.HEDGE_ACACIA);
	}
	
	@SubscribeEvent
    public static void registerEntityType(Register<EntityType<?>> evt) {
    	Index.SEAT_ENTITY_TYPE = EntityType.Builder.<SeatEntity>create(SeatEntity::new, EntityClassification.MISC).setCustomClientFactory((packet,world) -> new SeatEntity(Index.SEAT_ENTITY_TYPE, world)).build(BuildersAddition.MODID+":seat");
    	Index.SEAT_ENTITY_TYPE.setRegistryName(new ResourceLocation(BuildersAddition.MODID, "seat"));
    	evt.getRegistry().register(Index.SEAT_ENTITY_TYPE);
    }
}
