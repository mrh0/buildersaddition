package com.mrh0.buildersaddition.event;

import com.mrh0.buildersaddition.BuildersAddition;
import com.mrh0.buildersaddition.Index;
import com.mrh0.buildersaddition.data.block.BABlockTagsProvider;
import com.mrh0.buildersaddition.entity.SeatEntity;

import net.minecraft.data.DataGenerator;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.decoration.PaintingVariant;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber(modid = BuildersAddition.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EventHandler {
	/*@SubscribeEvent
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
	public static void registerTileEntity(Register<BlockEntityType<?>> evt){
		IForgeRegistry<BlockEntityType<?>> reg = evt.getRegistry();
		Index.tileentities();
		TileEntityRegistry.instance.initAll(reg);
	}
	
	@SubscribeEvent
	public static void registerContainer(Register<MenuType<?>> evt){
		IForgeRegistry<MenuType<?>> reg = evt.getRegistry();
		Index.containers();
		ContainerRegistry.instance.initAll(reg);
	}
	
	@SubscribeEvent
    public static void registerEntityType(Register<EntityType<?>> evt) {
    	Index.SEAT_ENTITY_TYPE = EntityType.Builder.<SeatEntity>of(SeatEntity::new, MobCategory.MISC).setCustomClientFactory((packet, world) -> new SeatEntity(Index.SEAT_ENTITY_TYPE, world)).build(BuildersAddition.MODID+":seat");
    	//Index.SEAT_ENTITY_TYPE.setRegistryName(new ResourceLocation(BuildersAddition.MODID, "seat"));
    	evt.getRegistry().register(Index.SEAT_ENTITY_TYPE);
    }*/
	
	/*@SubscribeEvent
	public static void registerPaintings(Register<PaintingVariant> evt){
		IForgeRegistry<PaintingVariant> reg = evt.getRegistry();
		
		Index.paintings();
		
		reg.register(Index.SUMMER_FIELD_PAINTING);
		reg.register(Index.SHARD_PAINTING);
		reg.register(Index.SKARGARD_PAINTING);
		reg.register(Index.HORIZONS_PAINTING);
		reg.register(Index.PORTRAIT_PAINTING);
		reg.register(Index.HEROBRINE_PAINTING);
		reg.register(Index.ENDERMAN_PAINTING);
		reg.register(Index.WINTER_PAINTING);
		//reg.register(Index.PROMO_PAINTING);
	}*/

	public static CreativeModeTab MAIN_TAB;
	@SubscribeEvent
	public static void registerCreativeTab(CreativeModeTabEvent.Register event) {
		MAIN_TAB = event.registerCreativeModeTab(
				new ResourceLocation(BuildersAddition.MODID, "builders_addition_group"),
				builder -> builder.icon(() -> new ItemStack(Index.CUT_BRICKS_PILLAR.get(), 1)).title(Component.translatable("itemGroup.buildersaddition:builders_addition_group"))
		);
	}
	
	@SubscribeEvent
	public static void gatherData(GatherDataEvent event) {
		ExistingFileHelper exHelper = event.getExistingFileHelper();
		DataGenerator gen = event.getGenerator();
		if(event.includeServer()) {
			gen.addProvider(true, new BABlockTagsProvider(gen, BuildersAddition.MODID, event.getLookupProvider(), exHelper));
		}
	}
}
