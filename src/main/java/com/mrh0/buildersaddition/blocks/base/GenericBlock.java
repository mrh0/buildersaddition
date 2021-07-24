package com.mrh0.buildersaddition.blocks.base;

import com.mrh0.buildersaddition.event.opts.BlockOptions;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class GenericBlock extends BaseBlock{

	private BlockOptions opts;
	
	public GenericBlock(String name, Properties properties, BlockOptions opts) {
		super(name, properties);
		this.opts = opts;
	}
	
	@Override
	public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
		return opts.shape;
	}
}
