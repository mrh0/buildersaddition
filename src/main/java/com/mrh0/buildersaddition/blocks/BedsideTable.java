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

	protected static final VoxelShape SHAPE_BASE = Block.makeCuboidShape(0d, 14d, 0d, 16d, 16d, 16d);
	protected static final VoxelShape SHAPE_NW = Block.makeCuboidShape(1d, 0d, 1d, 3d, 14d, 3d);
	protected static final VoxelShape SHAPE_NE = Block.makeCuboidShape(13d, 0d, 1d, 15d, 14d, 3d);
	protected static final VoxelShape SHAPE_SW = Block.makeCuboidShape(1d, 0d, 13d, 3d, 14d, 15d);
	protected static final VoxelShape SHAPE_SE = Block.makeCuboidShape(13d, 0d, 13d, 15d, 14d, 15d);
	
	protected static final VoxelShape SHAPE_BOX_X = Block.makeCuboidShape(1d, 8d, 3d, 15d, 16d, 13d);
	protected static final VoxelShape SHAPE_BOX_Z = Block.makeCuboidShape(3d, 8d, 1d, 13d, 16d, 15d);
	
	protected static final VoxelShape SHAPE_X = VoxelShapes.or(SHAPE_BASE, SHAPE_NW, SHAPE_NE, SHAPE_SW, SHAPE_SE, SHAPE_BOX_X);
	protected static final VoxelShape SHAPE_Z = VoxelShapes.or(SHAPE_BASE, SHAPE_NW, SHAPE_NE, SHAPE_SW, SHAPE_SE, SHAPE_BOX_Z);

	public BedsideTable(String name, Block source) {
		super("bedside_table_" + name, source);
	}

	@Override
	protected void fillStateContainer(Builder<Block, BlockState> builder) {
		builder.add(FACING, WATERLOGGED);
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext c) {
		return this.getDefaultState().with(FACING, c.getPlacementHorizontalFacing()).with(WATERLOGGED,
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
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return getShapeForDirection(state.get(FACING));
	}

	@Override
	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player,
			Hand handIn, BlockRayTraceResult hit) {
		if (worldIn.isRemote) {
			return ActionResultType.SUCCESS;
		} else {
			BlockState front = worldIn.getBlockState(pos.offset(state.get(FACING).getOpposite()));
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