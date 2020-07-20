package com.mrh0.buildersaddition;

import com.mrh0.buildersaddition.blocks.BedsideTable;
import com.mrh0.buildersaddition.blocks.Bookshelf;
import com.mrh0.buildersaddition.blocks.Cabinet;
import com.mrh0.buildersaddition.blocks.Counter;
import com.mrh0.buildersaddition.blocks.Crossrail;
import com.mrh0.buildersaddition.blocks.GravelPath;
import com.mrh0.buildersaddition.blocks.Hedge;
import com.mrh0.buildersaddition.blocks.IronFence;
import com.mrh0.buildersaddition.blocks.IronLadder;
import com.mrh0.buildersaddition.blocks.Pillar;
import com.mrh0.buildersaddition.blocks.Planter;
import com.mrh0.buildersaddition.blocks.Shelf;
import com.mrh0.buildersaddition.blocks.ShopSign;
import com.mrh0.buildersaddition.blocks.Speaker;
import com.mrh0.buildersaddition.blocks.Stool;
import com.mrh0.buildersaddition.blocks.Table;
import com.mrh0.buildersaddition.blocks.VerticalSlab;
import com.mrh0.buildersaddition.blocks.base.GenericBlock;
import com.mrh0.buildersaddition.blocks.base.IConnects;
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
import com.mrh0.buildersaddition.tileentity.BedsideTileEntity;
import com.mrh0.buildersaddition.tileentity.BookshelfTileEntity;
import com.mrh0.buildersaddition.tileentity.CabinetTileEntity;
import com.mrh0.buildersaddition.tileentity.CounterTileEntity;
import com.mrh0.buildersaddition.tileentity.ShelfTileEntity;
import com.mrh0.buildersaddition.tileentity.ShopSignTileEntity;
import com.mrh0.buildersaddition.tileentity.SpeakerTileEntity;
import com.mrh0.buildersaddition.util.RegistryUtil;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.PaintingType;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.Item;
import net.minecraft.item.Item.Properties;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.common.extensions.IForgeContainerType;

@SuppressWarnings("unchecked")
public class Index {
	
	public static final String[] woods = {"oak", "spruce", "birch", "acacia", "dark_oak", "jungle", "crimson", "warped"};
	public static final Block[] woodBlocks = {Blocks.OAK_PLANKS, Blocks.SPRUCE_PLANKS, Blocks.BIRCH_PLANKS, Blocks.DARK_OAK_PLANKS, Blocks.JUNGLE_PLANKS, Blocks.ACACIA_PLANKS, Blocks.field_235344_mC_, Blocks.field_235345_mD_};
	public static final String[] colors = {"black", "blue", "brown", "cyan", "gray", "green", "light_blue", 
			"light_gray", "lime", "magenta", "orange", "pink", "purple", "red", "white", "yellow"};
	
	public static final Item IRON_ROD = new GenericItem("iron_rod", new Properties(), new ItemOptions());
	
	public static final Block OAK_VERTICAL_SLAB = new VerticalSlab("oak", Blocks.OAK_PLANKS);
	public static final Block SPRUCE_VERTICAL_SLAB = new VerticalSlab("spruce", Blocks.SPRUCE_PLANKS);
	public static final Block BIRCH_VERTICAL_SLAB = new VerticalSlab("birch", Blocks.BIRCH_PLANKS);
	public static final Block JUNGLE_VERTICAL_SLAB = new VerticalSlab("jungle", Blocks.JUNGLE_PLANKS);
	public static final Block ACACIA_VERTICAL_SLAB = new VerticalSlab("acacia", Blocks.ACACIA_PLANKS);
	public static final Block DARK_OAK_VERTICAL_SLAB = new VerticalSlab("dark_oak", Blocks.DARK_OAK_PLANKS);
	public static final Block STONE_VERTICAL_SLAB = new VerticalSlab("stone", Blocks.STONE);
	public static final Block SMOOTH_STONE_VERTICAL_SLAB = new VerticalSlab("smooth_stone", Blocks.SMOOTH_STONE);
	public static final Block SANDSTONE_VERTICAL_SLAB = new VerticalSlab("sandstone", Blocks.SANDSTONE);
	public static final Block CUT_SANDSTONE_VERTICAL_SLAB = new VerticalSlab("cut_sandstone", Blocks.CUT_SANDSTONE);
	public static final Block COBBLESTONE_VERTICAL_SLAB = new VerticalSlab("cobblestone", Blocks.COBBLESTONE);
	public static final Block BRICKS_VERTICAL_SLAB = new VerticalSlab("bricks", Blocks.BRICKS);
	public static final Block STONE_BRICKS_VERTICAL_SLAB = new VerticalSlab("stone_bricks", Blocks.STONE_BRICKS);
	public static final Block NETHER_BRICKS_VERTICAL_SLAB = new VerticalSlab("nether_bricks", Blocks.NETHER_BRICKS);
	public static final Block QUARTZ_VERTICAL_SLAB = new VerticalSlab("quartz", Blocks.QUARTZ_BLOCK);
	public static final Block RED_SANDSTONE_VERTICAL_SLAB = new VerticalSlab("red_sandstone", Blocks.RED_SANDSTONE);
	public static final Block CUT_RED_SANDSTONE_VERTICAL_SLAB = new VerticalSlab("cut_red_sandstone", Blocks.CUT_RED_SANDSTONE);
	public static final Block PURPUR_VERTICAL_SLAB = new VerticalSlab("purpur", Blocks.PURPUR_BLOCK);
	public static final Block PRISMARINE_VERTICAL_SLAB = new VerticalSlab("prismarine", Blocks.PRISMARINE);
	public static final Block PRISMARINE_BRICKS_VERTICAL_SLAB = new VerticalSlab("prismarine_bricks", Blocks.PRISMARINE_BRICKS);
	public static final Block DARK_PRISMARINE_VERTICAL_SLAB = new VerticalSlab("dark_prismarine", Blocks.DARK_PRISMARINE);
	public static final Block POLISHED_GRANITE_VERTICAL_SLAB = new VerticalSlab("polished_granite", Blocks.POLISHED_GRANITE);
	public static final Block SMOOTH_RED_SANDSTONE_VERTICAL_SLAB = new VerticalSlab("smooth_red_sandstone", Blocks.SMOOTH_RED_SANDSTONE);
	public static final Block MOSSY_STONE_BRICKS_VERTICAL_SLAB = new VerticalSlab("mossy_stone_bricks", Blocks.MOSSY_STONE_BRICKS);
	public static final Block POLISHED_DIORITE_VERTICAL_SLAB = new VerticalSlab("polished_diorite", Blocks.POLISHED_DIORITE);
	public static final Block MOSSY_COBBLESTONE_VERTICAL_SLAB = new VerticalSlab("mossy_cobblestone", Blocks.MOSSY_COBBLESTONE);
	public static final Block END_STONE_BRICKS_VERTICAL_SLAB = new VerticalSlab("end_stone_bricks", Blocks.END_STONE_BRICKS);
	public static final Block SMOOTH_SANDSTONE_VERTICAL_SLAB = new VerticalSlab("smooth_sandstone", Blocks.SMOOTH_SANDSTONE);
	public static final Block SMOOTH_QUARTZ_VERTICAL_SLAB = new VerticalSlab("smooth_quartz", Blocks.SMOOTH_QUARTZ);
	public static final Block GRANITE_VERTICAL_SLAB = new VerticalSlab("granite", Blocks.GRANITE);
	public static final Block ANDESITE_VERTICAL_SLAB = new VerticalSlab("andesite", Blocks.ANDESITE);
	public static final Block RED_NETHER_BRICKS_VERTICAL_SLAB = new VerticalSlab("red_nether_bricks", Blocks.RED_NETHER_BRICKS);
	public static final Block POLISHED_ANDESITE_VERTICAL_SLAB = new VerticalSlab("polished_andesite", Blocks.POLISHED_ANDESITE);
	public static final Block DIORITE_VERTICAL_SLAB = new VerticalSlab("diorite", Blocks.DIORITE);
	
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
	
	public static final Block CRIMSON_VERTICAL_SLAB = new VerticalSlab("crimson", Blocks.field_235344_mC_);
	public static final Block WARPED_VERTICAL_SLAB = new VerticalSlab("warped", Blocks.field_235345_mD_);
	public static final Block BLACKSTONE_VERTICAL_SLAB = new VerticalSlab("blackstone", Blocks.field_235406_np_);
	public static final Block POLISHED_BLACKSTONE_VERTICAL_SLAB = new VerticalSlab("polished_blackstone", Blocks.field_235410_nt_);
	public static final Block POLISHED_BLACKSTONE_BRICKS_VERTICAL_SLAB = new VerticalSlab("polished_blackstone_bricks", Blocks.field_235411_nu_);
	
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
	
	public static final Block TABLE_OAK = new Table("oak", Blocks.OAK_PLANKS);
	public static final Block TABLE_SPRUCE = new Table("spruce", Blocks.SPRUCE_PLANKS);
	public static final Block TABLE_BIRCH = new Table("birch", Blocks.BIRCH_PLANKS);
	public static final Block TABLE_DARK_OAK = new Table("dark_oak", Blocks.DARK_OAK_PLANKS);
	public static final Block TABLE_JUNGLE = new Table("jungle", Blocks.JUNGLE_PLANKS);
	public static final Block TABLE_ACACIA = new Table("acacia", Blocks.ACACIA_PLANKS);
	public static final Block TABLE_CRIMSON = new Table("crimson", Blocks.field_235344_mC_);
	public static final Block TABLE_WARPED = new Table("warped", Blocks.field_235345_mD_);
	
	public static final Block[] STOOL_OAK = new Block[16];
	public static final Block[] STOOL_SPRUCE = new Block[16];
	public static final Block[] STOOL_BIRCH = new Block[16];
	public static final Block[] STOOL_DARK_OAK = new Block[16];
	public static final Block[] STOOL_JUNGLE = new Block[16];
	public static final Block[] STOOL_ACACIA = new Block[16];
	public static final Block[] STOOL_CRIMSON = new Block[16];
	public static final Block[] STOOL_WARPED = new Block[16];
	
	public static final Block PLANTER = new Planter();
	
	public static final Block HEDGE_OAK = new Hedge("oak", Blocks.OAK_LEAVES);
	public static final Block HEDGE_SPRUCE = new Hedge("spruce", Blocks.SPRUCE_LEAVES);
	public static final Block HEDGE_BIRCH = new Hedge("birch", Blocks.BIRCH_LEAVES);
	public static final Block HEDGE_DARK_OAK = new Hedge("dark_oak", Blocks.DARK_OAK_LEAVES);
	public static final Block HEDGE_JUNGLE = new Hedge("jungle", Blocks.JUNGLE_LEAVES);
	public static final Block HEDGE_ACACIA = new Hedge("acacia", Blocks.ACACIA_LEAVES);
	
	public static final Block[] COUNTER_ANDESITE = new Block[8];
	public static final Block[] COUNTER_DIORITE = new Block[8];
	public static final Block[] COUNTER_GRANITE = new Block[8];
	public static final Block[] COUNTER_BLACKSTONE = new Block[8];
	public static final Block[] COUNTER_ALL = new Block[32];
	
	public static final Block BOOKSHELF_OAK = new Bookshelf("oak");
	public static final Block BOOKSHELF_BIRCH = new Bookshelf("birch");
	public static final Block BOOKSHELF_SPRUCE = new Bookshelf("spruce");
	public static final Block BOOKSHELF_DARK_OAK = new Bookshelf("dark_oak");
	public static final Block BOOKSHELF_JUNGLE = new Bookshelf("jungle");
	public static final Block BOOKSHELF_ACACIA = new Bookshelf("acacia");
	public static final Block BOOKSHELF_WARPED = new Bookshelf("warped");
	public static final Block BOOKSHELF_CRIMSON = new Bookshelf("crimson");
	
	public static final Block SHELF_OAK = new Shelf("oak");
	public static final Block SHELF_BIRCH = new Shelf("birch");
	public static final Block SHELF_SPRUCE = new Shelf("spruce");
	public static final Block SHELF_DARK_OAK = new Shelf("dark_oak");
	public static final Block SHELF_JUNGLE = new Shelf("jungle");
	public static final Block SHELF_ACACIA = new Shelf("acacia");
	public static final Block SHELF_WARPED = new Shelf("warped");
	public static final Block SHELF_CRIMSON = new Shelf("crimson");
	
	public static final Block CABINET_OAK = new Cabinet("oak");
	public static final Block CABINET_BIRCH = new Cabinet("birch");
	public static final Block CABINET_SPRUCE = new Cabinet("spruce");
	public static final Block CABINET_DARK_OAK = new Cabinet("dark_oak");
	public static final Block CABINET_JUNGLE = new Cabinet("jungle");
	public static final Block CABINET_ACACIA = new Cabinet("acacia");
	public static final Block CABINET_WARPED = new Cabinet("warped");
	public static final Block CABINET_CRIMSON = new Cabinet("crimson");
	
	public static final Block SHOP_SIGN_WOOD = new ShopSign("wood");
	public static final Block IRON_LADDER = new IronLadder();
	public static final Block IRON_FENCE = new IronFence("iron_fence");
	public static final Block IRON_FENCE_ROUGH = new IronFence("iron_fence_rough");
	
	public static final Block[] BEDSIDE_TABLE = new BedsideTable[8];
	
	public static final Block ROUGH_IRON_BLOCK = new GenericBlock("rough_iron_block", Block.Properties.from(Blocks.IRON_BLOCK), new BlockOptions());
	public static final Block GRAVEL_PATH = new GravelPath();//new GenericBlock("gravel_path", Block.Properties.from(Blocks.GRAVEL), new BlockOptions().setShape(Block.makeCuboidShape(0, 0, 0, 16, 15, 16)));
	
	public static final Block CROSSRAIL = new Crossrail();
	
	public static final Block SPEAKER = new Speaker();
	
	static {
		final IConnects cobbleConnector = (state, source) -> { 
			return state.getBlock() == CUT_MOSSY_COBBLESTONE_PILLAR || state.getBlock() == CUT_COBBLESTONE_PILLAR; };
			
		final IConnects sandstoneConnector = (state, source) -> { 
			return state.getBlock() == CUT_SMOOTH_RED_SANDSTONE_PILLAR || state.getBlock() == CUT_SMOOTH_SANDSTONE_PILLAR || state.getBlock() == CUT_RED_SANDSTONE_PILLAR || state.getBlock() == CUT_SANDSTONE_PILLAR; };
		
		final IConnects stoneBricksConnector = (state, source) -> { 
			return state.getBlock() == CUT_STONE_BRICKS_PILLAR || state.getBlock() == CUT_MOSSY_STONE_BRICKS_PILLAR  || state.getBlock() == CUT_BLACKSTONE_PILLAR; };
			
		final IConnects netherBricksConnector = (state, source) -> { 
			return state.getBlock() == CUT_NETHER_BRICKS_PILLAR || state.getBlock() == CUT_RED_NETHER_BRICKS_PILLAR; };
			
		final IConnects quartzConnector = (state, source) -> { 
			return state.getBlock() == CUT_QUARTZ_PILLAR || state.getBlock() == CUT_QUARTZ_BRICKS_PILLAR; };
		
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
		CUT_BASALT_PILLAR = new Pillar("basalt", Blocks.field_235337_cO_);
		CUT_BLACKSTONE_PILLAR = new Pillar("blackstone", Blocks.field_235338_cP_, stoneBricksConnector);
		CUT_QUARTZ_BRICKS_PILLAR = new Pillar("quartz_bricks", Blocks.QUARTZ_BLOCK, quartzConnector);
		
		for(int i = 0; i < colors.length; i++) {
			STOOL_OAK[i] = new Stool("stool_oak_" + colors[i], Blocks.OAK_PLANKS);
			STOOL_SPRUCE[i] = new Stool("stool_spruce_" + colors[i], Blocks.SPRUCE_PLANKS);
			STOOL_BIRCH[i] = new Stool("stool_birch_" + colors[i], Blocks.BIRCH_PLANKS);
			STOOL_DARK_OAK[i] = new Stool("stool_dark_oak_" + colors[i], Blocks.DARK_OAK_PLANKS);
			STOOL_JUNGLE[i] = new Stool("stool_jungle_" + colors[i], Blocks.JUNGLE_PLANKS);
			STOOL_ACACIA[i] = new Stool("stool_acacia_" + colors[i], Blocks.ACACIA_PLANKS);
			STOOL_CRIMSON[i] = new Stool("stool_crimson_" + colors[i], Blocks.field_235344_mC_);
			STOOL_WARPED[i] = new Stool("stool_warped_" + colors[i], Blocks.field_235345_mD_);
		}
		
		for(int i = 0; i < woods.length; i++) {
			COUNTER_ANDESITE[i] = new Counter(woods[i]+"_andesite", woodBlocks[i]);
			COUNTER_DIORITE[i] = new Counter(woods[i]+"_diorite", woodBlocks[i]);
			COUNTER_GRANITE[i] = new Counter(woods[i]+"_granite", woodBlocks[i]);
			COUNTER_BLACKSTONE[i] = new Counter(woods[i]+"_blackstone", woodBlocks[i]);
			COUNTER_ALL[i*4] = COUNTER_ANDESITE[i];
			COUNTER_ALL[i*4+1] = COUNTER_DIORITE[i];
			COUNTER_ALL[i*4+2] = COUNTER_GRANITE[i];
			COUNTER_ALL[i*4+3] = COUNTER_BLACKSTONE[i];
		}
		
		for(int i = 0; i < woods.length; i++) {
			BEDSIDE_TABLE[i] = new BedsideTable(woods[i], woodBlocks[i]);
		}
		
		/*if(ModList.get().isLoaded("biomesoplenty")) {
			
		}
		
		BOP_CHERRY_VERTICAL_SLAB = new VerticalSlab("bop_cherry", Blocks.OAK_PLANKS, "biomesoplenty");
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
	
	public static EntityType<SeatEntity> SEAT_ENTITY_TYPE;
	
	public static TileEntityType<CounterTileEntity> COUNTER_TILE_ENTITY_TYPE = 
			TileEntityRegistry.instance.<CounterTileEntity>register(CounterTileEntity::new, new TileEntityOptions("counter", COUNTER_ALL));
	
	public static TileEntityType<CabinetTileEntity> CABINET_TILE_ENTITY_TYPE = 
	TileEntityRegistry.instance.<CabinetTileEntity>register(CabinetTileEntity::new, new TileEntityOptions("cabinet", CABINET_OAK, CABINET_BIRCH, CABINET_SPRUCE, CABINET_DARK_OAK, CABINET_JUNGLE, CABINET_ACACIA, CABINET_WARPED, CABINET_CRIMSON));
	
	public static TileEntityType<BookshelfTileEntity> BOOKSHELF_TILE_ENTITY_TYPE = 
			TileEntityRegistry.instance.<BookshelfTileEntity>register(BookshelfTileEntity::new, new TileEntityOptions("bookshelf", BOOKSHELF_OAK, BOOKSHELF_BIRCH, BOOKSHELF_SPRUCE, BOOKSHELF_DARK_OAK, BOOKSHELF_JUNGLE, BOOKSHELF_ACACIA, BOOKSHELF_WARPED, BOOKSHELF_CRIMSON));
	
	public static TileEntityType<ShelfTileEntity> SHELF_TILE_ENTITY_TYPE = 
			TileEntityRegistry.instance.<ShelfTileEntity>register(ShelfTileEntity::new, new TileEntityOptions("shelf", SHELF_OAK, SHELF_BIRCH, SHELF_SPRUCE, SHELF_DARK_OAK, SHELF_ACACIA, SHELF_JUNGLE, SHELF_CRIMSON, SHELF_WARPED));
	
	public static TileEntityType<ShopSignTileEntity> SHOP_SIGN_TILE_ENTITY_TYPE = 
			TileEntityRegistry.instance.<ShopSignTileEntity>register(ShopSignTileEntity::new, new TileEntityOptions("shop_sign", SHOP_SIGN_WOOD));
	
	public static TileEntityType<BedsideTileEntity> BEDSIDE_TILE_ENTITY_TYPE = 
			TileEntityRegistry.instance.<BedsideTileEntity>register(BedsideTileEntity::new, new TileEntityOptions("bedside_table", BEDSIDE_TABLE));
	
	public static TileEntityType<SpeakerTileEntity> SPEAKER_TILE_ENTITY_TYPE = 
			TileEntityRegistry.instance.<SpeakerTileEntity>register(SpeakerTileEntity::new, new TileEntityOptions("speaker", SPEAKER));

	public static ContainerType<BookshelfContainer> BOOKSHELF_CONTAINER = (ContainerType<BookshelfContainer>) 
			ContainerRegistry.instance.register(IForgeContainerType.create(BookshelfContainer::create).setRegistryName("bookshelf_container"), new ContainerOptions());
	
	public static ContainerType<ShelfContainer> SHELF_CONTAINER = (ContainerType<ShelfContainer>) 
			ContainerRegistry.instance.register(IForgeContainerType.create(ShelfContainer::create).setRegistryName("shelf_container"), new ContainerOptions());

	public static ContainerType<SpeakerContainer> SPEAKER_CONTAINER = (ContainerType<SpeakerContainer>) 
			ContainerRegistry.instance.register(IForgeContainerType.create(SpeakerContainer::create).setRegistryName("speaker_container"), new ContainerOptions());


	public static PaintingType SUMMER_FIELD_PAINTING = RegistryUtil.createPainting("summer_field", 1, 1);
	public static PaintingType SHARD_PAINTING = RegistryUtil.createPainting("shard", 1, 1);
	public static PaintingType SKARGARD_PAINTING = RegistryUtil.createPainting("skargard", 2, 1);
	public static PaintingType HORIZONS_PAINTING = RegistryUtil.createPainting("horizons", 1, 1);
	public static PaintingType PORTRAIT_PAINTING = RegistryUtil.createPainting("portrait", 1, 1);
	public static PaintingType PROMO_PAINTING = RegistryUtil.createPainting("promo", 1, 1);

}
