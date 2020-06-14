package com.mrh0.buildersaddition.event;

import com.mrh0.buildersaddition.BuildersAddition;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
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
}
