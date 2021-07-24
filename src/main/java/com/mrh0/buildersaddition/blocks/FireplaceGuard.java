package com.mrh0.buildersaddition.blocks;

import com.mrh0.buildersaddition.blocks.base.BaseDerivativeBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

import VoxelShape;

public class FireplaceGuard extends BaseDerivativeBlock {

	protected static final VoxelShape NORTH_SHAPE = Block.makeCuboidShape(0.0D, 0.0D, 14D, 16D, 9D, 16D);
	protected static final VoxelShape EAST_SHAPE = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 2.0D, 9D, 16.0D);
	protected static final VoxelShape SOUTH_SHAPE = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16D, 9D, 2D);
	protected static final VoxelShape WEST_SHAPE = Block.makeCuboidShape(14D, 0.0D, 0.0D, 16D, 9D, 16.0D);
	
	public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;
	
	public FireplaceGuard(String name) {
		super(name, Blocks.IRON_BARS);
	}

	@Override
	protected void fillStateContainer(Builder<Block, BlockState> builder) {
		builder.add(FACING);
	}
	
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext c) {
		if(c.getFace() == Direction.UP || c.getFace() == Direction.DOWN)
			return this.getDefaultState().with(FACING, c.getPlacementHorizontalFacing().getOpposite());
		return this.getDefaultState().with(FACING, c.getFace());
	}
	
	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		switch (state.get(FACING)) {
		case NORTH:
			return NORTH_SHAPE;
		case EAST:
			return EAST_SHAPE;
		case SOUTH:
			return SOUTH_SHAPE;
		case WEST:
			return WEST_SHAPE;
		default:
			return NORTH_SHAPE;
		}
	}
}
