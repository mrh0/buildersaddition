package com.mrh0.buildersaddition.blocks;

import com.mrh0.buildersaddition.blocks.base.BaseDerivativeBlock;
import com.mrh0.buildersaddition.tileentity.BookshelfTileEntity;
import com.mrh0.buildersaddition.tileentity.ShelfTileEntity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

public class Shelf extends BaseDerivativeBlock {
	public static final DirectionProperty FACING = DirectionProperty.create("facing",
			p -> p.getIndex() > 1 && p.getIndex() < Direction.values().length);
	
	protected static final VoxelShape NORTH_SHAPE = Block.makeCuboidShape(0.0D, 0.0D, 8D, 16D, 16D, 16D);
	protected static final VoxelShape EAST_SHAPE = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 8.0D, 16.0D, 16.0D);
	protected static final VoxelShape SOUTH_SHAPE = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16D, 16D, 8D);
	protected static final VoxelShape WEST_SHAPE = Block.makeCuboidShape(8D, 0.0D, 0.0D, 16D, 16.0D, 16.0D);
	
	public Shelf(String name) {
		super("shelf_" + name, Blocks.OAK_PLANKS);
		setDefaultState(getDefaultState().with(FACING, Direction.NORTH));
	}
	
	@Override
	protected void fillStateContainer(Builder<Block, BlockState> builder) {
		builder.add(FACING);
	}
	
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext c) {
		return this.getDefaultState().with(FACING, c.getPlacementHorizontalFacing().getOpposite());
	}

	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return new ShelfTileEntity();
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
