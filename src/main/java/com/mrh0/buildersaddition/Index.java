package com.mrh0.buildersaddition;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import com.mrh0.buildersaddition.blocks.Arcade;
import com.mrh0.buildersaddition.blocks.BedsideTable;
import com.mrh0.buildersaddition.blocks.Bench;
import com.mrh0.buildersaddition.blocks.Bookshelf;
import com.mrh0.buildersaddition.blocks.Cabinet;
import com.mrh0.buildersaddition.blocks.Counter;
import com.mrh0.buildersaddition.blocks.Crossrail;
import com.mrh0.buildersaddition.blocks.Cupboard;
import com.mrh0.buildersaddition.blocks.EntityDetector;
import com.mrh0.buildersaddition.blocks.FireplaceGuard;
import com.mrh0.buildersaddition.blocks.GravelPath;
import com.mrh0.buildersaddition.blocks.Hedge;
import com.mrh0.buildersaddition.blocks.IronFence;
import com.mrh0.buildersaddition.blocks.IronLadder;
import com.mrh0.buildersaddition.blocks.Pillar;
import com.mrh0.buildersaddition.blocks.Pillow;
import com.mrh0.buildersaddition.blocks.Planter;
import com.mrh0.buildersaddition.blocks.Shelf;
import com.mrh0.buildersaddition.blocks.ShopSign;
import com.mrh0.buildersaddition.blocks.SmallCupboard;
import com.mrh0.buildersaddition.blocks.Sofa;
import com.mrh0.buildersaddition.blocks.Speaker;
import com.mrh0.buildersaddition.blocks.Stool;
import com.mrh0.buildersaddition.blocks.SupportBracket;
import com.mrh0.buildersaddition.blocks.Table;
import com.mrh0.buildersaddition.blocks.VerticalComparatorBlock;
import com.mrh0.buildersaddition.blocks.VerticalRepeaterBlock;
import com.mrh0.buildersaddition.blocks.VerticalSlab;
import com.mrh0.buildersaddition.blocks.Chair;
import com.mrh0.buildersaddition.blocks.CopperVerticalSlab;
import com.mrh0.buildersaddition.blocks.base.GenericBlock;
import com.mrh0.buildersaddition.blocks.base.IConnects;
import com.mrh0.buildersaddition.container.ArcadeContainer;
import com.mrh0.buildersaddition.container.BookshelfContainer;
import com.mrh0.buildersaddition.container.ShelfContainer;
import com.mrh0.buildersaddition.container.SpeakerContainer;
import com.mrh0.buildersaddition.data.block.BABlockTagsProvider;
import com.mrh0.buildersaddition.entity.SeatEntity;
import com.mrh0.buildersaddition.event.ContainerRegistry;
import com.mrh0.buildersaddition.event.TileEntityRegistry;
import com.mrh0.buildersaddition.event.opts.BlockOptions;
import com.mrh0.buildersaddition.event.opts.ContainerOptions;
import com.mrh0.buildersaddition.event.opts.ItemOptions;
import com.mrh0.buildersaddition.event.opts.TileEntityOptions;
import com.mrh0.buildersaddition.items.base.GenericItem;
import com.mrh0.buildersaddition.tileentity.ArcadeTileEntity;
import com.mrh0.buildersaddition.tileentity.BedsideTileEntity;
import com.mrh0.buildersaddition.tileentity.BookshelfTileEntity;
import com.mrh0.buildersaddition.tileentity.CabinetTileEntity;
import com.mrh0.buildersaddition.tileentity.CounterTileEntity;
import com.mrh0.buildersaddition.tileentity.CupboardTileEntity;
import com.mrh0.buildersaddition.tileentity.EntityDetectorTileEntity;
import com.mrh0.buildersaddition.tileentity.ShelfTileEntity;
import com.mrh0.buildersaddition.tileentity.ShopSignTileEntity;
import com.mrh0.buildersaddition.tileentity.SmallCupboardTileEntity;
import com.mrh0.buildersaddition.tileentity.SpeakerTileEntity;
import com.mrh0.buildersaddition.tileentity.VerticalComparatorTileEntity;
import com.mrh0.buildersaddition.util.RegistryUtil;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.decoration.PaintingVariant;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@SuppressWarnings("unchecked")
public class Index {
	public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, BuildersAddition.MODID);
	public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, BuildersAddition.MODID);
	public static final DeferredRegister<PaintingVariant> PAINTINGS =
            DeferredRegister.create(ForgeRegistries.PAINTING_VARIANTS, BuildersAddition.MODID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, BuildersAddition.MODID);
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(ForgeRegistries.MENU_TYPES, BuildersAddition.MODID);
    public static final DeferredRegister<EntityType<?>> ENTITIES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, BuildersAddition.MODID);
    
    public static void register(IEventBus eventBus) {
    	BLOCKS.register(eventBus);
        ITEMS.register(eventBus);
        PAINTINGS.register(eventBus);
        BLOCK_ENTITIES.register(eventBus);
        MENUS.register(eventBus);
        ENTITIES.register(eventBus);
    }
    
    public static final String[] woods = {"oak", "spruce", "birch", "acacia", "dark_oak", "jungle", "mangrove", "crimson", "warped", "cherry", "bamboo"};
	public static final Block[] woodBlocks = {Blocks.OAK_PLANKS, Blocks.SPRUCE_PLANKS, Blocks.BIRCH_PLANKS, Blocks.DARK_OAK_PLANKS, Blocks.JUNGLE_PLANKS, Blocks.ACACIA_PLANKS, Blocks.MANGROVE_PLANKS, Blocks.CRIMSON_PLANKS, Blocks.WARPED_PLANKS, Blocks.CHERRY_PLANKS, Blocks.BAMBOO_PLANKS};
	public static final String[] colors = {"white", "orange", "magenta", "light_blue", "yellow", "lime", "pink", 
			"gray", "light_gray", "cyan", "purple", "blue", "brown", "green", "red", "black"};
	
	public static RegistryObject<GenericItem> IRON_ROD;
	
	public static RegistryObject<Block> OAK_VERTICAL_SLAB;
	public static RegistryObject<Block> SPRUCE_VERTICAL_SLAB;
	public static RegistryObject<Block> BIRCH_VERTICAL_SLAB;
	public static RegistryObject<Block> JUNGLE_VERTICAL_SLAB;
	public static RegistryObject<Block> ACACIA_VERTICAL_SLAB;
	public static RegistryObject<Block> DARK_OAK_VERTICAL_SLAB;
	public static RegistryObject<Block> STONE_VERTICAL_SLAB;
	public static RegistryObject<Block> SMOOTH_STONE_VERTICAL_SLAB;
	public static RegistryObject<Block> SANDSTONE_VERTICAL_SLAB;
	public static RegistryObject<Block> CUT_SANDSTONE_VERTICAL_SLAB;
	public static RegistryObject<Block> COBBLESTONE_VERTICAL_SLAB;
	public static RegistryObject<Block> BRICKS_VERTICAL_SLAB;
	public static RegistryObject<Block> STONE_BRICKS_VERTICAL_SLAB;
	public static RegistryObject<Block> NETHER_BRICKS_VERTICAL_SLAB;
	public static RegistryObject<Block> QUARTZ_VERTICAL_SLAB;
	public static RegistryObject<Block> RED_SANDSTONE_VERTICAL_SLAB;
	public static RegistryObject<Block> CUT_RED_SANDSTONE_VERTICAL_SLAB;
	public static RegistryObject<Block> PURPUR_VERTICAL_SLAB;
	public static RegistryObject<Block> PRISMARINE_VERTICAL_SLAB;
	public static RegistryObject<Block> PRISMARINE_BRICKS_VERTICAL_SLAB;
	public static RegistryObject<Block> DARK_PRISMARINE_VERTICAL_SLAB;
	public static RegistryObject<Block> POLISHED_GRANITE_VERTICAL_SLAB;
	public static RegistryObject<Block> SMOOTH_RED_SANDSTONE_VERTICAL_SLAB;
	public static RegistryObject<Block> MOSSY_STONE_BRICKS_VERTICAL_SLAB;
	public static RegistryObject<Block> POLISHED_DIORITE_VERTICAL_SLAB;
	public static RegistryObject<Block> MOSSY_COBBLESTONE_VERTICAL_SLAB;
	public static RegistryObject<Block> END_STONE_BRICKS_VERTICAL_SLAB;
	public static RegistryObject<Block> SMOOTH_SANDSTONE_VERTICAL_SLAB;
	public static RegistryObject<Block> SMOOTH_QUARTZ_VERTICAL_SLAB;
	public static RegistryObject<Block> GRANITE_VERTICAL_SLAB;
	public static RegistryObject<Block> ANDESITE_VERTICAL_SLAB;
	public static RegistryObject<Block> RED_NETHER_BRICKS_VERTICAL_SLAB;
	public static RegistryObject<Block> POLISHED_ANDESITE_VERTICAL_SLAB;
	public static RegistryObject<Block> DIORITE_VERTICAL_SLAB;
	public static RegistryObject<Block> CRIMSON_VERTICAL_SLAB;
	public static RegistryObject<Block> WARPED_VERTICAL_SLAB;
	public static RegistryObject<Block> BLACKSTONE_VERTICAL_SLAB;
	public static RegistryObject<Block> POLISHED_BLACKSTONE_VERTICAL_SLAB;
	public static RegistryObject<Block> POLISHED_BLACKSTONE_BRICKS_VERTICAL_SLAB;
	
	public static RegistryObject<Block> COBBLED_DEEPSLATE_VERTICAL_SLAB;
	public static RegistryObject<Block> POLISHED_DEEPSLATE_VERTICAL_SLAB;
	public static RegistryObject<Block> DEEPSLATE_BRICKS_VERTICAL_SLAB;
	public static RegistryObject<Block> DEEPSLATE_TILES_VERTICAL_SLAB;
	
	public static RegistryObject<Block> WAXED_CUT_COPPER_VERTICAL_SLAB;
	public static RegistryObject<Block> WAXED_EXPOSED_CUT_COPPER_VERTICAL_SLAB;
	public static RegistryObject<Block> WAXED_WEATHERED_CUT_COPPER_VERTICAL_SLAB;
	public static RegistryObject<Block> WAXED_OXIDIZED_CUT_COPPER_VERTICAL_SLAB;
	
	public static RegistryObject<Block> CUT_COPPER_VERTICAL_SLAB;
	public static RegistryObject<Block> EXPOSED_CUT_COPPER_VERTICAL_SLAB;
	public static RegistryObject<Block> WEATHERED_CUT_COPPER_VERTICAL_SLAB;
	public static RegistryObject<Block> OXIDIZED_CUT_COPPER_VERTICAL_SLAB;
	
	public static RegistryObject<Block> MUD_BRICK_VERTICAL_SLAB;
	public static RegistryObject<Block> MANGROVE_VERTICAL_SLAB;
	public static RegistryObject<Block> CHERRY_VERTICAL_SLAB;
	public static RegistryObject<Block> BAMBOO_VERTICAL_SLAB;
	
	public static RegistryObject<Block> CUT_STONE_PILLAR;
	public static RegistryObject<Block> CUT_SMOOTH_STONE_PILLAR;
	public static RegistryObject<Block> CUT_SANDSTONE_PILLAR;
	public static RegistryObject<Block> CUT_COBBLESTONE_PILLAR;
	public static RegistryObject<Block> CUT_BRICKS_PILLAR;
	public static RegistryObject<Block> CUT_STONE_BRICKS_PILLAR;
	public static RegistryObject<Block> CUT_NETHER_BRICKS_PILLAR;
	public static RegistryObject<Block> CUT_QUARTZ_PILLAR;
	public static RegistryObject<Block> CUT_RED_SANDSTONE_PILLAR;
	public static RegistryObject<Block> CUT_PURPUR_PILLAR;
	public static RegistryObject<Block> CUT_PRISMARINE_PILLAR;
	public static RegistryObject<Block> CUT_SMOOTH_RED_SANDSTONE_PILLAR;
	public static RegistryObject<Block> CUT_SMOOTH_SANDSTONE_PILLAR;
	public static RegistryObject<Block> CUT_MOSSY_STONE_BRICKS_PILLAR;
	public static RegistryObject<Block> CUT_MOSSY_COBBLESTONE_PILLAR;
	public static RegistryObject<Block> CUT_END_STONE_BRICKS_PILLAR;
	public static RegistryObject<Block> CUT_RED_NETHER_BRICKS_PILLAR;
	public static RegistryObject<Block> CUT_BASALT_PILLAR;
	public static RegistryObject<Block> CUT_BLACKSTONE_PILLAR;
	public static RegistryObject<Block> CUT_QUARTZ_BRICKS_PILLAR;
	public static RegistryObject<Block> CUT_DEEPSLATE_BRICKS_PILLAR;
	public static RegistryObject<Block> CUT_DEEPSLATE_TILES_PILLAR;
	public static RegistryObject<Block> CUT_MUD_BRICKS_PILLAR;
	
	public static RegistryObject<Block> TABLE_OAK;
	public static RegistryObject<Block> TABLE_SPRUCE;
	public static RegistryObject<Block> TABLE_BIRCH;
	public static RegistryObject<Block> TABLE_DARK_OAK;
	public static RegistryObject<Block> TABLE_JUNGLE;
	public static RegistryObject<Block> TABLE_ACACIA;
	public static RegistryObject<Block> TABLE_CRIMSON;
	public static RegistryObject<Block> TABLE_WARPED;
	public static RegistryObject<Block> TABLE_MANGROVE;
	public static RegistryObject<Block> TABLE_CHERRY;
	public static RegistryObject<Block> TABLE_BAMBOO;
	
	public static RegistryObject<Block> STOOL_OAK;
	public static RegistryObject<Block> STOOL_SPRUCE;
	public static RegistryObject<Block> STOOL_BIRCH;
	public static RegistryObject<Block> STOOL_DARK_OAK;
	public static RegistryObject<Block> STOOL_JUNGLE;
	public static RegistryObject<Block> STOOL_ACACIA;
	public static RegistryObject<Block> STOOL_CRIMSON;
	public static RegistryObject<Block> STOOL_WARPED;
	public static RegistryObject<Block> STOOL_MANGROVE;
	public static RegistryObject<Block> STOOL_CHERRY;
	public static RegistryObject<Block> STOOL_BAMBOO;
	
	public static RegistryObject<Block> CHAIR_OAK;
	public static RegistryObject<Block> CHAIR_SPRUCE;
	public static RegistryObject<Block> CHAIR_BIRCH;
	public static RegistryObject<Block> CHAIR_DARK_OAK;
	public static RegistryObject<Block> CHAIR_JUNGLE;
	public static RegistryObject<Block> CHAIR_ACACIA;
	public static RegistryObject<Block> CHAIR_CRIMSON;
	public static RegistryObject<Block> CHAIR_WARPED;
	public static RegistryObject<Block> CHAIR_MANGROVE;
	public static RegistryObject<Block> CHAIR_CHERRY;
	public static RegistryObject<Block> CHAIR_BAMBOO;
	
	public static RegistryObject<Block> PLANTER;
	
	public static RegistryObject<Block> HEDGE_OAK;
	public static RegistryObject<Block> HEDGE_SPRUCE;
	public static RegistryObject<Block> HEDGE_BIRCH;
	public static RegistryObject<Block> HEDGE_DARK_OAK;
	public static RegistryObject<Block> HEDGE_JUNGLE;
	public static RegistryObject<Block> HEDGE_ACACIA;
	public static RegistryObject<Block> HEDGE_MANGROVE;
	public static RegistryObject<Block> HEDGE_CHERRY;
	
	public static RegistryObject<Block>[] COUNTER_ANDESITE = new RegistryObject[11];
	public static RegistryObject<Block>[] COUNTER_DIORITE = new RegistryObject[11];
	public static RegistryObject<Block>[] COUNTER_GRANITE = new RegistryObject[11];
	public static RegistryObject<Block>[] COUNTER_BLACKSTONE = new RegistryObject[11];
	public static RegistryObject<Block>[] COUNTER_DEEPSLATE = new RegistryObject[11];
	public static RegistryObject<Block>[] COUNTER_ALL = new RegistryObject[11*5];
	
	public static RegistryObject<Block> BOOKSHELF_OAK;
	public static RegistryObject<Block> BOOKSHELF_BIRCH;
	public static RegistryObject<Block> BOOKSHELF_SPRUCE;
	public static RegistryObject<Block> BOOKSHELF_DARK_OAK;
	public static RegistryObject<Block> BOOKSHELF_JUNGLE;
	public static RegistryObject<Block> BOOKSHELF_ACACIA;
	public static RegistryObject<Block> BOOKSHELF_WARPED;
	public static RegistryObject<Block> BOOKSHELF_CRIMSON;
	public static RegistryObject<Block> BOOKSHELF_MANGROVE;
	public static RegistryObject<Block> BOOKSHELF_CHERRY;
	public static RegistryObject<Block> BOOKSHELF_BAMBOO;
	
	public static RegistryObject<Block> SHELF_OAK;
	public static RegistryObject<Block> SHELF_BIRCH;
	public static RegistryObject<Block> SHELF_SPRUCE;
	public static RegistryObject<Block> SHELF_DARK_OAK;
	public static RegistryObject<Block> SHELF_JUNGLE;
	public static RegistryObject<Block> SHELF_ACACIA;
	public static RegistryObject<Block> SHELF_WARPED;
	public static RegistryObject<Block> SHELF_CRIMSON;
	public static RegistryObject<Block> SHELF_MANGROVE;
	public static RegistryObject<Block> SHELF_CHERRY;
	public static RegistryObject<Block> SHELF_BAMBOO;
	
	public static RegistryObject<Block> CABINET_OAK;
	public static RegistryObject<Block> CABINET_BIRCH;
	public static RegistryObject<Block> CABINET_SPRUCE;
	public static RegistryObject<Block> CABINET_DARK_OAK;
	public static RegistryObject<Block> CABINET_JUNGLE;
	public static RegistryObject<Block> CABINET_ACACIA;
	public static RegistryObject<Block> CABINET_WARPED;
	public static RegistryObject<Block> CABINET_CRIMSON;
	public static RegistryObject<Block> CABINET_MANGROVE;
	public static RegistryObject<Block> CABINET_CHERRY;
	public static RegistryObject<Block> CABINET_BAMBOO;
	
	public static RegistryObject<Block> CUPBOARD_OAK;
	public static RegistryObject<Block> CUPBOARD_BIRCH;
	public static RegistryObject<Block> CUPBOARD_SPRUCE;
	public static RegistryObject<Block> CUPBOARD_DARK_OAK;
	public static RegistryObject<Block> CUPBOARD_JUNGLE;
	public static RegistryObject<Block> CUPBOARD_ACACIA;
	public static RegistryObject<Block> CUPBOARD_WARPED;
	public static RegistryObject<Block> CUPBOARD_CRIMSON;
	public static RegistryObject<Block> CUPBOARD_MANGROVE;
	public static RegistryObject<Block> CUPBOARD_CHERRY;
	public static RegistryObject<Block> CUPBOARD_BAMBOO;
	
	public static RegistryObject<Block> SMALL_CUPBOARD_OAK;
	public static RegistryObject<Block> SMALL_CUPBOARD_BIRCH;
	public static RegistryObject<Block> SMALL_CUPBOARD_SPRUCE;
	public static RegistryObject<Block> SMALL_CUPBOARD_DARK_OAK;
	public static RegistryObject<Block> SMALL_CUPBOARD_JUNGLE;
	public static RegistryObject<Block> SMALL_CUPBOARD_ACACIA;
	public static RegistryObject<Block> SMALL_CUPBOARD_WARPED;
	public static RegistryObject<Block> SMALL_CUPBOARD_CRIMSON;
	public static RegistryObject<Block> SMALL_CUPBOARD_MANGROVE;
	public static RegistryObject<Block> SMALL_CUPBOARD_CHERRY;
	public static RegistryObject<Block> SMALL_CUPBOARD_BAMBOO;
	
	public static RegistryObject<Block> BENCH_OAK;
	public static RegistryObject<Block> BENCH_BIRCH;
	public static RegistryObject<Block> BENCH_SPRUCE;
	public static RegistryObject<Block> BENCH_DARK_OAK;
	public static RegistryObject<Block> BENCH_JUNGLE;
	public static RegistryObject<Block> BENCH_ACACIA;
	public static RegistryObject<Block> BENCH_WARPED;
	public static RegistryObject<Block> BENCH_CRIMSON;
	public static RegistryObject<Block> BENCH_MANGROVE;
	public static RegistryObject<Block> BENCH_CHERRY;
	public static RegistryObject<Block> BENCH_BAMBOO;
	
	public static RegistryObject<Block> SUPPORT_BRACKET_OAK;
	public static RegistryObject<Block> SUPPORT_BRACKET_BIRCH;
	public static RegistryObject<Block> SUPPORT_BRACKET_SPRUCE;
	public static RegistryObject<Block> SUPPORT_BRACKET_DARK_OAK;
	public static RegistryObject<Block> SUPPORT_BRACKET_JUNGLE;
	public static RegistryObject<Block> SUPPORT_BRACKET_ACACIA;
	public static RegistryObject<Block> SUPPORT_BRACKET_WARPED;
	public static RegistryObject<Block> SUPPORT_BRACKET_CRIMSON;
	public static RegistryObject<Block> SUPPORT_BRACKET_MANGROVE;
	public static RegistryObject<Block> SUPPORT_BRACKET_CHERRY;
	public static RegistryObject<Block> SUPPORT_BRACKET_BAMBOO;
	
	public static RegistryObject<Block> SOFA[] = new RegistryObject[16];

	public static RegistryObject<Block> PILLOW[] = new RegistryObject[16];
	
	public static RegistryObject<Block>[] BEDSIDE_TABLE_ALL = new RegistryObject[11];
	
	public static RegistryObject<Block> SHOP_SIGN_WOOD;
	public static RegistryObject<Block> IRON_LADDER;
	public static RegistryObject<Block> IRON_LADDER_ROUGH;
	public static RegistryObject<Block> IRON_FENCE;
	public static RegistryObject<Block> IRON_FENCE_ROUGH;
	public static RegistryObject<Block> ROUGH_IRON_BLOCK;
	public static RegistryObject<Block> GRAVEL_PATH;
	public static RegistryObject<Block> CROSSRAIL;
	public static RegistryObject<Block> SPEAKER;
	public static RegistryObject<Block> LARGE_CANDLE;
	public static RegistryObject<Block> LARGE_SOUL_CANDLE;
	public static RegistryObject<Block> CANDLE;
	public static RegistryObject<Block> SOUL_CANDLE;
	
	//public static RegistryObject<Block> ARCADE;
	
	public static RegistryObject<Block> ARCADE_OAK;
	public static RegistryObject<Block> ARCADE_BIRCH;
	public static RegistryObject<Block> ARCADE_SPRUCE;
	public static RegistryObject<Block> ARCADE_DARK_OAK;
	public static RegistryObject<Block> ARCADE_JUNGLE;
	public static RegistryObject<Block> ARCADE_ACACIA;
	public static RegistryObject<Block> ARCADE_WARPED;
	public static RegistryObject<Block> ARCADE_CRIMSON;
	public static RegistryObject<Block> ARCADE_MANGROVE;
	public static RegistryObject<Block> ARCADE_CHERRY;
	public static RegistryObject<Block> ARCADE_BAMBOO;
	
	public static RegistryObject<Block> FIREPLACE_GUARD;
	public static RegistryObject<Block> ROUGH_FIREPLACE_GUARD;
	
	public static RegistryObject<Block> VERTICAL_REPEATER;
	public static RegistryObject<Block> VERTICAL_COMPARATOR;
	
	public static RegistryObject<Block> ENTITY_DETECTOR;
	
	public static RegistryObject<EntityType<SeatEntity>> SEAT_ENTITY_TYPE;
	
	public static RegistryObject<BlockEntityType<CounterTileEntity>> COUNTER_TILE_ENTITY_TYPE;
	public static RegistryObject<BlockEntityType<CupboardTileEntity>> CUPBOARD_TILE_ENTITY_TYPE;
	public static RegistryObject<BlockEntityType<SmallCupboardTileEntity>> SMALL_CUPBOARD_TILE_ENTITY_TYPE;
	public static RegistryObject<BlockEntityType<CabinetTileEntity>> CABINET_TILE_ENTITY_TYPE;
	public static RegistryObject<BlockEntityType<BookshelfTileEntity>> BOOKSHELF_TILE_ENTITY_TYPE;
	public static RegistryObject<BlockEntityType<ShelfTileEntity>> SHELF_TILE_ENTITY_TYPE;
	public static RegistryObject<BlockEntityType<ShopSignTileEntity>> SHOP_SIGN_TILE_ENTITY_TYPE;
	public static RegistryObject<BlockEntityType<BedsideTileEntity>> BEDSIDE_TILE_ENTITY_TYPE;
	public static RegistryObject<BlockEntityType<SpeakerTileEntity>> SPEAKER_TILE_ENTITY_TYPE;
	public static RegistryObject<BlockEntityType<ArcadeTileEntity>> ARCADE_TILE_ENTITY_TYPE;
	public static RegistryObject<BlockEntityType<EntityDetectorTileEntity>> ENTITY_DETECTOR_TILE_ENTITY_TYPE;
	public static RegistryObject<BlockEntityType<VerticalComparatorTileEntity>> VERTICAL_COMPARATOR_TILE_ENTITY_TYPE;

	public static RegistryObject<MenuType<BookshelfContainer>> BOOKSHELF_CONTAINER;
	public static RegistryObject<MenuType<ShelfContainer>> SHELF_CONTAINER;
	public static RegistryObject<MenuType<SpeakerContainer>> SPEAKER_CONTAINER;
	public static RegistryObject<MenuType<ArcadeContainer>> ARCADE_CONTAINER;

	public static RegistryObject<PaintingVariant> SUMMER_FIELD_PAINTING;
	public static RegistryObject<PaintingVariant> SHARD_PAINTING;
	public static RegistryObject<PaintingVariant> SKARGARD_PAINTING;
	public static RegistryObject<PaintingVariant> HORIZONS_PAINTING;
	public static RegistryObject<PaintingVariant> PORTRAIT_PAINTING;
	public static RegistryObject<PaintingVariant> PROMO_PAINTING;
	public static RegistryObject<PaintingVariant> HEROBRINE_PAINTING;
	public static RegistryObject<PaintingVariant> ENDERMAN_PAINTING;
	public static RegistryObject<PaintingVariant> WINTER_PAINTING;

	public static List<RegistryObject<Item>> ALL_ITEMS;
	
	public static void items() {
		IRON_ROD = ITEMS.register("iron_rod", () -> new GenericItem("iron_rod", new Properties(), new ItemOptions()));
	}
	
	public static RegistryObject<Block> block(String name, Supplier<Block> block) {
		var terms = new String[] {
				"oak", "spruce", "birch", "acacia", "dark_oak", "jungle", "crimson", "warped", "mangrove", "cherry", "bamboo",
				"wood", "speaker", "planter"
		};

		RegistryObject<Block> b = BLOCKS.register(name, block);
		ITEMS.register(name, () -> new BlockItem(b.get(), new Item.Properties()));

		for(String term : terms) {
			if (name.contains(term)) {
				BABlockTagsProvider.woodenBlocks.add(b.getKey());
				break;
			}
		}

		var found = false;
		for(String term : terms)
			if (name.contains(term)) found = true;
		if(!found) BABlockTagsProvider.stoneBlocks.add(b.getKey());
		if(name.contains("detector") || name.contains("rail")) BABlockTagsProvider.stoneBlocks.add(b.getKey());

		if(name.contains("hedge")) BABlockTagsProvider.leavesBlocks.add(b.getKey());

		if(name.contains("iron") || name.contains("guard") || name.contains("rail")) BABlockTagsProvider.ironBlocks.add(b.getKey());
		if(name.contains("gravel")) BABlockTagsProvider.earthBlocks.add(b.getKey());
		return b;
	}
	
	public static RegistryObject<Block> blockNoItem(String name, Supplier<Block> block) {
		RegistryObject<Block> b = BLOCKS.register(name, block);
		return b;
	}
	
	public static void blocks() {
		final IConnects cobbleConnector = (state, source) -> { 
			return state.getBlock() == CUT_MOSSY_COBBLESTONE_PILLAR.get() || state.getBlock() == CUT_COBBLESTONE_PILLAR.get(); 
		};
		final IConnects sandstoneConnector = (state, source) -> { 
			return state.getBlock() == CUT_SMOOTH_RED_SANDSTONE_PILLAR.get() || state.getBlock() == CUT_SMOOTH_SANDSTONE_PILLAR.get() || state.getBlock() == CUT_RED_SANDSTONE_PILLAR.get() || state.getBlock() == CUT_SANDSTONE_PILLAR.get(); 
		};
		final IConnects stoneBricksConnector = (state, source) -> { 
			return state.getBlock() == CUT_STONE_BRICKS_PILLAR.get() || state.getBlock() == CUT_MOSSY_STONE_BRICKS_PILLAR.get()  || state.getBlock() == CUT_BLACKSTONE_PILLAR.get() || state.getBlock() == CUT_DEEPSLATE_BRICKS_PILLAR.get() || state.getBlock() == CUT_MUD_BRICKS_PILLAR.get(); 
		};
		final IConnects netherBricksConnector = (state, source) -> { 
			return state.getBlock() == CUT_NETHER_BRICKS_PILLAR.get() || state.getBlock() == CUT_RED_NETHER_BRICKS_PILLAR.get(); 
		};
		final IConnects quartzConnector = (state, source) -> { 
			return state.getBlock() == CUT_QUARTZ_PILLAR.get() || state.getBlock() == CUT_QUARTZ_BRICKS_PILLAR.get(); 
		};
		final IConnects deepslateConnector = (state, source) -> { 
			return state.getBlock() == CUT_DEEPSLATE_TILES_PILLAR.get() || state.getBlock() == CUT_DEEPSLATE_BRICKS_PILLAR.get(); 
		};
		final IConnects deepslateBricksConnector = (state, source) -> { 
			return deepslateConnector.connect(state, source) || stoneBricksConnector.connect(state, source); 
		};
		
		OAK_VERTICAL_SLAB = block("oak_vertical_slab", () -> new VerticalSlab("oak", Blocks.OAK_PLANKS));
		SPRUCE_VERTICAL_SLAB = block("spruce_vertical_slab", () -> new VerticalSlab("spruce", Blocks.SPRUCE_PLANKS));
		BIRCH_VERTICAL_SLAB = block("birch_vertical_slab", () -> new VerticalSlab("birch", Blocks.BIRCH_PLANKS));
		JUNGLE_VERTICAL_SLAB = block("jungle_vertical_slab", () -> new VerticalSlab("jungle", Blocks.JUNGLE_PLANKS));
		ACACIA_VERTICAL_SLAB = block("acacia_vertical_slab", () -> new VerticalSlab("acacia", Blocks.ACACIA_PLANKS));
		DARK_OAK_VERTICAL_SLAB = block("dark_oak_vertical_slab", () -> new VerticalSlab("dark_oak", Blocks.DARK_OAK_PLANKS));
		STONE_VERTICAL_SLAB = block("stone_vertical_slab", () -> new VerticalSlab("stone", Blocks.STONE));
		SMOOTH_STONE_VERTICAL_SLAB = block("smooth_stone_vertical_slab", () -> new VerticalSlab("smooth_stone", Blocks.SMOOTH_STONE));
		SANDSTONE_VERTICAL_SLAB = block("sandstone_vertical_slab", () -> new VerticalSlab("sandstone", Blocks.SANDSTONE));
		CUT_SANDSTONE_VERTICAL_SLAB = block("cut_sandstone_vertical_slab", () -> new VerticalSlab("cut_sandstone", Blocks.CUT_SANDSTONE));
		COBBLESTONE_VERTICAL_SLAB = block("cobblestone_vertical_slab", () -> new VerticalSlab("cobblestone", Blocks.COBBLESTONE));
		BRICKS_VERTICAL_SLAB = block("bricks_vertical_slab", () -> new VerticalSlab("bricks", Blocks.BRICKS));
		STONE_BRICKS_VERTICAL_SLAB = block("stone_bricks_vertical_slab", () -> new VerticalSlab("stone_bricks", Blocks.STONE_BRICKS));
		NETHER_BRICKS_VERTICAL_SLAB = block("nether_bricks_vertical_slab", () -> new VerticalSlab("nether_bricks", Blocks.NETHER_BRICKS));
		QUARTZ_VERTICAL_SLAB = block("quartz_vertical_slab", () -> new VerticalSlab("quartz", Blocks.QUARTZ_BLOCK));
		RED_SANDSTONE_VERTICAL_SLAB = block("red_sandstone_vertical_slab", () -> new VerticalSlab("red_sandstone", Blocks.RED_SANDSTONE));
		CUT_RED_SANDSTONE_VERTICAL_SLAB = block("cut_red_sandstone_vertical_slab", () -> new VerticalSlab("cut_red_sandstone", Blocks.CUT_RED_SANDSTONE));
		PURPUR_VERTICAL_SLAB = block("purpur_vertical_slab", () -> new VerticalSlab("purpur", Blocks.PURPUR_BLOCK));
		PRISMARINE_VERTICAL_SLAB = block("prismarine_vertical_slab", () -> new VerticalSlab("prismarine", Blocks.PRISMARINE));
		PRISMARINE_BRICKS_VERTICAL_SLAB = block("prismarine_bricks_vertical_slab", () -> new VerticalSlab("prismarine_bricks", Blocks.PRISMARINE_BRICKS));
		DARK_PRISMARINE_VERTICAL_SLAB = block("dark_prismarine_vertical_slab", () -> new VerticalSlab("dark_prismarine", Blocks.DARK_PRISMARINE));
		POLISHED_GRANITE_VERTICAL_SLAB = block("polished_granite_vertical_slab", () -> new VerticalSlab("polished_granite", Blocks.POLISHED_GRANITE));
		SMOOTH_RED_SANDSTONE_VERTICAL_SLAB = block("smooth_red_sandstone_vertical_slab", () -> new VerticalSlab("smooth_red_sandstone", Blocks.SMOOTH_RED_SANDSTONE));
		MOSSY_STONE_BRICKS_VERTICAL_SLAB = block("mossy_stone_bricks_vertical_slab", () -> new VerticalSlab("mossy_stone_bricks", Blocks.MOSSY_STONE_BRICKS));
		POLISHED_DIORITE_VERTICAL_SLAB = block("polished_diorite_vertical_slab", () -> new VerticalSlab("polished_diorite", Blocks.POLISHED_DIORITE));
		MOSSY_COBBLESTONE_VERTICAL_SLAB = block("mossy_cobblestone_vertical_slab", () -> new VerticalSlab("mossy_cobblestone", Blocks.MOSSY_COBBLESTONE));
		END_STONE_BRICKS_VERTICAL_SLAB = block("end_stone_bricks_vertical_slab", () -> new VerticalSlab("end_stone_bricks", Blocks.END_STONE_BRICKS));
		SMOOTH_SANDSTONE_VERTICAL_SLAB = block("smooth_sandstone_vertical_slab", () -> new VerticalSlab("smooth_sandstone", Blocks.SMOOTH_SANDSTONE));
		SMOOTH_QUARTZ_VERTICAL_SLAB = block("smooth_quartz_vertical_slab", () -> new VerticalSlab("smooth_quartz", Blocks.SMOOTH_QUARTZ));
		GRANITE_VERTICAL_SLAB = block("granite_vertical_slab", () -> new VerticalSlab("granite", Blocks.GRANITE));
		ANDESITE_VERTICAL_SLAB = block("andesite_vertical_slab", () -> new VerticalSlab("andesite", Blocks.ANDESITE));
		RED_NETHER_BRICKS_VERTICAL_SLAB = block("red_nether_bricks_vertical_slab", () -> new VerticalSlab("red_nether_bricks", Blocks.RED_NETHER_BRICKS));
		POLISHED_ANDESITE_VERTICAL_SLAB = block("polished_andesite_vertical_slab", () -> new VerticalSlab("polished_andesite", Blocks.POLISHED_ANDESITE));
		DIORITE_VERTICAL_SLAB = block("diorite_vertical_slab", () -> new VerticalSlab("diorite", Blocks.DIORITE));
		CRIMSON_VERTICAL_SLAB = block("crimson_vertical_slab", () -> new VerticalSlab("crimson", Blocks.CRIMSON_PLANKS));
		WARPED_VERTICAL_SLAB = block("warped_vertical_slab", () -> new VerticalSlab("warped", Blocks.WARPED_PLANKS));
		BLACKSTONE_VERTICAL_SLAB = block("blackstone_vertical_slab", () -> new VerticalSlab("blackstone", Blocks.BLACKSTONE));
		POLISHED_BLACKSTONE_VERTICAL_SLAB = block("polished_blackstone_vertical_slab", () -> new VerticalSlab("polished_blackstone", Blocks.POLISHED_BLACKSTONE));
		POLISHED_BLACKSTONE_BRICKS_VERTICAL_SLAB = block("polished_blackstone_bricks_vertical_slab", () -> new VerticalSlab("polished_blackstone_bricks", Blocks.POLISHED_BLACKSTONE_BRICKS));
		COBBLED_DEEPSLATE_VERTICAL_SLAB = block("cobbled_deepslate_vertical_slab", () -> new VerticalSlab("cobbled_deepslate", Blocks.COBBLED_DEEPSLATE));
		POLISHED_DEEPSLATE_VERTICAL_SLAB = block("polished_deepslate_vertical_slab", () -> new VerticalSlab("polished_deepslate", Blocks.POLISHED_DEEPSLATE));
		DEEPSLATE_BRICKS_VERTICAL_SLAB = block("deepslate_bricks_vertical_slab", () -> new VerticalSlab("deepslate_bricks", Blocks.DEEPSLATE_BRICKS));
		DEEPSLATE_TILES_VERTICAL_SLAB = block("deepslate_tiles_vertical_slab", () -> new VerticalSlab("deepslate_tiles", Blocks.DEEPSLATE_TILES));
		WAXED_CUT_COPPER_VERTICAL_SLAB = block("waxed_cut_copper_vertical_slab", () -> new VerticalSlab("waxed_cut_copper", Blocks.COPPER_BLOCK));
		WAXED_EXPOSED_CUT_COPPER_VERTICAL_SLAB = block("waxed_exposed_cut_copper_vertical_slab", () -> new VerticalSlab("waxed_exposed_cut_copper", Blocks.COPPER_BLOCK));
		WAXED_WEATHERED_CUT_COPPER_VERTICAL_SLAB = block("waxed_weathered_cut_copper_vertical_slab", () -> new VerticalSlab("waxed_weathered_cut_copper", Blocks.COPPER_BLOCK));
		WAXED_OXIDIZED_CUT_COPPER_VERTICAL_SLAB = block("waxed_oxidized_cut_copper_vertical_slab", () -> new VerticalSlab("waxed_oxidized_cut_copper", Blocks.COPPER_BLOCK));
		CUT_COPPER_VERTICAL_SLAB = block("cut_copper_vertical_slab", () -> new CopperVerticalSlab("cut_copper", Blocks.COPPER_BLOCK, WeatheringCopper.WeatherState.UNAFFECTED));
		EXPOSED_CUT_COPPER_VERTICAL_SLAB = block("exposed_cut_copper_vertical_slab", () -> new CopperVerticalSlab("exposed_cut_copper", Blocks.COPPER_BLOCK, WeatheringCopper.WeatherState.EXPOSED));
		WEATHERED_CUT_COPPER_VERTICAL_SLAB = block("weathered_cut_copper_vertical_slab", () -> new CopperVerticalSlab("weathered_cut_copper", Blocks.COPPER_BLOCK, WeatheringCopper.WeatherState.WEATHERED));
		OXIDIZED_CUT_COPPER_VERTICAL_SLAB = block("oxidized_cut_copper_vertical_slab", () -> new CopperVerticalSlab("oxidized_cut_copper", Blocks.COPPER_BLOCK, WeatheringCopper.WeatherState.OXIDIZED));
		MANGROVE_VERTICAL_SLAB = block("mangrove_vertical_slab", () -> new VerticalSlab("mangrove", Blocks.MANGROVE_PLANKS));
		CHERRY_VERTICAL_SLAB = block("cherry_vertical_slab", () -> new VerticalSlab("cherry", Blocks.CHERRY_PLANKS));
		BAMBOO_VERTICAL_SLAB = block("bamboo_vertical_slab", () -> new VerticalSlab("bamboo", Blocks.BAMBOO_PLANKS));
		MUD_BRICK_VERTICAL_SLAB = block("mud_bricks_vertical_slab", () -> new VerticalSlab("mud_bricks", Blocks.MUD_BRICKS));
		
		CUT_STONE_PILLAR = block("cut_stone_pillar", () -> new Pillar("stone", Blocks.STONE));
		CUT_SMOOTH_STONE_PILLAR = block("cut_smooth_stone_pillar", () -> new Pillar("smooth_stone", Blocks.SMOOTH_STONE));
		CUT_SANDSTONE_PILLAR = block("cut_sandstone_pillar", () -> new Pillar("sandstone", Blocks.SANDSTONE, sandstoneConnector));
		CUT_COBBLESTONE_PILLAR = block("cut_cobblestone_pillar", () -> new Pillar("cobblestone", Blocks.COBBLESTONE, cobbleConnector));
		CUT_BRICKS_PILLAR = block("cut_bricks_pillar", () -> new Pillar("bricks", Blocks.BRICKS));
		CUT_STONE_BRICKS_PILLAR = block("cut_stone_bricks_pillar", () -> new Pillar("stone_bricks", Blocks.STONE_BRICKS, stoneBricksConnector));
		CUT_NETHER_BRICKS_PILLAR = block("cut_nether_bricks_pillar", () -> new Pillar("nether_bricks", Blocks.NETHER_BRICKS, netherBricksConnector));
		CUT_QUARTZ_PILLAR = block("cut_quartz_pillar", () -> new Pillar("quartz", Blocks.QUARTZ_BLOCK, quartzConnector));
		CUT_RED_SANDSTONE_PILLAR = block("cut_red_sandstone_pillar", () -> new Pillar("red_sandstone", Blocks.RED_SANDSTONE, sandstoneConnector));
		CUT_PURPUR_PILLAR = block("cut_purpur_pillar", () -> new Pillar("purpur", Blocks.PURPUR_PILLAR));
		CUT_PRISMARINE_PILLAR = block("cut_prismarine_pillar", () -> new Pillar("prismarine", Blocks.PRISMARINE, 15));
		CUT_SMOOTH_RED_SANDSTONE_PILLAR = block("cut_smooth_red_sandstone_pillar", () -> new Pillar("smooth_red_sandstone", Blocks.SMOOTH_RED_SANDSTONE, sandstoneConnector));
		CUT_SMOOTH_SANDSTONE_PILLAR = block("cut_smooth_sandstone_pillar", () -> new Pillar("smooth_sandstone", Blocks.SMOOTH_SANDSTONE, sandstoneConnector));
		CUT_MOSSY_STONE_BRICKS_PILLAR = block("cut_mossy_stone_bricks_pillar", () -> new Pillar("mossy_stone_bricks", Blocks.MOSSY_STONE_BRICKS, stoneBricksConnector));
		CUT_MOSSY_COBBLESTONE_PILLAR = block("cut_mossy_cobblestone_pillar", () -> new Pillar("mossy_cobblestone", Blocks.MOSSY_COBBLESTONE, cobbleConnector));
		CUT_END_STONE_BRICKS_PILLAR = block("cut_end_stone_bricks_pillar", () -> new Pillar("end_stone_bricks", Blocks.END_STONE_BRICKS));
		CUT_RED_NETHER_BRICKS_PILLAR = block("cut_red_nether_bricks_pillar", () -> new Pillar("red_nether_bricks", Blocks.RED_NETHER_BRICKS, netherBricksConnector));
		CUT_BASALT_PILLAR = block("cut_basalt_pillar", () -> new Pillar("basalt", Blocks.BASALT));
		CUT_BLACKSTONE_PILLAR = block("cut_blackstone_pillar", () -> new Pillar("blackstone", Blocks.BLACKSTONE, stoneBricksConnector));
		CUT_QUARTZ_BRICKS_PILLAR = block("cut_quartz_bricks_pillar", () -> new Pillar("quartz_bricks", Blocks.QUARTZ_BLOCK, quartzConnector));
		CUT_DEEPSLATE_BRICKS_PILLAR = block("cut_deepslate_bricks_pillar", () -> new Pillar("deepslate_bricks", Blocks.DEEPSLATE_BRICKS, deepslateBricksConnector));
		CUT_DEEPSLATE_TILES_PILLAR = block("cut_deepslate_tiles_pillar", () -> new Pillar("deepslate_tiles", Blocks.DEEPSLATE_TILES, deepslateConnector));
		CUT_MUD_BRICKS_PILLAR = block("cut_mud_bricks_pillar", () -> new Pillar("mud_bricks", Blocks.MUD_BRICKS, stoneBricksConnector));
		
		TABLE_OAK = block("table_oak", () -> new Table("oak", Blocks.OAK_PLANKS));
		TABLE_SPRUCE = block("table_spruce", () -> new Table("spruce", Blocks.SPRUCE_PLANKS));
		TABLE_BIRCH = block("table_birch", () -> new Table("birch", Blocks.BIRCH_PLANKS));
		TABLE_DARK_OAK = block("table_dark_oak", () -> new Table("dark_oak", Blocks.DARK_OAK_PLANKS));
		TABLE_JUNGLE = block("table_jungle", () -> new Table("jungle", Blocks.JUNGLE_PLANKS));
		TABLE_ACACIA = block("table_acacia", () -> new Table("acacia", Blocks.ACACIA_PLANKS));
		TABLE_CRIMSON = block("table_crimson", () -> new Table("crimson", Blocks.CRIMSON_PLANKS));
		TABLE_WARPED = block("table_warped", () -> new Table("warped", Blocks.WARPED_PLANKS));
		TABLE_MANGROVE = block("table_mangrove", () -> new Table("mangrove", Blocks.MANGROVE_PLANKS));
		TABLE_CHERRY = block("table_cherry", () -> new Table("cherry", Blocks.CHERRY_PLANKS));
		TABLE_BAMBOO = block("table_bamboo", () -> new Table("mangrove", Blocks.BAMBOO_PLANKS));
		
		for(int i = 0; i < colors.length; i++) {
			final String color = colors[i];
			PILLOW[i] = block("pillow_" + color, () -> new Pillow("pillow_" + color));
		}
		
		STOOL_OAK = block("stool_oak", () -> new Stool("stool_oak", Blocks.OAK_PLANKS));
		STOOL_SPRUCE = block("stool_spruce", () -> new Stool("stool_spruce", Blocks.SPRUCE_PLANKS));
		STOOL_BIRCH = block("stool_birch", () -> new Stool("stool_birch", Blocks.BIRCH_PLANKS));
		STOOL_DARK_OAK = block("stool_dark_oak", () -> new Stool("stool_dark_oak", Blocks.DARK_OAK_PLANKS));
		STOOL_JUNGLE = block("stool_jungle", () -> new Stool("stool_jungle", Blocks.JUNGLE_PLANKS));
		STOOL_ACACIA = block("stool_acacia", () -> new Stool("stool_acacia", Blocks.ACACIA_PLANKS));
		STOOL_CRIMSON = block("stool_crimson", () -> new Stool("stool_crimson", Blocks.CRIMSON_PLANKS));
		STOOL_WARPED = block("stool_warped", () -> new Stool("stool_warped", Blocks.WARPED_PLANKS));
		STOOL_MANGROVE = block("stool_mangrove", () -> new Stool("stool_mangrove", Blocks.MANGROVE_PLANKS));
		STOOL_CHERRY = block("stool_cherry", () -> new Stool("stool_cherry", Blocks.CHERRY_PLANKS));
		STOOL_BAMBOO = block("stool_bamboo", () -> new Stool("stool_bamboo", Blocks.BAMBOO_PLANKS));
		
		CHAIR_OAK = block("chair_oak", () -> new Chair("chair_oak", Blocks.OAK_PLANKS));
		CHAIR_SPRUCE = block("chair_spruce", () -> new Chair("chair_spruce", Blocks.SPRUCE_PLANKS));
		CHAIR_BIRCH = block("chair_birch", () -> new Chair("chair_birch", Blocks.BIRCH_PLANKS));
		CHAIR_DARK_OAK = block("chair_dark_oak", () -> new Chair("chair_dark_oak", Blocks.DARK_OAK_PLANKS));
		CHAIR_JUNGLE = block("chair_jungle", () -> new Chair("chair_jungle", Blocks.JUNGLE_PLANKS));
		CHAIR_ACACIA = block("chair_acacia", () -> new Chair("chair_acacia", Blocks.ACACIA_PLANKS));
		CHAIR_CRIMSON = block("chair_crimson", () -> new Chair("chair_crimson", Blocks.CRIMSON_PLANKS));
		CHAIR_WARPED = block("chair_warped", () -> new Chair("chair_warped", Blocks.WARPED_PLANKS));
		CHAIR_MANGROVE = block("chair_mangrove", () -> new Chair("chair_mangrove", Blocks.MANGROVE_PLANKS));
		CHAIR_CHERRY = block("chair_cherry", () -> new Chair("chair_cherry", Blocks.CHERRY_PLANKS));
		CHAIR_BAMBOO = block("chair_bamboo", () -> new Chair("chair_bamboo", Blocks.BAMBOO_PLANKS));

		HEDGE_OAK = block("hedge_oak", () -> new Hedge("oak", Blocks.OAK_LEAVES));
		HEDGE_SPRUCE = block("hedge_spruce", () -> new Hedge("spruce", Blocks.SPRUCE_LEAVES));
		HEDGE_BIRCH = block("hedge_birch", () -> new Hedge("birch", Blocks.BIRCH_LEAVES));
		HEDGE_DARK_OAK = block("hedge_dark_oak", () -> new Hedge("dark_oak", Blocks.DARK_OAK_LEAVES));
		HEDGE_JUNGLE = block("hedge_jungle", () -> new Hedge("jungle", Blocks.JUNGLE_LEAVES));
		HEDGE_ACACIA = block("hedge_acacia", () -> new Hedge("acacia", Blocks.ACACIA_LEAVES));
		HEDGE_MANGROVE = block("hedge_mangrove", () -> new Hedge("mangrove", Blocks.MANGROVE_LEAVES));
		HEDGE_CHERRY = block("hedge_cherry", () -> new Hedge("cherry", Blocks.CHERRY_LEAVES));
		
		for(int i = 0; i < woods.length; i++) {
			final String wood = woods[i];
			final Block woodBlock = woodBlocks[i];
			COUNTER_ANDESITE[i] = block("counter_"+wood+"_andesite", () -> new Counter(wood+"_andesite", woodBlock));
			COUNTER_DIORITE[i] = block("counter_"+woods[i]+"_diorite", () -> new Counter(wood+"_diorite", woodBlock));
			COUNTER_GRANITE[i] = block("counter_"+woods[i]+"_granite", () -> new Counter(wood+"_granite", woodBlock));
			COUNTER_BLACKSTONE[i] = block("counter_"+woods[i]+"_blackstone", () -> new Counter(wood+"_blackstone", woodBlock));
			COUNTER_DEEPSLATE[i] = block("counter_"+woods[i]+"_deepslate", () -> new Counter(wood+"_deepslate", woodBlock));
			int n = 5;
			COUNTER_ALL[i*n] = COUNTER_ANDESITE[i];
			COUNTER_ALL[i*n+1] = COUNTER_DIORITE[i];
			COUNTER_ALL[i*n+2] = COUNTER_GRANITE[i];
			COUNTER_ALL[i*n+3] = COUNTER_BLACKSTONE[i];
			COUNTER_ALL[i*n+4] = COUNTER_DEEPSLATE[i];
		}
		
		BOOKSHELF_OAK = block("bookshelf_oak", () -> new Bookshelf("oak"));
		BOOKSHELF_BIRCH = block("bookshelf_birch", () -> new Bookshelf("birch"));
		BOOKSHELF_SPRUCE = block("bookshelf_spruce", () -> new Bookshelf("spruce"));
		BOOKSHELF_DARK_OAK = block("bookshelf_dark_oak", () -> new Bookshelf("dark_oak"));
		BOOKSHELF_JUNGLE = block("bookshelf_jungle", () -> new Bookshelf("jungle"));
		BOOKSHELF_ACACIA = block("bookshelf_acacia", () -> new Bookshelf("acacia"));
		BOOKSHELF_WARPED = block("bookshelf_warped", () -> new Bookshelf("warped"));
		BOOKSHELF_CRIMSON = block("bookshelf_crimson", () -> new Bookshelf("crimson"));
		BOOKSHELF_MANGROVE = block("bookshelf_mangrove", () -> new Bookshelf("mangrove"));
		BOOKSHELF_CHERRY = block("bookshelf_cherry", () -> new Bookshelf("cherry"));
		BOOKSHELF_BAMBOO = block("bookshelf_bamboo", () -> new Bookshelf("bamboo"));
		
		SHELF_OAK = block("shelf_oak", () -> new Shelf("oak"));
		SHELF_BIRCH = block("shelf_birch", () -> new Shelf("birch"));
		SHELF_SPRUCE = block("shelf_spruce", () -> new Shelf("spruce"));
		SHELF_DARK_OAK = block("shelf_dark_oak", () -> new Shelf("dark_oak"));
		SHELF_JUNGLE = block("shelf_jungle", () -> new Shelf("jungle"));
		SHELF_ACACIA = block("shelf_acacia", () -> new Shelf("acacia"));
		SHELF_WARPED = block("shelf_warped", () -> new Shelf("warped"));
		SHELF_CRIMSON = block("shelf_crimson", () -> new Shelf("crimson"));
		SHELF_MANGROVE = block("shelf_mangrove", () -> new Shelf("mangrove"));
		SHELF_CHERRY = block("shelf_cherry", () -> new Shelf("cherry"));
		SHELF_BAMBOO = block("shelf_bamboo", () -> new Shelf("bamboo"));

		CABINET_OAK = block("cabinet_oak", () -> new Cabinet("oak"));
		CABINET_BIRCH = block("cabinet_birch", () -> new Cabinet("birch"));
		CABINET_SPRUCE = block("cabinet_spruce", () -> new Cabinet("spruce"));
		CABINET_DARK_OAK = block("cabinet_dark_oak", () -> new Cabinet("dark_oak"));
		CABINET_JUNGLE = block("cabinet_jungle", () -> new Cabinet("jungle"));
		CABINET_ACACIA = block("cabinet_acacia", () -> new Cabinet("acacia"));
		CABINET_WARPED = block("cabinet_warped", () -> new Cabinet("warped"));
		CABINET_CRIMSON = block("cabinet_crimson", () -> new Cabinet("crimson"));
		CABINET_MANGROVE = block("cabinet_mangrove", () -> new Cabinet("mangrove"));
		CABINET_CHERRY = block("cabinet_cherry", () -> new Cabinet("cherry"));
		CABINET_BAMBOO = block("cabinet_bamboo", () -> new Cabinet("bamboo"));
		
		CUPBOARD_OAK = block("cupboard_oak", () -> new Cupboard("oak", Blocks.OAK_PLANKS));
		CUPBOARD_BIRCH = block("cupboard_birch", () -> new Cupboard("birch", Blocks.OAK_PLANKS));
		CUPBOARD_SPRUCE = block("cupboard_spruce", () -> new Cupboard("spruce", Blocks.OAK_PLANKS));
		CUPBOARD_DARK_OAK = block("cupboard_dark_oak", () -> new Cupboard("dark_oak", Blocks.OAK_PLANKS));
		CUPBOARD_JUNGLE = block("cupboard_jungle", () -> new Cupboard("jungle", Blocks.OAK_PLANKS));
		CUPBOARD_ACACIA = block("cupboard_acacia", () -> new Cupboard("acacia", Blocks.OAK_PLANKS));
		CUPBOARD_WARPED = block("cupboard_warped", () -> new Cupboard("warped", Blocks.WARPED_PLANKS));
		CUPBOARD_CRIMSON = block("cupboard_crimson", () -> new Cupboard("crimson", Blocks.CRIMSON_PLANKS));
		CUPBOARD_MANGROVE = block("cupboard_mangrove", () -> new Cupboard("mangrove", Blocks.MANGROVE_PLANKS));
		CUPBOARD_CHERRY = block("cupboard_cherry", () -> new Cupboard("cherry", Blocks.CHERRY_PLANKS));
		CUPBOARD_BAMBOO = block("cupboard_bamboo", () -> new Cupboard("bamboo", Blocks.BAMBOO_PLANKS));

		SMALL_CUPBOARD_OAK = block("small_cupboard_oak", () -> new SmallCupboard("oak", Blocks.OAK_PLANKS));
		SMALL_CUPBOARD_BIRCH = block("small_cupboard_birch", () -> new SmallCupboard("birch", Blocks.OAK_PLANKS));
		SMALL_CUPBOARD_SPRUCE = block("small_cupboard_spruce", () -> new SmallCupboard("spruce", Blocks.OAK_PLANKS));
		SMALL_CUPBOARD_DARK_OAK = block("small_cupboard_dark_oak", () -> new SmallCupboard("dark_oak", Blocks.OAK_PLANKS));
		SMALL_CUPBOARD_JUNGLE = block("small_cupboard_jungle", () -> new SmallCupboard("jungle", Blocks.OAK_PLANKS));
		SMALL_CUPBOARD_ACACIA = block("small_cupboard_acacia", () -> new SmallCupboard("acacia", Blocks.OAK_PLANKS));
		SMALL_CUPBOARD_WARPED = block("small_cupboard_warped", () -> new SmallCupboard("warped", Blocks.WARPED_PLANKS));
		SMALL_CUPBOARD_CRIMSON = block("small_cupboard_crimson", () -> new SmallCupboard("crimson", Blocks.CRIMSON_PLANKS));
		SMALL_CUPBOARD_MANGROVE = block("small_cupboard_mangrove", () -> new SmallCupboard("mangrove", Blocks.MANGROVE_PLANKS));
		SMALL_CUPBOARD_CHERRY = block("small_cupboard_cherry", () -> new SmallCupboard("cherry", Blocks.CHERRY_PLANKS));
		SMALL_CUPBOARD_BAMBOO = block("small_cupboard_bamboo", () -> new SmallCupboard("bamboo", Blocks.BAMBOO_PLANKS));
		
		BENCH_OAK = block("bench_oak", () -> new Bench("oak"));
		BENCH_BIRCH = block("bench_birch", () -> new Bench("birch"));
		BENCH_SPRUCE = block("bench_spruce", () -> new Bench("spruce"));
		BENCH_DARK_OAK = block("bench_dark_oak", () -> new Bench("dark_oak"));
		BENCH_JUNGLE = block("bench_jungle", () -> new Bench("jungle"));
		BENCH_ACACIA = block("bench_acacia", () -> new Bench("acacia"));
		BENCH_WARPED = block("bench_warped", () -> new Bench("warped", Blocks.WARPED_PLANKS));
		BENCH_CRIMSON = block("bench_crimson", () -> new Bench("crimson", Blocks.CRIMSON_PLANKS));
		BENCH_MANGROVE = block("bench_mangrove", () -> new Bench("mangrove", Blocks.MANGROVE_PLANKS));
		BENCH_CHERRY = block("bench_cherry", () -> new Bench("cherry", Blocks.CHERRY_PLANKS));
		BENCH_BAMBOO = block("bench_bamboo", () -> new Bench("bamboo", Blocks.BAMBOO_PLANKS));
		
		SUPPORT_BRACKET_OAK = block("support_bracket_oak", () -> new SupportBracket("oak"));
		SUPPORT_BRACKET_BIRCH = block("support_bracket_birch", () -> new SupportBracket("birch"));
		SUPPORT_BRACKET_SPRUCE = block("support_bracket_spruce", () -> new SupportBracket("spruce"));
		SUPPORT_BRACKET_DARK_OAK = block("support_bracket_dark_oak", () -> new SupportBracket("dark_oak"));
		SUPPORT_BRACKET_JUNGLE = block("support_bracket_jungle", () -> new SupportBracket("jungle"));
		SUPPORT_BRACKET_ACACIA = block("support_bracket_acacia", () -> new SupportBracket("acacia"));
		SUPPORT_BRACKET_WARPED = block("support_bracket_warped", () -> new SupportBracket("warped", Blocks.WARPED_PLANKS));
		SUPPORT_BRACKET_CRIMSON = block("support_bracket_crimson", () -> new SupportBracket("crimson", Blocks.CRIMSON_PLANKS));
		SUPPORT_BRACKET_MANGROVE = block("support_bracket_mangrove", () -> new SupportBracket("mangrove", Blocks.MANGROVE_PLANKS));
		SUPPORT_BRACKET_CHERRY = block("support_bracket_cherry", () -> new SupportBracket("cherry", Blocks.CHERRY_PLANKS));
		SUPPORT_BRACKET_BAMBOO = block("support_bracket_bamboo", () -> new SupportBracket("bamboo", Blocks.BAMBOO_PLANKS));
		
		for(int i = 0; i < colors.length; i++) {
			final String color = colors[i];
			SOFA[i] = block("sofa_" + colors[i], () -> new Sofa(color));
		}
		
		for(int i = 0; i < woods.length; i++) {
			final String wood = woods[i];
			final Block woodBlock = woodBlocks[i];
			BEDSIDE_TABLE_ALL[i] = block("bedside_table_" + wood, () -> new BedsideTable(wood, woodBlock));
		}
		
		SHOP_SIGN_WOOD = block("shop_sign_wood", () -> new ShopSign("wood"));
		IRON_LADDER = block("iron_ladder", () -> new IronLadder("iron_ladder"));
		IRON_LADDER_ROUGH = block("iron_ladder_rough", () -> new IronLadder("iron_ladder_rough"));
		IRON_FENCE = block("iron_fence", () -> new IronFence("iron_fence"));
		IRON_FENCE_ROUGH = block("iron_fence_rough", () -> new IronFence("iron_fence_rough"));
		ROUGH_IRON_BLOCK = block("rough_iron_block", () -> new GenericBlock("rough_iron_block", Block.Properties.copy(Blocks.IRON_BLOCK), new BlockOptions()));
		GRAVEL_PATH = block("gravel_path", () -> new GravelPath());
		CROSSRAIL = block("crossrail", () -> new Crossrail());
		SPEAKER = block("speaker", () -> new Speaker());
		PLANTER = block("planter", () -> new Planter());

		ARCADE_OAK = block("arcade_oak", () -> new Arcade("oak"));
		ARCADE_BIRCH = block("arcade_birch", () -> new Arcade("birch"));
		ARCADE_SPRUCE = block("arcade_spruce", () -> new Arcade("spruce"));
		ARCADE_DARK_OAK = block("arcade_dark_oak", () -> new Arcade("dark_oak"));
		ARCADE_JUNGLE = block("arcade_jungle", () -> new Arcade("jungle"));
		ARCADE_ACACIA = block("arcade_acacia", () -> new Arcade("acacia"));
		ARCADE_WARPED = block("arcade_warped", () -> new Arcade("warped"));
		ARCADE_CRIMSON = block("arcade_crimson", () -> new Arcade("crimson"));
		ARCADE_MANGROVE = block("arcade_mangrove", () -> new Arcade("mangrove"));
		ARCADE_CHERRY = block("arcade_cherry", () -> new Arcade("cherry"));
		ARCADE_BAMBOO = block("arcade_bamboo", () -> new Arcade("bamboo"));
		
		FIREPLACE_GUARD = block("fireplace_guard", () -> new FireplaceGuard("fireplace_guard"));
		ROUGH_FIREPLACE_GUARD = block("rough_fireplace_guard", () -> new FireplaceGuard("rough_fireplace_guard"));
		
		VERTICAL_REPEATER = blockNoItem("vertical_repeater", () -> new VerticalRepeaterBlock());
		VERTICAL_COMPARATOR = blockNoItem("vertical_comparator", () -> new VerticalComparatorBlock());
		
		ENTITY_DETECTOR = block("entity_detector", () -> new EntityDetector());
	}
	
	public static void paintings() {
		SUMMER_FIELD_PAINTING = PAINTINGS.register("summer_field", () -> RegistryUtil.createPainting(1, 1));
		SHARD_PAINTING = PAINTINGS.register("shard", () -> RegistryUtil.createPainting(1, 1));
		SKARGARD_PAINTING = PAINTINGS.register("skargard", () -> RegistryUtil.createPainting(2, 1));
		HORIZONS_PAINTING = PAINTINGS.register("horizons", () -> RegistryUtil.createPainting(1, 1));
		PORTRAIT_PAINTING = PAINTINGS.register("self_portrait", () -> RegistryUtil.createPainting(1, 1));
		PROMO_PAINTING = PAINTINGS.register("promo", () -> RegistryUtil.createPainting(1, 1));
		HEROBRINE_PAINTING = PAINTINGS.register("herobrine_stare", () -> RegistryUtil.createPainting(1, 1));
		ENDERMAN_PAINTING = PAINTINGS.register("enderman_stare", () -> RegistryUtil.createPainting(1, 2));
		WINTER_PAINTING = PAINTINGS.register("winter", () -> RegistryUtil.createPainting(2, 2));
	}
	
	public static Block[] getAll(RegistryObject<Block>...regs) {
		Block[] a = new Block[regs.length];
		for(int i = 0; i < a.length; i++) {
			a[i] = regs[i].get();
		}
		return a;
	}
	
	public static void tileentities() {
		COUNTER_TILE_ENTITY_TYPE = BLOCK_ENTITIES.register("counter", () ->
        	BlockEntityType.Builder.of(CounterTileEntity::new, getAll(COUNTER_ALL)).build(null));
		
		CUPBOARD_TILE_ENTITY_TYPE = BLOCK_ENTITIES.register("cupboard", () ->
    		BlockEntityType.Builder.of(CupboardTileEntity::new, getAll(CUPBOARD_OAK, CUPBOARD_ACACIA, CUPBOARD_BIRCH, CUPBOARD_CRIMSON, CUPBOARD_DARK_OAK, CUPBOARD_JUNGLE, CUPBOARD_SPRUCE, CUPBOARD_WARPED, CUPBOARD_MANGROVE, CUPBOARD_CHERRY, CUPBOARD_BAMBOO)).build(null));
		
		SMALL_CUPBOARD_TILE_ENTITY_TYPE = BLOCK_ENTITIES.register("small_cupboard", () ->
    		BlockEntityType.Builder.of(SmallCupboardTileEntity::new, getAll(SMALL_CUPBOARD_OAK, SMALL_CUPBOARD_ACACIA, SMALL_CUPBOARD_BIRCH, SMALL_CUPBOARD_CRIMSON, SMALL_CUPBOARD_DARK_OAK, SMALL_CUPBOARD_JUNGLE, SMALL_CUPBOARD_SPRUCE, SMALL_CUPBOARD_WARPED, SMALL_CUPBOARD_CHERRY, SMALL_CUPBOARD_BAMBOO)).build(null));
		
		CABINET_TILE_ENTITY_TYPE = BLOCK_ENTITIES.register("cabinet", () ->
    		BlockEntityType.Builder.of(CabinetTileEntity::new, getAll(CABINET_OAK, CABINET_BIRCH, CABINET_SPRUCE, CABINET_DARK_OAK, CABINET_JUNGLE, CABINET_ACACIA, CABINET_WARPED, CABINET_CRIMSON, CABINET_MANGROVE, CABINET_CHERRY, CABINET_BAMBOO)).build(null));
		
		BOOKSHELF_TILE_ENTITY_TYPE = BLOCK_ENTITIES.register("bookshelf", () ->
    		BlockEntityType.Builder.of(BookshelfTileEntity::new, getAll(BOOKSHELF_OAK, BOOKSHELF_BIRCH, BOOKSHELF_SPRUCE, BOOKSHELF_DARK_OAK, BOOKSHELF_JUNGLE, BOOKSHELF_ACACIA, BOOKSHELF_WARPED, BOOKSHELF_CRIMSON, BOOKSHELF_MANGROVE, BOOKSHELF_CHERRY, BOOKSHELF_BAMBOO)).build(null));
		
		SHELF_TILE_ENTITY_TYPE = BLOCK_ENTITIES.register("shelf", () ->
    		BlockEntityType.Builder.of(ShelfTileEntity::new, getAll(SHELF_OAK, SHELF_BIRCH, SHELF_SPRUCE, SHELF_DARK_OAK, SHELF_ACACIA, SHELF_JUNGLE, SHELF_CRIMSON, SHELF_WARPED, SHELF_MANGROVE, SHELF_CHERRY, SHELF_BAMBOO)).build(null));
		
		SHOP_SIGN_TILE_ENTITY_TYPE = BLOCK_ENTITIES.register("shop_sign", () ->
    		BlockEntityType.Builder.of(ShopSignTileEntity::new, SHOP_SIGN_WOOD.get()).build(null));
		
		BEDSIDE_TILE_ENTITY_TYPE = BLOCK_ENTITIES.register("bedside_table", () ->
    		BlockEntityType.Builder.of(BedsideTileEntity::new, getAll(BEDSIDE_TABLE_ALL)).build(null));
		
		SPEAKER_TILE_ENTITY_TYPE = BLOCK_ENTITIES.register("speaker", () ->
    		BlockEntityType.Builder.of(SpeakerTileEntity::new, SPEAKER.get()).build(null));
		
		ARCADE_TILE_ENTITY_TYPE = BLOCK_ENTITIES.register("arcade", () ->
    		BlockEntityType.Builder.of(ArcadeTileEntity::new, getAll(ARCADE_OAK, ARCADE_BIRCH, ARCADE_SPRUCE, ARCADE_DARK_OAK, ARCADE_JUNGLE, ARCADE_ACACIA, ARCADE_WARPED, ARCADE_CRIMSON, ARCADE_MANGROVE, ARCADE_CHERRY, ARCADE_BAMBOO)).build(null));
		
		ENTITY_DETECTOR_TILE_ENTITY_TYPE = BLOCK_ENTITIES.register("entity_detector", () ->
    		BlockEntityType.Builder.of(EntityDetectorTileEntity::new, ENTITY_DETECTOR.get()).build(null));
		
		VERTICAL_COMPARATOR_TILE_ENTITY_TYPE = BLOCK_ENTITIES.register("vertical_comparator", () ->
    		BlockEntityType.Builder.of(VerticalComparatorTileEntity::new, VERTICAL_COMPARATOR.get()).build(null));
	}
	
	private static <T extends AbstractContainerMenu>RegistryObject<MenuType<T>> registerMenuType(IContainerFactory<T> factory, String name) {
        return MENUS.register(name, () -> IForgeMenuType.create(factory));
    }
	
	public static void containers() {
		BOOKSHELF_CONTAINER = registerMenuType(BookshelfContainer::new, "bookshelf_container");
		SHELF_CONTAINER = registerMenuType(ShelfContainer::create, "shelf_container");
		SPEAKER_CONTAINER = registerMenuType(SpeakerContainer::create, "speaker_container");
		ARCADE_CONTAINER = registerMenuType(ArcadeContainer::create, "arcade_container");
	}
	
	public static void entities() {
		SEAT_ENTITY_TYPE = ENTITIES.register("seat", () -> EntityType.Builder.<SeatEntity>of(SeatEntity::new, MobCategory.MISC)
				.setCustomClientFactory((packet, world) -> new SeatEntity(Index.SEAT_ENTITY_TYPE.get(), world)).build(BuildersAddition.MODID+":seat"));
	}

	static {
    	blocks();
    	items();
    	paintings();
    	tileentities();
    	containers();
    	entities();
    }
}
