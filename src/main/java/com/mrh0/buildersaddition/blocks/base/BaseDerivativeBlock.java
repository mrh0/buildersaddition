package com.mrh0.buildersaddition.blocks.base;

import com.mrh0.buildersaddition.event.opts.BlockOptions;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraftforge.common.ToolType;

public class BaseDerivativeBlock extends BaseBlock {

	private final Block source;
	
	public BaseDerivativeBlock(String name, Block source) {
		super(name, Properties.from(source));
		this.source = source;
	}
	
	public BaseDerivativeBlock(String name, Block source, BlockOptions opts) {
		super(name, Properties.from(source), opts);
		this.source = source;
	}
	
	public Block getSourceBlock() {
		return this.source;
	}
	
	@Override
	public boolean isFlammable(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
		return source.isFlammable(source.getDefaultState(), world, pos, face);
	}
	
	@Override
	public int getFlammability(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
		return source.getFlammability(source.getDefaultState(), world, pos, face);
	}
	
	@Override
	public int getFireSpreadSpeed(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
		return source.getFireSpreadSpeed(source.getDefaultState(), world, pos, face);
	}
	
	@Override
	public ToolType getHarvestTool(BlockState state) {
		return source.getHarvestTool(source.getDefaultState());
	}
	
	@Override
	public int getHarvestLevel(BlockState state) {
		return source.getHarvestLevel(source.getDefaultState());
	}
}
