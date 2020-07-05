package com.mrh0.buildersaddition.blocks;

import com.mrh0.buildersaddition.blocks.base.BaseDerivativeBlock;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;

public class Table extends BaseDerivativeBlock {

	public static final BooleanProperty NW = BooleanProperty.create("nw");
	public static final BooleanProperty NE = BooleanProperty.create("ne");
	public static final BooleanProperty SW = BooleanProperty.create("sw");
	public static final BooleanProperty SE = BooleanProperty.create("se");
	
	protected static final VoxelShape BASE_SHAPE = Block.makeCuboidShape(0d, 14d, 0d, 16d, 16d, 16d);
	protected static final VoxelShape NW_SHAPE = Block.makeCuboidShape(1d, 0d, 1d, 3d, 14d, 3d);
	protected static final VoxelShape NE_SHAPE = Block.makeCuboidShape(13d, 0d, 1d, 15d, 14d, 3d);
	protected static final VoxelShape SW_SHAPE = Block.makeCuboidShape(1d, 0d, 13d, 3d, 14d, 15d);
	protected static final VoxelShape SE_SHAPE = Block.makeCuboidShape(13d, 0d, 13d, 15d, 14d, 15d);
	
	public Table(String name, Block source) {
		super("table_" + name, source);
	}

	@Override
	protected void fillStateContainer(Builder<Block, BlockState> builder) {
		builder.add(NE, NW, SE, SW);
	}
	
	public BlockState getState(BlockState state, IWorld worldIn, BlockPos pos) {
		BlockState N = worldIn.getBlockState(pos.north());
		BlockState W = worldIn.getBlockState(pos.west());
		BlockState S = worldIn.getBlockState(pos.south());
		BlockState E = worldIn.getBlockState(pos.east());
		boolean n = N.getBlock() instanceof Table;
		boolean w = W.getBlock() instanceof Table;
		boolean s = S.getBlock() instanceof Table;
		boolean e = E.getBlock() instanceof Table;
		
		return this.getDefaultState()
				.with(NE, !n && !e)
				.with(NW, !n && !w)
				.with(SE, !s && !e)
				.with(SW, !s && !w);
	}
	
	public VoxelShape getShape(BlockState state) {
		boolean ne = state.get(NE);
		boolean nw = state.get(NW);
		boolean se = state.get(SE);
		boolean sw = state.get(SW);
		
        return VoxelShapes.or(BASE_SHAPE, 
        	ne ? NE_SHAPE : VoxelShapes.empty(),
        	nw ? NW_SHAPE : VoxelShapes.empty(),
        	se ? SE_SHAPE : VoxelShapes.empty(),
        	sw ? SW_SHAPE : VoxelShapes.empty()
        );
    }
	
	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return getShape(state);
	}
	
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext c) {
		return getState(this.getDefaultState(), c.getWorld(), c.getPos());
	}

	@Override
	public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn,
			BlockPos currentPos, BlockPos facingPos) {
		return getState(stateIn, worldIn, currentPos);
	}
}
