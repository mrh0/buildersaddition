package com.mrh0.buildersaddition.blocks.base;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraftforge.common.ToolType;

public class BaseDerivativeBlock extends BaseBlock {

	private final Block source;
	
	public BaseDerivativeBlock(String name, Block source) {
		super(name, Properties.create(Material.ROCK));
		this.source = source;
	}
	
	public Block getSourceBlock() {
		return this.source;
	}
	
	@Override
	public int getHarvestLevel(BlockState state) {
		return source.getHarvestLevel(source.getDefaultState());
	}
	
	@Override
	public Material getMaterial(BlockState state) {
		return source.getMaterial(source.getDefaultState());
	}
	
	@Override
	public SoundType getSoundType(BlockState state) {
		return source.getSoundType(source.getDefaultState());
	}
	
	@Override
	public float getBlockHardness(BlockState blockState, IBlockReader worldIn, BlockPos pos) {
		return source.getBlockHardness(source.getDefaultState(), worldIn, pos);
	}
	
	@Override
	public float getExplosionResistance(BlockState state, IWorldReader world, BlockPos pos, Entity exploder,
			Explosion explosion) {
		return source.getExplosionResistance(source.getDefaultState(), world, pos, exploder, explosion);
	}
	
	@Override
	public ToolType getHarvestTool(BlockState state) {
		return source.getHarvestTool(source.getDefaultState());
	}
	
	@Override
	public boolean isFlammable(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
		return source.isFlammable(source.getDefaultState(), world, pos, face);
	}
	
	@Override
	public int getFlammability(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
		return source.getFlammability(source.getDefaultState(), world, pos, face);
	}
}
