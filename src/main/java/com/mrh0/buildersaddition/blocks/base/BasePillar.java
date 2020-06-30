package com.mrh0.buildersaddition.blocks.base;

import com.mrh0.buildersaddition.state.PillarState;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

public class BasePillar extends BaseDerivativeBlock implements IWaterLoggable {
	
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	public static final EnumProperty<PillarState> STATE = EnumProperty.<PillarState>create("state", PillarState.class);
	protected static final AxisAlignedBB PILLAR = new AxisAlignedBB(2D/16D, 0.0D, 2D/16D, 14D/16D, 1D, 14D/16D);
	protected static final AxisAlignedBB PILLAR_NOT_CONNECTED = new AxisAlignedBB(1D/16D, 0.0D, 1D/16D, 15D/16D, 1D, 15D/16D);
	protected static final AxisAlignedBB PILLAR_BOTTOM = new AxisAlignedBB(1D/16D, 0.0D, 1D/16D, 15D/16D, 2D/16D, 15D/16D);
	protected static final AxisAlignedBB PILLAR_TOP = new AxisAlignedBB(1D/16D, 14D/16D, 1D/16D, 15D/16D, 1D, 15D/16D);
	
	private final int light;
	private final IConnects iconnect;

	public BasePillar(String name, Block source, int light, IConnects connects) {
		super("cut_" + name + "_pillar", source);
		this.light = light;
		this.setDefaultState(
				this.getDefaultState().with(STATE, PillarState.Both).with(WATERLOGGED, Boolean.valueOf(false)));
		this.iconnect = connects;
	}
	
	public BasePillar(String name, Block source, IConnects connects) {
		this(name, source, 0, connects);
	}
	
	public BasePillar(String name, Block source, int light) {
		this(name, source, (state, b) -> state.getBlock() == b);
	}
	
	public BasePillar(String name, Block source) {
		this(name, source, 0);
	}
	
	@Override
	public int getLightValue(BlockState state, IBlockReader world, BlockPos pos) {
		return (state.get(STATE) == PillarState.Top) ? light : 0;
	}
	
	@Override
	protected void fillStateContainer(Builder<Block, BlockState> builder) {
		builder.add(STATE, WATERLOGGED);
	}
	
	public VoxelShape getShape(BlockState state) {
		switch(state.get(STATE)) {
			case None:
				return VoxelShapes.create(PILLAR);
			case Top:
				return VoxelShapes.or(VoxelShapes.create(PILLAR), VoxelShapes.create(PILLAR_TOP));
			case Bottom:
				return VoxelShapes.or(VoxelShapes.create(PILLAR), VoxelShapes.create(PILLAR_BOTTOM));
			case Both:
				return VoxelShapes.create(PILLAR_NOT_CONNECTED);
		}
			
        return VoxelShapes.create(PILLAR);
    }
	
	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return getShape(state);
	}
	
	public BlockState getState(BlockState state, IWorld worldIn, BlockPos pos) {
		BlockState bstop = worldIn.getBlockState(pos.up());
		BlockState bsbottom = worldIn.getBlockState(pos.down());
		boolean top = !connects(bstop, this);
		boolean bottom = !connects(bsbottom, this);
		
		if(top && bottom) {
			return state.with(STATE, PillarState.Both);
		}
		else if(top) {
			return state.with(STATE, PillarState.Top);
		}
		else if(bottom) {
			return state.with(STATE, PillarState.Bottom);
		}
		return state.with(STATE, PillarState.None);
	}
	
	public BlockState getState(BlockState state, IWorldReader worldIn, BlockPos pos) {
		return getState(state, (IWorld)worldIn, pos);
	}
	
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext c) {
		/*BlockPos blockpos = c.getPos();
		PillarState p = getState(this.getDefaultState(), c.getWorld(), blockpos).get(STATE);
		BlockState cur = c.getWorld().getBlockState(blockpos);
		if(cur.getBlock() instanceof BasePillar)
			p = cur.get(STATE);
		

		FluidState ifluidstate = c.getWorld().getFluidState(blockpos);
		BlockState blockstate1 = this.getDefaultState().with(STATE, p).with(WATERLOGGED,
				Boolean.valueOf(ifluidstate.getFluid() == Fluids.WATER));*/
		return getState(this.getDefaultState(), c.getWorld(), c.getPos()).with(WATERLOGGED, 
				Boolean.valueOf(c.getWorld().getFluidState(c.getPos()).getFluid() == Fluids.WATER));
	}
	
	public boolean connects(BlockState state, Block b) {
		return iconnect.connect(state, b);
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
