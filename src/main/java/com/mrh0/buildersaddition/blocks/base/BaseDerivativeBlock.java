package com.mrh0.buildersaddition.blocks.base;

import com.mrh0.buildersaddition.event.opts.BlockOptions;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ToolType;


public class BaseDerivativeBlock extends BaseBlock {

	private final Block source;
	
	public BaseDerivativeBlock(String name, Block source) {
		super(name, Properties.copy(source));
		this.source = source;
	}
	
	public BaseDerivativeBlock(String name, Block source, BlockOptions opts) {
		super(name, Properties.copy(source), opts);
		this.source = source;
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
	
	@Override
	public ToolType getHarvestTool(BlockState state) {
		return source.getHarvestTool(source.defaultBlockState());
	}
	
	@Override
	public int getHarvestLevel(BlockState state) {
		return source.getHarvestLevel(source.defaultBlockState());
	}
}
