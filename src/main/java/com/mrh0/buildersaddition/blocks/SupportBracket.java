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

public class SupportBracket extends BaseDerivativeBlock {

	public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;
	public static final DirectionProperty SUPPORTING = DirectionProperty.create("supporting", p -> p == Direction.UP || p == Direction.DOWN);
	
	protected static final VoxelShape SHAPE_UP_SOUTH = Block.makeCuboidShape(6d, 4d, 4d, 10d, 16d, 16d);
	protected static final VoxelShape SHAPE_UP_NORTH = Block.makeCuboidShape(6d, 4d, 0d, 10d, 16d, 12d);
	
	protected static final VoxelShape SHAPE_UP_EAST = Block.makeCuboidShape(4d, 4d, 6d, 16d, 16d, 10d);
	protected static final VoxelShape SHAPE_UP_WEST = Block.makeCuboidShape(0d, 4d, 6d, 12d, 16d, 10d);
	
	protected static final VoxelShape SHAPE_DOWN_SOUTH = Block.makeCuboidShape(6d, 0d, 4d, 10d, 12d, 16d);
	protected static final VoxelShape SHAPE_DOWN_NORTH = Block.makeCuboidShape(6d, 0d, 0d, 10d, 12d, 12d);
	
	protected static final VoxelShape SHAPE_DOWN_EAST = Block.makeCuboidShape(4d, 0d, 6d, 16d, 12d, 10d);
	protected static final VoxelShape SHAPE_DOWN_WEST = Block.makeCuboidShape(0d, 0d, 6d, 12d, 12d, 10d);
	
	public SupportBracket(String name) {
		super("support_bracket_" + name, Blocks.OAK_PLANKS);
		setDefaultState(getDefaultState().with(FACING, Direction.NORTH).with(SUPPORTING, Direction.UP));
	}
	
	public SupportBracket(String name, Block b) {
		super("support_bracket_" + name, b);
		setDefaultState(getDefaultState().with(FACING, Direction.NORTH).with(SUPPORTING, Direction.UP));
	}
	
	@SuppressWarnings("incomplete-switch")
	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		if(state.get(SUPPORTING) == Direction.UP) {
			switch(state.get(FACING)) {
				case NORTH:
					return SHAPE_UP_NORTH;
				case EAST:
					return SHAPE_UP_EAST;
				case SOUTH:
					return SHAPE_UP_SOUTH;
				case WEST:
					return SHAPE_UP_WEST;
			}
		}
		else {
			switch(state.get(FACING)) {
				case NORTH:
					return SHAPE_DOWN_NORTH;
				case EAST:
					return SHAPE_DOWN_EAST;
				case SOUTH:
					return SHAPE_DOWN_SOUTH;
				case WEST:
					return SHAPE_DOWN_WEST;
			}
		}
		return SHAPE_UP_NORTH;
	}

	@Override
	protected void fillStateContainer(Builder<Block, BlockState> builder) {
		builder.add(FACING, SUPPORTING);
	}
	
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext c) {
		Direction f = c.getFace();
		
		if(f == Direction.UP)
			return getDefaultState().with(FACING, c.getPlacementHorizontalFacing()).with(SUPPORTING, Direction.DOWN);
		if(f == Direction.DOWN)
			return getDefaultState().with(FACING, c.getPlacementHorizontalFacing()).with(SUPPORTING, Direction.UP);
		
		double flag = c.getHitVec().y - (double) c.getPos().getY() - .5d;
		return getDefaultState().with(FACING, f.getOpposite()).with(SUPPORTING, flag > 0 ? Direction.UP : Direction .DOWN);
	}
}
