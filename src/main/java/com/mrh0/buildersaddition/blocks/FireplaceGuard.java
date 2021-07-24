package com.mrh0.buildersaddition.blocks;

import com.mrh0.buildersaddition.blocks.base.BaseDerivativeBlock;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class FireplaceGuard extends BaseDerivativeBlock {

	protected static final VoxelShape NORTH_SHAPE = Block.box(0.0D, 0.0D, 14D, 16D, 9D, 16D);
	protected static final VoxelShape EAST_SHAPE = Block.box(0.0D, 0.0D, 0.0D, 2.0D, 9D, 16.0D);
	protected static final VoxelShape SOUTH_SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16D, 9D, 2D);
	protected static final VoxelShape WEST_SHAPE = Block.box(14D, 0.0D, 0.0D, 16D, 9D, 16.0D);
	
	public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
	
	public FireplaceGuard(String name) {
		super(name, Blocks.IRON_BARS);
	}

	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
		builder.add(FACING);
	}
	
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext c) {
		if(c.getClickedFace() == Direction.UP || c.getClickedFace() == Direction.DOWN)
			return this.defaultBlockState().setValue(FACING, c.getHorizontalDirection().getOpposite());
		return this.defaultBlockState().setValue(FACING, c.getClickedFace());
	}
	
	@Override
	public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
		switch (state.getValue(FACING)) {
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
