package com.mrh0.buildersaddition.blocks.base;

import com.mrh0.buildersaddition.state.HedgeState;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.common.IForgeShearable;

public class BaseHedgeBlock extends BaseDerivativeBlock implements IWaterLoggable, IForgeShearable {
	
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	public static final EnumProperty<HedgeState> STATE = EnumProperty.<HedgeState>create("state", HedgeState.class);
	private static VoxelShape SHAPE_NONE = Block.makeCuboidShape(4d, 0d, 4d, 12d, 16d, 12d);
	private static VoxelShape SHAPE_STRAIGHT_Z = Block.makeCuboidShape(0d, 0d, 4d, 16d, 16d, 12d);
	private static VoxelShape SHAPE_STRAIGHT_X = Block.makeCuboidShape(4d, 0d, 0d, 12d, 16d, 16d);
	
	private static VoxelShape SHAPE_SHORT_N = Block.makeCuboidShape(4d, 0d, 0d, 12d, 16d, 4d);
	private static VoxelShape SHAPE_SHORT_E = Block.makeCuboidShape(12d, 0d, 4d, 16d, 16d, 12d);
	private static VoxelShape SHAPE_SHORT_S = Block.makeCuboidShape(4d, 0d, 12d, 12d, 16d, 16d);
	private static VoxelShape SHAPE_SHORT_W = Block.makeCuboidShape(0d, 0d, 4d, 4d, 16d, 12d);
	
	private static VoxelShape SHAPE_CORNER_NE = VoxelShapes.or(SHAPE_NONE, SHAPE_SHORT_N, SHAPE_SHORT_E);
	private static VoxelShape SHAPE_CORNER_NW = VoxelShapes.or(SHAPE_NONE, SHAPE_SHORT_N, SHAPE_SHORT_W);
	private static VoxelShape SHAPE_CORNER_SE = VoxelShapes.or(SHAPE_NONE, SHAPE_SHORT_S, SHAPE_SHORT_E);
	private static VoxelShape SHAPE_CORNER_SW = VoxelShapes.or(SHAPE_NONE, SHAPE_SHORT_S, SHAPE_SHORT_W);
	
	private static VoxelShape SHAPE_T_N = VoxelShapes.or(SHAPE_STRAIGHT_Z, SHAPE_SHORT_N);
	private static VoxelShape SHAPE_T_E = VoxelShapes.or(SHAPE_STRAIGHT_X, SHAPE_SHORT_E);
	private static VoxelShape SHAPE_T_S = VoxelShapes.or(SHAPE_STRAIGHT_Z, SHAPE_SHORT_S);
	private static VoxelShape SHAPE_T_W = VoxelShapes.or(SHAPE_STRAIGHT_X, SHAPE_SHORT_W);
	
	private static VoxelShape SHAPE_CROSS = VoxelShapes.or(SHAPE_STRAIGHT_X, SHAPE_STRAIGHT_Z);
	
	public BaseHedgeBlock(String name, Block source) {
		super("hedge_" + name, source);
		setDefaultState(getDefaultState().with(WATERLOGGED, false).with(STATE, HedgeState.None));
	}
	
	@Override
	protected void fillStateContainer(Builder<Block, BlockState> builder) {
		builder.add(STATE, WATERLOGGED);
	}
	
	@Override
	public boolean isShearable(ItemStack item, World world, BlockPos pos) {
		return true;
	}
	
	public VoxelShape getShape(BlockState state) {
		switch(state.get(STATE)) {
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
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return getShape(state);
	}
	
	public BlockState getState(BlockState state, IWorld worldIn, BlockPos pos) {
		BlockState bn = worldIn.getBlockState(pos.north());
		BlockState be = worldIn.getBlockState(pos.east());
		BlockState bs = worldIn.getBlockState(pos.south());
		BlockState bw = worldIn.getBlockState(pos.west());
		
		boolean n = bn.getBlock() instanceof BaseHedgeBlock;
		boolean e = be.getBlock() instanceof BaseHedgeBlock;
		boolean s = bs.getBlock() instanceof BaseHedgeBlock;
		boolean w = bw.getBlock() instanceof BaseHedgeBlock;
		
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
		
		return this.getDefaultState();
	}
	
	private BlockState getNextState(BlockState state, HedgeState shape) {
		return state.with(STATE, shape);
	}
	
	public BlockState getState(BlockState state, IWorldReader worldIn, BlockPos pos) {
		return getState(state, (IWorld)worldIn, pos);
	}
	
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext c) {
		return getState(this.getDefaultState(), c.getWorld(), c.getPos()).with(WATERLOGGED, 
				Boolean.valueOf(c.getWorld().getFluidState(c.getPos()).getFluid() == Fluids.WATER));
	}
	
	@Override
	public FluidState getFluidState(BlockState state) {
		return state.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : super.getFluidState(state);
	}

	@Override
	public boolean receiveFluid(IWorld worldIn, BlockPos pos, BlockState state, FluidState fluidStateIn) {
		return IWaterLoggable.super.receiveFluid(worldIn, pos, state, fluidStateIn);
	}

	@Override
	public boolean canContainFluid(IBlockReader worldIn, BlockPos pos, BlockState state, Fluid fluidIn) {
		return IWaterLoggable.super.canContainFluid(worldIn, pos, state, fluidIn);
	}

	@Override
	public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn,
			BlockPos currentPos, BlockPos facingPos) {
		if(stateIn.get(WATERLOGGED)) {
			worldIn.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));
		}
		return getState(stateIn, worldIn, currentPos);
	}
	
	@Override
	public boolean allowsMovement(BlockState state, IBlockReader worldIn, BlockPos pos, PathType type) {
		switch (type) {
		case LAND:
			return false;
		case WATER:
			return worldIn.getFluidState(pos).isTagged(FluidTags.WATER);
		case AIR:
			return false;
		default:
			return false;
		}
	}
}
