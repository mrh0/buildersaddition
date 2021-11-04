package com.mrh0.buildersaddition;

import com.mrh0.buildersaddition.blocks.Arcade;
import com.mrh0.buildersaddition.blocks.BedsideTable;
import com.mrh0.buildersaddition.blocks.Bench;
import com.mrh0.buildersaddition.blocks.Bookshelf;
import com.mrh0.buildersaddition.blocks.Cabinet;
import com.mrh0.buildersaddition.blocks.LargeCandle;
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
import com.mrh0.buildersaddition.blocks.Candle;
import com.mrh0.buildersaddition.blocks.Chair;
import com.mrh0.buildersaddition.blocks.CopperVerticalSlab;
import com.mrh0.buildersaddition.blocks.base.GenericBlock;
import com.mrh0.buildersaddition.blocks.base.IConnects;
import com.mrh0.buildersaddition.container.ArcadeContainer;
import com.mrh0.buildersaddition.container.BookshelfContainer;
import com.mrh0.buildersaddition.container.ShelfContainer;
import com.mrh0.buildersaddition.container.SpeakerContainer;
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
import net.minecraft.world.entity.decoration.Motive;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.common.extensions.IForgeContainerType;

@SuppressWarnings("unchecked")
public class Index {
	
	public static String[] woods = {"oak", "spruce", "birch", "acacia", "dark_oak", "jungle", "crimson", "warped"};
	public static Block[] woodBlocks = {Blocks.OAK_PLANKS, Blocks.SPRUCE_PLANKS, Blocks.BIRCH_PLANKS, Blocks.DARK_OAK_PLANKS, Blocks.JUNGLE_PLANKS, Blocks.ACACIA_PLANKS, Blocks.CRIMSON_PLANKS, Blocks.WARPED_PLANKS};
	public static String[] colors = {"white", "orange", "magenta", "light_blue", "yellow", "lime", "pink", 
			"gray", "light_gray", "cyan", "purple", "blue", "brown", "green", "red", "black"};
	
	public static Item IRON_ROD;
	
	public static Block OAK_VERTICAL_SLAB;
	public static Block SPRUCE_VERTICAL_SLAB;
	public static Block BIRCH_VERTICAL_SLAB;
	public static Block JUNGLE_VERTICAL_SLAB;
	public static Block ACACIA_VERTICAL_SLAB;
	public static Block DARK_OAK_VERTICAL_SLAB;
	public static Block STONE_VERTICAL_SLAB;
	public static Block SMOOTH_STONE_VERTICAL_SLAB;
	public static Block SANDSTONE_VERTICAL_SLAB;
	public static Block CUT_SANDSTONE_VERTICAL_SLAB;
	public static Block COBBLESTONE_VERTICAL_SLAB;
	public static Block BRICKS_VERTICAL_SLAB;
	public static Block STONE_BRICKS_VERTICAL_SLAB;
	public static Block NETHER_BRICKS_VERTICAL_SLAB;
	public static Block QUARTZ_VERTICAL_SLAB;
	public static Block RED_SANDSTONE_VERTICAL_SLAB;
	public static Block CUT_RED_SANDSTONE_VERTICAL_SLAB;
	public static Block PURPUR_VERTICAL_SLAB;
	public static Block PRISMARINE_VERTICAL_SLAB;
	public static Block PRISMARINE_BRICKS_VERTICAL_SLAB;
	public static Block DARK_PRISMARINE_VERTICAL_SLAB;
	public static Block POLISHED_GRANITE_VERTICAL_SLAB;
	public static Block SMOOTH_RED_SANDSTONE_VERTICAL_SLAB;
	public static Block MOSSY_STONE_BRICKS_VERTICAL_SLAB;
	public static Block POLISHED_DIORITE_VERTICAL_SLAB;
	public static Block MOSSY_COBBLESTONE_VERTICAL_SLAB;
	public static Block END_STONE_BRICKS_VERTICAL_SLAB;
	public static Block SMOOTH_SANDSTONE_VERTICAL_SLAB;
	public static Block SMOOTH_QUARTZ_VERTICAL_SLAB;
	public static Block GRANITE_VERTICAL_SLAB;
	public static Block ANDESITE_VERTICAL_SLAB;
	public static Block RED_NETHER_BRICKS_VERTICAL_SLAB;
	public static Block POLISHED_ANDESITE_VERTICAL_SLAB;
	public static Block DIORITE_VERTICAL_SLAB;
	public static Block CRIMSON_VERTICAL_SLAB;
	public static Block WARPED_VERTICAL_SLAB;
	public static Block BLACKSTONE_VERTICAL_SLAB;
	public static Block POLISHED_BLACKSTONE_VERTICAL_SLAB;
	public static Block POLISHED_BLACKSTONE_BRICKS_VERTICAL_SLAB;
	
	public static Block COBBLED_DEEPSLATE_VERTICAL_SLAB;
	public static Block POLISHED_DEEPSLATE_VERTICAL_SLAB;
	public static Block DEEPSLATE_BRICKS_VERTICAL_SLAB;
	public static Block DEEPSLATE_TILES_VERTICAL_SLAB;
	
	public static Block WAXED_CUT_COPPER_VERTICAL_SLAB;
	public static Block WAXED_EXPOSED_CUT_COPPER_VERTICAL_SLAB;
	public static Block WAXED_WEATHERED_CUT_COPPER_VERTICAL_SLAB;
	public static Block WAXED_OXIDIZED_CUT_COPPER_VERTICAL_SLAB;
	
	public static Block CUT_COPPER_VERTICAL_SLAB;
	public static Block EXPOSED_CUT_COPPER_VERTICAL_SLAB;
	public static Block WEATHERED_CUT_COPPER_VERTICAL_SLAB;
	public static Block OXIDIZED_CUT_COPPER_VERTICAL_SLAB;
	
	public static Block BOP_CHERRY_VERTICAL_SLAB;
	public static Block BOP_CUT_WHITE_SANDSTONE_VERTICAL_SLAB;
	public static Block BOP_DEAD_VERTICAL_SLAB;
	public static Block BOP_FIR_VERTICAL_SLAB;
	public static Block BOP_HELLBARK_VERTICAL_SLAB;
	public static Block BOP_JACARANDA_VERTICAL_SLAB;
	public static Block BOP_MAGIC_VERTICAL_SLAB;
	public static Block BOP_MAHOGANY_VERTICAL_SLAB;
	public static Block BOP_MUD_BRICKS_VERTICAL_SLAB;
	public static Block BOP_PALM_VERTICAL_SLAB;
	public static Block BOP_REDWOOD_VERTICAL_SLAB;
	public static Block BOP_SMOOTH_WHITE_SANDSTONE_VERTICAL_SLAB;
	public static Block BOP_UMBRAN_VERTICAL_SLAB;
	public static Block BOP_WHITE_SANDSTONE_VERTICAL_SLAB;
	public static Block BOP_WILLOW_VERTICAL_SLAB;
	
	public static Block CUT_STONE_PILLAR;
	public static Block CUT_SMOOTH_STONE_PILLAR;
	public static Block CUT_SANDSTONE_PILLAR;
	public static Block CUT_COBBLESTONE_PILLAR;
	public static Block CUT_BRICKS_PILLAR;
	public static Block CUT_STONE_BRICKS_PILLAR;
	public static Block CUT_NETHER_BRICKS_PILLAR;
	public static Block CUT_QUARTZ_PILLAR;
	public static Block CUT_RED_SANDSTONE_PILLAR;
	public static Block CUT_PURPUR_PILLAR;
	public static Block CUT_PRISMARINE_PILLAR;
	public static Block CUT_SMOOTH_RED_SANDSTONE_PILLAR;
	public static Block CUT_SMOOTH_SANDSTONE_PILLAR;
	public static Block CUT_MOSSY_STONE_BRICKS_PILLAR;
	public static Block CUT_MOSSY_COBBLESTONE_PILLAR;
	public static Block CUT_END_STONE_BRICKS_PILLAR;
	public static Block CUT_RED_NETHER_BRICKS_PILLAR;
	public static Block CUT_BASALT_PILLAR;
	public static Block CUT_BLACKSTONE_PILLAR;
	public static Block CUT_QUARTZ_BRICKS_PILLAR;
	public static Block CUT_DEEPSLATE_BRICKS_PILLAR;
	public static Block CUT_DEEPSLATE_TILES_PILLAR;
	
	public static Block TABLE_OAK;
	public static Block TABLE_SPRUCE;
	public static Block TABLE_BIRCH;
	public static Block TABLE_DARK_OAK;
	public static Block TABLE_JUNGLE;
	public static Block TABLE_ACACIA;
	public static Block TABLE_CRIMSON;
	public static Block TABLE_WARPED;
	
	/*public static Block[] STOOL_OAK = new Block[16];
	public static Block[] STOOL_SPRUCE = new Block[16];
	public static Block[] STOOL_BIRCH = new Block[16];
	public static Block[] STOOL_DARK_OAK = new Block[16];
	public static Block[] STOOL_JUNGLE = new Block[16];
	public static Block[] STOOL_ACACIA = new Block[16];
	public static Block[] STOOL_CRIMSON = new Block[16];
	public static Block[] STOOL_WARPED = new Block[16];*/
	
	public static Block STOOL_OAK;
	public static Block STOOL_SPRUCE;
	public static Block STOOL_BIRCH;
	public static Block STOOL_DARK_OAK;
	public static Block STOOL_JUNGLE;
	public static Block STOOL_ACACIA;
	public static Block STOOL_CRIMSON;
	public static Block STOOL_WARPED;
	
	public static Block CHAIR_OAK;
	public static Block CHAIR_SPRUCE;
	public static Block CHAIR_BIRCH;
	public static Block CHAIR_DARK_OAK;
	public static Block CHAIR_JUNGLE;
	public static Block CHAIR_ACACIA;
	public static Block CHAIR_CRIMSON;
	public static Block CHAIR_WARPED;
	
	public static Block PLANTER;
	
	public static Block HEDGE_OAK;
	public static Block HEDGE_SPRUCE;
	public static Block HEDGE_BIRCH;
	public static Block HEDGE_DARK_OAK;
	public static Block HEDGE_JUNGLE;
	public static Block HEDGE_ACACIA;
	
	public static Block[] COUNTER_ANDESITE = new Block[8];
	public static Block[] COUNTER_DIORITE = new Block[8];
	public static Block[] COUNTER_GRANITE = new Block[8];
	public static Block[] COUNTER_BLACKSTONE = new Block[8];
	public static Block[] COUNTER_DEEPSLATE = new Block[8];
	public static Block[] COUNTER_ALL = new Block[8*5];
	
	public static Block BOOKSHELF_OAK;
	public static Block BOOKSHELF_BIRCH;
	public static Block BOOKSHELF_SPRUCE;
	public static Block BOOKSHELF_DARK_OAK;
	public static Block BOOKSHELF_JUNGLE;
	public static Block BOOKSHELF_ACACIA;
	public static Block BOOKSHELF_WARPED;
	public static Block BOOKSHELF_CRIMSON;
	
	public static Block SHELF_OAK;
	public static Block SHELF_BIRCH;
	public static Block SHELF_SPRUCE;
	public static Block SHELF_DARK_OAK;
	public static Block SHELF_JUNGLE;
	public static Block SHELF_ACACIA;
	public static Block SHELF_WARPED;
	public static Block SHELF_CRIMSON;
	
	public static Block CABINET_OAK;
	public static Block CABINET_BIRCH;
	public static Block CABINET_SPRUCE;
	public static Block CABINET_DARK_OAK;
	public static Block CABINET_JUNGLE;
	public static Block CABINET_ACACIA;
	public static Block CABINET_WARPED;
	public static Block CABINET_CRIMSON;
	
	public static Block CUPBOARD_OAK;
	public static Block CUPBOARD_BIRCH;
	public static Block CUPBOARD_SPRUCE;
	public static Block CUPBOARD_DARK_OAK;
	public static Block CUPBOARD_JUNGLE;
	public static Block CUPBOARD_ACACIA;
	public static Block CUPBOARD_WARPED;
	public static Block CUPBOARD_CRIMSON;
	
	public static Block SMALL_CUPBOARD_OAK;
	public static Block SMALL_CUPBOARD_BIRCH;
	public static Block SMALL_CUPBOARD_SPRUCE;
	public static Block SMALL_CUPBOARD_DARK_OAK;
	public static Block SMALL_CUPBOARD_JUNGLE;
	public static Block SMALL_CUPBOARD_ACACIA;
	public static Block SMALL_CUPBOARD_WARPED;
	public static Block SMALL_CUPBOARD_CRIMSON;
	
	public static Block BENCH_OAK;
	public static Block BENCH_BIRCH;
	public static Block BENCH_SPRUCE;
	public static Block BENCH_DARK_OAK;
	public static Block BENCH_JUNGLE;
	public static Block BENCH_ACACIA;
	public static Block BENCH_WARPED;
	public static Block BENCH_CRIMSON;
	
	public static Block SUPPORT_BRACKET_OAK;
	public static Block SUPPORT_BRACKET_BIRCH;
	public static Block SUPPORT_BRACKET_SPRUCE;
	public static Block SUPPORT_BRACKET_DARK_OAK;
	public static Block SUPPORT_BRACKET_JUNGLE;
	public static Block SUPPORT_BRACKET_ACACIA;
	public static Block SUPPORT_BRACKET_WARPED;
	public static Block SUPPORT_BRACKET_CRIMSON;
	
	public static Block SOFA[] = new Sofa[16];
	
	//public static Block LUMINUS_GLASS[] = new LuminusGlass[16];
	
	public static Block PILLOW[] = new Pillow[16];
	
	public static Block[] BEDSIDE_TABLE = new BedsideTable[8];
	
	public static Block SHOP_SIGN_WOOD;
	public static Block IRON_LADDER;
	public static Block IRON_LADDER_ROUGH;
	public static Block IRON_FENCE;
	public static Block IRON_FENCE_ROUGH;
	public static Block ROUGH_IRON_BLOCK;
	public static Block GRAVEL_PATH;
	public static Block CROSSRAIL;
	public static Block SPEAKER;
	public static Block LARGE_CANDLE;
	public static Block LARGE_SOUL_CANDLE;
	public static Block CANDLE;
	public static Block SOUL_CANDLE;
	
	public static Block ARCADE;
	
	public static Block ARCADE_OAK;
	public static Block ARCADE_BIRCH;
	public static Block ARCADE_SPRUCE;
	public static Block ARCADE_DARK_OAK;
	public static Block ARCADE_JUNGLE;
	public static Block ARCADE_ACACIA;
	public static Block ARCADE_WARPED;
	public static Block ARCADE_CRIMSON;
	
	public static Block FIREPLACE_GUARD;
	public static Block ROUGH_FIREPLACE_GUARD;
	
	public static Block VERTICAL_REPEATER;
	public static Block VERTICAL_COMPARATOR;
	
	public static Block ENTITY_DETECTOR;
	
	public static EntityType<SeatEntity> SEAT_ENTITY_TYPE;
	
	public static BlockEntityType<CounterTileEntity> COUNTER_TILE_ENTITY_TYPE;
	public static BlockEntityType<CupboardTileEntity> CUPBOARD_TILE_ENTITY_TYPE;
	public static BlockEntityType<SmallCupboardTileEntity> SMALL_CUPBOARD_TILE_ENTITY_TYPE;
	public static BlockEntityType<CabinetTileEntity> CABINET_TILE_ENTITY_TYPE;
	public static BlockEntityType<BookshelfTileEntity> BOOKSHELF_TILE_ENTITY_TYPE;
	public static BlockEntityType<ShelfTileEntity> SHELF_TILE_ENTITY_TYPE;
	public static BlockEntityType<ShopSignTileEntity> SHOP_SIGN_TILE_ENTITY_TYPE;
	public static BlockEntityType<BedsideTileEntity> BEDSIDE_TILE_ENTITY_TYPE;
	public static BlockEntityType<SpeakerTileEntity> SPEAKER_TILE_ENTITY_TYPE;
	public static BlockEntityType<ArcadeTileEntity> ARCADE_TILE_ENTITY_TYPE;
	public static BlockEntityType<EntityDetectorTileEntity> ENTITY_DETECTOR_TILE_ENTITY_TYPE;
	public static BlockEntityType<VerticalComparatorTileEntity> VERTICAL_COMPARATOR_TILE_ENTITY_TYPE;

	public static MenuType<BookshelfContainer> BOOKSHELF_CONTAINER;
	public static MenuType<ShelfContainer> SHELF_CONTAINER;
	public static MenuType<SpeakerContainer> SPEAKER_CONTAINER;
	public static MenuType<ArcadeContainer> ARCADE_CONTAINER;

	public static Motive SUMMER_FIELD_PAINTING;
	public static Motive SHARD_PAINTING;
	public static Motive SKARGARD_PAINTING;
	public static Motive HORIZONS_PAINTING;
	public static Motive PORTRAIT_PAINTING;
	public static Motive PROMO_PAINTING;
	public static Motive HEROBRINE_PAINTING;
	public static Motive ENDERMAN_PAINTING;
	public static Motive WINTER_PAINTING;
	
	
	public static void items() {
		IRON_ROD = new GenericItem("iron_rod", new Properties(), new ItemOptions());
	}
	
	public static void blocks() {
		final IConnects cobbleConnector = (state, source) -> { 
			return state.getBlock() == CUT_MOSSY_COBBLESTONE_PILLAR || state.getBlock() == CUT_COBBLESTONE_PILLAR; 
		};
		final IConnects sandstoneConnector = (state, source) -> { 
			return state.getBlock() == CUT_SMOOTH_RED_SANDSTONE_PILLAR || state.getBlock() == CUT_SMOOTH_SANDSTONE_PILLAR || state.getBlock() == CUT_RED_SANDSTONE_PILLAR || state.getBlock() == CUT_SANDSTONE_PILLAR; 
		};
		final IConnects stoneBricksConnector = (state, source) -> { 
			return state.getBlock() == CUT_STONE_BRICKS_PILLAR || state.getBlock() == CUT_MOSSY_STONE_BRICKS_PILLAR  || state.getBlock() == CUT_BLACKSTONE_PILLAR || state.getBlock() == CUT_DEEPSLATE_BRICKS_PILLAR; 
		};
		final IConnects netherBricksConnector = (state, source) -> { 
			return state.getBlock() == CUT_NETHER_BRICKS_PILLAR || state.getBlock() == CUT_RED_NETHER_BRICKS_PILLAR; 
		};
		final IConnects quartzConnector = (state, source) -> { 
			return state.getBlock() == CUT_QUARTZ_PILLAR || state.getBlock() == CUT_QUARTZ_BRICKS_PILLAR; 
		};
		final IConnects deepslateConnector = (state, source) -> { 
			return state.getBlock() == CUT_DEEPSLATE_TILES_PILLAR || state.getBlock() == CUT_DEEPSLATE_BRICKS_PILLAR; 
		};
		final IConnects deepslateBricksConnector = (state, source) -> { 
			return deepslateConnector.connect(state, source) || stoneBricksConnector.connect(state, source); 
		};
		
		
		
		OAK_VERTICAL_SLAB = new VerticalSlab("oak", Blocks.OAK_PLANKS);
		SPRUCE_VERTICAL_SLAB = new VerticalSlab("spruce", Blocks.SPRUCE_PLANKS);
		BIRCH_VERTICAL_SLAB = new VerticalSlab("birch", Blocks.BIRCH_PLANKS);
		JUNGLE_VERTICAL_SLAB = new VerticalSlab("jungle", Blocks.JUNGLE_PLANKS);
		ACACIA_VERTICAL_SLAB = new VerticalSlab("acacia", Blocks.ACACIA_PLANKS);
		DARK_OAK_VERTICAL_SLAB = new VerticalSlab("dark_oak", Blocks.DARK_OAK_PLANKS);
		STONE_VERTICAL_SLAB = new VerticalSlab("stone", Blocks.STONE);
		SMOOTH_STONE_VERTICAL_SLAB = new VerticalSlab("smooth_stone", Blocks.SMOOTH_STONE);
		SANDSTONE_VERTICAL_SLAB = new VerticalSlab("sandstone", Blocks.SANDSTONE);
		CUT_SANDSTONE_VERTICAL_SLAB = new VerticalSlab("cut_sandstone", Blocks.CUT_SANDSTONE);
		COBBLESTONE_VERTICAL_SLAB = new VerticalSlab("cobblestone", Blocks.COBBLESTONE);
		BRICKS_VERTICAL_SLAB = new VerticalSlab("bricks", Blocks.BRICKS);
		STONE_BRICKS_VERTICAL_SLAB = new VerticalSlab("stone_bricks", Blocks.STONE_BRICKS);
		NETHER_BRICKS_VERTICAL_SLAB = new VerticalSlab("nether_bricks", Blocks.NETHER_BRICKS);
		QUARTZ_VERTICAL_SLAB = new VerticalSlab("quartz", Blocks.QUARTZ_BLOCK);
		RED_SANDSTONE_VERTICAL_SLAB = new VerticalSlab("red_sandstone", Blocks.RED_SANDSTONE);
		CUT_RED_SANDSTONE_VERTICAL_SLAB = new VerticalSlab("cut_red_sandstone", Blocks.CUT_RED_SANDSTONE);
		PURPUR_VERTICAL_SLAB = new VerticalSlab("purpur", Blocks.PURPUR_BLOCK);
		PRISMARINE_VERTICAL_SLAB = new VerticalSlab("prismarine", Blocks.PRISMARINE);
		PRISMARINE_BRICKS_VERTICAL_SLAB = new VerticalSlab("prismarine_bricks", Blocks.PRISMARINE_BRICKS);
		DARK_PRISMARINE_VERTICAL_SLAB = new VerticalSlab("dark_prismarine", Blocks.DARK_PRISMARINE);
		POLISHED_GRANITE_VERTICAL_SLAB = new VerticalSlab("polished_granite", Blocks.POLISHED_GRANITE);
		SMOOTH_RED_SANDSTONE_VERTICAL_SLAB = new VerticalSlab("smooth_red_sandstone", Blocks.SMOOTH_RED_SANDSTONE);
		MOSSY_STONE_BRICKS_VERTICAL_SLAB = new VerticalSlab("mossy_stone_bricks", Blocks.MOSSY_STONE_BRICKS);
		POLISHED_DIORITE_VERTICAL_SLAB = new VerticalSlab("polished_diorite", Blocks.POLISHED_DIORITE);
		MOSSY_COBBLESTONE_VERTICAL_SLAB = new VerticalSlab("mossy_cobblestone", Blocks.MOSSY_COBBLESTONE);
		END_STONE_BRICKS_VERTICAL_SLAB = new VerticalSlab("end_stone_bricks", Blocks.END_STONE_BRICKS);
		SMOOTH_SANDSTONE_VERTICAL_SLAB = new VerticalSlab("smooth_sandstone", Blocks.SMOOTH_SANDSTONE);
		SMOOTH_QUARTZ_VERTICAL_SLAB = new VerticalSlab("smooth_quartz", Blocks.SMOOTH_QUARTZ);
		GRANITE_VERTICAL_SLAB = new VerticalSlab("granite", Blocks.GRANITE);
		ANDESITE_VERTICAL_SLAB = new VerticalSlab("andesite", Blocks.ANDESITE);
		RED_NETHER_BRICKS_VERTICAL_SLAB = new VerticalSlab("red_nether_bricks", Blocks.RED_NETHER_BRICKS);
		POLISHED_ANDESITE_VERTICAL_SLAB = new VerticalSlab("polished_andesite", Blocks.POLISHED_ANDESITE);
		DIORITE_VERTICAL_SLAB = new VerticalSlab("diorite", Blocks.DIORITE);
		CRIMSON_VERTICAL_SLAB = new VerticalSlab("crimson", Blocks.CRIMSON_PLANKS);
		WARPED_VERTICAL_SLAB = new VerticalSlab("warped", Blocks.WARPED_PLANKS);
		BLACKSTONE_VERTICAL_SLAB = new VerticalSlab("blackstone", Blocks.BLACKSTONE);
		POLISHED_BLACKSTONE_VERTICAL_SLAB = new VerticalSlab("polished_blackstone", Blocks.POLISHED_BLACKSTONE);
		POLISHED_BLACKSTONE_BRICKS_VERTICAL_SLAB = new VerticalSlab("polished_blackstone_bricks", Blocks.POLISHED_BLACKSTONE_BRICKS);
		
		COBBLED_DEEPSLATE_VERTICAL_SLAB = new VerticalSlab("cobbled_deepslate", Blocks.COBBLED_DEEPSLATE);
		POLISHED_DEEPSLATE_VERTICAL_SLAB = new VerticalSlab("polished_deepslate", Blocks.POLISHED_DEEPSLATE);
		DEEPSLATE_BRICKS_VERTICAL_SLAB = new VerticalSlab("deepslate_bricks", Blocks.DEEPSLATE_BRICKS);
		DEEPSLATE_TILES_VERTICAL_SLAB = new VerticalSlab("deepslate_tiles", Blocks.DEEPSLATE_TILES);
		WAXED_CUT_COPPER_VERTICAL_SLAB = new VerticalSlab("waxed_cut_copper", Blocks.COPPER_BLOCK);
		WAXED_EXPOSED_CUT_COPPER_VERTICAL_SLAB = new VerticalSlab("waxed_exposed_cut_copper", Blocks.COPPER_BLOCK);
		WAXED_WEATHERED_CUT_COPPER_VERTICAL_SLAB = new VerticalSlab("waxed_weathered_cut_copper", Blocks.COPPER_BLOCK);
		WAXED_OXIDIZED_CUT_COPPER_VERTICAL_SLAB = new VerticalSlab("waxed_oxidized_cut_copper", Blocks.COPPER_BLOCK);
		CUT_COPPER_VERTICAL_SLAB = new CopperVerticalSlab("cut_copper", Blocks.COPPER_BLOCK, WeatheringCopper.WeatherState.UNAFFECTED);
		EXPOSED_CUT_COPPER_VERTICAL_SLAB = new CopperVerticalSlab("exposed_cut_copper", Blocks.COPPER_BLOCK, WeatheringCopper.WeatherState.EXPOSED);
		WEATHERED_CUT_COPPER_VERTICAL_SLAB = new CopperVerticalSlab("weathered_cut_copper", Blocks.COPPER_BLOCK, WeatheringCopper.WeatherState.WEATHERED);
		OXIDIZED_CUT_COPPER_VERTICAL_SLAB = new CopperVerticalSlab("oxidized_cut_copper", Blocks.COPPER_BLOCK, WeatheringCopper.WeatherState.OXIDIZED);
		
		CUT_STONE_PILLAR = new Pillar("stone", Blocks.STONE);
		CUT_SMOOTH_STONE_PILLAR = new Pillar("smooth_stone", Blocks.SMOOTH_STONE);
		CUT_SANDSTONE_PILLAR = new Pillar("sandstone", Blocks.SANDSTONE, sandstoneConnector);
		CUT_COBBLESTONE_PILLAR = new Pillar("cobblestone", Blocks.COBBLESTONE, cobbleConnector);
		CUT_BRICKS_PILLAR = new Pillar("bricks", Blocks.BRICKS);
		CUT_STONE_BRICKS_PILLAR = new Pillar("stone_bricks", Blocks.STONE_BRICKS, stoneBricksConnector);
		CUT_NETHER_BRICKS_PILLAR = new Pillar("nether_bricks", Blocks.NETHER_BRICKS, netherBricksConnector);
		CUT_QUARTZ_PILLAR = new Pillar("quartz", Blocks.QUARTZ_BLOCK, quartzConnector);
		CUT_RED_SANDSTONE_PILLAR = new Pillar("red_sandstone", Blocks.RED_SANDSTONE, sandstoneConnector);
		CUT_PURPUR_PILLAR = new Pillar("purpur", Blocks.PURPUR_PILLAR);
		CUT_PRISMARINE_PILLAR = new Pillar("prismarine", Blocks.PRISMARINE, 15);
		CUT_SMOOTH_RED_SANDSTONE_PILLAR = new Pillar("smooth_red_sandstone", Blocks.SMOOTH_RED_SANDSTONE, sandstoneConnector);
		CUT_SMOOTH_SANDSTONE_PILLAR = new Pillar("smooth_sandstone", Blocks.SMOOTH_SANDSTONE, sandstoneConnector);
		CUT_MOSSY_STONE_BRICKS_PILLAR = new Pillar("mossy_stone_bricks", Blocks.MOSSY_STONE_BRICKS, stoneBricksConnector);
		CUT_MOSSY_COBBLESTONE_PILLAR = new Pillar("mossy_cobblestone", Blocks.MOSSY_COBBLESTONE, cobbleConnector);
		CUT_END_STONE_BRICKS_PILLAR = new Pillar("end_stone_bricks", Blocks.END_STONE_BRICKS);
		CUT_RED_NETHER_BRICKS_PILLAR = new Pillar("red_nether_bricks", Blocks.RED_NETHER_BRICKS, netherBricksConnector);
		CUT_BASALT_PILLAR = new Pillar("basalt", Blocks.BASALT);
		CUT_BLACKSTONE_PILLAR = new Pillar("blackstone", Blocks.BLACKSTONE, stoneBricksConnector);
		CUT_QUARTZ_BRICKS_PILLAR = new Pillar("quartz_bricks", Blocks.QUARTZ_BLOCK, quartzConnector);
		CUT_DEEPSLATE_BRICKS_PILLAR = new Pillar("deepslate_bricks", Blocks.DEEPSLATE_BRICKS, deepslateBricksConnector);
		CUT_DEEPSLATE_TILES_PILLAR = new Pillar("deepslate_tiles", Blocks.DEEPSLATE_TILES, deepslateConnector);
		
		TABLE_OAK = new Table("oak", Blocks.OAK_PLANKS);
		TABLE_SPRUCE = new Table("spruce", Blocks.SPRUCE_PLANKS);
		TABLE_BIRCH = new Table("birch", Blocks.BIRCH_PLANKS);
		TABLE_DARK_OAK = new Table("dark_oak", Blocks.DARK_OAK_PLANKS);
		TABLE_JUNGLE = new Table("jungle", Blocks.JUNGLE_PLANKS);
		TABLE_ACACIA = new Table("acacia", Blocks.ACACIA_PLANKS);
		TABLE_CRIMSON = new Table("crimson", Blocks.CRIMSON_PLANKS);
		TABLE_WARPED = new Table("warped", Blocks.WARPED_PLANKS);
		
		/*for(int i = 0; i < colors.length; i++) {
			STOOL_OAK[i] = new Stool("stool_oak_" + colors[i], Blocks.OAK_PLANKS);
			STOOL_SPRUCE[i] = new Stool("stool_spruce_" + colors[i], Blocks.SPRUCE_PLANKS);
			STOOL_BIRCH[i] = new Stool("stool_birch_" + colors[i], Blocks.BIRCH_PLANKS);
			STOOL_DARK_OAK[i] = new Stool("stool_dark_oak_" + colors[i], Blocks.DARK_OAK_PLANKS);
			STOOL_JUNGLE[i] = new Stool("stool_jungle_" + colors[i], Blocks.JUNGLE_PLANKS);
			STOOL_ACACIA[i] = new Stool("stool_acacia_" + colors[i], Blocks.ACACIA_PLANKS);
			STOOL_CRIMSON[i] = new Stool("stool_crimson_" + colors[i], Blocks.CRIMSON_PLANKS);
			STOOL_WARPED[i] = new Stool("stool_warped_" + colors[i], Blocks.WARPED_PLANKS);
		}*/
		
		for(int i = 0; i < colors.length; i++) {
			PILLOW[i] = new Pillow("pillow_" + colors[i]);
		}
		
		STOOL_OAK = new Stool("stool_oak", Blocks.OAK_PLANKS);
		STOOL_SPRUCE = new Stool("stool_spruce", Blocks.SPRUCE_PLANKS);
		STOOL_BIRCH = new Stool("stool_birch", Blocks.BIRCH_PLANKS);
		STOOL_DARK_OAK = new Stool("stool_dark_oak", Blocks.DARK_OAK_PLANKS);
		STOOL_JUNGLE = new Stool("stool_jungle", Blocks.JUNGLE_PLANKS);
		STOOL_ACACIA = new Stool("stool_acacia", Blocks.ACACIA_PLANKS);
		STOOL_CRIMSON = new Stool("stool_crimson", Blocks.CRIMSON_PLANKS);
		STOOL_WARPED = new Stool("stool_warped", Blocks.WARPED_PLANKS);
		
		CHAIR_OAK = new Chair("chair_oak", Blocks.OAK_PLANKS);
		CHAIR_SPRUCE = new Chair("chair_spruce", Blocks.SPRUCE_PLANKS);
		CHAIR_BIRCH = new Chair("chair_birch", Blocks.BIRCH_PLANKS);
		CHAIR_DARK_OAK = new Chair("chair_dark_oak", Blocks.DARK_OAK_PLANKS);
		CHAIR_JUNGLE = new Chair("chair_jungle", Blocks.JUNGLE_PLANKS);
		CHAIR_ACACIA = new Chair("chair_acacia", Blocks.ACACIA_PLANKS);
		CHAIR_CRIMSON = new Chair("chair_crimson", Blocks.CRIMSON_PLANKS);
		CHAIR_WARPED = new Chair("chair_warped", Blocks.WARPED_PLANKS);
		
		HEDGE_OAK = new Hedge("oak", Blocks.OAK_LEAVES);
		HEDGE_SPRUCE = new Hedge("spruce", Blocks.SPRUCE_LEAVES);
		HEDGE_BIRCH = new Hedge("birch", Blocks.BIRCH_LEAVES);
		HEDGE_DARK_OAK = new Hedge("dark_oak", Blocks.DARK_OAK_LEAVES);
		HEDGE_JUNGLE = new Hedge("jungle", Blocks.JUNGLE_LEAVES);
		HEDGE_ACACIA = new Hedge("acacia", Blocks.ACACIA_LEAVES);
		
		for(int i = 0; i < woods.length; i++) {
			COUNTER_ANDESITE[i] = new Counter(woods[i]+"_andesite", woodBlocks[i]);
			COUNTER_DIORITE[i] = new Counter(woods[i]+"_diorite", woodBlocks[i]);
			COUNTER_GRANITE[i] = new Counter(woods[i]+"_granite", woodBlocks[i]);
			COUNTER_BLACKSTONE[i] = new Counter(woods[i]+"_blackstone", woodBlocks[i]);
			COUNTER_DEEPSLATE[i] = new Counter(woods[i]+"_deepslate", woodBlocks[i]);
			int n = 5;
			COUNTER_ALL[i*n] = COUNTER_ANDESITE[i];
			COUNTER_ALL[i*n+1] = COUNTER_DIORITE[i];
			COUNTER_ALL[i*n+2] = COUNTER_GRANITE[i];
			COUNTER_ALL[i*n+3] = COUNTER_BLACKSTONE[i];
			COUNTER_ALL[i*n+4] = COUNTER_DEEPSLATE[i];
		}
		
		BOOKSHELF_OAK = new Bookshelf("oak");
		BOOKSHELF_BIRCH = new Bookshelf("birch");
		BOOKSHELF_SPRUCE = new Bookshelf("spruce");
		BOOKSHELF_DARK_OAK = new Bookshelf("dark_oak");
		BOOKSHELF_JUNGLE = new Bookshelf("jungle");
		BOOKSHELF_ACACIA = new Bookshelf("acacia");
		BOOKSHELF_WARPED = new Bookshelf("warped");
		BOOKSHELF_CRIMSON = new Bookshelf("crimson");
		
		SHELF_OAK = new Shelf("oak");
		SHELF_BIRCH = new Shelf("birch");
		SHELF_SPRUCE = new Shelf("spruce");
		SHELF_DARK_OAK = new Shelf("dark_oak");
		SHELF_JUNGLE = new Shelf("jungle");
		SHELF_ACACIA = new Shelf("acacia");
		SHELF_WARPED = new Shelf("warped");
		SHELF_CRIMSON = new Shelf("crimson");
		
		CABINET_OAK = new Cabinet("oak");
		CABINET_BIRCH = new Cabinet("birch");
		CABINET_SPRUCE = new Cabinet("spruce");
		CABINET_DARK_OAK = new Cabinet("dark_oak");
		CABINET_JUNGLE = new Cabinet("jungle");
		CABINET_ACACIA = new Cabinet("acacia");
		CABINET_WARPED = new Cabinet("warped");
		CABINET_CRIMSON = new Cabinet("crimson");
		
		CUPBOARD_OAK = new Cupboard("oak", Blocks.OAK_PLANKS);
		CUPBOARD_BIRCH = new Cupboard("birch", Blocks.OAK_PLANKS);
		CUPBOARD_SPRUCE = new Cupboard("spruce", Blocks.OAK_PLANKS);
		CUPBOARD_DARK_OAK = new Cupboard("dark_oak", Blocks.OAK_PLANKS);
		CUPBOARD_JUNGLE = new Cupboard("jungle", Blocks.OAK_PLANKS);
		CUPBOARD_ACACIA = new Cupboard("acacia", Blocks.OAK_PLANKS);
		CUPBOARD_WARPED = new Cupboard("warped", Blocks.WARPED_PLANKS);
		CUPBOARD_CRIMSON = new Cupboard("crimson", Blocks.CRIMSON_PLANKS);
		
		SMALL_CUPBOARD_OAK = new SmallCupboard("oak", Blocks.OAK_PLANKS);
		SMALL_CUPBOARD_BIRCH = new SmallCupboard("birch", Blocks.OAK_PLANKS);
		SMALL_CUPBOARD_SPRUCE = new SmallCupboard("spruce", Blocks.OAK_PLANKS);
		SMALL_CUPBOARD_DARK_OAK = new SmallCupboard("dark_oak", Blocks.OAK_PLANKS);
		SMALL_CUPBOARD_JUNGLE = new SmallCupboard("jungle", Blocks.OAK_PLANKS);
		SMALL_CUPBOARD_ACACIA = new SmallCupboard("acacia", Blocks.OAK_PLANKS);
		SMALL_CUPBOARD_WARPED = new SmallCupboard("warped", Blocks.WARPED_PLANKS);
		SMALL_CUPBOARD_CRIMSON = new SmallCupboard("crimson", Blocks.CRIMSON_PLANKS);
		
		BENCH_OAK = new Bench("oak");
		BENCH_BIRCH = new Bench("birch");
		BENCH_SPRUCE = new Bench("spruce");
		BENCH_DARK_OAK = new Bench("dark_oak");
		BENCH_JUNGLE = new Bench("jungle");
		BENCH_ACACIA = new Bench("acacia");
		BENCH_WARPED = new Bench("warped", Blocks.WARPED_PLANKS);
		BENCH_CRIMSON = new Bench("crimson", Blocks.CRIMSON_PLANKS);
		
		SUPPORT_BRACKET_OAK = new SupportBracket("oak");
		SUPPORT_BRACKET_BIRCH = new SupportBracket("birch");
		SUPPORT_BRACKET_SPRUCE = new SupportBracket("spruce");
		SUPPORT_BRACKET_DARK_OAK = new SupportBracket("dark_oak");
		SUPPORT_BRACKET_JUNGLE = new SupportBracket("jungle");
		SUPPORT_BRACKET_ACACIA = new SupportBracket("acacia");
		SUPPORT_BRACKET_WARPED = new SupportBracket("warped", Blocks.WARPED_PLANKS);
		SUPPORT_BRACKET_CRIMSON = new SupportBracket("crimson", Blocks.CRIMSON_PLANKS);
		
		for(int i = 0; i < colors.length; i++) {
			SOFA[i] = new Sofa(colors[i]);
		}
		
		for(int i = 0; i < colors.length; i++) {
			//LUMINUS_GLASS[i] = new LuminusGlass(colors[i], DyeColor.byId(i));
		}
		
		for(int i = 0; i < woods.length; i++) {
			BEDSIDE_TABLE[i] = new BedsideTable(woods[i], woodBlocks[i]);
		}
		
		SHOP_SIGN_WOOD = new ShopSign("wood");
		IRON_LADDER = new IronLadder("iron_ladder");
		IRON_LADDER_ROUGH = new IronLadder("iron_ladder_rough");
		IRON_FENCE = new IronFence("iron_fence");
		IRON_FENCE_ROUGH = new IronFence("iron_fence_rough");
		ROUGH_IRON_BLOCK = new GenericBlock("rough_iron_block", Block.Properties.copy(Blocks.IRON_BLOCK), new BlockOptions());
		GRAVEL_PATH = new GravelPath();
		CROSSRAIL = new Crossrail();
		SPEAKER = new Speaker();
		PLANTER = new Planter();
		//LARGE_CANDLE = new LargeCandle("large_candle", ParticleTypes.FLAME);
		//LARGE_SOUL_CANDLE = new LargeCandle("large_soul_candle", ParticleTypes.SOUL_FIRE_FLAME);
		//CANDLE = new Candle("candle", ParticleTypes.FLAME);
		//SOUL_CANDLE = new Candle("soul_candle", ParticleTypes.SOUL_FIRE_FLAME);
		
		ARCADE = new Arcade();
		ARCADE_OAK = new Arcade("oak");
		ARCADE_BIRCH = new Arcade("birch");
		ARCADE_SPRUCE = new Arcade("spruce");
		ARCADE_DARK_OAK = new Arcade("dark_oak");
		ARCADE_JUNGLE = new Arcade("jungle");
		ARCADE_ACACIA = new Arcade("acacia");
		ARCADE_WARPED = new Arcade("warped");
		ARCADE_CRIMSON = new Arcade("crimson");
		
		FIREPLACE_GUARD = new FireplaceGuard("fireplace_guard");
		ROUGH_FIREPLACE_GUARD = new FireplaceGuard("rough_fireplace_guard");
		
		VERTICAL_REPEATER = new VerticalRepeaterBlock();
		VERTICAL_COMPARATOR = new VerticalComparatorBlock();
		
		ENTITY_DETECTOR = new EntityDetector();
	}
	
	public static void paintings() {
		SUMMER_FIELD_PAINTING = RegistryUtil.createPainting("summer_field", 1, 1);
		SHARD_PAINTING = RegistryUtil.createPainting("shard", 1, 1);
		SKARGARD_PAINTING = RegistryUtil.createPainting("skargard", 2, 1);
		HORIZONS_PAINTING = RegistryUtil.createPainting("horizons", 1, 1);
		PORTRAIT_PAINTING = RegistryUtil.createPainting("portrait", 1, 1);
		PROMO_PAINTING = RegistryUtil.createPainting("promo", 1, 1);
		HEROBRINE_PAINTING = RegistryUtil.createPainting("herobrine", 1, 1);
		ENDERMAN_PAINTING = RegistryUtil.createPainting("enderman", 1, 2);
		WINTER_PAINTING = RegistryUtil.createPainting("winter", 2, 2);
	}
	
	public static void tileentities() {
		COUNTER_TILE_ENTITY_TYPE = 
				TileEntityRegistry.instance.<CounterTileEntity>register(CounterTileEntity::new, new TileEntityOptions("counter", COUNTER_ALL));
		CUPBOARD_TILE_ENTITY_TYPE = 
				TileEntityRegistry.instance.<CupboardTileEntity>register(CupboardTileEntity::new, new TileEntityOptions("cupboard", CUPBOARD_OAK, CUPBOARD_ACACIA, CUPBOARD_BIRCH, CUPBOARD_CRIMSON, CUPBOARD_DARK_OAK, CUPBOARD_JUNGLE, CUPBOARD_SPRUCE, CUPBOARD_WARPED));
		SMALL_CUPBOARD_TILE_ENTITY_TYPE = 
				TileEntityRegistry.instance.<SmallCupboardTileEntity>register(SmallCupboardTileEntity::new, new TileEntityOptions("small_cupboard", SMALL_CUPBOARD_OAK, SMALL_CUPBOARD_ACACIA, SMALL_CUPBOARD_BIRCH, SMALL_CUPBOARD_CRIMSON, SMALL_CUPBOARD_DARK_OAK, SMALL_CUPBOARD_JUNGLE, SMALL_CUPBOARD_SPRUCE, SMALL_CUPBOARD_WARPED));
		CABINET_TILE_ENTITY_TYPE = 
				TileEntityRegistry.instance.<CabinetTileEntity>register(CabinetTileEntity::new, new TileEntityOptions("cabinet", CABINET_OAK, CABINET_BIRCH, CABINET_SPRUCE, CABINET_DARK_OAK, CABINET_JUNGLE, CABINET_ACACIA, CABINET_WARPED, CABINET_CRIMSON));
		BOOKSHELF_TILE_ENTITY_TYPE = 
				TileEntityRegistry.instance.<BookshelfTileEntity>register(BookshelfTileEntity::new, new TileEntityOptions("bookshelf", BOOKSHELF_OAK, BOOKSHELF_BIRCH, BOOKSHELF_SPRUCE, BOOKSHELF_DARK_OAK, BOOKSHELF_JUNGLE, BOOKSHELF_ACACIA, BOOKSHELF_WARPED, BOOKSHELF_CRIMSON));
		SHELF_TILE_ENTITY_TYPE = 
				TileEntityRegistry.instance.<ShelfTileEntity>register(ShelfTileEntity::new, new TileEntityOptions("shelf", SHELF_OAK, SHELF_BIRCH, SHELF_SPRUCE, SHELF_DARK_OAK, SHELF_ACACIA, SHELF_JUNGLE, SHELF_CRIMSON, SHELF_WARPED));
		SHOP_SIGN_TILE_ENTITY_TYPE = 
				TileEntityRegistry.instance.<ShopSignTileEntity>register(ShopSignTileEntity::new, new TileEntityOptions("shop_sign", SHOP_SIGN_WOOD));
		BEDSIDE_TILE_ENTITY_TYPE = 
				TileEntityRegistry.instance.<BedsideTileEntity>register(BedsideTileEntity::new, new TileEntityOptions("bedside_table", BEDSIDE_TABLE));
		SPEAKER_TILE_ENTITY_TYPE = 
				TileEntityRegistry.instance.<SpeakerTileEntity>register(SpeakerTileEntity::new, new TileEntityOptions("speaker", SPEAKER));
		ARCADE_TILE_ENTITY_TYPE = 
				TileEntityRegistry.instance.<ArcadeTileEntity>register(ArcadeTileEntity::new, new TileEntityOptions("arcade", ARCADE, ARCADE_OAK, ARCADE_BIRCH, ARCADE_SPRUCE, ARCADE_DARK_OAK, ARCADE_JUNGLE, ARCADE_ACACIA, ARCADE_WARPED, ARCADE_CRIMSON));
		ENTITY_DETECTOR_TILE_ENTITY_TYPE = 
				TileEntityRegistry.instance.<EntityDetectorTileEntity>register(EntityDetectorTileEntity::new, new TileEntityOptions("entity_detector", ENTITY_DETECTOR));
		VERTICAL_COMPARATOR_TILE_ENTITY_TYPE = 
				TileEntityRegistry.instance.<VerticalComparatorTileEntity>register(VerticalComparatorTileEntity::new, new TileEntityOptions("vertical_comparator", VERTICAL_COMPARATOR));
	}
	
	public static void containers() {
		BOOKSHELF_CONTAINER = (MenuType<BookshelfContainer>) 
				ContainerRegistry.instance.register(IForgeContainerType.create(BookshelfContainer::create).setRegistryName("bookshelf_container"), new ContainerOptions());
		SHELF_CONTAINER = (MenuType<ShelfContainer>) 
				ContainerRegistry.instance.register(IForgeContainerType.create(ShelfContainer::create).setRegistryName("shelf_container"), new ContainerOptions());
		SPEAKER_CONTAINER = (MenuType<SpeakerContainer>) 
				ContainerRegistry.instance.register(IForgeContainerType.create(SpeakerContainer::create).setRegistryName("speaker_container"), new ContainerOptions());
		ARCADE_CONTAINER = (MenuType<ArcadeContainer>) 
				ContainerRegistry.instance.register(IForgeContainerType.create(ArcadeContainer::create).setRegistryName("arcade_container"), new ContainerOptions());
	}
	
	/*BOP_CHERRY_VERTICAL_SLAB = new VerticalSlab("bop_cherry", Blocks.OAK_PLANKS, "biomesoplenty");
	BOP_CUT_WHITE_SANDSTONE_VERTICAL_SLAB = new VerticalSlab("bop_cut_white_sandstone", Blocks.SANDSTONE, "biomesoplenty");
	BOP_DEAD_VERTICAL_SLAB = new VerticalSlab("bop_dead", Blocks.OAK_PLANKS, "biomesoplenty");
	BOP_FIR_VERTICAL_SLAB = new VerticalSlab("bop_fir", Blocks.OAK_PLANKS, "biomesoplenty");
	BOP_HELLBARK_VERTICAL_SLAB = new VerticalSlab("bop_hellbark", Blocks.OAK_PLANKS, "biomesoplenty");
	BOP_JACARANDA_VERTICAL_SLAB = new VerticalSlab("bop_jacaranda", Blocks.OAK_PLANKS, "biomesoplenty");
	BOP_MAGIC_VERTICAL_SLAB = new VerticalSlab("bop_magic", Blocks.OAK_PLANKS, "biomesoplenty");
	BOP_MAHOGANY_VERTICAL_SLAB = new VerticalSlab("bop_mahogany", Blocks.OAK_PLANKS, "biomesoplenty");
	BOP_MUD_BRICKS_VERTICAL_SLAB = new VerticalSlab("bop_mud_bricks", Blocks.BRICKS, "biomesoplenty");
	BOP_PALM_VERTICAL_SLAB = new VerticalSlab("bop_palm", Blocks.OAK_PLANKS, "biomesoplenty");
	BOP_REDWOOD_VERTICAL_SLAB = new VerticalSlab("bop_redwood", Blocks.OAK_PLANKS, "biomesoplenty");
	BOP_SMOOTH_WHITE_SANDSTONE_VERTICAL_SLAB = new VerticalSlab("bop_smooth_white_sandstone", Blocks.SANDSTONE, "biomesoplenty");
	BOP_UMBRAN_VERTICAL_SLAB = new VerticalSlab("bop_umbran", Blocks.OAK_PLANKS, "biomesoplenty");
	BOP_WHITE_SANDSTONE_VERTICAL_SLAB = new VerticalSlab("bop_white_sandstone", Blocks.SANDSTONE, "biomesoplenty");
	BOP_WILLOW_VERTICAL_SLAB = new VerticalSlab("bop_willow", Blocks.OAK_PLANKS, "biomesoplenty");*/
}
