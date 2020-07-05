package com.mrh0.buildersaddition.blocks;

import java.util.Random;
import javax.annotation.Nullable;
import com.mrh0.buildersaddition.blocks.base.BaseDerivativeBlock;
import com.mrh0.buildersaddition.tileentity.BedsideTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.piglin.PiglinTasks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.FluidTags;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class BedsideTable extends BaseDerivativeBlock implements IWaterLoggable, ITileEntityProvider {

	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	public static final DirectionProperty FACING = DirectionProperty.create("facing",
			p -> p.getIndex() > 1 && p.getIndex() < Direction.values().length);

	private static VoxelShape SHAPE_COUNTERTOP = Block.makeCuboidShape(0d, 15d, 0d, 16d, 16d, 16d);
	private static VoxelShape SHAPE_NORTH = Block.makeCuboidShape(0d, 0d, 1d, 16d, 16d, 16d);
	private static VoxelShape SHAPE_EAST = Block.makeCuboidShape(0d, 0d, 0d, 15d, 16d, 16d);
	private static VoxelShape SHAPE_SOUTH = Block.makeCuboidShape(0d, 0d, 0d, 16d, 16d, 15d);
	private static VoxelShape SHAPE_WEST = Block.makeCuboidShape(1d, 0d, 0d, 16d, 16d, 16d);

	public BedsideTable(String name, Block source) {
		super("counter_" + name, source);
	}

	@Override
	protected void fillStateContainer(Builder<Block, BlockState> builder) {
		builder.add(FACING, WATERLOGGED);
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext c) {
		return this.getDefaultState().with(FACING, c.getPlacementHorizontalFacing().getOpposite()).with(WATERLOGGED,
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
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return VoxelShapes.or(SHAPE_COUNTERTOP, getShapeForDirection(state.get(FACING)));
	}

	@Override
	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player,
			Hand handIn, BlockRayTraceResult hit) {
		if (worldIn.isRemote) {
			return ActionResultType.SUCCESS;
		} else {
			BlockState front = worldIn.getBlockState(pos.offset(state.get(FACING)));
			if(front.isSolid())
				return ActionResultType.CONSUME;
			TileEntity tileentity = worldIn.getTileEntity(pos);
			if (tileentity instanceof BedsideTileEntity) {
				player.openContainer((BedsideTileEntity) tileentity);
				PiglinTasks.func_234478_a_(player, true);
			}

			return ActionResultType.CONSUME;
		}
	}

	@Override
	public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
		if (!state.isIn(newState.getBlock())) {
			TileEntity tileentity = worldIn.getTileEntity(pos);
			if (tileentity instanceof IInventory) {
				InventoryHelper.dropInventoryItems(worldIn, pos, (IInventory) tileentity);
				worldIn.updateComparatorOutputLevel(pos, this);
			}

			super.onReplaced(state, worldIn, pos, newState, isMoving);
		}
	}

	@Override
	public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
		TileEntity tileentity = worldIn.getTileEntity(pos);
		if (tileentity instanceof BedsideTileEntity) {
			((BedsideTileEntity) tileentity).invTick();
		}

	}

	@Override
	public BlockRenderType getRenderType(BlockState state) {
		return BlockRenderType.MODEL;
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer,
			ItemStack stack) {
		if (stack.hasDisplayName()) {
			TileEntity tileentity = worldIn.getTileEntity(pos);
			if (tileentity instanceof BedsideTileEntity) {
				((BedsideTileEntity) tileentity).setCustomName(stack.getDisplayName());
			}
		}

	}

	@Override
	public boolean eventReceived(BlockState state, World worldIn, BlockPos pos, int id, int param) {
		super.eventReceived(state, worldIn, pos, id, param);
		TileEntity tileentity = worldIn.getTileEntity(pos);
		return tileentity == null ? false : tileentity.receiveClientEvent(id, param);
	}

	@Override
	@Nullable
	public INamedContainerProvider getContainer(BlockState state, World worldIn, BlockPos pos) {
		TileEntity tileentity = worldIn.getTileEntity(pos);
		return tileentity instanceof INamedContainerProvider ? (INamedContainerProvider) tileentity : null;
	}
	
	@Override
	@Nullable
	public TileEntity createNewTileEntity(IBlockReader worldIn) {
		return new BedsideTileEntity();
	}
}