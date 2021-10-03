package com.mrh0.buildersaddition.blocks.base;
/*
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BaseTable extends BaseDerivativeBlock {

	public static final BooleanProperty NW = BooleanProperty.create("nw");
	public static final BooleanProperty NE = BooleanProperty.create("ne");
	public static final BooleanProperty SW = BooleanProperty.create("sw");
	public static final BooleanProperty SE = BooleanProperty.create("se");
	
	protected static final VoxelShape BASE_SHAPE = Block.box(0d, 14d, 0d, 16d, 16d, 16d);
	protected static final VoxelShape NW_SHAPE = Block.box(1d, 0d, 1d, 3d, 14d, 3d);
	protected static final VoxelShape NE_SHAPE = Block.box(13d, 0d, 1d, 15d, 14d, 3d);
	protected static final VoxelShape SW_SHAPE = Block.box(1d, 0d, 13d, 3d, 14d, 15d);
	protected static final VoxelShape SE_SHAPE = Block.box(13d, 0d, 13d, 15d, 14d, 15d);
	
	public BaseTable(String name, Block source) {
		super("table_" + name, source);
	}
	
	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
		builder.add(NE, NW, SE, SW);
	}
	
	@Override
	public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos,
			CollisionContext c) {
		return getShape(state);
	}
	
	public BlockState getState(BlockState state, BlockGetter worldIn, BlockPos pos) {
		BlockState N = worldIn.getBlockState(pos.north());
		BlockState W = worldIn.getBlockState(pos.west());
		BlockState S = worldIn.getBlockState(pos.south());
		BlockState E = worldIn.getBlockState(pos.east());
		boolean n = N.getBlock() instanceof BaseTable;
		boolean w = W.getBlock() instanceof BaseTable;
		boolean s = S.getBlock() instanceof BaseTable;
		boolean e = E.getBlock() instanceof BaseTable;
		
		return this.defaultBlockState()
				.setValue(NE, !n && !e)
				.setValue(NW, !n && !w)
				.setValue(SE, !s && !e)
				.setValue(SW, !s && !w);
	}
	
	public VoxelShape getShape(BlockState state) {
		boolean ne = state.getValue(NE);
		boolean nw = state.getValue(NW);
		boolean se = state.getValue(SE);
		boolean sw = state.getValue(SW);
		
        return Shapes.or(BASE_SHAPE, 
        	ne ? NE_SHAPE : Shapes.empty(),
        	nw ? NW_SHAPE : Shapes.empty(),
        	se ? SE_SHAPE : Shapes.empty(),
        	sw ? SW_SHAPE : Shapes.empty()
        );
    }
	
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext c) {
		return getState(this.defaultBlockState(), c.getLevel(), c.getClickedPos());
	}

	@Override
	public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
		return getState(stateIn, worldIn, currentPos);
	}
}*/
