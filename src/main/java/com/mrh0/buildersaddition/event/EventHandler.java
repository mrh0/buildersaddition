package com.mrh0.buildersaddition.event;

import com.mrh0.buildersaddition.BuildersAddition;
import com.mrh0.buildersaddition.Index;
import com.mrh0.buildersaddition.entity.SeatEntity;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
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
	public static void registerTileEntity(Register<TileEntityType<?>> evt){
		IForgeRegistry<TileEntityType<?>> reg = evt.getRegistry();
		TileEntityRegistry.instance.initAll(reg);
	}
	
	@SubscribeEvent
	public static void registerContainer(Register<ContainerType<?>> evt){
		IForgeRegistry<ContainerType<?>> reg = evt.getRegistry();
		ContainerRegistry.instance.initAll(reg);
	}
	
	@SubscribeEvent
    public static void registerEntityType(Register<EntityType<?>> evt) {
    	Index.SEAT_ENTITY_TYPE = EntityType.Builder.<SeatEntity>create(SeatEntity::new, EntityClassification.MISC).setCustomClientFactory((packet,world) -> new SeatEntity(Index.SEAT_ENTITY_TYPE, world)).build(BuildersAddition.MODID+":seat");
    	Index.SEAT_ENTITY_TYPE.setRegistryName(new ResourceLocation(BuildersAddition.MODID, "seat"));
    	evt.getRegistry().register(Index.SEAT_ENTITY_TYPE);
    }
}
