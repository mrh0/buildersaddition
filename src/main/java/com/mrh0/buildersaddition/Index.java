package com.mrh0.buildersaddition;

import com.mrh0.buildersaddition.blocks.base.BasePillar;
import com.mrh0.buildersaddition.blocks.base.BaseStool;
import com.mrh0.buildersaddition.blocks.base.BaseTable;
import com.mrh0.buildersaddition.blocks.base.BaseVerticalSlab;
import com.mrh0.buildersaddition.blocks.base.IConnects;
import com.mrh0.buildersaddition.entity.SeatEntity;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;

public class Index {
	
	public static final String[] colors = {"black", "blue", "brown", "cyan", "gray", "green", "light_blue", 
			"light_gray", "lime", "magenta", "orange", "pink", "purple", "red", "white", "yellow"};
	
	public static final Block OAK_VERTICAL_SLAB = new BaseVerticalSlab("oak", Blocks.OAK_PLANKS);
	public static final Block SPRUCE_VERTICAL_SLAB = new BaseVerticalSlab("spruce", Blocks.SPRUCE_PLANKS);
	public static final Block BIRCH_VERTICAL_SLAB = new BaseVerticalSlab("birch", Blocks.BIRCH_PLANKS);
	public static final Block JUNGLE_VERTICAL_SLAB = new BaseVerticalSlab("jungle", Blocks.JUNGLE_PLANKS);
	public static final Block ACACIA_VERTICAL_SLAB = new BaseVerticalSlab("acacia", Blocks.ACACIA_PLANKS);
	public static final Block DARK_OAK_VERTICAL_SLAB = new BaseVerticalSlab("dark_oak", Blocks.DARK_OAK_PLANKS);
	public static final Block STONE_VERTICAL_SLAB = new BaseVerticalSlab("stone", Blocks.STONE);
	public static final Block SMOOTH_STONE_VERTICAL_SLAB = new BaseVerticalSlab("smooth_stone", Blocks.SMOOTH_STONE);
	public static final Block SANDSTONE_VERTICAL_SLAB = new BaseVerticalSlab("sandstone", Blocks.SANDSTONE);
	public static final Block CUT_SANDSTONE_VERTICAL_SLAB = new BaseVerticalSlab("cut_sandstone", Blocks.CUT_SANDSTONE);
	public static final Block COBBLESTONE_VERTICAL_SLAB = new BaseVerticalSlab("cobblestone", Blocks.COBBLESTONE);
	public static final Block BRICKS_VERTICAL_SLAB = new BaseVerticalSlab("bricks", Blocks.BRICKS);
	public static final Block STONE_BRICKS_VERTICAL_SLAB = new BaseVerticalSlab("stone_bricks", Blocks.STONE_BRICKS);
	public static final Block NETHER_BRICKS_VERTICAL_SLAB = new BaseVerticalSlab("nether_bricks", Blocks.NETHER_BRICKS);
	public static final Block QUARTZ_VERTICAL_SLAB = new BaseVerticalSlab("quartz", Blocks.QUARTZ_BLOCK);
	public static final Block RED_SANDSTONE_VERTICAL_SLAB = new BaseVerticalSlab("red_sandstone", Blocks.RED_SANDSTONE);
	public static final Block CUT_RED_SANDSTONE_VERTICAL_SLAB = new BaseVerticalSlab("cut_red_sandstone", Blocks.CUT_RED_SANDSTONE);
	public static final Block PURPUR_VERTICAL_SLAB = new BaseVerticalSlab("purpur", Blocks.PURPUR_BLOCK);
	public static final Block PRISMARINE_VERTICAL_SLAB = new BaseVerticalSlab("prismarine", Blocks.PRISMARINE);
	public static final Block PRISMARINE_BRICKS_VERTICAL_SLAB = new BaseVerticalSlab("prismarine_bricks", Blocks.PRISMARINE_BRICKS);
	public static final Block DARK_PRISMARINE_VERTICAL_SLAB = new BaseVerticalSlab("dark_prismarine", Blocks.DARK_PRISMARINE);
	public static final Block POLISHED_GRANITE_VERTICAL_SLAB = new BaseVerticalSlab("polished_granite", Blocks.POLISHED_GRANITE);
	public static final Block SMOOTH_RED_SANDSTONE_VERTICAL_SLAB = new BaseVerticalSlab("smooth_red_sandstone", Blocks.SMOOTH_RED_SANDSTONE);
	public static final Block MOSSY_STONE_BRICKS_VERTICAL_SLAB = new BaseVerticalSlab("mossy_stone_bricks", Blocks.MOSSY_STONE_BRICKS);
	public static final Block POLISHED_DIORITE_VERTICAL_SLAB = new BaseVerticalSlab("polished_diorite", Blocks.POLISHED_DIORITE);
	public static final Block MOSSY_COBBLESTONE_VERTICAL_SLAB = new BaseVerticalSlab("mossy_cobblestone", Blocks.MOSSY_COBBLESTONE);
	public static final Block END_STONE_BRICKS_VERTICAL_SLAB = new BaseVerticalSlab("end_stone_bricks", Blocks.END_STONE_BRICKS);
	public static final Block SMOOTH_SANDSTONE_VERTICAL_SLAB = new BaseVerticalSlab("smooth_sandstone", Blocks.SMOOTH_SANDSTONE);
	public static final Block SMOOTH_QUARTZ_VERTICAL_SLAB = new BaseVerticalSlab("smooth_quartz", Blocks.SMOOTH_QUARTZ);
	public static final Block GRANITE_VERTICAL_SLAB = new BaseVerticalSlab("granite", Blocks.GRANITE);
	public static final Block ANDESITE_VERTICAL_SLAB = new BaseVerticalSlab("andesite", Blocks.ANDESITE);
	public static final Block RED_NETHER_BRICKS_VERTICAL_SLAB = new BaseVerticalSlab("red_nether_bricks", Blocks.RED_NETHER_BRICKS);
	public static final Block POLISHED_ANDESITE_VERTICAL_SLAB = new BaseVerticalSlab("polished_andesite", Blocks.POLISHED_ANDESITE);
	public static final Block DIORITE_VERTICAL_SLAB = new BaseVerticalSlab("diorite", Blocks.DIORITE);
	
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
	
	public static final Block TABLE_OAK = new BaseTable("oak", Blocks.OAK_PLANKS);
	public static final Block TABLE_SPRUCE = new BaseTable("spruce", Blocks.SPRUCE_PLANKS);
	public static final Block TABLE_BIRCH = new BaseTable("birch", Blocks.BIRCH_PLANKS);
	public static final Block TABLE_DARK_OAK = new BaseTable("dark_oak", Blocks.DARK_OAK_PLANKS);
	public static final Block TABLE_JUNGLE = new BaseTable("jungle", Blocks.JUNGLE_PLANKS);
	public static final Block TABLE_ACACIA = new BaseTable("acacia", Blocks.ACACIA_PLANKS);
	
	public static final Block[] STOOL_OAK = new Block[16];
	public static final Block[] STOOL_SPRUCE = new Block[16];
	public static final Block[] STOOL_BIRCH = new Block[16];
	public static final Block[] STOOL_DARK_OAK = new Block[16];
	public static final Block[] STOOL_JUNGLE = new Block[16];
	public static final Block[] STOOL_ACACIA = new Block[16];
	
		
	static {
		
		final IConnects cobbleConnector = (state, source) -> { 
			return state.getBlock() == CUT_MOSSY_COBBLESTONE_PILLAR || state.getBlock() == CUT_COBBLESTONE_PILLAR; };
			
		final IConnects sandstoneConnector = (state, source) -> { 
			return state.getBlock() == CUT_SMOOTH_RED_SANDSTONE_PILLAR || state.getBlock() == CUT_SMOOTH_SANDSTONE_PILLAR || state.getBlock() == CUT_RED_SANDSTONE_PILLAR || state.getBlock() == CUT_SANDSTONE_PILLAR; };
		
		final IConnects stoneBricksConnector = (state, source) -> { 
			return state.getBlock() == CUT_STONE_BRICKS_PILLAR || state.getBlock() == CUT_MOSSY_STONE_BRICKS_PILLAR; };
			
			final IConnects netherBricksConnector = (state, source) -> { 
				return state.getBlock() == CUT_NETHER_BRICKS_PILLAR || state.getBlock() == CUT_RED_NETHER_BRICKS_PILLAR; };
		
		CUT_STONE_PILLAR = new BasePillar("stone", Blocks.STONE);
		CUT_SMOOTH_STONE_PILLAR = new BasePillar("smooth_stone", Blocks.SMOOTH_STONE);
		CUT_SANDSTONE_PILLAR = new BasePillar("sandstone", Blocks.SANDSTONE, sandstoneConnector);
		CUT_COBBLESTONE_PILLAR = new BasePillar("cobblestone", Blocks.COBBLESTONE, cobbleConnector);
		CUT_BRICKS_PILLAR = new BasePillar("bricks", Blocks.BRICKS);
		CUT_STONE_BRICKS_PILLAR = new BasePillar("stone_bricks", Blocks.STONE_BRICKS, stoneBricksConnector);
		CUT_NETHER_BRICKS_PILLAR = new BasePillar("nether_bricks", Blocks.NETHER_BRICKS, netherBricksConnector);
		CUT_QUARTZ_PILLAR = new BasePillar("quartz", Blocks.QUARTZ_BLOCK);
		CUT_RED_SANDSTONE_PILLAR = new BasePillar("red_sandstone", Blocks.RED_SANDSTONE, sandstoneConnector);
		CUT_PURPUR_PILLAR = new BasePillar("purpur", Blocks.PURPUR_PILLAR);
		CUT_PRISMARINE_PILLAR = new BasePillar("prismarine", Blocks.PRISMARINE, 15);
		CUT_SMOOTH_RED_SANDSTONE_PILLAR = new BasePillar("smooth_red_sandstone", Blocks.SMOOTH_RED_SANDSTONE, sandstoneConnector);
		CUT_SMOOTH_SANDSTONE_PILLAR = new BasePillar("smooth_sandstone", Blocks.SMOOTH_SANDSTONE, sandstoneConnector);
		CUT_MOSSY_STONE_BRICKS_PILLAR = new BasePillar("mossy_stone_bricks", Blocks.MOSSY_STONE_BRICKS, stoneBricksConnector);
		CUT_MOSSY_COBBLESTONE_PILLAR = new BasePillar("mossy_cobblestone", Blocks.MOSSY_COBBLESTONE, cobbleConnector);
		CUT_END_STONE_BRICKS_PILLAR = new BasePillar("end_stone_bricks", Blocks.END_STONE_BRICKS);
		CUT_RED_NETHER_BRICKS_PILLAR = new BasePillar("red_nether_bricks", Blocks.RED_NETHER_BRICKS, netherBricksConnector);
		
		for(int i = 0; i < colors.length; i++) {
			STOOL_OAK[i] = new BaseStool("stool_oak_" + colors[i], Blocks.OAK_PLANKS);
			STOOL_SPRUCE[i] = new BaseStool("stool_spruce_" + colors[i], Blocks.SPRUCE_PLANKS);
			STOOL_BIRCH[i] = new BaseStool("stool_birch_" + colors[i], Blocks.BIRCH_PLANKS);
			STOOL_DARK_OAK[i] = new BaseStool("stool_dark_oak_" + colors[i], Blocks.DARK_OAK_PLANKS);
			STOOL_JUNGLE[i] = new BaseStool("stool_jungle_" + colors[i], Blocks.JUNGLE_PLANKS);
			STOOL_ACACIA[i] = new BaseStool("stool_acacia_" + colors[i], Blocks.ACACIA_PLANKS);
		}
	}
	
	public static EntityType<SeatEntity> SEAT_ENTITY_TYPE;
}
