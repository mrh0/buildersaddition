package com.mrh0.buildersaddition.blocks.base;

import javax.annotation.Nullable;
import com.mrh0.buildersaddition.event.BlockRegistry;
import com.mrh0.buildersaddition.event.opts.BlockOptions;
import com.mrh0.buildersaddition.state.VerticalSlabState;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
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
import net.minecraftforge.common.ToolType;

public class BaseVerticalSlab extends BaseDerivativeBlock implements SimpleWaterloggedBlock {
	public static final EnumProperty<VerticalSlabState> TYPE = EnumProperty.<VerticalSlabState>create("type",
			VerticalSlabState.class);
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

	protected static final VoxelShape WEST_SHAPE = Block.box(0.0D, 0.0D, 0.0D, 8.0D, 16.0D, 16.0D);
	protected static final VoxelShape EAST_SHAPE = Block.box(8D, 0.0D, 0.0D, 16D, 16.0D, 16.0D);
	protected static final VoxelShape NORTH_SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16D, 16D, 8D);
	protected static final VoxelShape SOUTH_SHAPE = Block.box(0.0D, 0.0D, 8D, 16D, 16D, 16D);

	public BaseVerticalSlab(String name, Block source) {
		super(name + "_vertical_slab", source);
		this.registerDefaultState(
				this.defaultBlockState().setValue(TYPE, VerticalSlabState.NORTH).setValue(WATERLOGGED, Boolean.valueOf(false)));
	}
	
	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
		builder.add(TYPE, WATERLOGGED);
	}
	
	@Override
	public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
		VerticalSlabState slabtype = state.getValue(TYPE);
		switch (slabtype) {
		case DOUBLEX:
			return Shapes.block();
		case DOUBLEZ:
			return Shapes.block();
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
	public BlockState getStateForPlacement(BlockPlaceContext c) {
		BlockPos blockpos = c.getClickedPos();
		BlockState blockstate = c.getLevel().getBlockState(blockpos);
		if (blockstate.getBlock() == this) {
			return blockstate.setValue(TYPE, getDoubleState(blockstate.getValue(TYPE))).setValue(WATERLOGGED,
					Boolean.valueOf(false));
		} else {
			FluidState ifluidstate = c.getLevel().getFluidState(blockpos);
			BlockState blockstate1 = this.defaultBlockState().setValue(TYPE, VerticalSlabState.NORTH).setValue(WATERLOGGED,
					Boolean.valueOf(ifluidstate.getType() == Fluids.WATER));
			Direction face = c.getClickedFace();
			
			double flagdx = c.getClickLocation().x - (double) c.getClickedPos().getX() - .5d;
			double flagdz = c.getClickLocation().z - (double) c.getClickedPos().getZ() - .5d;

			VerticalSlabState vss = VerticalSlabState.forFacings(face, c.getHorizontalDirection());
			
			if (flagdz > 0 && difg(flagdz, flagdx))
				vss = VerticalSlabState.SOUTH;
			if (flagdz < 0 && difg(flagdz, flagdx))
				vss = VerticalSlabState.NORTH;
			if (flagdx > 0 && difg(flagdx, flagdz))
				vss = VerticalSlabState.EAST;
			if (flagdx < 0 && difg(flagdx, flagdz))
				vss = VerticalSlabState.WEST;
			
			return blockstate1.setValue(TYPE, vss);
		}
	}
	
	private boolean difg(double d1, double d2) {
		return Math.abs(d1) > Math.abs(d2);
	}
	
	@Override
	public boolean canBeReplaced(BlockState state, BlockPlaceContext c) {
		ItemStack itemstack = c.getItemInHand();
		VerticalSlabState slabtype = state.getValue(TYPE);
		if (slabtype != VerticalSlabState.DOUBLEX && slabtype != VerticalSlabState.DOUBLEZ
				&& itemstack.getItem() == this.asItem()) {
			if (c.replacingClickedOnBlock()) {
				if (c.getClickedFace() == Direction.NORTH && slabtype == VerticalSlabState.SOUTH)
					return true;
				if (c.getClickedFace() == Direction.EAST && slabtype == VerticalSlabState.WEST)
					return true;
				if (c.getClickedFace() == Direction.SOUTH && slabtype == VerticalSlabState.NORTH)
					return true;
				if (c.getClickedFace() == Direction.WEST && slabtype == VerticalSlabState.EAST)
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
		return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
	}
	
	@Override
	public boolean placeLiquid(LevelAccessor world, BlockPos pos, BlockState state, FluidState fluidStateIn) {
		return (state.getValue(TYPE) != VerticalSlabState.DOUBLEX && state.getValue(TYPE) != VerticalSlabState.DOUBLEZ)
				? SimpleWaterloggedBlock.super.placeLiquid(world, pos, state, fluidStateIn)
				: false;
	}

	@Override
	public boolean canPlaceLiquid(BlockGetter world, BlockPos pos, BlockState state, Fluid fluidIn) {
		return (state.getValue(TYPE) != VerticalSlabState.DOUBLEX && state.getValue(TYPE) != VerticalSlabState.DOUBLEZ)
				? SimpleWaterloggedBlock.super.canPlaceLiquid(world, pos, state, fluidIn)
				: false;
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
	public BlockState updateShape(BlockState state, Direction dir, BlockState newState, LevelAccessor world,
			BlockPos currentPos, BlockPos otherPos) {
		if (state.getValue(WATERLOGGED)) {
			world.getLiquidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(world));
		}
		return state;
	}
}
