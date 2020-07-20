package com.mrh0.buildersaddition.blocks.base;

import com.mrh0.buildersaddition.event.opts.BlockOptions;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

public class GenericBlock extends BaseBlock{

	private BlockOptions opts;
	
	public GenericBlock(String name, Properties properties, BlockOptions opts) {
		super(name, properties);
		this.opts = opts;
	}
	
	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return opts.shape;
	}
}
