package com.mrh0.buildersaddition.blocks.base;

import com.mrh0.buildersaddition.data.block.BABlockTagsProvider;
import com.mrh0.buildersaddition.event.opts.BlockOptions;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;


public class BaseDerivativeBlock extends BaseBlock {

	private final Block source;
	
	public BaseDerivativeBlock(String name, Block source) {
		super(name, Properties.copy(source));
		this.source = source;
		
		blockTagSort(this, source);
	}
	
	public BaseDerivativeBlock(String name, Block source, BlockOptions opts) {
		super(name, Properties.copy(source), opts);
		this.source = source;
		
		blockTagSort(this, source);
	}
	
	public static void blockTagSort(Block current, Block source) {
		Material m = source.defaultBlockState().getMaterial();
		
		if(m == Material.WOOD || m == Material.NETHER_WOOD || m == Material.WOOL)
			BABlockTagsProvider.woodenBlocks.add(current);
		else if(m == Material.STONE)
			BABlockTagsProvider.stoneBlocks.add(current);
		else if(m == Material.METAL || m == Material.HEAVY_METAL) {
			BABlockTagsProvider.ironBlocks.add(current);
			BABlockTagsProvider.stoneBlocks.add(current);
		}
		else if(m == Material.DIRT || m == Material.SAND || m == Material.GRASS) 
			BABlockTagsProvider.earthBlocks.add(current);
		else if(m == Material.LEAVES) 
			BABlockTagsProvider.leavesBlocks.add(current);
		else
			System.out.println("Skipping: " + current.getRegistryName());
	}
	
	public Block getSourceBlock() {
		return this.source;
	}
	
	@Override
	public boolean isFlammable(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
		return source.isFlammable(source.defaultBlockState(), world, pos, face);
	}
	
	@Override
	public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
		return source.getFlammability(source.defaultBlockState(), world, pos, face);
	}
	
	@Override
	public int getFireSpreadSpeed(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
		return source.getFireSpreadSpeed(source.defaultBlockState(), world, pos, face);
	}
}
