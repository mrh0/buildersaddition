package com.mrh0.buildersaddition.blocks;

import java.util.Random;
import javax.annotation.Nullable;
import com.mrh0.buildersaddition.blocks.base.BaseDerivativeBlock;
import com.mrh0.buildersaddition.tileentity.BedsideTileEntity;
import com.mrh0.buildersaddition.util.IComparetorOverride;
import com.mrh0.buildersaddition.util.Util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.Container;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
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

public class BedsideTable extends BaseDerivativeBlock implements SimpleWaterloggedBlock, EntityBlock {

	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

	protected static final VoxelShape SHAPE_BASE = Block.box(0d, 14d, 0d, 16d, 16d, 16d);
	protected static final VoxelShape SHAPE_NW = Block.box(1d, 0d, 1d, 3d, 14d, 3d);
	protected static final VoxelShape SHAPE_NE = Block.box(13d, 0d, 1d, 15d, 14d, 3d);
	protected static final VoxelShape SHAPE_SW = Block.box(1d, 0d, 13d, 3d, 14d, 15d);
	protected static final VoxelShape SHAPE_SE = Block.box(13d, 0d, 13d, 15d, 14d, 15d);
	
	protected static final VoxelShape SHAPE_BOX_X = Block.box(1d, 8d, 3d, 15d, 16d, 13d);
	protected static final VoxelShape SHAPE_BOX_Z = Block.box(3d, 8d, 1d, 13d, 16d, 15d);
	
	protected static final VoxelShape SHAPE_X = Shapes.or(SHAPE_BASE, SHAPE_NW, SHAPE_NE, SHAPE_SW, SHAPE_SE, SHAPE_BOX_X);
	protected static final VoxelShape SHAPE_Z = Shapes.or(SHAPE_BASE, SHAPE_NW, SHAPE_NE, SHAPE_SW, SHAPE_SE, SHAPE_BOX_Z);

	public BedsideTable(String name, Block source) {
		super("bedside_table_" + name, source);
	}
	
	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
		builder.add(FACING, WATERLOGGED);
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext c) {
		return this.defaultBlockState().setValue(FACING, c.getHorizontalDirection()).setValue(WATERLOGGED,
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
			return SHAPE_Z;
		case SOUTH:
			return SHAPE_Z;
		case EAST:
			return SHAPE_X;
		case WEST:
			return SHAPE_X;
		default:
			break;
		}
		return SHAPE_Z;
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
		return getShapeForDirection(state.getValue(FACING));
	}
	
	@Override
	public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
		if (world.isClientSide()) {
			return InteractionResult.SUCCESS;
		} else {
			//BlockState front = worldIn.getBlockState(pos.offset(state.get(FACING).getOpposite()));
			//if(front.isSolid())
			if(!Util.accessCheck(world, pos, state.getValue(FACING).getOpposite()))
				return InteractionResult.CONSUME;
			BlockEntity tileentity = world.getBlockEntity(pos);
			if (tileentity instanceof BedsideTileEntity) {
				player.openMenu((BedsideTileEntity) tileentity);
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
				world.updateNeighborsAt(pos, this);
			}

			super.onRemove(state, world, pos, newState, isMoving);
		}
	}

	/*@Override
	public void tick(BlockState state, ServerLevel worldIn, BlockPos pos, Random rand) {
		BlockEntity tileentity = worldIn.getBlockEntity(pos);
		if (tileentity instanceof BedsideTileEntity) {
			((BedsideTileEntity) tileentity).invTick();
		}

	}*/

	@Override
	public RenderShape getRenderShape(BlockState state) {
		return RenderShape.MODEL;
	}

	@Override
	public void setPlacedBy(Level world, BlockPos pos, BlockState state, LivingEntity ent, ItemStack stack) {
		if (stack.hasCustomHoverName()) {
			BlockEntity tileentity = world.getBlockEntity(pos);
			if (tileentity instanceof BedsideTileEntity) {
				((BedsideTileEntity) tileentity).setCustomName(stack.getDisplayName());
			}
		}
	}

	@Override
	public boolean triggerEvent(BlockState state, Level world, BlockPos pos, int id, int param) {
		super.triggerEvent(state, world, pos, id, param);
		BlockEntity tileentity = world.getBlockEntity(pos);
		return tileentity == null ? false : tileentity.triggerEvent(id, param);
	}
	
	@Override
	public MenuProvider getMenuProvider(BlockState state, Level world, BlockPos pos) {
		BlockEntity tileentity = world.getBlockEntity(pos);
		return tileentity instanceof MenuProvider ? (MenuProvider) tileentity : null;
	}
	
	@Override
	public boolean hasAnalogOutputSignal(BlockState state) {
		return true;
	}
	
	@Override
	public int getAnalogOutputSignal(BlockState state, Level world, BlockPos pos) {
		return IComparetorOverride.getComparetorOverride(world, pos);
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new BedsideTileEntity(pos, state);
	}
}