package com.mrh0.buildersaddition.blocks;

import javax.annotation.Nullable;

import com.mrh0.buildersaddition.blocks.base.BaseDerivativeBlock;
import com.mrh0.buildersaddition.event.BlockRegistry;
import com.mrh0.buildersaddition.event.opts.BlockOptions;
import com.mrh0.buildersaddition.state.VerticalSlabState;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraftforge.common.ToolType;

public class VerticalSlab extends BaseDerivativeBlock implements IWaterLoggable {
	public static final EnumProperty<VerticalSlabState> TYPE = EnumProperty.<VerticalSlabState>create("type",
			VerticalSlabState.class);
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

	protected static final VoxelShape WEST_SHAPE = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 8.0D, 16.0D, 16.0D);
	protected static final VoxelShape EAST_SHAPE = Block.makeCuboidShape(8D, 0.0D, 0.0D, 16D, 16.0D, 16.0D);
	protected static final VoxelShape NORTH_SHAPE = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16D, 16D, 8D);
	protected static final VoxelShape SOUTH_SHAPE = Block.makeCuboidShape(0.0D, 0.0D, 8D, 16D, 16D, 16D);

	public VerticalSlab(String name, Block source) {
		super(name + "_vertical_slab", source);
		this.setDefaultState(
				this.getDefaultState().with(TYPE, VerticalSlabState.NORTH).with(WATERLOGGED, Boolean.valueOf(false)));
	}
	
	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(TYPE, WATERLOGGED);
	}
	
	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		VerticalSlabState slabtype = state.get(TYPE);
		switch (slabtype) {
		case DOUBLEX:
			return VoxelShapes.fullCube();
		case DOUBLEZ:
			return VoxelShapes.fullCube();
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

	

	private VerticalSlabState getDoubleState(VerticalSlabState s) {
		if (s == VerticalSlabState.EAST || s == VerticalSlabState.WEST) {
			return VerticalSlabState.DOUBLEX;
		}
		return VerticalSlabState.DOUBLEZ;
	}

	@Override
	@Nullable
	public BlockState getStateForPlacement(BlockItemUseContext c) {
		BlockPos blockpos = c.getPos();
		BlockState blockstate = c.getWorld().getBlockState(blockpos);
		if (blockstate.getBlock() == this) {
			return blockstate.with(TYPE, getDoubleState(blockstate.get(TYPE))).with(WATERLOGGED,
					Boolean.valueOf(false));
		} else {
			FluidState ifluidstate = c.getWorld().getFluidState(blockpos);
			BlockState blockstate1 = this.getDefaultState().with(TYPE, VerticalSlabState.NORTH).with(WATERLOGGED,
					Boolean.valueOf(ifluidstate.getFluid() == Fluids.WATER));
			Direction face = c.getFace();
			
			double flagdx = c.getHitVec().x - (double) c.getPos().getX() - .5d;
			double flagdz = c.getHitVec().z - (double) c.getPos().getZ() - .5d;

			VerticalSlabState vss = VerticalSlabState.forFacings(face, c.getPlacementHorizontalFacing());
			
			if (flagdz > 0 && difg(flagdz, flagdx))
				vss = VerticalSlabState.SOUTH;
			if (flagdz < 0 && difg(flagdz, flagdx))
				vss = VerticalSlabState.NORTH;
			if (flagdx > 0 && difg(flagdx, flagdz))
				vss = VerticalSlabState.EAST;
			if (flagdx < 0 && difg(flagdx, flagdz))
				vss = VerticalSlabState.WEST;
			
			return blockstate1.with(TYPE, vss);
		}
	}
	
	private boolean difg(double d1, double d2) {
		return Math.abs(d1) > Math.abs(d2);
	}

	@Override
	public boolean isReplaceable(BlockState state, BlockItemUseContext c) {
		ItemStack itemstack = c.getItem();
		VerticalSlabState slabtype = state.get(TYPE);
		if (slabtype != VerticalSlabState.DOUBLEX && slabtype != VerticalSlabState.DOUBLEZ
				&& itemstack.getItem() == this.asItem()) {
			if (c.replacingClickedOnBlock()) {
				if (c.getFace() == Direction.NORTH && slabtype == VerticalSlabState.SOUTH)
					return true;
				if (c.getFace() == Direction.EAST && slabtype == VerticalSlabState.WEST)
					return true;
				if (c.getFace() == Direction.SOUTH && slabtype == VerticalSlabState.NORTH)
					return true;
				if (c.getFace() == Direction.WEST && slabtype == VerticalSlabState.EAST)
					return true;
				return false;
			} else {
				return true;
			}
		} else {
			return false;
		}
	}

	@Override
	public FluidState getFluidState(BlockState state) {
		return state.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : super.getFluidState(state);
	}

	@Override
	public boolean receiveFluid(IWorld worldIn, BlockPos pos, BlockState state, FluidState fluidStateIn) {
		return (state.get(TYPE) != VerticalSlabState.DOUBLEX && state.get(TYPE) != VerticalSlabState.DOUBLEZ)
				? IWaterLoggable.super.receiveFluid(worldIn, pos, state, fluidStateIn)
				: false;
	}

	@Override
	public boolean canContainFluid(IBlockReader worldIn, BlockPos pos, BlockState state, Fluid fluidIn) {
		return (state.get(TYPE) != VerticalSlabState.DOUBLEX && state.get(TYPE) != VerticalSlabState.DOUBLEZ)
				? IWaterLoggable.super.canContainFluid(worldIn, pos, state, fluidIn)
				: false;
	}

	@Override
	public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn,
			BlockPos currentPos, BlockPos facingPos) {
		if (stateIn.get(WATERLOGGED)) {
			worldIn.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));
		}
		return stateIn;
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
