package com.mrh0.buildersaddition.blocks;

import java.util.Random;
import javax.annotation.Nullable;
import com.mrh0.buildersaddition.blocks.base.BaseBlock;
import com.mrh0.buildersaddition.tileentity.CounterTileEntity;
import com.mrh0.buildersaddition.tileentity.CupboardTileEntity;
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
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
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
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class Cupboard extends BaseBlock implements SimpleWaterloggedBlock, EntityBlock {

	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;
	public static final BooleanProperty MIRROR = BooleanProperty.create("mirror");
	public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

	private static VoxelShape SHAPE_NORTH_LOWER = Block.box(0d, 0d, 1d, 16d, 32d, 16d);
	private static VoxelShape SHAPE_EAST_LOWER = Block.box(0d, 0d, 0d, 15d, 32d, 16d);
	private static VoxelShape SHAPE_SOUTH_LOWER = Block.box(0d, 0d, 0d, 16d, 32d, 15d);
	private static VoxelShape SHAPE_WEST_LOWER = Block.box(1d, 0d, 0d, 16d, 32d, 16d);
	
	private static VoxelShape SHAPE_NORTH_UPPER = Block.box(0d, -16d, 1d, 16d, 16d, 16d);
	private static VoxelShape SHAPE_EAST_UPPER = Block.box(0d, -16d, 0d, 15d, 16d, 16d);
	private static VoxelShape SHAPE_SOUTH_UPPER = Block.box(0d, -16d, 0d, 16d, 16d, 15d);
	private static VoxelShape SHAPE_WEST_UPPER = Block.box(1d, -16d, 0d, 16d, 16d, 16d);

	public Cupboard(String name, Block source) {
		super("cupboard_" + name, Properties.copy(source));
		this.registerDefaultState(this.defaultBlockState().setValue(WATERLOGGED, false).setValue(HALF, DoubleBlockHalf.LOWER).setValue(FACING, Direction.NORTH));
	}
	
	@Override
	public PushReaction getPistonPushReaction(BlockState p_60584_) {
		return PushReaction.BLOCK;
	}
	
	@Nullable
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		BlockPos blockpos = context.getClickedPos();
		if (blockpos.getY() < 255 && context.getLevel().getBlockState(blockpos.above()).canBeReplaced(context)) {
			Level world = context.getLevel();
			boolean flag = world.hasNeighborSignal(blockpos) || world.hasNeighborSignal(blockpos.above());
			return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite())
					.setValue(HALF, DoubleBlockHalf.LOWER).setValue(MIRROR, context.isSecondaryUseActive())
					.setValue(WATERLOGGED, Boolean.valueOf(context.getLevel().getFluidState(context.getClickedPos()).getType() == Fluids.WATER));
		} 
		return null;
	}

	@Override
	public void setPlacedBy(Level world, BlockPos pos, BlockState state, LivingEntity end,
			ItemStack stack) {
		world.setBlock(pos.above(), state.setValue(HALF, DoubleBlockHalf.UPPER).setValue(WATERLOGGED, Boolean.valueOf(world.getFluidState(pos.above()).getType() == Fluids.WATER)), 3);
	}
	
	public boolean isValidPosition(BlockState state, BlockGetter worldIn, BlockPos pos) {
		BlockPos blockpos = pos.below();
		BlockState blockstate = worldIn.getBlockState(blockpos);
		return state.getValue(HALF) == DoubleBlockHalf.LOWER ? true : blockstate.is(this);
	}

	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
		builder.add(FACING, WATERLOGGED, HALF, MIRROR);
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

	private VoxelShape getShapeForDirection(Direction d, boolean b) {
		switch (d) {
			case NORTH:
				return b ? SHAPE_NORTH_UPPER : SHAPE_NORTH_LOWER;
			case SOUTH:
				return b ? SHAPE_SOUTH_UPPER : SHAPE_SOUTH_LOWER;
			case EAST:
				return b ? SHAPE_EAST_UPPER : SHAPE_EAST_LOWER;
			case WEST:
				return b ? SHAPE_WEST_UPPER : SHAPE_WEST_LOWER;
			default:
				break;
		}
		return SHAPE_NORTH_LOWER;
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
		return getShapeForDirection(state.getValue(FACING), state.getValue(HALF) == DoubleBlockHalf.UPPER);
	}

	@Override
	public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player,
			InteractionHand hand, BlockHitResult hit) {
		if (world.isClientSide()) {
			return InteractionResult.SUCCESS;
		} else {
			if(state.getValue(HALF) == DoubleBlockHalf.LOWER) {
				if(!Util.accessCheck(world, pos, state.getValue(FACING)) || !Util.accessCheck(world, pos.above(), state.getValue(FACING)))
					return InteractionResult.CONSUME;
				BlockEntity tileentity = world.getBlockEntity(pos);
				if (tileentity instanceof CupboardTileEntity) {
					player.openMenu((CupboardTileEntity) tileentity);
					PiglinAi.angerNearbyPiglins(player, true);
				}
			}
			else {
				if(!Util.accessCheck(world, pos, state.getValue(FACING)) || !Util.accessCheck(world, pos.below(), state.getValue(FACING)))
					return InteractionResult.CONSUME;
				BlockEntity tileentity = world.getBlockEntity(pos.below());
				if (tileentity instanceof CupboardTileEntity) {
					player.openMenu((CupboardTileEntity) tileentity);
					PiglinAi.angerNearbyPiglins(player, true);
				}
			}
			return InteractionResult.CONSUME;
		}
	}

	@Override
	public void onRemove(BlockState state, Level world, BlockPos pos, BlockState newState, boolean isMoving) {
		if (!state.is(newState.getBlock())) {
			CupboardTileEntity tileentity = getTE(state, world, pos);
			
			if(state.getValue(HALF) == DoubleBlockHalf.LOWER) {
				BlockState upper = world.getBlockState(pos.above());
				if(upper.getBlock() == this)
					world.setBlock(pos.above(), getFluidState(upper).getType() == Fluids.WATER ? getFluidState(upper).createLegacyBlock() : Blocks.AIR.defaultBlockState(), 35);
				if (tileentity != null) {
					Containers.dropContents(world, pos, (Container) tileentity);
					world.updateNeighbourForOutputSignal(pos, this);
					world.updateNeighbourForOutputSignal(pos.above(), this);
				}
			}
			else {
				BlockState lower = world.getBlockState(pos.below());
				if(lower.getBlock() == this)
					world.setBlock(pos.below(), getFluidState(lower).getType() == Fluids.WATER ? getFluidState(lower).createLegacyBlock() : Blocks.AIR.defaultBlockState(), 35);
				if (tileentity != null) {
					Containers.dropContents(world, pos.below(), (Container) tileentity);
					world.updateNeighbourForOutputSignal(pos, this);
					world.updateNeighbourForOutputSignal(pos.below(), this);
				}
			}
			super.onRemove(state, world, pos, newState, isMoving);
		}
	}

	@Override
	public void tick(BlockState state, ServerLevel worldIn, BlockPos pos, Random rand) {
		if(state.getValue(HALF) == DoubleBlockHalf.UPPER)
			return;
		CupboardTileEntity tileentity = getTE(state, worldIn, pos);
		if (tileentity != null) {
			tileentity.invTick();
		}

	}
	
	private CupboardTileEntity getTE(BlockState state, Level world, BlockPos pos) {
		BlockEntity tileentity;
		if(state.getValue(HALF) == DoubleBlockHalf.LOWER)
			tileentity = world.getBlockEntity(pos);
		else
			tileentity = world.getBlockEntity(pos.below());
		if (tileentity != null && tileentity instanceof CupboardTileEntity)
			return (CupboardTileEntity) tileentity;
		return null;
	}

	@Override
	public RenderShape getRenderShape(BlockState p_60550_) {
		return RenderShape.MODEL;
	}
	
	@Override
	public boolean triggerEvent(BlockState state, Level world, BlockPos pos, int id, int param) {
		super.triggerEvent(state, world, pos, id, param);
		BlockEntity tileentity = world.getBlockEntity(pos);
		return tileentity == null ? false : tileentity.triggerEvent(id, param);
	}

	/*@Override
	@Nullable
	public INamedContainerProvider getContainer(BlockState state, World worldIn, BlockPos pos) {
		TileEntity tileentity = worldIn.getTileEntity(pos);
		return tileentity instanceof INamedContainerProvider ? (INamedContainerProvider) tileentity : null;
	}*/
	
	
	@Override
	public int getAnalogOutputSignal(BlockState state, Level world, BlockPos pos) {
		if(state.getValue(HALF) == DoubleBlockHalf.LOWER)
			return IComparetorOverride.getComparetorOverride(world, pos);
		else
			return IComparetorOverride.getComparetorOverride(world, pos.below());
	}
	
	@Override
	public boolean hasAnalogOutputSignal(BlockState p_60457_) {
		return true;
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new CupboardTileEntity(pos, state);
	}
}

