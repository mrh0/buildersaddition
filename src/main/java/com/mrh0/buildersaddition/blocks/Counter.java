package com.mrh0.buildersaddition.blocks;

import com.mrh0.buildersaddition.blocks.base.BaseDerivativeBlock;
import com.mrh0.buildersaddition.tileentity.CounterTileEntity;
import com.mrh0.buildersaddition.util.IComparetorOverride;
import com.mrh0.buildersaddition.util.Util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.Container;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class Counter extends BaseDerivativeBlock implements SimpleWaterloggedBlock, EntityBlock {

	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

	private static VoxelShape SHAPE_COUNTERTOP = Block.box(0d, 15d, 0d, 16d, 16d, 16d);
	private static VoxelShape SHAPE_NORTH = Block.box(0d, 0d, 1d, 16d, 16d, 16d);
	private static VoxelShape SHAPE_EAST = Block.box(0d, 0d, 0d, 15d, 16d, 16d);
	private static VoxelShape SHAPE_SOUTH = Block.box(0d, 0d, 0d, 16d, 16d, 15d);
	private static VoxelShape SHAPE_WEST = Block.box(1d, 0d, 0d, 16d, 16d, 16d);

	public Counter(String name, Block source) {
		super("counter_" + name, source);
	}

	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
		builder.add(FACING, WATERLOGGED);
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext c) {
		return this.defaultBlockState().setValue(FACING, c.getHorizontalDirection().getOpposite()).setValue(WATERLOGGED,
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

	private VoxelShape getShapeForDirection(Direction d) {
		switch (d) {
		case NORTH:
			return SHAPE_NORTH;
		case SOUTH:
			return SHAPE_SOUTH;
		case EAST:
			return SHAPE_EAST;
		case WEST:
			return SHAPE_WEST;
		default:
			break;
		}
		return SHAPE_NORTH;
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
		return Shapes.or(SHAPE_COUNTERTOP, getShapeForDirection(state.getValue(FACING)));
	}

	@Override
	public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player,
			InteractionHand hand, BlockHitResult hit) {
		if (world.isClientSide()) {
			return InteractionResult.SUCCESS;
		} else {
			//BlockState front = worldIn.getBlockState(pos.offset(state.get(FACING)));
			//if(front.isSolid())
			if(!Util.accessCheck(world, pos, state.getValue(FACING)))
				return InteractionResult.CONSUME;
			BlockEntity tileentity = world.getBlockEntity(pos);
			if (tileentity instanceof CounterTileEntity) {
				player.openMenu((CounterTileEntity) tileentity);
				PiglinAi.angerNearbyPiglins(player, true);
			}

			return InteractionResult.CONSUME;
		}
	}

	@Override
	public void onRemove(BlockState state, Level world, BlockPos pos, BlockState newState, boolean isMoving) {
		if (!state.is(newState.getBlock())) {
			BlockEntity tileentity = world.getBlockEntity(pos);
			if (tileentity instanceof Container) {
				Containers.dropContents(world, pos, (Container) tileentity);
				world.updateNeighbourForOutputSignal(pos, this);
			}
		}
		super.onRemove(state, world, pos, newState, isMoving);
	}

	/*@Override
	public void tick(BlockState state, ServerLevel worldIn, BlockPos pos, Random rand) {
		BlockEntity tileentity = worldIn.getBlockEntity(pos);
		if (tileentity instanceof CounterTileEntity) {
			((CounterTileEntity) tileentity).invTick();
		}

	}*/

	@Override
	public RenderShape getRenderShape(BlockState p_60550_) {
		return RenderShape.MODEL;
	}
	
	@Override
	public void setPlacedBy(Level world, BlockPos pos, BlockState state, LivingEntity ent,
			ItemStack stack) {
		if (stack.hasCustomHoverName()) {
			BlockEntity tileentity = world.getBlockEntity(pos);
			if (tileentity instanceof CounterTileEntity) {
				((CounterTileEntity) tileentity).setCustomName(stack.getDisplayName());
			}
		}
	}
	
	@Override
	public boolean triggerEvent(BlockState state, Level world, BlockPos pos, int id, int param) {
		super.triggerEvent(state, world, pos, id, param);
		BlockEntity tileentity = world.getBlockEntity(pos);
		return tileentity == null ? false : tileentity.triggerEvent(id, param);
	}

	
	// 1.17 ? TODO
	/*@Override
	@Nullable
	public INamedContainerProvider getContainer(BlockState state, World worldIn, BlockPos pos) {
		TileEntity tileentity = worldIn.getTileEntity(pos);
		return tileentity instanceof INamedContainerProvider ? (INamedContainerProvider) tileentity : null;
	}*/
	
	@Override
	public int getAnalogOutputSignal(BlockState state, Level world, BlockPos pos) {
		return IComparetorOverride.getComparetorOverride(world, pos);
	}
	
	@Override
	public boolean hasAnalogOutputSignal(BlockState p_60457_) {
		return super.hasAnalogOutputSignal(p_60457_);
	}
	
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new CounterTileEntity(pos, state);
	}
}
