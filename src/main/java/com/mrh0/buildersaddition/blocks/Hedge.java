package com.mrh0.buildersaddition.blocks;

import com.mrh0.buildersaddition.blocks.base.BaseDerivativeBlock;
import com.mrh0.buildersaddition.state.HedgeState;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.IForgeShearable;

public class Hedge extends BaseDerivativeBlock implements SimpleWaterloggedBlock, IForgeShearable {
	
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	public static final EnumProperty<HedgeState> STATE = EnumProperty.<HedgeState>create("state", HedgeState.class);
	
	private static VoxelShape SHAPE_NONE = Block.box(4d, 0d, 4d, 12d, 16d, 12d);
	private static VoxelShape SHAPE_STRAIGHT_Z = Block.box(0d, 0d, 4d, 16d, 16d, 12d);
	private static VoxelShape SHAPE_STRAIGHT_X = Block.box(4d, 0d, 0d, 12d, 16d, 16d);
	
	private static VoxelShape SHAPE_SHORT_N = Block.box(4d, 0d, 0d, 12d, 16d, 4d);
	private static VoxelShape SHAPE_SHORT_E = Block.box(12d, 0d, 4d, 16d, 16d, 12d);
	private static VoxelShape SHAPE_SHORT_S = Block.box(4d, 0d, 12d, 12d, 16d, 16d);
	private static VoxelShape SHAPE_SHORT_W = Block.box(0d, 0d, 4d, 4d, 16d, 12d);
	
	private static VoxelShape SHAPE_CORNER_NE = Shapes.or(SHAPE_NONE, SHAPE_SHORT_N, SHAPE_SHORT_E);
	private static VoxelShape SHAPE_CORNER_NW = Shapes.or(SHAPE_NONE, SHAPE_SHORT_N, SHAPE_SHORT_W);
	private static VoxelShape SHAPE_CORNER_SE = Shapes.or(SHAPE_NONE, SHAPE_SHORT_S, SHAPE_SHORT_E);
	private static VoxelShape SHAPE_CORNER_SW = Shapes.or(SHAPE_NONE, SHAPE_SHORT_S, SHAPE_SHORT_W);
	
	private static VoxelShape SHAPE_T_N = Shapes.or(SHAPE_STRAIGHT_Z, SHAPE_SHORT_N);
	private static VoxelShape SHAPE_T_E = Shapes.or(SHAPE_STRAIGHT_X, SHAPE_SHORT_E);
	private static VoxelShape SHAPE_T_S = Shapes.or(SHAPE_STRAIGHT_Z, SHAPE_SHORT_S);
	private static VoxelShape SHAPE_T_W = Shapes.or(SHAPE_STRAIGHT_X, SHAPE_SHORT_W);
	
	private static VoxelShape SHAPE_CROSS = Shapes.or(SHAPE_STRAIGHT_X, SHAPE_STRAIGHT_Z);
	
	private static VoxelShape COL_NONE = Block.box(4d, 0d, 4d, 12d, 24d, 12d);
	private static VoxelShape COL_STRAIGHT_Z = Block.box(0d, 0d, 4d, 16d, 24d, 12d);
	private static VoxelShape COL_STRAIGHT_X = Block.box(4d, 0d, 0d, 12d, 24d, 16d);
	
	private static VoxelShape COL_SHORT_N = Block.box(4d, 0d, 0d, 12d, 24d, 4d);
	private static VoxelShape COL_SHORT_E = Block.box(12d, 0d, 4d, 16d, 24d, 12d);
	private static VoxelShape COL_SHORT_S = Block.box(4d, 0d, 12d, 12d, 24d, 16d);
	private static VoxelShape COL_SHORT_W = Block.box(0d, 0d, 4d, 4d, 24d, 12d);
	
	private static VoxelShape COL_CORNER_NE = Shapes.or(COL_NONE, COL_SHORT_N, COL_SHORT_E);
	private static VoxelShape COL_CORNER_NW = Shapes.or(COL_NONE, COL_SHORT_N, COL_SHORT_W);
	private static VoxelShape COL_CORNER_SE = Shapes.or(COL_NONE, COL_SHORT_S, COL_SHORT_E);
	private static VoxelShape COL_CORNER_SW = Shapes.or(COL_NONE, COL_SHORT_S, COL_SHORT_W);
	
	private static VoxelShape COL_T_N = Shapes.or(COL_STRAIGHT_Z, COL_SHORT_N);
	private static VoxelShape COL_T_E = Shapes.or(COL_STRAIGHT_X, COL_SHORT_E);
	private static VoxelShape COL_T_S = Shapes.or(COL_STRAIGHT_Z, COL_SHORT_S);
	private static VoxelShape COL_T_W = Shapes.or(COL_STRAIGHT_X, COL_SHORT_W);
	
	private static VoxelShape COL_CROSS = Shapes.or(COL_STRAIGHT_X, COL_STRAIGHT_Z);
	
	public Hedge(String name, Block source) {
		super("hedge_" + name, source);
		registerDefaultState(defaultBlockState().setValue(WATERLOGGED, false).setValue(STATE, HedgeState.None));
	}
	
	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
		builder.add(STATE, WATERLOGGED);
	}
	
	@Override
	public boolean isShearable(ItemStack item, Level world, BlockPos pos) {
		return true;
	}

	public VoxelShape getShape(BlockState state) {
		switch(state.getValue(STATE)) {
			case None:
				return SHAPE_NONE;
				
			case Straight_X:
				return SHAPE_STRAIGHT_X;
			case Straight_Z:
				return SHAPE_STRAIGHT_Z;
				
			case Corner_NE:
				return SHAPE_CORNER_NE;
			case Corner_NW:
				return SHAPE_CORNER_NW;
			case Corner_SE:
				return SHAPE_CORNER_SE;
			case Corner_SW:
				return SHAPE_CORNER_SW;
				
			case TCross_N:
				return SHAPE_T_N;
			case TCross_E:
				return SHAPE_T_E;
			case TCross_S:
				return SHAPE_T_S;
			case TCross_W:
				return SHAPE_T_W;
				
			case Cross:
				return SHAPE_CROSS;
		}
			
        return SHAPE_NONE;
    }
	
	@Override
	public VoxelShape getCollisionShape(BlockState state, BlockGetter worldIn, BlockPos pos,
			CollisionContext context) {
		switch(state.getValue(STATE)) {
			case None:
				return COL_NONE;
				
			case Straight_X:
				return COL_STRAIGHT_X;
			case Straight_Z:
				return COL_STRAIGHT_Z;
				
			case Corner_NE:
				return COL_CORNER_NE;
			case Corner_NW:
				return COL_CORNER_NW;
			case Corner_SE:
				return COL_CORNER_SE;
			case Corner_SW:
				return COL_CORNER_SW;
				
			case TCross_N:
				return COL_T_N;
			case TCross_E:
				return COL_T_E;
			case TCross_S:
				return COL_T_S;
			case TCross_W:
				return COL_T_W;
				
			case Cross:
				return COL_CROSS;
		}
	    return COL_NONE;
	}
	
	@Override
	public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
		return getShape(state);
	}
	
	public BlockState getState(BlockState state, BlockGetter worldIn, BlockPos pos) {
		BlockState bn = worldIn.getBlockState(pos.north());
		BlockState be = worldIn.getBlockState(pos.east());
		BlockState bs = worldIn.getBlockState(pos.south());
		BlockState bw = worldIn.getBlockState(pos.west());
		
		boolean n = bn.getBlock() instanceof Hedge;
		boolean e = be.getBlock() instanceof Hedge;
		boolean s = bs.getBlock() instanceof Hedge;
		boolean w = bw.getBlock() instanceof Hedge;
		
		if(n && e && s && w)
			return getNextState(state, HedgeState.Cross);
		
		if(!n && !e && !s && !w)
			return getNextState(state, HedgeState.None);
		
		else if(n && e && !s && w)
			return getNextState(state, HedgeState.TCross_N);
		else if(n && e && s && !w)
			return getNextState(state, HedgeState.TCross_E);
		else if(!n && e && s && w)
			return getNextState(state, HedgeState.TCross_S);
		else if(n && !e && s && w)
			return getNextState(state, HedgeState.TCross_W);
		
		else if(!e && !w && (n || s))
			return getNextState(state, HedgeState.Straight_X);
		else if(!n && !s && (e || w))
			return getNextState(state, HedgeState.Straight_Z);
		
		else if(n && e && !s && !w)
			return getNextState(state, HedgeState.Corner_NE);
		else if(n && !e && !s && w)
			return getNextState(state, HedgeState.Corner_NW);
		else if(!n && e && s && !w)
			return getNextState(state, HedgeState.Corner_SE);
		else if(!n && !e && s && w)
			return getNextState(state, HedgeState.Corner_SW);
		
		return this.defaultBlockState();
	}
	
	private BlockState getNextState(BlockState state, HedgeState shape) {
		return state.setValue(STATE, shape);
	}
	
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext c) {
		return getState(this.defaultBlockState(), c.getLevel(), c.getClickedPos()).setValue(WATERLOGGED, 
				Boolean.valueOf(c.getLevel().getFluidState(c.getClickedPos()).getType() == Fluids.WATER));
	}
	
	@Override
	public FluidState getFluidState(BlockState state) {
		return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
	}

	@Override
	public boolean placeLiquid(LevelAccessor world, BlockPos pos, BlockState state, FluidState fluidStateIn) {
		return SimpleWaterloggedBlock.super.placeLiquid(world, pos, state, fluidStateIn);
	}

	@Override
	public boolean canPlaceLiquid(BlockGetter world, BlockPos pos, BlockState state, Fluid fluidIn) {
		return SimpleWaterloggedBlock.super.canPlaceLiquid(world, pos, state, fluidIn);
	}

	@Override
	public boolean isPathfindable(BlockState state, BlockGetter world, BlockPos pos, PathComputationType type) {
		switch (type) {
		case LAND:
			return false;
		case WATER:
			return world.getFluidState(pos).is(FluidTags.WATER);
		case AIR:
			return false;
		default:
			return false;
		}
	}
	
	@Override
	public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn,
			BlockPos currentPos, BlockPos facingPos) {
		if(stateIn.getValue(WATERLOGGED)) {
			worldIn.m_186469_(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(worldIn));
		}
		return getState(stateIn, worldIn, currentPos);
	}
}
