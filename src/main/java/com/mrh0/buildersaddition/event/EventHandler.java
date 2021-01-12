package com.mrh0.buildersaddition.event;

import com.mrh0.buildersaddition.BuildersAddition;
import com.mrh0.buildersaddition.Index;
import com.mrh0.buildersaddition.entity.SeatEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.PaintingType;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.Item;
import net.minecraft.item.ShovelItem;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber(modid = BuildersAddition.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EventHandler {
	@SubscribeEvent
	public static void registerBlock(Register<Block> evt){
		IForgeRegistry<Block> reg = evt.getRegistry();
		Index.blocks();
		BlockRegistry.instance.initAll(reg);
	}
	
	@SubscribeEvent
	public static void registerItem(Register<Item> evt){
		IForgeRegistry<Item> reg = evt.getRegistry();
		Index.items();
		ItemRegistry.instance.initAll(reg);
	}
	
	@SubscribeEvent
	public static void registerTileEntity(Register<TileEntityType<?>> evt){
		IForgeRegistry<TileEntityType<?>> reg = evt.getRegistry();
		Index.tileentities();
		TileEntityRegistry.instance.initAll(reg);
	}
	
	@SubscribeEvent
	public static void registerContainer(Register<ContainerType<?>> evt){
		IForgeRegistry<ContainerType<?>> reg = evt.getRegistry();
		Index.containers();
		ContainerRegistry.instance.initAll(reg);
	}
	
	@SubscribeEvent
    public static void registerEntityType(Register<EntityType<?>> evt) {
    	Index.SEAT_ENTITY_TYPE = EntityType.Builder.<SeatEntity>create(SeatEntity::new, EntityClassification.MISC).setCustomClientFactory((packet, world) -> new SeatEntity(Index.SEAT_ENTITY_TYPE, world)).build(BuildersAddition.MODID+":seat");
    	Index.SEAT_ENTITY_TYPE.setRegistryName(new ResourceLocation(BuildersAddition.MODID, "seat"));
    	evt.getRegistry().register(Index.SEAT_ENTITY_TYPE);
    }
	
	@SubscribeEvent
	public static void registerPaintings(Register<PaintingType> evt){
		IForgeRegistry<PaintingType> reg = evt.getRegistry();
		
		Index.paintings();
		
		reg.register(Index.SUMMER_FIELD_PAINTING);
		reg.register(Index.SHARD_PAINTING);
		reg.register(Index.SKARGARD_PAINTING);
		reg.register(Index.HORIZONS_PAINTING);
		reg.register(Index.PORTRAIT_PAINTING);
		//reg.register(Index.PROMO_PAINTING);
	}
}
